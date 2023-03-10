package com.dao.daotv.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "VIDEO".
*/
public class VideoDao extends AbstractDao<Video, Long> {

    public static final String TABLENAME = "VIDEO";

    /**
     * Properties of entity Video.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Video_id = new Property(1, String.class, "video_id", false, "VIDEO_ID");
        public final static Property Video_name = new Property(2, String.class, "video_name", false, "VIDEO_NAME");
        public final static Property Video_play = new Property(3, String.class, "video_play", false, "VIDEO_PLAY");
        public final static Property Video_img = new Property(4, String.class, "video_img", false, "VIDEO_IMG");
        public final static Property Video_player = new Property(5, int.class, "video_player", false, "VIDEO_PLAYER");
        public final static Property Video_player_list = new Property(6, int.class, "video_player_list", false, "VIDEO_PLAYER_LIST");
        public final static Property Video_mark = new Property(7, float.class, "video_mark", false, "VIDEO_MARK");
    }


    public VideoDao(DaoConfig config) {
        super(config);
    }
    
    public VideoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"VIDEO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"VIDEO_ID\" TEXT," + // 1: video_id
                "\"VIDEO_NAME\" TEXT," + // 2: video_name
                "\"VIDEO_PLAY\" TEXT," + // 3: video_play
                "\"VIDEO_IMG\" TEXT," + // 4: video_img
                "\"VIDEO_PLAYER\" INTEGER NOT NULL ," + // 5: video_player
                "\"VIDEO_PLAYER_LIST\" INTEGER NOT NULL ," + // 6: video_player_list
                "\"VIDEO_MARK\" REAL NOT NULL );"); // 7: video_mark
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"VIDEO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Video entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String video_id = entity.getVideo_id();
        if (video_id != null) {
            stmt.bindString(2, video_id);
        }
 
        String video_name = entity.getVideo_name();
        if (video_name != null) {
            stmt.bindString(3, video_name);
        }
 
        String video_play = entity.getVideo_play();
        if (video_play != null) {
            stmt.bindString(4, video_play);
        }
 
        String video_img = entity.getVideo_img();
        if (video_img != null) {
            stmt.bindString(5, video_img);
        }
        stmt.bindLong(6, entity.getVideo_player());
        stmt.bindLong(7, entity.getVideo_player_list());
        stmt.bindDouble(8, entity.getVideo_mark());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Video entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String video_id = entity.getVideo_id();
        if (video_id != null) {
            stmt.bindString(2, video_id);
        }
 
        String video_name = entity.getVideo_name();
        if (video_name != null) {
            stmt.bindString(3, video_name);
        }
 
        String video_play = entity.getVideo_play();
        if (video_play != null) {
            stmt.bindString(4, video_play);
        }
 
        String video_img = entity.getVideo_img();
        if (video_img != null) {
            stmt.bindString(5, video_img);
        }
        stmt.bindLong(6, entity.getVideo_player());
        stmt.bindLong(7, entity.getVideo_player_list());
        stmt.bindDouble(8, entity.getVideo_mark());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Video readEntity(Cursor cursor, int offset) {
        Video entity = new Video( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // video_id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // video_name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // video_play
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // video_img
            cursor.getInt(offset + 5), // video_player
            cursor.getInt(offset + 6), // video_player_list
            cursor.getFloat(offset + 7) // video_mark
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Video entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setVideo_id(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setVideo_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setVideo_play(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setVideo_img(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setVideo_player(cursor.getInt(offset + 5));
        entity.setVideo_player_list(cursor.getInt(offset + 6));
        entity.setVideo_mark(cursor.getFloat(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Video entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Video entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Video entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
