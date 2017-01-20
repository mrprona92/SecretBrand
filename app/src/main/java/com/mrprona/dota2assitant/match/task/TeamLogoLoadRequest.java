package com.mrprona.dota2assitant.match.task;

import android.content.Context;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.base.util.LongPair;
import com.mrprona.dota2assitant.match.service.team.TeamService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 15:41
 */
public class TeamLogoLoadRequest extends TaskRequest<LongPair> {

    private Context mContext;
    private long mTeamId;

    public TeamLogoLoadRequest(Context context, long teamId) {
        super(LongPair.class);
        this.mContext = context;
        this.mTeamId = teamId;
    }

    @Override
    public LongPair loadData() throws Exception {
        TeamService service = BeanContainer.getInstance().getTeamService();
        String result = service.getTeamLogo(mContext, mTeamId);
        return new LongPair(mTeamId, result);
    }
}
