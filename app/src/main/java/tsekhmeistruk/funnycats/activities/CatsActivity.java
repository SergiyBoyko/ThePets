package tsekhmeistruk.funnycats.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.ListView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tsekhmeistruk.funnycats.AppFunnyCats;
import tsekhmeistruk.funnycats.R;
import tsekhmeistruk.funnycats.di.component.AppComponent;
import tsekhmeistruk.funnycats.di.component.DaggerPresentersComponent;
import tsekhmeistruk.funnycats.di.module.PresentersModule;
import tsekhmeistruk.funnycats.models.entities.CategoryList;
import tsekhmeistruk.funnycats.models.entities.ImageList;
import tsekhmeistruk.funnycats.presenters.CatPhotosPresenter;
import tsekhmeistruk.funnycats.views.CatPhotosView;
import tsekhmeistruk.funnycats.widgets.adapters.CategoryListAdapter;
import tsekhmeistruk.funnycats.widgets.adapters.PhotoAdapter;

public class CatsActivity extends AppCompatActivity implements CatPhotosView {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.photo_container)
    GridView photoContainer;

    @Inject
    CatPhotosPresenter catPhotosPresenter;

    private ListView categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        categoryList = (ListView) navigationView.findViewById(R.id.category_list);

        catPhotosPresenter.setView(this);
        catPhotosPresenter.loadCategoryList();
        catPhotosPresenter.loadPhotoList(null);
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
        PhotoAdapter photoAdapter
                = new PhotoAdapter(getContext(), imageList.getImageList());
        photoContainer.setAdapter(photoAdapter);
    }

    public AppComponent getAppComponent() {
        return ((AppFunnyCats) getApplication()).appComponent();
    }

}
