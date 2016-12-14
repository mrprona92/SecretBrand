package com.badr.infodota.base.menu.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.menu.entity.MenuItem;


/**
 * Created by Binh.TH on 09/12/16.
 */
public class DHMenuAdapter extends RecyclerView.Adapter<DHMenuAdapter.ViewHolder> {

    private static final String TAG = "SCMenuAdapter";
    private Context mContext;
    private MenuItem[] mArrayMenuItems;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    // 50*50 is for xxhdpi
    private void initializeMenuItems() {
        mArrayMenuItems = new MenuItem[]{
                new MenuItem(R.string.menu_items_game, R.drawable.ic_menu_items, 1, false),
                new MenuItem(R.string.menu_news_dota, R.drawable.ic_menu_news, 1, false),
                new MenuItem(R.string.menu_tournament_match, R.drawable.ic_menu_tournament, 1, false),
                new MenuItem(R.string.menu_recent_match, R.drawable.ic_menu_recentmatch, 2, false),
                new MenuItem(R.string.menu_twitch_tv, R.drawable.ic_menu_twitch, 1, false),
                new MenuItem(R.string.menu_check_for_update, R.drawable.ic_menu_check_update, 2, false),
                new MenuItem(R.string.menu_about, R.drawable.ic_menu_about, 2, false),
                new MenuItem(R.string.menu_exit, R.drawable.ic_menu_quit, 1, false),
        };
    }

    public DHMenuAdapter(Context context) {
        mContext = context;
        initializeMenuItems();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_menu, parent, false));

    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        return viewType;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MenuItem menuItem = mArrayMenuItems[position];
        final int itemType = getItemViewType(position);
        if (itemType == 0) {
            ViewHolder ViewHolder = (ViewHolder) holder;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(v, holder.getAdapterPosition());
                }
            });
            ViewHolder.lblTitle.setText(menuItem.titleRes);
            ViewHolder.lblTitle.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, menuItem.iconRes), null, null, null);
            if(menuItem.grayBg) {
                holder.itemView.setBackgroundResource(R.drawable.selector_row_menu_item_gray);
            } else {
                holder.itemView.setBackgroundResource(R.drawable.selector_row_menu_item);
            }
        } else {{
            holder.itemView.setBackgroundResource(R.drawable.selector_row_menu_item);
        }
            // Do nothing
        }
    }

    @Override
    public int getItemCount() {
        return mArrayMenuItems.length;
    }

    public MenuItem[] getArrayMenuItems() {
        return mArrayMenuItems;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final LinearLayout layoutMenu;
        final TextView lblTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            lblTitle = (TextView) itemView.findViewById(R.id.lblTitle);
            layoutMenu = (LinearLayout) itemView.findViewById(R.id.layoutMenu);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /*static class ZeroHeightViewHolder extends DHMenuAdapter.ViewHolder {

        public ZeroHeightViewHolder(View itemView) {
            super(itemView);
        }
    }*/
}
