package com.badr.infodota.counter.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.badr.infodota.base.dao.GeneralDaoImpl;
import com.badr.infodota.counter.api.TruepickerHero;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.dao.HeroDao;

import java.util.List;

/**
 * Created by ABadretdinov
 * 21.08.2015
 * 11:57
 */
public class TruepickerHeroDao extends GeneralDaoImpl<TruepickerHero> {
    public static final String TABLE_NAME = "truepicker";
    public static final String COLUMN_TRUEPICKER_ID = "tpId";
    private static final String CREATE_TABLE_QUERY = " (" +
            COLUMN_ID + " integer not null, " +
            COLUMN_TRUEPICKER_ID + " integer not null " +
            ");";
    private static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_TRUEPICKER_ID
    };

    private HeroDao mHeroDao;

    public TruepickerHeroDao(HeroDao heroDao) {
        super();
        this.mHeroDao = heroDao;
    }

    @Override
    protected String getNoTableNameDataBaseCreateQuery() {
        return CREATE_TABLE_QUERY;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getAllColumns() {
        return ALL_COLUMNS;
    }

    @Override
    public TruepickerHero cursorToEntity(Cursor cursor, int index) {
        TruepickerHero entity = new TruepickerHero();
        int i = index;
        entity.setId(cursor.getLong(i));
        i++;
        entity.setTpId(cursor.getLong(i));
        return entity;
    }

    @Override
    protected ContentValues entityToContentValues(TruepickerHero entity) {
        ContentValues values = super.entityToContentValues(entity);
        values.put(COLUMN_TRUEPICKER_ID, entity.getTpId());
        return values;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        super.onCreate(database);
        initTruePickerIds(database);
    }

    @Override
    public List<TruepickerHero> getAllEntities(SQLiteDatabase database) {
        Cursor cursor = database.query(true, getTableName(), getAllColumns(), null, null, null, null, null, null);
        TruepickerHero.List heroes = new TruepickerHero.List();
        try {
            if (cursor.moveToFirst()) {
                do {
                    long tpIp = cursor.getLong(cursor.getColumnIndex(COLUMN_TRUEPICKER_ID));
                    Hero hero = mHeroDao.getById(database, cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                    TruepickerHero tpHero = new TruepickerHero(hero);
                    tpHero.setTpId(tpIp);
                    heroes.add(tpHero);
                } while (cursor.moveToNext());
            }
            return heroes;
        } finally {
            cursor.close();
        }
    }

    @Override
    public TruepickerHero getById(SQLiteDatabase database, long id) {
        Cursor cursor = database.query(true, getTableName(), getAllColumns(), COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        try {
            TruepickerHero tpHero = null;
            if (cursor.moveToFirst()) {
                long tpIp = cursor.getLong(cursor.getColumnIndex(COLUMN_TRUEPICKER_ID));
                Hero hero = mHeroDao.getById(database, id);
                tpHero = new TruepickerHero(hero);
                tpHero.setTpId(tpIp);
            }
            return tpHero;
        } finally {
            cursor.close();
        }
    }

    public TruepickerHero getByTpId(SQLiteDatabase database, long tpId) {
        Cursor cursor = database.query(true, getTableName(), getAllColumns(), COLUMN_TRUEPICKER_ID + "=?", new String[]{String.valueOf(tpId)}, null, null, null, null);
        try {
            TruepickerHero tpHero = null;
            if (cursor.moveToFirst()) {
                long tpIp = cursor.getLong(cursor.getColumnIndex(COLUMN_TRUEPICKER_ID));
                Hero hero = mHeroDao.getById(database, cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                tpHero = new TruepickerHero(hero);
                tpHero.setTpId(tpIp);
            }
            return tpHero;
        } finally {
            cursor.close();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("drop table if exists " + getTableName());
        onCreate(database);
    }

    private void initTruePickerIds(SQLiteDatabase db) {
        bindTruepickerId(db, 102, 72);//abaddon
        bindTruepickerId(db, 73, 10);//alchemist
        bindTruepickerId(db, 68, 103);//ancient apparition
        bindTruepickerId(db, 1, 22);//anti-mage
        bindTruepickerId(db, 2, 58);//axe
        bindTruepickerId(db, 3, 89);//bane
        bindTruepickerId(db, 65, 102);//batrider
        bindTruepickerId(db, 38, 5);//beastmaster
        bindTruepickerId(db, 4, 73);//bloodseaker
        bindTruepickerId(db, 62, 33);//bounty hunter
        bindTruepickerId(db, 78, 11);//brewmaster
        bindTruepickerId(db, 99, 16);//bristleback
        bindTruepickerId(db, 61, 81);//broodmother
        bindTruepickerId(db, 96, 14);//centaur
        bindTruepickerId(db, 81, 69);//chaos
        bindTruepickerId(db, 66, 51);//chen
        bindTruepickerId(db, 56, 80);//clinkz
        bindTruepickerId(db, 51, 7);//clockwerk
        bindTruepickerId(db, 5, 40);//crystal maiden
        bindTruepickerId(db, 55, 101);//dark seer
        bindTruepickerId(db, 50, 99);//dazzle
        bindTruepickerId(db, 43, 97);//death prophet
        bindTruepickerId(db, 87, 55);//disruptor
        bindTruepickerId(db, 69, 66);//doom
        bindTruepickerId(db, 49, 6);//Dragon Knight
        bindTruepickerId(db, 6, 23);//drow ranger
        bindTruepickerId(db, 107, 20);//earth spirit
        bindTruepickerId(db, 7, 1);//earthshaker
        bindTruepickerId(db, 103, 18);//elder titan
        bindTruepickerId(db, 106, 39);//ember spirit
        bindTruepickerId(db, 58, 49);//enchantress
        bindTruepickerId(db, 33, 93);//enigma
        bindTruepickerId(db, 41, 77);//void
        bindTruepickerId(db, 72, 35);//gyrocopter
        bindTruepickerId(db, 59, 9);//huskar
        bindTruepickerId(db, 74, 104);//invoker
        bindTruepickerId(db, 91, 13);//io
        bindTruepickerId(db, 64, 50);//jakiro
        bindTruepickerId(db, 8, 24);//juggernaut
        bindTruepickerId(db, 90, 56);//keeper of the light
        bindTruepickerId(db, 23, 4);//Kunkka
        bindTruepickerId(db, 104, 19);//legion commander
        bindTruepickerId(db, 52, 100);//leshrac
        bindTruepickerId(db, 31, 90);//lich
        bindTruepickerId(db, 54, 64);//LifeStealer
        bindTruepickerId(db, 25, 45);//lina
        bindTruepickerId(db, 26, 91);//lion
        bindTruepickerId(db, 80, 36);//lone druid
        bindTruepickerId(db, 48, 32);//luna
        bindTruepickerId(db, 77, 68);//lycan
        bindTruepickerId(db, 97, 71);//magnus
        bindTruepickerId(db, 94, 87);//medusa
        bindTruepickerId(db, 82, 84);//meepo
        bindTruepickerId(db, 9, 25);//mirana
        bindTruepickerId(db, 10, 26);//morphling
        bindTruepickerId(db, 89, 37);//naga siren
        bindTruepickerId(db, 53, 48);//nature's prophet
        bindTruepickerId(db, 36, 94);//necrophos
        bindTruepickerId(db, 60, 65);//Night Stalker
        bindTruepickerId(db, 88, 85);//nyx assassin
        bindTruepickerId(db, 84, 53);//ogre magi
        bindTruepickerId(db, 57, 8);//omniknight
        bindTruepickerId(db, 76, 105);//Outworld devourer
        bindTruepickerId(db, 44, 78);//phantom assasin
        bindTruepickerId(db, 12, 27);//phantom lancer
        bindTruepickerId(db, 110, 21);//phoenix
        bindTruepickerId(db, 13, 41);//puck
        bindTruepickerId(db, 14, 59);//pudge
        bindTruepickerId(db, 45, 98);//pugna
        bindTruepickerId(db, 39, 96);//qeen of pain
        bindTruepickerId(db, 15, 75);//razor
        bindTruepickerId(db, 32, 29);//riki
        bindTruepickerId(db, 86, 54);//rubick
        bindTruepickerId(db, 16, 60);//sand king
        bindTruepickerId(db, 79, 106);//shadow demon
        bindTruepickerId(db, 11, 74);//shadow fiend
        bindTruepickerId(db, 27, 46);//shadow shaman
        bindTruepickerId(db, 75, 52);//silencer
        bindTruepickerId(db, 101, 57);//skywrath mage
        bindTruepickerId(db, 28, 61);//slardar
        bindTruepickerId(db, 93, 86);//slark
        bindTruepickerId(db, 35, 30);//sniper
        bindTruepickerId(db, 67, 83);//spectre
        bindTruepickerId(db, 71, 67);//spirit breaker
        bindTruepickerId(db, 17, 42);//storm spirit
        bindTruepickerId(db, 18, 2);//sven
        bindTruepickerId(db, 46, 31);//templar assasin
        bindTruepickerId(db, 109, 88);//terrorblade
        bindTruepickerId(db, 29, 62);//tidehunter
        bindTruepickerId(db, 98, 15);//timbersaw
        bindTruepickerId(db, 34, 47);//tinker
        bindTruepickerId(db, 19, 3);//tiny
        bindTruepickerId(db, 83, 12);//treant protector
        bindTruepickerId(db, 95, 38);//troll warlord
        bindTruepickerId(db, 100, 17);//tuskar
        bindTruepickerId(db, 85, 70);//undying
        bindTruepickerId(db, 70, 34);//ursa
        bindTruepickerId(db, 20, 28);//vengeful spirit
        bindTruepickerId(db, 40, 76);//venomancer
        bindTruepickerId(db, 47, 79);//viper
        bindTruepickerId(db, 92, 107);//visage
        bindTruepickerId(db, 37, 95);//warlock
        bindTruepickerId(db, 63, 82);//weaver
        bindTruepickerId(db, 21, 43);//windranger
        bindTruepickerId(db, 30, 92);//witch doctor
        bindTruepickerId(db, 42, 63);//wraith king
        bindTruepickerId(db, 22, 44);//zeus
        bindTruepickerId(db, 105, 109);//techies
        bindTruepickerId(db, 111, 111);//oracle
        bindTruepickerId(db, 112, 112);//wintern wyvern
    }

    public void bindTruepickerId(SQLiteDatabase database, long heroId, long tpId) {
        unbindTruepickerId(database, heroId);
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, heroId);
        values.put(COLUMN_TRUEPICKER_ID, tpId);
        database.insert(getTableName(), null, values);
    }

    public void unbindTruepickerId(SQLiteDatabase database, long heroId) {
        database.delete(getTableName(), COLUMN_ID + "=?", new String[]{String.valueOf(heroId)});
    }
}
