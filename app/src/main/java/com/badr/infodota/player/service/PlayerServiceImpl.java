package com.badr.infodota.player.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.dao.DatabaseManager;
import com.badr.infodota.player.api.Unit;
import com.badr.infodota.player.dao.AccountDao;
import com.badr.infodota.player.remote.PlayerRemoteService;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 16:01
 */
public class PlayerServiceImpl implements PlayerService {
    private PlayerRemoteService service;
    private AccountDao accountDao;

    @Override
    public void initialize() {
        BeanContainer container = BeanContainer.getInstance();
        service = container.getPlayerRemoteService();
        accountDao = container.getAccountDao();
    }

    @Override
    public Unit.List loadAccounts(List<Long> ids) {
        try {
            Unit.List result = service.getAccounts(ids);
            if (result != null) {
                return result;
            } else {
                String message = "Failed to get players";
                Log.e(PlayerServiceImpl.class.getName(), message);
            }
        } catch (Exception e) {
            String message = "Failed to get players, cause: " + e.getMessage();
            Log.e(PlayerServiceImpl.class.getName(), message, e);
        }
        return null;
    }

    @Override
    public Unit.List loadAccounts(String name) {
        try {
            Unit.List result = service.getAccounts(name);
            if (result != null) {
                return result;
            } else {
                String message = "Failed to get players";
                Log.e(PlayerServiceImpl.class.getName(), message);
            }
        } catch (Exception e) {
            String message = "Failed to get players, cause: " + e.getMessage();
            Log.e(PlayerServiceImpl.class.getName(), message, e);
        }
        return null;
    }

    @Override
    public Unit.List loadSteamFriends(long id) {
        try {
            return service.getSteamFriends(id);
        } catch (Exception e) {
            String message = "Failed to get friends, cause: " + e.getMessage();
            Log.e(PlayerServiceImpl.class.getName(), message, e);
            return null;
        }
    }

    @Override
    public void saveAccount(Context context, Unit unit) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            accountDao.saveOrUpdate(database, unit);
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public Unit getAccountById(Context context, long id) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            return accountDao.getById(database, id);
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public void deleteAccount(Context context, Unit unit) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            accountDao.delete(database, unit);
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public Unit.List getSearchedAccounts(Context context) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            return new Unit.List(accountDao.getSearchedEntities(database));
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public Unit.List getAccountsByGroup(Context context, Unit.Groups group) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            return new Unit.List(accountDao.getEntitiesByGroup(database, group));
        } finally {
            manager.closeDatabase();
        }
    }
}
