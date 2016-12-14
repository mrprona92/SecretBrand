package com.badr.infodota.item.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.BaseRecyclerAdapter;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.item.adapter.holder.ItemHolder;
import com.badr.infodota.item.api.Item;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Histler
 * Date: 18.01.14
 */
public class ItemsAdapter extends BaseRecyclerAdapter<Item, ItemHolder> implements Filterable {

    private List<Item> mFiltered;
    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<Item> filteredEntities = new ArrayList<Item>();
            String lowerConstr = constraint != null ? constraint.toString().toLowerCase() : "";
            filterResults.count = filteredEntities.size();
            for (Item entity : mData) {
                if (entity.getDotaName().toLowerCase().contains(lowerConstr)
                        || entity.getDotaId().toLowerCase().contains(lowerConstr)) {
                    filteredEntities.add(entity);
                }
            }
            filterResults.values = filteredEntities;
            return filterResults;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFiltered = (List<Item>) results.values;
            if (mFiltered == null) {
                mFiltered = new ArrayList<>();
            }
            notifyDataSetChanged();
        }
    };

    public ItemsAdapter(List<Item> items) {
        super(items);
        this.mFiltered = mData;
    }

    @Override
    public int getItemCount() {
        return mFiltered.size();
    }

    @Override
    public Item getItem(int position) {
        return mFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mFiltered.get(position).getId();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ItemHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

        Item item = getItem(position);
        holder.name.setText(item.getDotaName());

        Context context = holder.name.getContext();
        Glide.with(context).load(SteamUtils.getItemImage(item.getDotaId())).into(holder.image);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}
