package com.badr.infodota.base.dao;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 11:32
 */
public interface CreateTableDao {

    void onCreate(SQLiteDatabase database);

    void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion);
}
