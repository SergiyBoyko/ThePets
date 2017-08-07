package tsekhmeistruk.funnycats.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.Serializable;

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
import tsekhmeistruk.funnycats.models.entities.CategoryList;
import tsekhmeistruk.funnycats.models.entities.ImageList;
import tsekhmeistruk.funnycats.presenters.PhotoListPresenter;
import tsekhmeistruk.funnycats.views.CatPhotosView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        photoAdapter = new PhotoAdapter(getContext());
        photoContainer.setAdapter(photoAdapter);

        // TODO: make elements initialization with ButterKnife
        loginButton = (LoginButton) navigationView.findViewById(R.id.login_button);
        addFacebookLoginButton();

        categoryList = (ListView) navigationView.findViewById(R.id.category_list);
        categoryList.setOnItemClickListener((parent, view, position, id) -> {
            clearPhotoList();
            photoContainer.setOnScrollListener(new ImagesBarScrollListener());

            if (position == 0) {
                categoryName = null;
            } else if (position == 1) {
                photoListPresenter.loadFavouritesPhotoList(userId);
                drawer.closeDrawer(GravityCompat.START);
                return;
            } else {
                categoryName = (String) view.getTag();
            }

            photoListPresenter.loadPhotoList(categoryName);
            drawer.closeDrawer(GravityCompat.START);
        });

        photoListPresenter.setView(this);
        photoListPresenter.loadCategoryList();
        photoListPresenter.loadPhotoList(categoryName);

        photoContainer.setOnScrollListener(new ImagesBarScrollListener());
    }

    @OnItemClick(R.id.photo_container)
    public void startFullSizePhotoActivity(int position) {
        Intent intent = new Intent(CatsActivity.this, FullSizeImageActivity.class);
        intent.putExtra(Constants.IMAGE, (Serializable) photoAdapter.getItem(position));
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
            }

            @Override
            public void onCancel() {
                // TODO: make method for toast showing
                Toast toast = Toast.makeText(getApplicationContext(),
                        R.string.logging_was_canceled, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        R.string.logging_error, Toast.LENGTH_SHORT);
                toast.show();
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
                photoListPresenter.loadPhotoList(categoryName);
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

    }

}
