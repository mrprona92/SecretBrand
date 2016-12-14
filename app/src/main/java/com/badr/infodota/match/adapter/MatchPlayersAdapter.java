package com.badr.infodota.match.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.BaseRecyclerAdapter;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.item.activity.ItemInfoActivity;
import com.badr.infodota.match.adapter.holder.MatchPlayerHolder;
import com.badr.infodota.match.api.Player;
import com.badr.infodota.match.api.detailed.AdditionalUnit;
import com.badr.infodota.player.api.Unit;
import com.bumptech.glide.Glide;

import java.util.List;

import static com.badr.infodota.base.util.Utils.PHONE;
import static com.badr.infodota.base.util.Utils.TABLET_LANDSCAPE;
import static com.badr.infodota.base.util.Utils.TABLET_PORTRAIT;

/**
 * User: ABadretdinov
 * Date: 21.01.14
 * Time: 14:44
 */
public class MatchPlayersAdapter extends BaseRecyclerAdapter<Player, MatchPlayerHolder> {
    private int mState;
    private View.OnClickListener mEmptyOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public MatchPlayersAdapter(List<Player> players, int state) {
        super(players);
        mState = state;
    }

    public void notifyStateChanged(int state) {
        mState = state;
        notifyDataSetChanged();
    }

    @Override
    public MatchPlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_player_row, parent, false);
        return new MatchPlayerHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(MatchPlayerHolder holder, int position) {
        Context context = holder.nick.getContext();
        switch (mState) {
            case TABLET_LANDSCAPE:
                holder.unitHolder.setOrientation(LinearLayout.VERTICAL);
                holder.additionalUnitHolder.setOrientation(LinearLayout.VERTICAL);
                holder.itemHolder.setOrientation(LinearLayout.HORIZONTAL);
                ((LinearLayout) holder.itemView).setOrientation(LinearLayout.HORIZONTAL);
                break;
            case TABLET_PORTRAIT:
                holder.unitHolder.setOrientation(LinearLayout.VERTICAL);
                holder.additionalUnitHolder.setOrientation(LinearLayout.VERTICAL);
                holder.itemHolder.setOrientation(LinearLayout.VERTICAL);
                ((LinearLayout) holder.itemView).setOrientation(LinearLayout.HORIZONTAL);
                break;
            case PHONE:
                holder.unitHolder.setOrientation(LinearLayout.HORIZONTAL);
                holder.additionalUnitHolder.setOrientation(LinearLayout.HORIZONTAL);
                holder.itemHolder.setOrientation(LinearLayout.VERTICAL);
                ((LinearLayout) holder.itemView).setOrientation(LinearLayout.VERTICAL);
                break;
        }
        Player player = getItem(position);

        Unit account = player.getAccount();
        if (account != null) {
            holder.nick.setText(account.getName());
            holder.nick.setVisibility(View.VISIBLE);
        } else {
            holder.nick.setVisibility(View.INVISIBLE);
        }
        Integer leaver = player.getLeaverStatus();
        if (leaver == null) {
            holder.leaver.setText(context.getString(R.string.bot));
            holder.leaver.setVisibility(View.VISIBLE);
        } else switch (leaver) {
            case 1: {
                holder.leaver.setText(context.getString(R.string.disconnected));
                holder.leaver.setVisibility(View.VISIBLE);
                break;
            }
            case 2: {
                holder.leaver.setText(context.getString(R.string.abandoned));
                holder.leaver.setVisibility(View.VISIBLE);
                break;
            }
            case 3: {
                holder.leaver.setText(context.getString(R.string.leaved));
                holder.leaver.setVisibility(View.VISIBLE);
                break;
            }
            case 4: {
                holder.leaver.setText(context.getString(R.string.afk));
                holder.leaver.setVisibility(View.VISIBLE);
                break;
            }
            case 5: {
                holder.leaver.setText(context.getString(R.string.never_connected));
                holder.leaver.setVisibility(View.VISIBLE);
                break;
            }
            case 6: {
                holder.leaver.setText(context.getString(R.string.never_connected_too_long));
                holder.leaver.setVisibility(View.VISIBLE);
                break;
            }
            default: {
                holder.leaver.setVisibility(View.INVISIBLE);
            }
        }
        final Hero hero = player.getHero();
        if (hero != null) {
            Glide.with(context).load(SteamUtils.getHeroFullImage(hero.getDotaId())).placeholder(R.drawable.default_img).into(holder.heroImg);
            holder.heroName.setText(hero.getLocalizedName());
        } else {
            holder.heroImg.setImageResource(R.drawable.default_img);
            holder.heroName.setText("");
        }
        if (player.getItem0dotaId() != null) {
            Glide.with(context).load(SteamUtils.getItemImage(player.getItem0dotaId())).into(holder.item0);
            holder.item0.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(player.getItem0()));
        } else {
            holder.item0.setImageResource(R.drawable.emptyitembg);
            holder.item0.setOnClickListener(mEmptyOnClickListener);
        }
        if (player.getItem1dotaId() != null) {
            Glide.with(context).load(SteamUtils.getItemImage(player.getItem1dotaId())).into(holder.item1);
            holder.item1.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(player.getItem1()));
        } else {
            holder.item1.setImageResource(R.drawable.emptyitembg);
            holder.item1.setOnClickListener(mEmptyOnClickListener);
        }

        if (player.getItem2dotaId() != null) {
            Glide.with(context).load(SteamUtils.getItemImage(player.getItem2dotaId())).into(holder.item2);
            holder.item2.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(player.getItem2()));
        } else {
            holder.item2.setImageResource(R.drawable.emptyitembg);
            holder.item2.setOnClickListener(mEmptyOnClickListener);
        }

        if (player.getItem3dotaId() != null) {
            Glide.with(context).load(SteamUtils.getItemImage(player.getItem3dotaId())).into(holder.item3);
            holder.item3.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(player.getItem3()));
        } else {
            holder.item3.setImageResource(R.drawable.emptyitembg);
            holder.item3.setOnClickListener(mEmptyOnClickListener);
        }

        if (player.getItem4dotaId() != null) {
            Glide.with(context).load(SteamUtils.getItemImage(player.getItem4dotaId())).into(holder.item4);
            holder.item4.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(player.getItem4()));
        } else {
            holder.item4.setImageResource(R.drawable.emptyitembg);
            holder.item4.setOnClickListener(mEmptyOnClickListener);
        }

        if (player.getItem5dotaId() != null) {
            Glide.with(context).load(SteamUtils.getItemImage(player.getItem5dotaId())).into(holder.item5);
            holder.item5.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(player.getItem5()));
        } else {
            holder.item5.setImageResource(R.drawable.emptyitembg);
            holder.item5.setOnClickListener(mEmptyOnClickListener);
        }
        if (player.getAdditionalUnits() != null && player.getAdditionalUnits().size() > 0) {
            AdditionalUnit unit = player.getAdditionalUnits().get(0);
            holder.additionalUnitHolder.setVisibility(View.VISIBLE);

            if (unit.getItem0dotaId() != null) {
                Glide.with(context).load(SteamUtils.getItemImage(unit.getItem0dotaId())).into(holder.additionalUnitItem0);
                holder.additionalUnitItem0.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(unit.getItem0()));
            } else {
                holder.additionalUnitItem0.setImageResource(R.drawable.emptyitembg);
                holder.additionalUnitItem0.setOnClickListener(mEmptyOnClickListener);
            }

            if (unit.getItem1dotaId() != null) {
                Glide.with(context).load(SteamUtils.getItemImage(unit.getItem1dotaId())).into(holder.additionalUnitItem1);
                holder.additionalUnitItem1.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(unit.getItem1()));
            } else {
                holder.additionalUnitItem1.setImageResource(R.drawable.emptyitembg);
                holder.additionalUnitItem1.setOnClickListener(mEmptyOnClickListener);
            }

            if (unit.getItem2dotaId() != null) {
                Glide.with(context).load(SteamUtils.getItemImage(unit.getItem2dotaId())).into(holder.additionalUnitItem2);
                holder.additionalUnitItem2.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(unit.getItem2()));
            } else {
                holder.additionalUnitItem2.setImageResource(R.drawable.emptyitembg);
                holder.additionalUnitItem2.setOnClickListener(mEmptyOnClickListener);
            }

            if (unit.getItem3dotaId() != null) {
                Glide.with(context).load(SteamUtils.getItemImage(unit.getItem3dotaId())).into(holder.additionalUnitItem3);
                holder.additionalUnitItem3.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(unit.getItem3()));
            } else {
                holder.additionalUnitItem3.setImageResource(R.drawable.emptyitembg);
                holder.additionalUnitItem3.setOnClickListener(mEmptyOnClickListener);
            }

            if (unit.getItem4dotaId() != null) {
                Glide.with(context).load(SteamUtils.getItemImage(unit.getItem4dotaId())).into(holder.additionalUnitItem4);
                holder.additionalUnitItem4.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(unit.getItem4()));
            } else {
                holder.additionalUnitItem4.setImageResource(R.drawable.emptyitembg);
                holder.additionalUnitItem4.setOnClickListener(mEmptyOnClickListener);
            }

            if (unit.getItem5dotaId() != null) {
                Glide.with(context).load(SteamUtils.getItemImage(unit.getItem5dotaId())).into(holder.additionalUnitItem5);
                holder.additionalUnitItem5.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(unit.getItem5()));
            } else {
                holder.additionalUnitItem5.setImageResource(R.drawable.emptyitembg);
                holder.additionalUnitItem5.setOnClickListener(mEmptyOnClickListener);
            }
        } else {
            holder.additionalUnitHolder.setVisibility(View.GONE);
        }

        holder.playerLvl.setText(context.getString(R.string.level) + ": " + player.getLevel());
        holder.kills.setText(Html.fromHtml(context.getString(R.string.kills) + " " + player.getKills()));
        holder.deaths.setText(Html.fromHtml(context.getString(R.string.deaths) + " " + player.getDeaths()));
        holder.assists.setText(Html.fromHtml(context.getString(R.string.assists) + " " + player.getAssists()));
        holder.gold.setText(Html.fromHtml(context.getString(R.string.gold) + " " + player.getGold()));
        holder.lastHits.setText(Html.fromHtml(context.getString(R.string.last_hits) + " " + player.getLastHits()));
        holder.denies.setText(Html.fromHtml(context.getString(R.string.denies) + " " + player.getDenies()));
        holder.gpm.setText(Html.fromHtml(context.getString(R.string.gpm) + " " + player.getGoldPerMin()));
        holder.xpm.setText(Html.fromHtml(context.getString(R.string.xpm) + " " + player.getXpPerMin()));
    }
}
