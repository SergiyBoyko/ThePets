package tsekhmeistruk.funnycats.widgets.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import tsekhmeistruk.funnycats.R;

/**
 * Created by Roman Tsekhmeistruk on 14.04.2017.
 */

public class CategoryListAdapter extends BaseAdapter{

    private List<String> categories;

    public CategoryListAdapter(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_photo_category, parent, false);
        }

        TextView categoryName = (TextView) convertView.findViewById(R.id.category_name);
        categoryName.setText(categories.get(position));

        return convertView;
    }

}
