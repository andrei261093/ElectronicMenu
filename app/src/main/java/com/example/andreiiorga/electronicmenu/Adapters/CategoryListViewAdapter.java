package com.example.andreiiorga.electronicmenu.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andreiiorga.electronicmenu.R;
import com.example.andreiiorga.electronicmenu.models.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreiiorga on 21/06/2017.
 */

public class CategoryListViewAdapter extends ArrayAdapter<Category> {
    List<Category> list = new ArrayList<>();

    public CategoryListViewAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public void add(Category category) {
        super.add(category);
        list.add(category);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Category getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;

        row = convertView;
        final CategoryHolder categoryHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_category, parent, false);
            categoryHolder = new CategoryHolder();

            categoryHolder.tx_name = (TextView) row.findViewById(R.id.category_name);
            categoryHolder.img = (ImageView) row.findViewById(R.id.category_image);

            row.setTag(categoryHolder);

        } else {
            categoryHolder = (CategoryHolder) row.getTag();
        }

        final Category category = (Category) this.getItem(position);
        categoryHolder.tx_name.setText(category.getName());
        Picasso.with(getContext())
                .load(category.getImageUrl())
                .into(categoryHolder.img);

        return row;
    }

    static class CategoryHolder {
        TextView tx_name;
        ImageView img;
    }

}
