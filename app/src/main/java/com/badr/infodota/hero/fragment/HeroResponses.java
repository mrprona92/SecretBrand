package com.badr.infodota.hero.fragment;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;

import com.badr.infodota.R;
import com.badr.infodota.base.service.LocalSpiceService;
import com.badr.infodota.hero.adapter.HeroResponsesAdapter;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.api.responses.HeroResponse;
import com.badr.infodota.hero.api.responses.HeroResponsesSection;
import com.badr.infodota.hero.task.HeroResponseLoadRequest;
import com.badr.infodota.hero.task.MusicLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.File;

/**
 * User: ABadretdinov
 * Date: 05.02.14
 * Time: 17:53
 */
public class HeroResponses extends Fragment implements RequestListener<HeroResponsesSection.List> {
    private MediaPlayer mMediaPlayer;
    private HeroResponsesAdapter mAdapter;
    private Filter mFilter;
    private EditText searchView;
    private ListView listView;
    private Hero hero;
    private SpiceManager mSpiceManager = new SpiceManager(LocalSpiceService.class);

    public static HeroResponses newInstance(Hero hero) {
        HeroResponses fragment = new HeroResponses();
        fragment.hero = hero;
        return fragment;
    }

    @Override
    public void onStart() {
        if (!mSpiceManager.isStarted()) {
            Activity activity = getActivity();
            if (activity != null) {
                mSpiceManager.start(activity);
                mSpiceManager.execute(new HeroResponseLoadRequest(activity.getApplicationContext(), hero.getDotaId()), this);
            }
        }
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hero_responses, container, false);
        listView = (ListView) view.findViewById(android.R.id.list);
        searchView = (EditText) view.findViewById(R.id.search);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mAdapter != null) {
            listView.setAdapter(mAdapter);
        }
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mFilter != null) {
                    mFilter.filter(s);
                }
            }
        });
        final View root = getView();
        if (root != null) {
            root.findViewById(R.id.select_to_download).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.changeEditMode(true);
                    root.findViewById(R.id.buttons_holder).setVisibility(View.VISIBLE);
                }
            });
            root.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.changeEditMode(false);
                    root.findViewById(R.id.buttons_holder).setVisibility(View.GONE);
                }
            });
            root.findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.startLoadingFiles();
                    root.findViewById(R.id.buttons_holder).setVisibility(View.GONE);
                }
            });
            root.findViewById(R.id.invert).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.inverseChecked();
                }
            });
            root.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchView.setText("");
                }
            });
            setOnClickListener();
        }
    }

    private void killMediaPlayer() {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapter.isEditMode()) {
                    mAdapter.setItemClicked(position);
                } else {
                    Object object = mAdapter.getItem(position);
                    if (object instanceof HeroResponse) {
                        HeroResponse heroResponse = (HeroResponse) object;
                        mAdapter.addToPlayLoading(position);
                        mSpiceManager.execute(new MusicLoadRequest(mMediaPlayer, heroResponse), new MusicLoadRequestListener(position));
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        killMediaPlayer();
        super.onDestroy();
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
    }

    @Override
    public void onRequestSuccess(HeroResponsesSection.List heroResponses) {
        File musicFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "Music" + File.separator + "dota2" + File.separator + hero.getDotaId() + File.separator);
        String musicPath = musicFolder.getAbsolutePath();
        mAdapter = new HeroResponsesAdapter(getActivity(), heroResponses, musicPath);
        mFilter = mAdapter.getFilter();
        listView.setAdapter(mAdapter);
    }

    public class MusicLoadRequestListener implements RequestListener<MediaPlayer> {
        private int mPosition;

        public MusicLoadRequestListener(int position) {
            this.mPosition = position;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Context context = getActivity();
            if (context != null) {
                Toast.makeText(context, getString(R.string.loading_response_error), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onRequestSuccess(MediaPlayer mediaPlayer) {
            mMediaPlayer = mediaPlayer;
            mAdapter.loaded(mPosition);
        }
    }
}
