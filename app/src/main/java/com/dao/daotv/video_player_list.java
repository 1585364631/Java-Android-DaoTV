package com.dao.daotv;

import android.util.Log;

import java.util.ArrayList;

public class video_player_list {
    private String name;
    private ArrayList<String> video_name;
    private ArrayList<String> video_url;

    public video_player_list(String name,String context){
        this.name = name;
        if (name.equals("qq")){
            this.name = "☰乾";
        }
        if (name.equals("youku")){
            this.name = "☵坤";
        }
        if (name.equals("qiyi")){
            this.name = "☶震";
        }
        if (name.equals("mgtv")){
            this.name = "☳巽";
        }
        if (name.equals("sohu")){
            this.name = "☴坎";
        }
        if (name.equals("pptv")){
            this.name = "☲离";
        }
        if (name.equals("letv")){
            this.name = "☷艮";
        }
        if (name.equals("m1905")||name.equals("leshi")||name.equals("cntv")){
            this.name = "☱兑";
        }

        this.video_name = new ArrayList<>();
        this.video_url = new ArrayList<>();
        String [] video_cache = context.split("#");
        for (int i=0;i<video_cache.length;i++){
            this.video_name.add(video_cache[i].split("\\$")[0]);
            this.video_url.add(video_cache[i].split("\\$")[1]);
        }
    }

    public String getName() {
        return name;
    }

    public String getvideoname(int i){
        return video_name.get(i);
    }

    public ArrayList<String> getVideo_name() {
        return video_name;
    }

    public ArrayList<String> getVideo_url() {
        return video_url;
    }


    public String getvideourl(int i){
        return video_url.get(i);
    }

    public void getall(){
        for (int i=0;i<video_name.size();i++){
            Log.d(video_name.get(i),video_url.get(i));
        }
    }
}
