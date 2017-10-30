package tsekhmeistruk.funnycats.widgets.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import tsekhmeistruk.funnycats.models.cats.entities.Image;
import tsekhmeistruk.funnycats.widgets.views.SquaredImageView;

/**
 * Created by Roman Tsekhmeistruk on 16.05.2017.
 */

public class PhotoAdapter extends BaseAdapter {

    private final Context context;
    private List<Image> images;

    public PhotoAdapter(Context context) {
        images = new ArrayList<>();
        this.context = context;
    }

    public void addImages(List<Image> images) {
        if (images != null) {
            this.images.addAll(images);
        }
    }

    public void clearImages() {
        this.images.clear();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Image getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FrameLayout view = (FrameLayout) convertView;
        final SquaredImageView imageView;
        final ProgressBar progressBar;

        if (view == null) {
            view = new FrameLayout(context);

            imageView = new SquaredImageView(context);
            progressBar = new ProgressBar(context);
            imageView.setVisibility(View.INVISIBLE);

            view.addView(imageView);
            view.addView(progressBar);
        } else {
            imageView = (SquaredImageView) view.getChildAt(0);
            progressBar = (ProgressBar) view.getChildAt(1);
            progressBar.setVisibility(View.VISIBLE);
        }

        String currentImageUrl = getItem(position).getUrl();
        Glide.with(context).load(currentImageUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e,
                                               String model,
                                               Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        imageView.setVisibility(View.INVISIBLE);
                        try {
                            images.remove(position);
                            notifyDataSetChanged();
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource,
                                                   String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .centerCrop()
                .into(imageView);

        return view;
    }

}
