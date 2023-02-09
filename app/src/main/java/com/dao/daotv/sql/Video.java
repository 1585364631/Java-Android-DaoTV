package com.dao.daotv.sql;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Video {

    @Id(autoincrement = true)
    private Long id;
    private String video_id;
    private String video_name;
    private String video_play;
    private String video_img;
    private int video_player;
    private int video_player_list;
    private float video_mark;
    @Generated(hash = 230568818)
    public Video(Long id, String video_id, String video_name, String video_play,
            String video_img, int video_player, int video_player_list,
            float video_mark) {
        this.id = id;
        this.video_id = video_id;
        this.video_name = video_name;
        this.video_play = video_play;
        this.video_img = video_img;
        this.video_player = video_player;
        this.video_player_list = video_player_list;
        this.video_mark = video_mark;
    }
    @Generated(hash = 237528154)
    public Video() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getVideo_id() {
        return this.video_id;
    }
    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
    public String getVideo_name() {
        return this.video_name;
    }
    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }
    public String getVideo_play() {
        return this.video_play;
    }
    public void setVideo_play(String video_play) {
        this.video_play = video_play;
    }
    public String getVideo_img() {
        return this.video_img;
    }
    public void setVideo_img(String video_img) {
        this.video_img = video_img;
    }
    public int getVideo_player() {
        return this.video_player;
    }
    public void setVideo_player(int video_player) {
        this.video_player = video_player;
    }
    public int getVideo_player_list() {
        return this.video_player_list;
    }
    public void setVideo_player_list(int video_player_list) {
        this.video_player_list = video_player_list;
    }
    public float getVideo_mark() {
        return this.video_mark;
    }
    public void setVideo_mark(float video_mark) {
        this.video_mark = video_mark;
    }

}
