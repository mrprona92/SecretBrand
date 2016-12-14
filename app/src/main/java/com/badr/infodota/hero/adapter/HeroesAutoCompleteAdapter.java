package com.badr.infodota.hero.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.hero.api.Hero;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 29.08.13
 * Time: 12:25
 */
public class HeroesAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater mInflater;
    private List<Hero> mHeroes;
    private List<Hero> allHeroes;

    public HeroesAutoCompleteAdapter(Context context, List<Hero> heroes) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        allHeroes = heroes;
        mHeroes = heroes != null ? heroes : new ArrayList<Hero>();
    }

    public int getCount() {
        return mHeroes.size();
    }

    public Hero getItem(int position) {
        return mHeroes.get(position);
    }

    public long getItemId(int position) {
        return mHeroes.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        HeroHolder holder;
        if (convertView == null) {
            vi = mInflater.inflate(R.layout.hero_autocomplete_row, parent, false);
            holder = new HeroHolder();
            holder.name = (TextView) vi.findViewById(R.id.name);
            holder.image = (ImageView) vi.findViewById(R.id.img);
            vi.setTag(holder);
        } else {
            holder = (HeroHolder) vi.getTag();
        }
        Hero hero = getItem(position);
        holder.name.setText(hero.getLocalizedName());

        Context context = parent.getContext();
        Glide.with(context).load(SteamUtils.getHeroFullImage(hero.getDotaId())).placeholder(R.drawable.default_img).into(holder.image);
        return vi;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<Hero> filteredHeroes = new ArrayList<Hero>();
                if (constraint == null) {
                    filterResults.values = allHeroes;
                    filterResults.count = allHeroes.size();
                    return filterResults;
                }
                String lowerConstr = constraint.toString().toLowerCase();
                for (Hero hero : allHeroes) {
                    if (hero.getLocalizedName().toLowerCase().contains(lowerConstr) || hero.getName().toLowerCase().contains(lowerConstr)) {
                        filteredHeroes.add(hero);
                    }
                }
                filterResults.count = filteredHeroes.size();
                filterResults.values = filteredHeroes;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mHeroes = (ArrayList<Hero>) results.values;
                if (mHeroes == null) {
                    mHeroes = new ArrayList<Hero>();
                }
                if (results.count >= 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    public static class HeroHolder {
        TextView name;
        ImageView image;
    }
}
