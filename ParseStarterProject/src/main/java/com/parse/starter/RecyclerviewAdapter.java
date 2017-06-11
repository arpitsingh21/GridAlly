package com.parse.starter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Arpit on 05-06-2017.
 */
public class RecyclerviewAdapter  extends RecyclerView.Adapter
        <RecyclerviewAdapter.ListItemViewHolder> {
    private List<Model> items;
    Context context;
    private SparseBooleanArray selectedItems;

    RecyclerviewAdapter(List<Model> modelData, Context context) {
        if (modelData == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
this.context=context;
        items = modelData;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.header_logo, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        Model model = items.get(position);
        viewHolder.name.setText(String.valueOf(model.data));
        viewHolder.age.setText(String.valueOf(model.text));


        Glide.with(context).load(model.s)
                .thumbnail(0.8f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.im);



        viewHolder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView age;
ImageView im;
        public ListItemViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_label_item);
            age = (TextView) itemView.findViewById(R.id.txt_date_time);
            im=(ImageView)itemView.findViewById(R.id.imageView2);
        }
    }
}
