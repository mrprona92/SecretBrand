package com.badr.infodota.stream.activity;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.view.TappableSurfaceView;
import com.badr.infodota.stream.task.TwitchQualitiesLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.parser.Element;

/**
 * User: Histler
 * Date: 25.02.14
 */
public class TwitchPlayActivity extends BaseActivity implements SurfaceHolder.Callback, RequestListener<Element.List> {//}  implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {
    TappableSurfaceView videoView;
    MediaPlayer mediaPlayer;
    SurfaceHolder surfaceHolder;
    //LoaderProgressTask accessTokenThread;
    private Menu menu;
    private ImageView pause;
    private View progressBar;
    private int qualityPosition;
    private Element.List qualities;
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);

    @Override
    protected void onStart() {
        super.onStart();
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(this);
            String channelName = getIntent().getExtras().getString("channelName");
            getAccessToken(channelName);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        this.menu = menu;
        MenuItem item = menu.add(1, R.id.filter, 1, R.string.quality);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            PopupMenu popup = new PopupMenu(this, findViewById(item.getItemId()));
            final Menu menu = popup.getMenu();

            if (qualities != null && qualities.size() > 0)
                for (int i = 0; i < qualities.size(); i++) {
                    Element quality = qualities.get(i);
                    menu.add(2, i, 0, quality.getQuality());
                }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    qualityPosition = menuItem.getItemId();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(TwitchPlayActivity.this);
                    preferences.edit().putInt("player_default_quality", qualityPosition).commit();
                    setStreamQuality();
                    return true;
                }
            });
            popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
                @Override
                public void onDismiss(PopupMenu popupMenu) {
                    toggleHideyBar();
                }
            });
            popup.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.twitch_game);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        toggleHideyBar();
        videoView = (TappableSurfaceView) findViewById(R.id.video);
        progressBar = findViewById(R.id.progressBar);
        pause = (ImageView) findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.GONE);
                if (mediaPlayer.isPlaying()) {
                    pause.setImageResource(R.drawable.play);
                    mediaPlayer.reset();
                } else {
                    pause.setImageResource(R.drawable.pause);
                    setStreamQuality();
                }
            }
        });
        videoView.addTapListener(new TappableSurfaceView.TapListener() {
            @Override
            public void onTap(MotionEvent event) {
                getSupportActionBar().show();
                pause.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        getSupportActionBar().hide();
                        pause.setVisibility(View.GONE);
                        toggleHideyBar();
                        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    }
                }, 5000);
            }
        });
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*getSupportActionBar().show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        getSupportActionBar().hide();
                        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    }
                }, 5000);*/
            }
        });
        //onConfigChanged();
        surfaceHolder = videoView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setKeepScreenOn(true);
        //surfaceHolder.setFixedSize(176, 144);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
                pause.setVisibility(View.GONE);
                if (!mp.isPlaying()) {
                    mp.start();
                    getSupportActionBar().hide();
                }
            }
        });
        //http://video12.fra01.hls.twitch.tv/hls4/starladder1_8686670976_68409999/mobile/index.m3u8?token=id=2186420592380609324,bid=8686670976,exp=1393625278,node=video12-1.fra01.hls.justin.tv,nname=video12.fra01,fmt=mobile&sig=c522f7bacc493511b053c6c32301b0ef6ae863f1
        //String url="http://usher.twitch.tv/api/channel/hls/gsstudio_dota.m3u8?token={\"user_id\":null,\"channel\":\"gsstudio_dota\",\"expires\":1393530360,\"chansub\":{\"view_until\":1924905600,\"restricted_bitrates\":[]},\"private\":{\"allowed_to_view\":true},\"privileged\":false}&sig=61458b8929e15eb6508dc44484bc32ef091abec4";
        getSupportActionBar().setTitle(getIntent().getExtras().getString("channelTitle"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            pause.setImageResource(R.drawable.play);
            mediaPlayer.reset();
        }
    }

    private void getAccessToken(String channelName) {
        mSpiceManager.execute(new TwitchQualitiesLoadRequest(getApplicationContext(), channelName), this);
    }

    private void setStreamQuality() {
        if (qualities != null && qualities.size() > 0) {
            progressBar.setVisibility(View.VISIBLE);
            Element quality = qualities.get(qualityPosition);
            menu.getItem(0).setTitle(quality.getQuality());

            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                }
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDisplay(surfaceHolder);

                mediaPlayer.setDataSource(quality.getURI().toString());
                mediaPlayer.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    /**
     * Detects and toggles immersive mode (also known as "hidey bar" mode).
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void toggleHideyBar() {

        // BEGIN_INCLUDE (get_current_ui_flags)
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
       /*
        if (isImmersiveModeEnabled) {
            Log.i(TAG, "Turning immersive mode mode off. ");
        } else {
            Log.i(TAG, "Turning immersive mode mode on.");
        }*/
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        finish();
    }

    @Override
    public void onRequestSuccess(Element.List elements) {
        qualities = elements;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(TwitchPlayActivity.this);
        qualityPosition = preferences.getInt("player_default_quality", qualities.size() - 1);
        qualityPosition = Math.min(qualityPosition, qualities.size() - 1);
        setStreamQuality();
    }
}
