package com.badr.infodota.quiz.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.IncreasingCountDownTimer;
import com.badr.infodota.base.util.prefs.AchievementPreferences;
import com.badr.infodota.quiz.fragment.HeroSkillsQuiz;
import com.badr.infodota.quiz.fragment.ItemsQuiz;
import com.badr.infodota.quiz.fragment.QuizFragment;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * User: Histler
 * Date: 09.02.14
 */
public class QuizActivity extends BaseActivity {
    public static final int MAX_ATTEMPTS = 3;
    public static final int MODE_NONE = 0;
    public static final int MODE_ITEMS = 1;
    public static final int MODE_HEROES = 2;
    /* @Override
     public void onSignInSucceeded() {
         Bundle bundle = getIntent().getExtras();
         if(bundle.containsKey("forAchievements")){
             startActivityForResult(getGamesClient().getAchievementsIntent(), RC_UNUSED);
             finish();
         }else if(bundle.containsKey("forLeaderboards")){
             startActivityForResult(getGamesClient().getAllLeaderboardsIntent(), RC_UNUSED);
             finish();
         }
         else if (forRecord && timer == null) {
             signedInit();
         }
     }*/
    public static final int QUIZ_TIME = 60000;
    private static final int RC_UNUSED = 1;
    /*todo рекорды. по героям, по предметам, в сумме*/
    private int attempts = MAX_ATTEMPTS;
    private int mode;
    private long score = 0;
    private long bonus;
    private TextView scoreText;
    private TextView attemptText;
    private Random idRandom;
    private IncreasingCountDownTimer timer;
    private QuizFragment.OnQuizStateChangeListener listener;
    private boolean forRecord;
    private AchievementPreferences prefs;
    private Map<String, String> attrs = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_holder);
        getSupportActionBar().setTitle(R.string.quiz);
        idRandom = new Random();
        Bundle bundle = getIntent().getExtras();
        /*if(bundle.containsKey("forAchievements")){
            findViewById(R.id.quiz_stats).setVisibility(View.GONE);
            if(isSignedIn())
            {
                startActivityForResult(getGamesClient().getAchievementsIntent(), RC_UNUSED);
                finish();
            }
            else {
                beginUserInitiatedSignIn();
            }
			attrs.put("type","forAchievements");
        }else if(bundle.containsKey("forLeaderboards")){
            findViewById(R.id.quiz_stats).setVisibility(View.GONE);
            if(isSignedIn())
            {
                startActivityForResult(getGamesClient().getAllLeaderboardsIntent(), RC_UNUSED);
                finish();
            }else {
                beginUserInitiatedSignIn();
            }
			attrs.put("type","forLeaderboards");
        }else*/
        {
            findViewById(R.id.quiz_stats).setVisibility(View.VISIBLE);
            mode = bundle.getInt("mode", 0);
            forRecord = bundle.getBoolean("forRecord", false);
            prefs = new AchievementPreferences(this);
            attrs.put("type", "forPlay");
            attrs.put("mode", String.valueOf(mode));
            attrs.put("forRecord", String.valueOf(forRecord));

            listener = new QuizFragment.OnQuizStateChangeListener() {
                @Override
                public void onQuizAnswered() {
                    if (timer != null) {
                        timer.increaseTimer(500);
                    }
                    long increaseTo = 200 + 30 * bonus;
                    if (score > Long.MAX_VALUE - increaseTo) {
                        score = Long.MAX_VALUE;
                    } else {
                        score += increaseTo;
                        bonus++;
                    }

                    checkScoreAchievements();

                    scoreText.setText(String.valueOf(score));
                    //Toast.makeText(QuizActivity.this,getString(R.string.right),Toast.LENGTH_SHORT).show();
                    initQuiz();
                }

                @Override
                public void onLoosed() {
                    if (timer != null) {
                        timer.cancel();
                    }
                    if (forRecord/* && isSignedIn()*/) {
                        checkAchievements();
                        updateLeaderboards(score);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
                    builder.setTitle(getString(R.string.game_over));
                    builder.setMessage(MessageFormat.format(getString(R.string.total_score), score));
                    builder.setNegativeButton(getString(R.string.back), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    builder.setPositiveButton(getString(R.string.show_right), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ((QuizFragment) getSupportFragmentManager().findFragmentById(R.id.details)).showLoosed();
                        }
                    });
                    /*if (forRecord) {
                        builder.setNeutralButton(getString(R.string.show_records), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startActivityForResult(getGamesClient().getAllLeaderboardsIntent(), RC_UNUSED);
                                finish();
                            }
                        });
                    }*/
                    builder.setCancelable(false);
                /*builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
					public void onCancel(DialogInterface dialog) {
						finish();
					}
				});*/
                    builder.show();
                }

                @Override
                public void onQuizAttemptLoosed() {
                    attempts--;
                    attemptText.setText(String.valueOf(attempts));
                    bonus = 0;
                    if (attempts == 0) {
                        onLoosed();
                    } else {
                        Toast.makeText(QuizActivity.this, getString(R.string.wrong), Toast.LENGTH_SHORT).show();
                    }
                }
            };
            scoreText = (TextView) findViewById(R.id.score);
            attemptText = (TextView) findViewById(R.id.attempt);
            scoreText.setText(String.valueOf(score));
            attemptText.setText(String.valueOf(attempts));
            if (forRecord) {
                prefs.setEverywhereMode(mode);
                /*if (!isSignedIn()) {
                    beginUserInitiatedSignIn();
                } else*/
                {
                    signedInit();
                }
            } else {
                initQuiz();
            }
        }
    }

    private void initQuiz() {
        int tekQuiz = mode;
        if (tekQuiz == MODE_NONE) {
            tekQuiz = new Random().nextInt() % 2 + 1;
        }
        Fragment fragment;
        switch (tekQuiz) {
            case MODE_ITEMS:
                fragment = ItemsQuiz.newInstance(listener, idRandom);
                break;
            default:
            case MODE_HEROES:
                fragment = HeroSkillsQuiz.newInstance(listener, idRandom);
        }
        replaceFragment(fragment);
    }

    /*@Override
    public void onSignInFailed() {
        Bundle bundle = getIntent().getExtras();
        if (forRecord||bundle.containsKey("forAchievements")||bundle.containsKey("forLeaderboards")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.error);
            builder.setMessage(R.string.gamehelper_sign_in_failed);
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.setPositiveButton(R.string.repeat, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    beginUserInitiatedSignIn();
                    *//*if(forRecord)
                    {
                        prefs.clear();
                    }*//*
                }
            });
            builder.show();
        }
    }*/

    public void replaceFragment(Fragment details) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.details, details);
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (timer != null) {
            timer.resume();
        }
    }

    @Override
    public void onStop() {
        if (timer != null) {
            timer.cancel();
        }
        super.onStop();
    }

    private void signedInit() {
        timer = new IncreasingCountDownTimer(QUIZ_TIME, 1000) {

            public void onTick(long millisUntilFinished) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    getSupportActionBar().setTitle(MessageFormat.format(getString(R.string.seconds_remaining), millisUntilFinished / 1000));
                }
            }

            public void onFinish() {
                listener.onLoosed();
            }
        }.start();
        initQuiz();
    }

    private void checkAchievements() {

        if (forRecord /*&& isSignedIn()*/) {
            if (score == 0 && !prefs.isLoLerUnlocked()) {
                unlockAchievement(R.string.achievement_loler);
            }
            if (!prefs.isImEverywhere() && prefs.isEverywhere()) {
                unlockAchievement(R.string.achievement_im_everywhere);
            }
            if (attempts == MAX_ATTEMPTS && !prefs.isPerfectAttemptUnlocked() && score >= 1300) {
                unlockAchievement(R.string.achievement_perfect_attempt);
            }
            checkScoreAchievements();
           /* getGamesClient().loadAchievements(new OnAchievementsLoadedListener() {
                @Override
                public void onAchievementsLoaded(int i, AchievementBuffer achievements) {
                    for (Achievement achievement : achievements) {
                        String id = achievement.getAchievementId();
                        if (achievement.getState() == 0) {
                            if (getString(R.string.achievement_a_little_better).equals(id)) {
                                prefs.setLittleBetterUnlocked();
                            } else if (getString(R.string.achievement_cheater).equals(id)) {
                                prefs.setCheaterUnlocked();
                            } else if (getString(R.string.achievement_first_game__first_achievement).equals(id)) {
                                prefs.setFirstGameUnlocked();
                            } else if (getString(R.string.achievement_high_five).equals(id)) {
                                prefs.setHighFiveUnlocked();
                            } else if (getString(R.string.achievement_im_proud_of_you).equals(id)) {
                                prefs.setProudOfYouUnlocked();
                            } else if (getString(R.string.achievement_loler).equals(id)) {
                                prefs.setLoLerUnlocked();
                            } else if (getString(R.string.achievement_newbie).equals(id)) {
                                prefs.setNewbieUnlocked();
                            } else if (getString(R.string.achievement_pro).equals(id)) {
                                prefs.setProUnlocked();
                            } else if (getString(R.string.achievement_perfect_attempt).equals(id)) {
                                prefs.setPerfectAttemptUnlocked();
                            } else if (getString(R.string.achievement_not_bad).equals(id)) {
                                prefs.setNotBadUnlocked();
                            } else if (getString(R.string.achievement_im_everywhere).equals(id)) {
                                prefs.setImEverywhere();
                            }
                        }
                    }
                }
            }, true);*/
        }
    }

    private void checkScoreAchievements() {

        if (forRecord/* && isSignedIn()*/) {
            if (!prefs.isFirstGameUnlocked()) {
                unlockAchievement(R.string.achievement_first_game__first_achievement);
            }
            if (!prefs.isNewbieBetterUnlocked() && score >= 1300) {
                unlockAchievement(R.string.achievement_newbie);
            }
            if (!prefs.isLittleBetterUnlocked() && score >= 4300) {
                unlockAchievement(R.string.achievement_a_little_better);
            }
            if (!prefs.isNotBadUnlocked() && score >= 10000) {
                unlockAchievement(R.string.achievement_not_bad);
            }
            if (!prefs.isHighFiveUnlocked() && score >= 25000) {
                unlockAchievement(R.string.achievement_high_five);
            }
            if (!prefs.isProUnlocked() && score >= 50000) {
                unlockAchievement(R.string.achievement_pro);
            }
            if (!prefs.isProudOfYouUnlocked() && score >= 75000) {
                unlockAchievement(R.string.achievement_im_proud_of_you);
            }
            if (!prefs.isCheaterUnlocked() && score >= 100000) {
                unlockAchievement(R.string.achievement_cheater);
            }
        }
    }

    void unlockAchievement(int achievementId) {
        /*if (isSignedIn()) {
            getGamesClient().unlockAchievement(getString(achievementId));
        }*/
    }

    private void updateLeaderboards(long score) {
        int strMode;
        switch (mode) {
            case MODE_ITEMS:
                strMode = R.string.leaderboard_best_by_items_quiz;
                break;
            case MODE_HEROES:
                strMode = R.string.leaderboard_best_by_abilities_quiz;
                break;
            default:
                strMode = R.string.leaderboard_luckers;
        }
        if (score < 110000 || prefs.isCheaterUnlocked()) {
            // getGamesClient().submitScore(getString(strMode), score);
        }
    }
}
