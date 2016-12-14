package com.badr.infodota;

import com.badr.infodota.base.dao.CreateTableDao;
import com.badr.infodota.base.remote.SteamService;
import com.badr.infodota.base.remote.update.UpdateRemoteService;
import com.badr.infodota.base.remote.update.UpdateRemoteServiceImpl;
import com.badr.infodota.base.service.LocalUpdateService;
import com.badr.infodota.base.service.ti4.TI4ServiceImpl;
import com.badr.infodota.base.service.update.UpdateService;
import com.badr.infodota.base.service.update.UpdateServiceImpl;
import com.badr.infodota.cosmetic.service.CosmeticService;
import com.badr.infodota.cosmetic.service.CosmeticServiceImpl;
import com.badr.infodota.counter.dao.TruepickerHeroDao;
import com.badr.infodota.counter.remote.CounterRemoteEntityServiceImpl;
import com.badr.infodota.counter.service.CounterServiceImpl;
import com.badr.infodota.hero.dao.AbilityDao;
import com.badr.infodota.hero.dao.HeroDao;
import com.badr.infodota.hero.dao.HeroStatsDao;
import com.badr.infodota.hero.service.HeroServiceImpl;
import com.badr.infodota.item.dao.ItemDao;
import com.badr.infodota.item.service.ItemServiceImpl;
import com.badr.infodota.joindota.remote.JoinDotaRemoteServiceImpl;
import com.badr.infodota.joindota.service.JoinDotaServiceImpl;
import com.badr.infodota.match.dao.TeamDao;
import com.badr.infodota.match.service.MatchServiceImpl;
import com.badr.infodota.match.service.team.TeamServiceImpl;
import com.badr.infodota.news.service.NewsService;
import com.badr.infodota.news.service.NewsServiceImpl;
import com.badr.infodota.player.dao.AccountDao;
import com.badr.infodota.player.remote.PlayerRemoteServiceImpl;
import com.badr.infodota.player.service.PlayerServiceImpl;
import com.badr.infodota.stream.dao.StreamDao;
import com.badr.infodota.stream.remote.DouyuRestService;
import com.badr.infodota.stream.remote.TwitchRemoteServiceImpl;
import com.badr.infodota.stream.remote.TwitchRestService;
import com.badr.infodota.stream.service.DouyuServiceImpl;
import com.badr.infodota.stream.service.TwitchServiceImpl;
import com.badr.infodota.trackdota.remote.TrackdotaRestService;
import com.badr.infodota.trackdota.service.TrackdotaServiceImpl;

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
    private HeroStatsDao heroStatsDao;
    private TruepickerHeroDao truepickerHeroDao;
    private AbilityDao abilityDao;

    private ItemServiceImpl itemService;
    private ItemDao itemDao;

    private LocalUpdateService localUpdateService;

    private UpdateRemoteService updateRemoteService;
    private UpdateService updateService;

    private List<CreateTableDao> allDaos;

    public BeanContainer() {

        allDaos = new ArrayList<>();

        heroDao = new HeroDao();
        heroStatsDao = new HeroStatsDao();
        truepickerHeroDao = new TruepickerHeroDao(heroDao);
        abilityDao = new AbilityDao();
        itemDao = new ItemDao();
        accountDao = new AccountDao();
        streamDao = new StreamDao();
        teamDao = new TeamDao();

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
    }

    public static BeanContainer getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (MONITOR) {
            if (instance == null) {
                instance = new BeanContainer();
                instance.initialize();
            }
        }
        return instance;
    }

    @Override
    public void initialize() {
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
        return ti4Service;
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
                    .setEndpoint("http://api.douyutv.com/api/client/")
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
