package tsekhmeistruk.funnycats.activities.dog_activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import tsekhmeistruk.funnycats.AppFunnyPets;
import tsekhmeistruk.funnycats.Constants;
import tsekhmeistruk.funnycats.R;
import tsekhmeistruk.funnycats.activities.FullSizeImageActivity;
import tsekhmeistruk.funnycats.di.component.AppComponent;
import tsekhmeistruk.funnycats.di.component.DaggerPresentersComponent;
import tsekhmeistruk.funnycats.di.module.PresentersModule;
import tsekhmeistruk.funnycats.models.dogs.entities.ImageUrl;
import tsekhmeistruk.funnycats.presenters.dogs_presenter.DogPhotoListPresenter;
import tsekhmeistruk.funnycats.views.dog_views.DogPhotosView;
import tsekhmeistruk.funnycats.widgets.adapters.CategoryListAdapter;
import tsekhmeistruk.funnycats.widgets.adapters.PhotoAdapter;

public class DogsActivity extends AppCompatActivity implements DogPhotosView {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.photo_container)
    GridView photoContainer;

    @Inject
    DogPhotoListPresenter photoListPresenter;

    private LoginButton loginButton;
    private ListView categoryList;

    private PhotoAdapter photoAdapter;

    private NavigationView navigationView;

    private NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.dogs_part_app_name));
        setSupportActionBar(toolbar);

        ((NavigationView) findViewById(R.id.nav_view))
                .addHeaderView(LayoutInflater.from(this)
                        .inflate(R.layout.header_navigation_drawer_dogs, null));

        DaggerPresentersComponent.builder()
                .appComponent(getAppComponent())
                .presentersModule(new PresentersModule())
                .build()
                .inject(this);

        ButterKnife.bind(this);

        // TODO: 31.10.2017 facebook

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        photoAdapter = new PhotoAdapter(getContext());
        photoContainer.setAdapter(photoAdapter);

        loginButton = (LoginButton) navigationView.findViewById(R.id.login_button);
        hideFacebookButton();

        categoryList = (ListView) navigationView.findViewById(R.id.category_list);
        categoryList.setOnItemClickListener((parent, view, position, id) -> {
            clearPhotoList();
            String categoryName = (String) view.getTag();


            loadCategoryByString(categoryName);
            showToast(categoryName);

            drawer.closeDrawer(GravityCompat.START);
        });

        photoListPresenter.setView(this);
        photoListPresenter.loadCategoryList();
        photoListPresenter.loadBulldogPhotoList();
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
        Intent intent = new Intent(DogsActivity.this, FullSizeImageActivity.class);
        intent.putExtra(Constants.IMAGE, photoAdapter.getItem(position));
        intent.putExtra(Constants.USER_ID, Constants.NULL_USER);
        intent.putExtra(Constants.IS_FAVORITE, false);
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
    public void showPhotoList(List<ImageUrl> imageUrls) {
        photoAdapter.addImages(imageUrls);
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategoryList(List<String> categories) {
        CategoryListAdapter categoryListAdapter
                = new CategoryListAdapter(categories, Constants.DOG_ICON);
        categoryList.setAdapter(categoryListAdapter);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return ((AppFunnyPets) getApplication()).appComponent();
    }

    private void clearPhotoList() {
        photoAdapter.clearImages();
        photoAdapter.notifyDataSetChanged();
    }

    private void hideFacebookButton() {
        loginButton.setVisibility(View.INVISIBLE);
    }

    private void loadCategoryByString(String category) { // test temp implementation
        int i = Constants.dogsCategories.indexOf(category);
        switch (i) {
            case 0:
                photoListPresenter.loadBulldogPhotoList();
                break;
            case 1:
                photoListPresenter.loadBoxerPhotoList();
                break;
            case 2:
                photoListPresenter.loadDobermanPhotoList();
                break;
            case 3:
                photoListPresenter.loadLabradorPhotoList();
                break;
            case 4:
                photoListPresenter.loadPoodlePhotoList();
        }
    }

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
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
                    photoListPresenter.loadBulldogPhotoList();
                } else {
                    showToast(getString(R.string.disconnected));
                }
            }
        }

    }

}
