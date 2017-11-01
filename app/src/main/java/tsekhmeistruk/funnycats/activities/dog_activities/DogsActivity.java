package tsekhmeistruk.funnycats.activities.dog_activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
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

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tsekhmeistruk.funnycats.AppFunnyPets;
import tsekhmeistruk.funnycats.R;
import tsekhmeistruk.funnycats.di.component.AppComponent;
import tsekhmeistruk.funnycats.di.component.DaggerPresentersComponent;
import tsekhmeistruk.funnycats.di.module.PresentersModule;
import tsekhmeistruk.funnycats.models.dogs.entities.ImageUrl;
import tsekhmeistruk.funnycats.presenters.dogs_presenter.DogPhotoListPresenter;
import tsekhmeistruk.funnycats.views.dog_views.DogPhotosView;
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

    private CallbackManager callbackManager;

    private PhotoAdapter photoAdapter;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.dogs_part_app_name);
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


        photoListPresenter.setView(this);
        photoListPresenter.loadPhotoList();
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
    public Context getContext() {
        return getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return ((AppFunnyPets) getApplication()).appComponent();
    }

    private void hideFacebookButton() {
        loginButton.setVisibility(View.INVISIBLE);
    }

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
