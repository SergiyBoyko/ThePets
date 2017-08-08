package tsekhmeistruk.funnycats.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tsekhmeistruk.funnycats.AppFunnyCats;
import tsekhmeistruk.funnycats.Constants;
import tsekhmeistruk.funnycats.R;
import tsekhmeistruk.funnycats.di.component.AppComponent;
import tsekhmeistruk.funnycats.di.component.DaggerPresentersComponent;
import tsekhmeistruk.funnycats.di.module.PresentersModule;
import tsekhmeistruk.funnycats.models.entities.Image;
import tsekhmeistruk.funnycats.presenters.FullSizePhotoPresenter;
import tsekhmeistruk.funnycats.views.FullSizePhotoView;

public class FullSizeImageActivity extends AppCompatActivity implements FullSizePhotoView {

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.image)
    ImageView image;

    @Inject
    FullSizePhotoPresenter fullSizePhotoPresenter;

    private String userId;
    private String imageId;
    private String imageUrl;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_size_image);

        ButterKnife.bind(this);

        DaggerPresentersComponent.builder()
                .appComponent(getAppComponent())
                .presentersModule(new PresentersModule())
                .build()
                .inject(this);

        Image image = (Image) getIntent().getExtras().getSerializable(Constants.IMAGE);
        if (image != null) {
            imageUrl = image.getUrl();
            imageId = image.getId();
            showImage();
        }
        userId = getIntent().getExtras().getString(Constants.USER_ID);
        isFavorite = getIntent().getExtras().getBoolean(Constants.IS_FAVORITE);

        fullSizePhotoPresenter.setView(this);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onAddingToFavorites() {
        showToast(getString(R.string.added_favorite));
    }

    @Override
    public void onRemovingFromFavorites() {
        showToast(getString(R.string.removed_favorite));
        onBackPressed();
    }

    @OnClick(R.id.like)
    public void addFavorite() {
        if (userId != null) {
            if (!isFavorite) {
                fullSizePhotoPresenter.favourite(imageId, userId, null);
            } else {
                showToast(getString(R.string.already_favorited));
            }
        } else {
            showToast(getString(R.string.log_in));
        }
    }

    @OnClick(R.id.dislike)
    public void removeFavorite() {
        if (userId != null) {
            if (isFavorite) {
                fullSizePhotoPresenter.favourite(imageId, userId, Constants.ACTION_REMOVE);
            } else {
                showToast(getString(R.string.is_not_favorite));
            }
        } else {
            showToast(getString(R.string.log_in));
        }
    }

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void showImage() {
        if ((imageUrl != null) && (!TextUtils.isEmpty(imageUrl))) {
            Glide.with(this.getApplicationContext())
                    .load(imageUrl)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e,
                                                   String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            showToast(getString(R.string.request_execution_has_failed));
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource,
                                                       String model,
                                                       Target<GlideDrawable> target,
                                                       boolean isFromMemoryCache,
                                                       boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(image);
        }
    }

    public AppComponent getAppComponent() {
        return ((AppFunnyCats) getApplication()).appComponent();
    }

}
