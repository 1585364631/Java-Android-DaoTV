package fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.dao.daotv.MainActivity;
import com.dao.daotv.R;
import com.dao.daotv.index;
import com.dao.daotv.public_class;
import com.dao.daotv.video_list_player;
import com.dao.daotv.video_player;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
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

public class Fragment_index extends Fragment {

    private View view;
    private Myadapter list_class;
    List<video_list> video_lists = new ArrayList<>();
    Intent player_face;
    public Fragment_index() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_index, container, false);
            player_face =  new Intent(getActivity(),video_list_player.class);
            public_class.Video_status = 0;
            banner_load((Banner)view.findViewById(R.id.banner_image));
            tablayout_create((TabLayout)view.findViewById(R.id.nav_index));
            network_load(public_class.url_api + "?ac=detail");
            list_create((RecyclerView)view.findViewById(R.id.item_list));
        }
        return view;
    }










    public void network_load(String url){
        public_class.http_get(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json_text = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(json_text);
                    public_class.Video_pg = jsonObject.optInt("page");
                    public_class.Video_pgc = jsonObject.optInt("pagecount");
                    json_text = jsonObject.getString("list");
                    JSONArray jsonArray = new JSONArray(json_text);
                    for (int i=0;i<jsonArray.length();i++){
                        String text_cache = String.valueOf(jsonArray.get(i));
                        JSONObject jsonObject1 = new JSONObject(text_cache);
                        video_lists.add(new video_list(jsonObject1.getInt("vod_id"),jsonObject1.getString("vod_pic"),jsonObject1.getString("vod_name"),String.valueOf(jsonObject1.getString("vod_blurb")),(float) jsonObject1.getDouble("vod_score")));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list_class.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    public void banner_load(Banner banne){
        banne.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片网址或地址的集合
        banne.setImages(public_class.banner_url);
        banne.setImageLoader(new MyLoader());
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banne.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banne.setBannerTitles(public_class.banner_title);
        //设置轮播间隔时间
        banne.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banne.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banne.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                public_class.Video_id = public_class.banner_id.get(position);
                startActivity(player_face);
                //页面跳转
            }
        });
        banne.setIndicatorGravity(BannerConfig.CENTER).start();
    }

    public void tablayout_create(TabLayout tablayout){
        tablayout.removeAllTabs();
        tablayout.addTab(tablayout.newTab().setText("更新"));
        tablayout.addTab(tablayout.newTab().setText("电影"));
        tablayout.addTab(tablayout.newTab().setText("电视剧"));
        tablayout.addTab(tablayout.newTab().setText("综艺"));
        tablayout.addTab(tablayout.newTab().setText("动漫"));
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                public_class.Video_status = tab.getPosition();
                video_lists.clear();
                list_class.notifyDataSetChanged();
                String url = public_class.url_api + "?ac=detail&pg=1";
                if (tab.getPosition() == 0){ }
                if (tab.getPosition() == 1){ url = url + "&t=" + public_class.video_vod; }
                if (tab.getPosition() == 2){ url = url + "&t=" + public_class.video_tv; }
                if (tab.getPosition() == 3){ url = url + "&t=" + public_class.video_arts; }
                if (tab.getPosition() == 4){ url = url + "&t=" + public_class.video_animation; }
                public_class.http_get(url, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {}
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String json_text = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(json_text);
                            public_class.Video_pg = jsonObject.optInt("page");
                            public_class.Video_pgc = jsonObject.optInt("pagecount");
                            json_text = jsonObject.getString("list");
                            JSONArray jsonArray = new JSONArray(json_text);
                            for (int i=0;i<jsonArray.length();i++){
                                String text_cache = String.valueOf(jsonArray.get(i));
                                JSONObject jsonObject1 = new JSONObject(text_cache);
                                video_lists.add(new video_list(jsonObject1.getInt("vod_id"),jsonObject1.getString("vod_pic"),jsonObject1.getString("vod_name"),String.valueOf(jsonObject1.getString("vod_blurb")),(float) jsonObject1.getDouble("vod_score")));
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    list_class.notifyDataSetChanged();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    public void list_create(RecyclerView recyclerView){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        Myadapter myadapter = new Myadapter(getContext(),video_lists);
        list_class = myadapter;
        myadapter.setOnItemClickListener(new Myadapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                public_class.Video_id = String.valueOf(video_lists.get(position).getId());
                startActivity(player_face);
                //跳转函数
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {
                        String url = public_class.url_api + "?ac=detail&pg=";
                        if (public_class.Video_status==0){
                            if (public_class.Video_pg < public_class.Video_pgc){
                                url = url + String.valueOf(public_class.Video_pg + 1);
                            }
                            else {
                                Toast.makeText(getContext(),"我可是有底线的",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    if (public_class.Video_status==1){
                        if (public_class.Video_pg < public_class.Video_pgc){
                            url = url + String.valueOf(public_class.Video_pg + 1) + "&t=" + public_class.video_vod;
                        }
                        else {
                            Toast.makeText(getContext(),"我可是有底线的",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (public_class.Video_status==2){
                        if (public_class.Video_pg < public_class.Video_pgc){
                            url = url + String.valueOf(public_class.Video_pg + 1) + "&t=" + public_class.video_tv;
                        }
                        else {
                            Toast.makeText(getContext(),"我可是有底线的",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (public_class.Video_status==3){
                        if (public_class.Video_pg < public_class.Video_pgc){
                            url = url + String.valueOf(public_class.Video_pg + 1) + "&t=" + public_class.video_arts;
                        }
                        else {
                            Toast.makeText(getContext(),"我可是有底线的",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (public_class.Video_status==4){
                        if (public_class.Video_pg < public_class.Video_pgc){
                            url = url + String.valueOf(public_class.Video_pg + 1) + "&t=" + public_class.video_animation;
                        }
                        else {
                            Toast.makeText(getContext(),"我可是有底线的",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    public_class.http_get(url, new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {}
                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String json_text = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(json_text);
                                public_class.Video_pg = jsonObject.optInt("page");
                                public_class.Video_pgc = jsonObject.optInt("pagecount");
                                json_text = jsonObject.getString("list");
                                JSONArray jsonArray = new JSONArray(json_text);
                                for (int i=0;i<jsonArray.length();i++){
                                    String text_cache = String.valueOf(jsonArray.get(i));
                                    JSONObject jsonObject1 = new JSONObject(text_cache);
                                    video_lists.add(new video_list(jsonObject1.getInt("vod_id"),jsonObject1.getString("vod_pic"),jsonObject1.getString("vod_name"),String.valueOf(jsonObject1.getString("vod_blurb")),(float) jsonObject1.getDouble("vod_score")));
                                }
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        list_class.notifyDataSetChanged();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        recyclerView.setAdapter(myadapter);

    }

    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}


class Myadapter extends RecyclerView.Adapter<Myadapter.MyviewHolder>{

    private OnItemClickListener mOnItemClickListener;
    public List<video_list> video_lists;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public Myadapter(Context context, List<video_list> video_lists){
        this.video_lists = video_lists;
        this.context = context;
    }

    @NonNull
    @Override
    public Myadapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        MyviewHolder myviewHolder=new MyviewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Myadapter.MyviewHolder holder, int position) {
        Glide.with(context).load(video_lists.get(position).getUrl()).into(holder.image);
        holder.number.setText(String.valueOf(video_lists.get(position).getNumber()));
        holder.name.setText(video_lists.get(position).getName());
        holder.title.setText(video_lists.get(position).getSmall_name());
        if (mOnItemClickListener !=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
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
    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView name,title,number;
        ImageView image;
        public MyviewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.video_title);
            title = view.findViewById(R.id.video_small_title);
            number = view.findViewById(R.id.video_number);
            image = view.findViewById(R.id.video_image);
        }
    }



}