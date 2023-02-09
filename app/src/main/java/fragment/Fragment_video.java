package fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dao.daotv.R;
import com.dao.daotv.public_class;
import com.dao.daotv.video_list_player;

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


public class Fragment_video extends Fragment {
    private View view;
    private Myadapter list_class;
    List<video_list> video_lists = new ArrayList<>();
    public Button search_button;
    Intent player_face;
    public TextView search_text;
    public String search_txt;
    public int pg;
    public int pgc;
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_video, container, false);
            player_face =  new Intent(getActivity(), video_list_player.class);
            search_button = (Button)view.findViewById(R.id.search_but);
            search_text = (EditText)view.findViewById(R.id.search_text);
            list_create((RecyclerView)view.findViewById(R.id.search_list));
            search_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (search_text.getText().length()==0){
                        Toast.makeText(getContext(),"请输入搜索内容",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    video_lists.clear();
                    pg=1;
                    search_txt = String.valueOf(search_text.getText());
                    public_class.http_get(public_class.url_api + "?ac=detail&pg=" + pg + "&wd=" + search_txt, new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Toast.makeText(getContext(),"搜索失败",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String json_text = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(json_text);
                                pg = jsonObject.optInt("page");
                                pgc = jsonObject.optInt("pagecount");
                                json_text = jsonObject.getString("list");
                                JSONArray jsonArray = new JSONArray(json_text);
                                for (int i=0;i<jsonArray.length();i++){
                                    String text_cache = String.valueOf(jsonArray.get(i));
                                    JSONObject jsonObject1 = new JSONObject(text_cache);
                                    video_lists.add(new video_list(jsonObject1.getInt("vod_id"),jsonObject1.getString("vod_pic"),jsonObject1.getString("vod_name"),String.valueOf(jsonObject1.getString("vod_class")),(float) jsonObject1.getDouble("vod_score")));
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
            });
        }
        return view;
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
                Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
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
                    if (pg>=pgc){
                        public_class.shouToast(getContext(),"我是有底线的");
                        return;
                    }
                    String url = public_class.url_api + "?ac=detail"+ "&wd=" + search_txt + "&pg=" + ++pg;

                    public_class.http_get(url, new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {}
                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String json_text = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(json_text);
                                pg = jsonObject.optInt("page");
                                pgc = jsonObject.optInt("pagecount");
                                json_text = jsonObject.getString("list");
                                JSONArray jsonArray = new JSONArray(json_text);
                                for (int i=0;i<jsonArray.length();i++){
                                    String text_cache = String.valueOf(jsonArray.get(i));
                                    JSONObject jsonObject1 = new JSONObject(text_cache);
                                    video_lists.add(new video_list(jsonObject1.getInt("vod_id"),jsonObject1.getString("vod_pic"),jsonObject1.getString("vod_name"),String.valueOf(jsonObject1.getString("vod_class")),(float) jsonObject1.getDouble("vod_score")));
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
}