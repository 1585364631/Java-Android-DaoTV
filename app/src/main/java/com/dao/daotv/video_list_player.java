package com.dao.daotv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dao.daotv.function.function_qmdj;
import com.dao.daotv.sql.App;
import com.dao.daotv.sql.DaoSession;
import com.dao.daotv.sql.Video;
import com.dao.daotv.sql.VideoDao;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class video_list_player extends AppCompatActivity {

    List<video_player_list> video_cache = new ArrayList<>();
    ArrayList<String> video_playername;
    TextView index_title;
    private playerAdapter1 playerAdapter_now;
    private ProgressBar progressBar_round;
    public static int list_onclice = 0;
    public static int list_onclice1 = 0;
    Intent player_face;
    private App app;
    private DaoSession daoSession;
    private VideoDao videoDao;
    private Video video;
    private Long cache_id;
    RadioGroup radioGroup;
    String Video_cache_id;
    private int ls_1 = 0;


    public void sql(){
        app = App.getApp();
        daoSession = app.getDaoSession();
        videoDao = daoSession.getVideoDao();
        video = videoDao.queryBuilder().where(VideoDao.Properties.Video_id.eq(public_class.Video_id)).unique();
        cache_id = null;
        if (video != null){
            list_onclice = video.getVideo_player();
            list_onclice1 = video.getVideo_player_list();
            cache_id = video.getId();
        }
        video = new Video();
    }

    public void sql_up(){
        if (cache_id != null){
            videoDao.deleteByKeyInTx(cache_id);
        }
        videoDao.insertOrReplace(video);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list_player);

        list_onclice=0;
        list_onclice1=99999;

        radioGroup = (RadioGroup)findViewById(R.id.player_more_button);
        radioGroup.setOnCheckedChangeListener(new MyRadioButtonListener());
        if (public_class.player_daoism == 0){
            radioGroup.check(R.id.player_button0);
        }
        if (public_class.player_daoism == 1){
            radioGroup.check(R.id.player_button1);
        }


        index_title = (TextView)findViewById(R.id.video_list_player_title);
        if (public_class.dao_gbook.equals("1")){
            index_title.setText("天道☯模式");
            index_title.setBackgroundColor(R.color.black);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    public_class.setGradientFont(index_title,"#FFC107","#FF9800");
                }
            });
        }

        //数据库

        new Thread(){
            @Override
            public void run() {
                super.run();
                sql();
            }
        }.start();

        player_face = new Intent(video_list_player.this,video_player.class);
        progressBar_round = (ProgressBar)findViewById(R.id.progress_round);


        public_class.http_get(public_class.url_api + "?ac=detail&ids=" + public_class.Video_id, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json_text = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = new JSONArray(new JSONObject(json_text).getString("list"));
                            JSONObject jsonObject = new JSONObject(jsonArray.get(0).toString());

                            //图片加载
                            Glide.with(video_list_player.this).load(jsonObject.getString("vod_pic")).into((ImageView)findViewById(R.id.video_list_player_img));
                            //ID
                            Video_cache_id = jsonObject.getString("vod_id");
                            //影片名
                            TextView textView_name = (TextView)findViewById(R.id.video_list_player_name);
                            textView_name.setText(jsonObject.getString("vod_name"));
                            public_class.player_video_name = jsonObject.getString("vod_name");
                            //评分
                            TextView textView_number = (TextView)findViewById(R.id.video_list_player_number);
                            textView_number.setText(jsonObject.getString("vod_score"));
                            ProgressBar progressBar = (ProgressBar)findViewById(R.id.video_list_player_progress);
                            progressBar.setProgress((int) (jsonObject.getDouble("vod_score")*10));
                            //状态
                            TextView textView_class = (TextView)findViewById(R.id.video_list_player_class);
                            textView_class.setText(jsonObject.getString("vod_class"));
                            TextView textView_area = (TextView)findViewById(R.id.video_list_player_area);
                            textView_area.setText(jsonObject.getString("vod_area"));
                            TextView textView_year = (TextView)findViewById(R.id.video_list_player_year);
                            textView_year.setText(jsonObject.getString("vod_year"));
                            TextView textView_remarks = (TextView)findViewById(R.id.video_list_player_remarks);
                            textView_remarks.setText(jsonObject.getString("vod_remarks"));
                            TextView textView_time = (TextView)findViewById(R.id.video_list_player_time);
                            textView_time.setText((jsonObject.getString("vod_time").split("-")[1] + "/"+jsonObject.getString("vod_time").split("-")[2]).split(" ")[0]);
                            TextView textView_actor = (TextView)findViewById(R.id.video_list_player_actor);
                            textView_actor.setText("主演: " + jsonObject.getString("vod_actor"));
                            if (jsonObject.getString("vod_actor").length()==0){ textView_actor.setText("主演: 未知"); }
                            TextView textView_director = (TextView)findViewById(R.id.video_list_player_director);
                            textView_director.setText("导演: " + jsonObject.getString("vod_director"));
                            if (jsonObject.getString("vod_director").length()==0){textView_director.setText("导演: 未知");}
                            //剧情简介
                            TextView textView_blurb = (TextView)findViewById(R.id.video_list_player_blurb);
                            textView_blurb.setText(jsonObject.getString("vod_content"));
                            if(textView_blurb.getText().length()==0){textView_blurb.setText("暂无");}

                            String [] play_name = (jsonObject.getString("vod_play_from")).split("\\$\\$\\$");
                            String [] play_url = (jsonObject.getString("vod_play_url")).split("\\$\\$\\$");
                            video_cache.clear();


                            video.setVideo_id(public_class.Video_id);
                            video.setVideo_name(jsonObject.getString("vod_name"));
                            video.setVideo_img(jsonObject.getString("vod_pic"));
                            video.setVideo_mark((float) jsonObject.getDouble("vod_score"));


                            for (int i=0;i<play_name.length;i++){ video_cache.add(new video_player_list(play_name[i],play_url[i])); }

                            video_playername = video_cache.get(list_onclice).getVideo_name();
                            //创建列表
                            list_create((RecyclerView)findViewById(R.id.video_list_player_player));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void list_create(RecyclerView recyclerView){

        GridLayoutManager gridLayoutManager=new GridLayoutManager(video_list_player.this,8,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        playerAdapter playerAdapter = new playerAdapter(video_list_player.this,video_cache);
        playerAdapter.setmOnItemClickListener(new playerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                list_onclice = position;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playerAdapter.notifyDataSetChanged();
                    }
                });
                f5();
            }
            @Override
            public void onItemLongClick(View view, int position) { }
        });
        recyclerView.setAdapter(playerAdapter);
        list_create1((RecyclerView)findViewById(R.id.video_list_player_video));
    }

    public void f5(){
        video_playername = video_cache.get(list_onclice).getVideo_name();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list_create1((RecyclerView)findViewById(R.id.video_list_player_video));
            }
        });
    }

    public void load_player(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (public_class.player_url.contains("=http")){
                    public_class.player_url = public_class.encodeURIComponent(public_class.player_url.split("=",2)[1]);
                }
                startActivity(player_face);
            }
        });
    }


    public void openvideo(String url){
        ls_1++;
        Log.d("111",public_class.player_jx + url);
        public_class.http_get(public_class.player_jx + url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ls_1<2){
                            Toast.makeText(video_list_player.this,"自动重拨",Toast.LENGTH_SHORT).show();
                            call.cancel();
                            openvideo(url);
                            return;
                        }
                        Toast.makeText(video_list_player.this,"解析失败",Toast.LENGTH_SHORT).show();
                        progressBar_round.setVisibility(View.INVISIBLE);
                        return;
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json_text = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (json_text.contains("404 Not Found")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    public_class.shouToast(video_list_player.this,"接口失效，请联系管理员修复或更换接口");
                                    progressBar_round.setVisibility(View.INVISIBLE);
                                }
                            });
                            return;
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(json_text);
                            if (jsonObject.getInt("code")!=200){
                                call.cancel();
                                if (ls_1<2){
                                    openvideo(url);
                                    Toast.makeText(video_list_player.this,"自动重拨",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(video_list_player.this,"解析失败",Toast.LENGTH_SHORT).show();
                                progressBar_round.setVisibility(View.INVISIBLE);
                                return;
                            }else {
                                public_class.player_url = jsonObject.getString("url");
                                progressBar_round.setVisibility(View.INVISIBLE);
                                if (public_class.Video_id.contains(Video_cache_id)){
                                    load_player();
                                }
                                return;
                            }
                        } catch (JSONException e) {
                            Toast.makeText(video_list_player.this,"解析失败,请联系管理员修复",Toast.LENGTH_SHORT).show();
                            progressBar_round.setVisibility(View.INVISIBLE);
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void openvideo1(String url){
        ls_1++;
        Log.d("1111",public_class.player_jx1 + url);
        public_class.http_get(public_class.player_jx1 + url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ls_1<2){
                            Toast.makeText(video_list_player.this,"自动重拨",Toast.LENGTH_SHORT).show();
                            openvideo1(url);
                            return;
                        }
                        Toast.makeText(video_list_player.this,"解析失败",Toast.LENGTH_SHORT).show();
                        progressBar_round.setVisibility(View.INVISIBLE);
                        return;
                    }
                });

            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json_text = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (json_text.contains("404 Not Found")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    public_class.shouToast(video_list_player.this,"接口失效，请联系管理员修复或更换接口");
                                    progressBar_round.setVisibility(View.INVISIBLE);
                                }
                            });
                            return;
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(json_text);
                            if (jsonObject.getInt("code")!=200){
                                call.cancel();
                                if (ls_1<2){
                                    Toast.makeText(video_list_player.this,"自动重拨",Toast.LENGTH_SHORT).show();
                                    openvideo1(url);
                                    return;
                                }
                                Toast.makeText(video_list_player.this,"解析失败",Toast.LENGTH_SHORT).show();
                                progressBar_round.setVisibility(View.INVISIBLE);
                                return;
                            }else {
                                public_class.player_url = jsonObject.getString("url");
                                progressBar_round.setVisibility(View.INVISIBLE);
                                if (public_class.Video_id.contains(Video_cache_id)){
                                    load_player();
                                }
                                return;
                            }
                        } catch (JSONException e) {
                            progressBar_round.setVisibility(View.INVISIBLE);
                            Toast.makeText(video_list_player.this,"解析失败,请联系管理员修复",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void list_create1(RecyclerView recyclerView){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(video_list_player.this,5,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        playerAdapter1 playerAdapter1 = new playerAdapter1(video_list_player.this,video_playername);
        playerAdapter1.setmOnItemClickListener(new playerAdapter1.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                video.setVideo_play(video_playername.get(position));
                video.setVideo_player_list(position);
                video.setVideo_player(list_onclice);
                list_onclice1=position;
                runOnUiThread(new Runnable() {
                    public void run() {
                        playerAdapter1.notifyDataSetChanged();
                    }
                });
                Toast.makeText(video_list_player.this,"解析中，请稍等",Toast.LENGTH_SHORT).show();
                public_class.player_name = video_cache.get(list_onclice).getvideoname(position);
                ls_1=0;
                progressBar_round.setVisibility(View.VISIBLE);
                sql_up();
                ls_1=0;
                if (public_class.player_daoism==0){
                    openvideo(video_cache.get(list_onclice).getvideourl(position));
                    return;
                }
                if (public_class.player_daoism==1){
                    openvideo1(video_cache.get(list_onclice).getvideourl(position));
                    return;
                }


            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        playerAdapter_now = playerAdapter1;
        recyclerView.setAdapter(playerAdapter_now);
        playerAdapter_now.notifyDataSetChanged();
    }


    class MyRadioButtonListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // 选中状态改变时被触发
            switch (checkedId) {
                case R.id.player_button0:
                    public_class.player_daoism=0;
//                    public_class.shouToast(video_list_player.this,"选中接口天");
                    break;
                case R.id.player_button1:
                    public_class.player_daoism=1;
//                    public_class.shouToast(video_list_player.this,"选中接口地");
                    break;
            }
        }
    }

}


class playerAdapter extends RecyclerView.Adapter<playerAdapter.MyviewHolder>{
    private playerAdapter.OnItemClickListener mOnItemClickListener;
    public List<video_player_list> video_lists;
    private Context context;

    @NonNull
    @Override
    public playerAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.player_list_form,parent,false);
        MyviewHolder myviewHolder=new MyviewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull playerAdapter.MyviewHolder holder, int position) {
        holder.textView.setText(video_lists.get(position).getName());
        if (video_list_player.list_onclice==position){
            holder.textView.setTextColor(Color.YELLOW);
        }
        if (video_list_player.list_onclice!=position){
            holder.textView.setTextColor(Color.WHITE);
        }
        if (mOnItemClickListener !=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return video_lists.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public playerAdapter(Context context,List<video_player_list> name){
        this.context = context;
        this.video_lists = name;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyviewHolder(View view){
            super(view);
            textView = view.findViewById(R.id.form_player_id);
        }
    }
}



class playerAdapter1 extends RecyclerView.Adapter<playerAdapter1.MyviewHolder> {
    private playerAdapter1.OnItemClickListener mOnItemClickListener;
    public List<String> video_name;
    private Context context;

    @NonNull
    @Override
    public playerAdapter1.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.player_list_season, parent, false);
        MyviewHolder myviewHolder = new MyviewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull playerAdapter1.MyviewHolder holder, int position) {
        if (video_list_player.list_onclice1==position){
            holder.textView.setTextColor(Color.YELLOW);
        }
        if (video_list_player.list_onclice1!=position){
            holder.textView.setTextColor(Color.WHITE);
        }
        holder.textView.setText(video_name.get(position));
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return video_name.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public playerAdapter1(Context context,List<String> name) {
        this.context = context;
        this.video_name = name;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyviewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.player_season);
        }
    }
}