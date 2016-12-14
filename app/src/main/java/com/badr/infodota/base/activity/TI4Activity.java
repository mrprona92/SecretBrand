package com.badr.infodota.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.adapter.pager.TI4PagerAdapter;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.base.service.ti4.TI4Service;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.text.MessageFormat;

/**
 * User: ABadretdinov
 * Date: 14.05.14
 * Time: 20:17
 */
@Deprecated
public class TI4Activity extends BaseActivity implements RequestListener<Long> {
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);

    @Override
    protected void onStart() {
        super.onStart();
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(this);
            mSpiceManager.execute(new PrizePoolLoadRequest(getApplicationContext()), this);
        }
    }

    @Override
    protected void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ti4_holder);
        getSupportActionBar().setTitle("The International 2014");
        initPager();
    }

    private void initPager() {
        FragmentPagerAdapter adapter = new TI4PagerAdapter(getSupportFragmentManager(), this);
        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        Toast.makeText(this, spiceException.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestSuccess(Long result) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(MessageFormat.format(getString(R.string.current_prizepool), String.valueOf(result)));
    }

    public static class PrizePoolLoadRequest extends TaskRequest<Long> {
        private Context context;

        public PrizePoolLoadRequest(Context context) {
            super(Long.class);
            this.context = context;

        }

        @Override
        public Long loadData() throws Exception {
            BeanContainer container = BeanContainer.getInstance();
            TI4Service service = container.getTi4Service();
            return service.getPrizePool(context);
        }
    }
}
