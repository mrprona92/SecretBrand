package com.mrprona.dota2assitant;

import com.mrprona.dota2assitant.base.dao.CreateTableDao;
import com.mrprona.dota2assitant.base.remote.SteamService;
import com.mrprona.dota2assitant.base.remote.update.UpdateRemoteService;
import com.mrprona.dota2assitant.base.remote.update.UpdateRemoteServiceImpl;
import com.mrprona.dota2assitant.base.service.LocalUpdateService;
import com.mrprona.dota2assitant.base.service.ti4.TI4ServiceImpl;
import com.mrprona.dota2assitant.base.service.update.UpdateService;
import com.mrprona.dota2assitant.base.service.update.UpdateServiceImpl;
import com.mrprona.dota2assitant.cosmetic.service.CosmeticService;
import com.mrprona.dota2assitant.cosmetic.service.CosmeticServiceImpl;
import com.mrprona.dota2assitant.counter.dao.TruepickerHeroDao;
import com.mrprona.dota2assitant.counter.remote.CounterRemoteEntityServiceImpl;
import com.mrprona.dota2assitant.counter.service.CounterServiceImpl;
import com.mrprona.dota2assitant.hero.dao.AbilityDao;
import com.mrprona.dota2assitant.hero.dao.HeroDao;
import com.mrprona.dota2assitant.hero.dao.HeroStatsDao;
import com.mrprona.dota2assitant.hero.dao.TalentDao;
import com.mrprona.dota2assitant.hero.service.HeroServiceImpl;
import com.mrprona.dota2assitant.item.dao.ItemDao;
import com.mrprona.dota2assitant.item.service.ItemServiceImpl;
import com.mrprona.dota2assitant.joindota.remote.JoinDotaRemoteServiceImpl;
import com.mrprona.dota2assitant.joindota.service.JoinDotaServiceImpl;
import com.mrprona.dota2assitant.match.dao.TeamDao;
import com.mrprona.dota2assitant.match.service.MatchServiceImpl;
import com.mrprona.dota2assitant.match.service.team.TeamServiceImpl;
import com.mrprona.dota2assitant.news.service.NewsService;
import com.mrprona.dota2assitant.news.service.NewsServiceImpl;
import com.mrprona.dota2assitant.player.dao.AccountDao;
import com.mrprona.dota2assitant.player.remote.PlayerRemoteServiceImpl;
import com.mrprona.dota2assitant.player.service.PlayerServiceImpl;
import com.mrprona.dota2assitant.ranking.service.RankingService;
import com.mrprona.dota2assitant.ranking.service.RankingServiceImpl;
import com.mrprona.dota2assitant.stream.dao.StreamDao;
import com.mrprona.dota2assitant.stream.remote.DouyuRestService;
import com.mrprona.dota2assitant.stream.remote.TwitchRemoteServiceImpl;
import com.mrprona.dota2assitant.stream.remote.TwitchRestService;
import com.mrprona.dota2assitant.stream.service.DouyuServiceImpl;
import com.mrprona.dota2assitant.stream.service.TwitchServiceImpl;
import com.mrprona.dota2assitant.trackdota.remote.TrackdotaRestService;
import com.mrprona.dota2assitant.trackdota.service.TrackdotaServiceImpl;

import java.util.ArrayList;
import java.util.List;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 10:51
 */
public class BeanContainer implements InitializingBean {
    private static final Object MONITOR = new Object();
    private static BeanContainer instance = null;

    private RestAdapter steamRestAdapter;
    private SteamService steamService;

    private RestAdapter twitchRestAdapter;
    private TwitchRestService twitchRestService;

    private RestAdapter douyuRestAdapter;
    private DouyuRestService douyuRestService;

    private RestAdapter trackdotaRestAdapter;
    private TrackdotaRestService trackdotaRestService;
    private TrackdotaServiceImpl trackdotaService;

    private CosmeticServiceImpl cosmeticService;

    private CounterRemoteEntityServiceImpl counterRemoteEntityService;
    private CounterServiceImpl counterService;

    private PlayerRemoteServiceImpl playerRemoteService;
    private PlayerServiceImpl playerService;
    private AccountDao accountDao;

