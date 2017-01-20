package com.mrprona.dota2assitant.hero.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mrprona.dota2assitant.base.dao.GeneralDaoImpl;
import com.mrprona.dota2assitant.hero.api.TalentTree;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 17:05
 */
public class TalentDao extends GeneralDaoImpl<TalentTree> {
    public static final String TABLE_NAME = "talent_tree";
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
    public static final String COLUMN_NAME = "hero_name";
    public static final String COLUMN_25LEFT = "col25left";
    public static final String COLUMN_20LEFT = "col20left";
    public static final String COLUMN_15LEFT = "col15left";
    public static final String COLUMN_10LEFT = "col10left";
    public static final String COLUMN_25RIGHT = "col25right";
    public static final String COLUMN_20RIGHT = "col20right";
    public static final String COLUMN_15RIGHT = "col15right";
    public static final String COLUMN_10RIGHT = "col10right";


    private static final String CREATE_TABLE_QUERY = " ( " +
            COLUMN_ID + " integer not null, " +
            COLUMN_NAME + " text default null," +
            COLUMN_25LEFT + " text default null," +
            COLUMN_20LEFT + " text default null," +
            COLUMN_15LEFT + " text default null," +
            COLUMN_10LEFT + " text default null," +
            COLUMN_25RIGHT + " text default null," +
            COLUMN_20RIGHT + " text default null," +
            COLUMN_15RIGHT + " text default null," +
            COLUMN_10RIGHT + " text default null);";

    private static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_25LEFT,
            COLUMN_20LEFT,
            COLUMN_15LEFT,
            COLUMN_10LEFT,
            COLUMN_25RIGHT,
            COLUMN_20RIGHT,
            COLUMN_15RIGHT,
            COLUMN_10RIGHT
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
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("drop table if exists " + getTableName());
        onCreate(database);
    }

    @Override
    public TalentTree cursorToEntity(Cursor cursor, int index) {
        TalentTree entity = new TalentTree();
        int i = index;
        entity.setId(cursor.getInt(i));
        i++;
        entity.setName(cursor.getString(i));
        i++;
        entity.setLv25Left(cursor.getString(i));
        i++;
        entity.setLv20Left(cursor.getString(i));
        i++;
        entity.setLv15Left(cursor.getString(i));
        i++;
        entity.setLv10Left(cursor.getString(i));
        i++;
        entity.setLv25Right(cursor.getString(i));
        i++;
        entity.setLv20Right(cursor.getString(i));
        i++;
        entity.setLv15Right(cursor.getString(i));
        i++;
        entity.setLv10Right(cursor.getString(i));
        return entity;
    }

    @Override
    protected ContentValues entityToContentValues(TalentTree entity) {
        ContentValues values = super.entityToContentValues(entity);
        values.put(COLUMN_NAME, entity.getName());
        values.put(COLUMN_25LEFT, entity.getLv25Left());
        values.put(COLUMN_20LEFT, entity.getLv20Left());
        values.put(COLUMN_15LEFT, entity.getLv15Left());
        values.put(COLUMN_10LEFT, entity.getLv10Left());
        values.put(COLUMN_25RIGHT, entity.getLv25Right());
        values.put(COLUMN_20RIGHT, entity.getLv20Right());
        values.put(COLUMN_15RIGHT, entity.getLv15Right());
        values.put(COLUMN_10RIGHT, entity.getLv10Right());
        return values;
    }

    public TalentTree getShortTalentsTree(SQLiteDatabase database, long id) {
        Cursor cursor = database.query(getTableName(), new String[]{
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_25LEFT,
                COLUMN_20LEFT,
                COLUMN_15LEFT,
                COLUMN_10LEFT,
                COLUMN_25RIGHT,
                COLUMN_20RIGHT,
                COLUMN_15RIGHT,
                COLUMN_10RIGHT
        }, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        try {
            TalentTree entity = null;
            if (cursor.moveToFirst()) {
                entity = new TalentTree();
                entity.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                entity.setLv25Left(cursor.getString(cursor.getColumnIndex(COLUMN_25LEFT)));
                entity.setLv20Left(cursor.getString(cursor.getColumnIndex(COLUMN_20LEFT)));
                entity.setLv15Left(cursor.getString(cursor.getColumnIndex(COLUMN_15LEFT)));
                entity.setLv10Left(cursor.getString(cursor.getColumnIndex(COLUMN_10LEFT)));
                entity.setLv25Right(cursor.getString(cursor.getColumnIndex(COLUMN_25RIGHT)));
                entity.setLv20Right(cursor.getString(cursor.getColumnIndex(COLUMN_20RIGHT)));
                entity.setLv15Right(cursor.getString(cursor.getColumnIndex(COLUMN_15RIGHT)));
                entity.setLv10Right(cursor.getString(cursor.getColumnIndex(COLUMN_10RIGHT)));
            }
            return entity;
        } finally {
            cursor.close();
        }
    }

}
