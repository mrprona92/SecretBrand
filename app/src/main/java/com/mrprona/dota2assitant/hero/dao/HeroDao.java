package com.mrprona.dota2assitant.hero.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.mrprona.dota2assitant.base.dao.GeneralDaoImpl;
import com.mrprona.dota2assitant.hero.api.Hero;
import com.mrprona.dota2assitant.hero.api.TalentTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 11:30
 */
public class HeroDao extends GeneralDaoImpl<Hero> {

    public static final String TABLE_NAME = "hero";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LOCALIZED_NAME = "localized_name";

    public static final String COLUMN_ITEM_ID = "item_id";



    public static final String COLUMN_NAME_TALENT = "hero_name";
    public static final String TABLE_TALENT_NAME = "talent_tree";
    /*
    * 0- id
    * 1- name
    * 2- 25left
    * 3- 20left
    * 4- 15left
    * 5- 10left
    * 6- 25right
    * 7- 20right
    * 8- 15right
    * 9- 10right

    * */
    public static final String COLUMN_TALENT_TREE_NAME = "hero_name";
    public static final String COLUMN_25LEFT = "col25left";
    public static final String COLUMN_20LEFT = "col20left";
    public static final String COLUMN_15LEFT = "col15left";
    public static final String COLUMN_10LEFT = "col10left";
    public static final String COLUMN_25RIGHT = "col25right";
    public static final String COLUMN_20RIGHT = "col20right";
    public static final String COLUMN_15RIGHT = "col15right";
    public static final String COLUMN_10RIGHT = "col10right";





    private static final String CREATE_TABLE_QUERY = "( "
            + COLUMN_ID + " integer primary key, "
            + COLUMN_NAME + " text default null,"
            + COLUMN_LOCALIZED_NAME + " text default null);";
    private static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_LOCALIZED_NAME
    };

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
    public Hero cursorToEntity(Cursor cursor, int index) {
        Hero entity = new Hero();
        int i = index;
        entity.setId(cursor.getLong(i));
        i++;
        entity.setName(cursor.getString(i));
        i++;
        entity.setLocalizedName(cursor.getString(i));
        return entity;
    }

    @Override
    protected ContentValues entityToContentValues(Hero entity) {
        ContentValues values = super.entityToContentValues(entity);
        if (!TextUtils.isEmpty(entity.getName())) {
            values.put(COLUMN_NAME, entity.getName());
        } else {
            values.putNull(COLUMN_NAME);
        }
        if (!TextUtils.isEmpty(entity.getLocalizedName())) {
            values.put(COLUMN_LOCALIZED_NAME, entity.getLocalizedName());
        } else {
            values.putNull(COLUMN_LOCALIZED_NAME);
        }
        return values;
    }

    @Override
    public String getDefaultOrderColumns() {
        return COLUMN_LOCALIZED_NAME;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("drop table if exists " + getTableName());
        onCreate(database);
    }

    public Hero getExactEntity(SQLiteDatabase database, String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        String lower = name.toLowerCase();
        Cursor cursor = database.query(getTableName(), getAllColumns(), COLUMN_NAME + " like ? or " + COLUMN_LOCALIZED_NAME + " like ?", new String[]{lower, lower}, null, null, getDefaultOrderColumns());
        try {
            Hero entity = null;
            if (cursor.moveToFirst()) {
                entity = cursorToEntity(cursor, 0);
            }
            return entity;
        } finally {
            cursor.close();
        }
    }

    public List<Hero> getEntities(SQLiteDatabase database, String name) {
        if (TextUtils.isEmpty(name)) {
            return getAllEntities(database);
        }
        String lower = "%" + name.toLowerCase() + "%";
        Cursor cursor = database.query(getTableName(), getAllColumns(), COLUMN_NAME + " like ? or " + COLUMN_LOCALIZED_NAME + " like ?", new String[]{lower, lower}, null, null, getDefaultOrderColumns());
        try {
            List<Hero> entities = new ArrayList<Hero>(cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    Hero entity = cursorToEntity(cursor, 0);
                    entities.add(entity);
                } while (cursor.moveToNext());
            }
            return entities;
        } finally {
            cursor.close();
        }
    }

    public static void bindItems(SQLiteDatabase database, TalentTree talentTree) {
        unbindTalentTree(database, talentTree);
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, talentTree.getId());
        values.put(COLUMN_NAME_TALENT, talentTree.getName());
        values.put(COLUMN_25LEFT, talentTree.getLv25Left());
        values.put(COLUMN_20LEFT, talentTree.getLv20Left());
        values.put(COLUMN_15LEFT, talentTree.getLv15Left());
        values.put(COLUMN_10LEFT, talentTree.getLv10Left());
        values.put(COLUMN_25RIGHT, talentTree.getLv25Right());
        values.put(COLUMN_20RIGHT, talentTree.getLv20Right());
        values.put(COLUMN_15RIGHT, talentTree.getLv15Right());
        values.put(COLUMN_10RIGHT, talentTree.getLv10Right());
        database.insert(TABLE_TALENT_NAME, null, values);

    }

    public static void unbindTalentTree(SQLiteDatabase database, TalentTree talentTree) {
        database.delete(TABLE_TALENT_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(talentTree.getId())});
    }
}