    private MatchServiceImpl matchService;

    private NewsServiceImpl newsService;

    private JoinDotaRemoteServiceImpl joinDotaRemoteService;
    private JoinDotaServiceImpl joinDotaService;

    private TI4ServiceImpl ti4Service;

    private TeamServiceImpl teamService;
    private TeamDao teamDao;

    private TwitchRemoteServiceImpl twitchRemoteService;
    private TwitchServiceImpl twitchService;
    private DouyuServiceImpl douyuService;
    private StreamDao streamDao;

    private HeroServiceImpl heroService;
    private HeroDao heroDao;
    private TalentDao talentDao;

    private HeroStatsDao heroStatsDao;
    private TruepickerHeroDao truepickerHeroDao;
    private AbilityDao abilityDao;

    private ItemServiceImpl itemService;
    private ItemDao itemDao;

    private LocalUpdateService localUpdateService;

    private UpdateRemoteService updateRemoteService;
    private UpdateService updateService;

    private List<CreateTableDao> allDaos;

    private RankingServiceImpl mRankingService;



    public BeanContainer() {

        allDaos = new ArrayList<>();
        talentDao= new TalentDao();
        heroDao = new HeroDao();
        heroStatsDao = new HeroStatsDao();
        truepickerHeroDao = new TruepickerHeroDao(heroDao);
        abilityDao = new AbilityDao();
        itemDao = new ItemDao();
        accountDao = new AccountDao();
        streamDao = new StreamDao();
        teamDao = new TeamDao();


        allDaos.add(talentDao);
        allDaos.add(heroDao);
        allDaos.add(heroStatsDao);
        allDaos.add(truepickerHeroDao);
        allDaos.add(itemDao);
        allDaos.add(accountDao);
        allDaos.add(abilityDao);
        allDaos.add(streamDao);
        //todo updated_version
        allDaos.add(teamDao);



        localUpdateService = new LocalUpdateService();

        cosmeticService = new CosmeticServiceImpl();

        counterRemoteEntityService = new CounterRemoteEntityServiceImpl();
        counterService = new CounterServiceImpl();

        playerRemoteService = new PlayerRemoteServiceImpl();
        playerService = new PlayerServiceImpl();

        matchService = new MatchServiceImpl();

        newsService = new NewsServiceImpl();

        joinDotaRemoteService = new JoinDotaRemoteServiceImpl();
        joinDotaService = new JoinDotaServiceImpl();

        ti4Service = new TI4ServiceImpl();

        teamService = new TeamServiceImpl();

        twitchRemoteService = new TwitchRemoteServiceImpl();
        twitchService = new TwitchServiceImpl();

        douyuService = new DouyuServiceImpl();

        heroService = new HeroServiceImpl();

        itemService = new ItemServiceImpl();

        trackdotaService=new TrackdotaServiceImpl();

        updateRemoteService=new UpdateRemoteServiceImpl();
        updateService=new UpdateServiceImpl();

        mRankingService= new RankingServiceImpl();

    }

