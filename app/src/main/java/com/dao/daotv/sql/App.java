package com.dao.daotv.sql;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.dao.daotv.dao.dao_celestial;
import com.google.android.exoplayer2.util.Log;
import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.cache.ProxyCacheManager;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;

import static com.liulishuo.filedownloader.FileDownloader.setupOnApplicationOnCreate;

public class App extends Application {
    private DaoMaster.DevOpenHelper my_dao;
    private static App app = null;
    private SQLiteDatabase writabledatabase;
    private AtomicReference<DaoMaster> daoMaster;
    private DaoSession daoSession;


    public void onCreate(){
        super.onCreate();



        setupOnApplicationOnCreate(this);
        App.app = this;
        my_dao = new DaoMaster.DevOpenHelper(this, "video_history.db", null);
        writabledatabase = my_dao.getWritableDatabase();
        writabledatabase.disableWriteAheadLogging();
        daoMaster = new AtomicReference<>(new DaoMaster(writabledatabase));
        daoSession = daoMaster.get().newSession();
        VideoDao videoDao = daoSession.getVideoDao();
        dao_celestialDao dao_celestialDao = daoSession.getDao_celestialDao();
        if (dao_celestialDao.queryBuilder().build().list().size() == 0){
            dao_celestial dao_celestial = new dao_celestial(null,"dao_gbook","0");
            dao_celestialDao.insert(dao_celestial);
            dao_celestialDao.insert(new dao_celestial(null,"dao_device","0"));
        }
    }


    public static App getApp() {
        return app;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
