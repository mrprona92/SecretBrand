package com.badr.infodota.match.task;

import android.content.Context;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.base.util.LongPair;
import com.badr.infodota.match.service.team.TeamService;

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
