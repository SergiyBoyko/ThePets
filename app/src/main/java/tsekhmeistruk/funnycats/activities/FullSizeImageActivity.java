package tsekhmeistruk.funnycats.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import tsekhmeistruk.funnycats.Constants;
import tsekhmeistruk.funnycats.R;
import tsekhmeistruk.funnycats.models.entities.Image;
import tsekhmeistruk.funnycats.views.FullSizePhotoView;

public class FullSizeImageActivity extends AppCompatActivity implements FullSizePhotoView {

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.dislike)
    ImageView dislikeButton;
    @BindView(R.id.like)
    ImageView likeButton;
    @BindView(R.id.download)
    ImageView downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_size_image);

        ButterKnife.bind(this);

        Image image = (Image) getIntent().getExtras().getSerializable(Constants.IMAGE);
        showImage(image.getUrl());
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    private void showImage(String imageUrl) {
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

}