    public static BeanContainer getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (MONITOR) {
            if (instance == null) {
                instance = new BeanContainer();
            }
            instance.initialize();
        }
        return instance;
    }

    @Override
    public void initialize() {
        mRankingService.initialize();
        heroService.initialize();
        itemService.initialize();
        counterService.initialize();
        playerService.initialize();
        joinDotaService.initialize();
        teamService.initialize();
        twitchService.initialize();
        douyuService.initialize();
        trackdotaService.initialize();
        updateService.initialize();
    }

    public CosmeticService getCosmeticService() {
        return cosmeticService;
    }

    public LocalUpdateService getLocalUpdateService() {
        return localUpdateService;
    }

    public CounterRemoteEntityServiceImpl getCounterRemoteEntityService() {
        return counterRemoteEntityService;
    }

    public TruepickerHeroDao getTruepickerHeroDao() {
        return truepickerHeroDao;
    }

    public CounterServiceImpl getCounterService() {
        return counterService;
    }

    public PlayerServiceImpl getPlayerService() {
        return playerService;
    }

    public PlayerRemoteServiceImpl getPlayerRemoteService() {
        return playerRemoteService;
    }

    public MatchServiceImpl getMatchService() {
        return matchService;
    }

    public NewsService getNewsService() {
        return newsService;
    }

    public JoinDotaRemoteServiceImpl getJoinDotaRemoteService() {
        return joinDotaRemoteService;
    }

    public JoinDotaServiceImpl getJoinDotaService() {
        return joinDotaService;
    }

    public TI4ServiceImpl getTi4Service() {
        return ti4Service ;
    }

    public TeamServiceImpl getTeamService() {
        return teamService;
    }

    public TwitchRemoteServiceImpl getTwitchRemoteService() {
        return twitchRemoteService;
    }

    public TwitchServiceImpl getTwitchService() {
        return twitchService;
    }

    public DouyuServiceImpl getDouyuService() {
        return douyuService;
    }

    public HeroDao getHeroDao() {
        return heroDao;
    }


    public TalentDao getTalentDao() {
        return talentDao;
    }

    public HeroStatsDao getHeroStatsDao() {
        return heroStatsDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public AbilityDao getAbilityDao() {
        return abilityDao;
    }

    public List<CreateTableDao> getAllDaos() {
        return allDaos;
    }

    public HeroServiceImpl getHeroService() {
        return heroService;
    }

    public RankingServiceImpl getmRankingService() {
        return mRankingService;
    }

    public ItemServiceImpl getItemService() {
        return itemService;
    }

    public StreamDao getStreamDao() {
        return streamDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public TeamDao getTeamDao() {
        return teamDao;
    }

    public TrackdotaServiceImpl getTrackdotaService() {
        return trackdotaService;
    }

    public UpdateRemoteService getUpdateRemoteService() {
        return updateRemoteService;
    }

    public UpdateService getUpdateService() {
        return updateService;
    }

    public TwitchRestService getTwitchRestService(){
        if(twitchRestService==null){
            twitchRestService=getTwitchRestAdapter().create(TwitchRestService.class);
        }
        return twitchRestService;
    }

    public DouyuRestService getDouyuRestService(){
        if(douyuRestService==null){
            douyuRestService=getDouyuRestAdapter().create(DouyuRestService.class);
        }
        return douyuRestService;
    }

    public TrackdotaRestService getTrackdotaRestService(){
        if(trackdotaRestService==null){
            trackdotaRestService=getTrackdotaRestAdapter().create(TrackdotaRestService.class);
        }
        return trackdotaRestService;
    }

    public RestAdapter getTwitchRestAdapter() {
        if(twitchRestAdapter == null){
            twitchRestAdapter =new RestAdapter.Builder()
                    .setEndpoint("https://api.twitch.tv/")
                    .build();
        }
        return twitchRestAdapter;
    }

    public RestAdapter getDouyuRestAdapter(){
        if(douyuRestAdapter==null){
            douyuRestAdapter=new RestAdapter.Builder()
                    .setEndpoint("http://capi.douyucdn.cn/api/v1/searchNew/")
                    .build();
        }
        return douyuRestAdapter;
    }

    public RestAdapter getSteamRestAdapter(){
        if(steamRestAdapter == null){
            steamRestAdapter =new RestAdapter.Builder()
                    .setEndpoint("http://api.steampowered.com/")
                    .setRequestInterceptor(new SteamRequestInterceptor())
                    .build();
        }
        return steamRestAdapter;
    }

    public RestAdapter getTrackdotaRestAdapter(){
        if(trackdotaRestAdapter==null){
            trackdotaRestAdapter=new RestAdapter.Builder()
                    .setEndpoint("http://www.trackdota.com/data/")
                    .build();
        }
        return trackdotaRestAdapter;
    }

    public SteamService getSteamService(){
        if(steamService==null){
            steamService=getSteamRestAdapter().create(SteamService.class);
        }
        return steamService;
    }


    private class SteamRequestInterceptor implements RequestInterceptor{
        @Override
        public void intercept(RequestFacade request) {
            request.addQueryParam("key","54E61DBFB0A2D4A1B24B4C7EC5C5EFFD");
        }
    }
}
