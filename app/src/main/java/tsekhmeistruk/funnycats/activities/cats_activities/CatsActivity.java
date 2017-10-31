package tsekhmeistruk.funnycats.activities.cats_activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import tsekhmeistruk.funnycats.AppFunnyCats;
import tsekhmeistruk.funnycats.Constants;
import tsekhmeistruk.funnycats.R;
import tsekhmeistruk.funnycats.di.component.AppComponent;
import tsekhmeistruk.funnycats.di.component.DaggerPresentersComponent;
import tsekhmeistruk.funnycats.di.module.PresentersModule;
import tsekhmeistruk.funnycats.models.cats.entities.CategoryList;
import tsekhmeistruk.funnycats.models.cats.entities.ImageList;
import tsekhmeistruk.funnycats.presenters.cats_presenter.PhotoListPresenter;
import tsekhmeistruk.funnycats.views.cats_views.CatPhotosView;
import tsekhmeistruk.funnycats.widgets.adapters.CategoryListAdapter;
import tsekhmeistruk.funnycats.widgets.adapters.PhotoAdapter;

public class CatsActivity extends AppCompatActivity implements CatPhotosView {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.photo_container)
    GridView photoContainer;

    @Inject
    PhotoListPresenter photoListPresenter;

    private LoginButton loginButton;
    private ListView categoryList;

    private CallbackManager callbackManager;

    private PhotoAdapter photoAdapter;

    private String categoryName = null;
    private String userId;
    private boolean isFavorite = false;

    private NavigationView navigationView;

    private NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ((NavigationView) findViewById(R.id.nav_view))
                .addHeaderView(LayoutInflater.from(this)
                        .inflate(R.layout.header_navigation_drawer_cats, null));

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        DaggerPresentersComponent.builder()
                .appComponent(getAppComponent())
                .presentersModule(new PresentersModule())
                .build()
                .inject(this);

        ButterKnife.bind(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        photoAdapter = new PhotoAdapter(getContext());
        photoContainer.setAdapter(photoAdapter);

        loginButton = (LoginButton) navigationView.findViewById(R.id.login_button);
        addFacebookLoginButton();

        categoryList = (ListView) navigationView.findViewById(R.id.category_list);
        categoryList.setOnItemClickListener((parent, view, position, id) -> {
            clearPhotoList();
            photoContainer.setOnScrollListener(new ImagesBarScrollListener());

            if (position == 0) {
                categoryName = null;
                isFavorite = false;
            } else if (position == 1) {
                photoListPresenter.loadFavouritesPhotoList(userId);
                drawer.closeDrawer(GravityCompat.START);
                categoryName = Constants.FAVORITE;
                isFavorite = true;
                return;
            } else {
                categoryName = (String) view.getTag();
                isFavorite = false;
            }

            photoListPresenter.loadPhotoList(categoryName);
            drawer.closeDrawer(GravityCompat.START);
        });

        photoListPresenter.setView(this);
        photoListPresenter.loadCategoryList();
        photoListPresenter.loadPhotoList(categoryName);

        photoContainer.setOnScrollListener(new ImagesBarScrollListener());

        showUserName();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
    }

    @OnItemClick(R.id.photo_container)
    public void startFullSizePhotoActivity(int position) {
        Intent intent = new Intent(CatsActivity.this, FullSizeImageActivity.class);
        intent.putExtra(Constants.IMAGE, photoAdapter.getItem(position));
        intent.putExtra(Constants.USER_ID, userId);
        intent.putExtra(Constants.IS_FAVORITE, isFavorite);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showCategoryList(CategoryList categoryNameList) {
        CategoryListAdapter categoryListAdapter
                = new CategoryListAdapter(categoryNameList.getCategories());
        categoryList.setAdapter(categoryListAdapter);
    }

    @Override
    public void showPhotoList(ImageList imageList) {
        photoAdapter.addImages(imageList.getImageList());
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public AppComponent getAppComponent() {
        return ((AppFunnyCats) getApplication()).appComponent();
    }

    private void clearPhotoList() {
        photoAdapter.clearImages();
        photoAdapter.notifyDataSetChanged();
    }

    private void showUserName() {
        View header = navigationView.getHeaderView(0);
        TextView userName = (TextView) header.findViewById(R.id.user_name);
        TextView logIn = (TextView) header.findViewById(R.id.log_in);
        TextView loggedIs = (TextView) header.findViewById(R.id.logged_in_as);

        Profile profile = Profile.getCurrentProfile();

        if (profile != null) {
            logIn.setVisibility(View.GONE);
            loggedIs.setVisibility(View.VISIBLE);
            userName.setVisibility(View.VISIBLE);
            userName.setText(profile.getName());
        } else {
            logIn.setVisibility(View.VISIBLE);
            loggedIs.setVisibility(View.GONE);
            userName.setVisibility(View.GONE);
        }
    }

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void addFacebookLoginButton() {
        loginButton.setReadPermissions("public_profile");

        try {
            Profile profile = Profile.getCurrentProfile();
            userId = profile.getId();
        } catch (NullPointerException ignored) {
        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                userId = accessToken.getUserId();
                showUserName();
            }

            @Override
            public void onCancel() {
                showUserName();
                showToast(getString(R.string.logging_was_canceled));
            }

            @Override
            public void onError(FacebookException e) {
                showUserName();
                showToast(getString(R.string.logging_error));
            }
        });
    }

    private class ImagesBarScrollListener implements AbsListView.OnScrollListener {

        private int visibleThreshold = 0;
        private int previousTotal = 0;
        private boolean loading = true;

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if ((!loading) &&
                    ((totalItemCount - visibleItemCount) <= (firstVisibleItem - visibleThreshold))) {
                loading = true;
                if (!Constants.FAVORITE.equals(categoryName)) {
                    photoListPresenter.loadPhotoList(categoryName);
                }
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

    }

    private class NetworkChangeReceiver extends BroadcastReceiver {

        public NetworkChangeReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isInitialStickyBroadcast()) {
                final ConnectivityManager connMgr = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                final android.net.NetworkInfo wifi = connMgr
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                final android.net.NetworkInfo mobile = connMgr
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if (wifi.isConnected() || mobile.isConnected()) {
                    photoListPresenter.loadCategoryList();
                    if (Constants.FAVORITE.equals(categoryName)) {
                        photoListPresenter.loadFavouritesPhotoList(userId);
                    } else {
                        photoListPresenter.loadPhotoList(categoryName);
                    }
                } else {
                    showToast(getString(R.string.disconnected));
                }
            }
        }

    }

}
