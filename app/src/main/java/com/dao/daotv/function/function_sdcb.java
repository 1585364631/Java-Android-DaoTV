package com.dao.daotv.function;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dao.daotv.R;
import com.dao.daotv.index;
import com.dao.daotv.public_class;
import com.google.android.exoplayer2.C;
import com.liulishuo.filedownloader.BaseDownloadTask;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class function_sdcb extends AppCompatActivity {

    EditText editText;
    Button button;
    RecyclerView recyclerView;
    List<String> list = new ArrayList<>();;
    sdcbAdapter sdcbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_function_sdcb);
        editText = (EditText)findViewById(R.id.sdcb_edittext);
        button = (Button)findViewById(R.id.sdcb_button);
        recyclerView = (RecyclerView)findViewById(R.id.sdcb_list);
        editText.setText(public_class.gbook_ip);

        list.clear();
        list_create();

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editText.getText().toString().length()==0){
                            Toast.makeText(function_sdcb.this,"请输入IP地址",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (public_class.isIpv4(editText.getText().toString())){
                            list.clear();
                            public_class.http_get(public_class.gaode_apiv2 + editText.getText().toString() + "&type=4", new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    list.add("高德地图v2：IP查询失败");
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    String text = response.body().string();
                                    try {
                                        JSONObject jsonObject = new JSONObject(text);
                                        if (jsonObject.getString("status").equals("1")){
                                            if (jsonObject.getString("info").equals("OK")){
                                                list.add("高德地图v2 IP：" + jsonObject.getString("ip"));
                                                list.add("高德地图v2 地址：" + jsonObject.getString("country") +
                                                        jsonObject.getString("province") +
                                                        jsonObject.getString("city") +
                                                        jsonObject.getString("district"));
                                                list.add("高德地图v2 运营商：" + jsonObject.getString("isp"));
                                                list.add("高德地图v2 经度：" + jsonObject.getString("location").split(",")[0]);
                                                list.add("高德地图v2 纬度：" + jsonObject.getString("location").split(",")[1]);
                                                list.add("高德地图v2 经纬格式：" + jsonObject.getString("location"));
                                                list.add(" ");
                                            }
                                        }
                                        else{
                                            list.add("高德地图v2：IP查询失败");
                                        }
                                        list_f5();
                                        return;
                                    } catch (JSONException e) {
                                        list.add("高德地图v2：IP查询失败");
                                        e.printStackTrace();
                                    }
                                }
                            });

                            http_get_list(public_class.gaode_apiv1 + editText.getText().toString(), new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    list.add("高德地图v1：IP查询失败");
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    String text = response.body().string();
                                    try {
                                        JSONObject jsonObject = new JSONObject(text);
                                        if (jsonObject.getString("status").equals("1")){
                                            if (jsonObject.getString("info").equals("OK")){
                                                list.add("高德地图v1 地址：" +
                                                        jsonObject.getString("province") +
                                                        jsonObject.getString("city"));
                                                list.add("高德地图v1 城市编码：" + jsonObject.getString("adcode"));
                                                list.add("高德地图v1 经纬范围：" + jsonObject.getString("rectangle"));
                                                String text_jw = jsonObject.getString("rectangle");
                                                Float [] tjw = {Float.valueOf(text_jw.split(";")[0].split(",")[0]),
                                                        Float.valueOf(text_jw.split(";")[0].split(",")[1]),
                                                                Float.valueOf(text_jw.split(";")[1].split(",")[0]),
                                                                        Float.valueOf(text_jw.split(";")[1].split(",")[1])};
                                                text_jw = String.valueOf((tjw[2]+tjw[0])/2) + "," +String.valueOf((tjw[3]+tjw[1])/2);
                                                list.add("高德地图v1 经度：" + String.valueOf((tjw[2]+tjw[0])/2));
                                                list.add("高德地图v1 纬度：" + String.valueOf((tjw[3]+tjw[1])/2));
                                                list.add("高德地图v1 经纬格式：" + text_jw);
                                                list.add(" ");
                                            }
                                        }
                                        else{
                                            list.add("高德地图v1：IP查询失败");
                                        }
                                        list_f5();
                                        return;
                                    } catch (JSONException e) {
                                        list.add("高德地图v1：IP查询失败");
                                        e.printStackTrace();
                                    }
                                }
                            });

                            http_get_list(public_class.tenxun_api + editText.getText().toString(), new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    list.add("腾讯地图：IP查询失败");
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    String text = response.body().string();
                                    try {
                                        JSONObject jsonObject = new JSONObject(text);
                                        if (jsonObject.getString("status").equals("0")){
                                            if (jsonObject.getString("message").equals("query ok")){
                                                jsonObject = new JSONObject(jsonObject.getString("result"));
                                                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("ad_info"));
                                                JSONObject jsonObject2 = new JSONObject(jsonObject.getString("location"));
                                                list.add("腾讯地图 ip：" + jsonObject.getString("ip"));
                                                list.add("腾讯地图 地址：" + jsonObject1.getString("nation")+
                                                        jsonObject1.getString("province")+
                                                        jsonObject1.getString("city")+
                                                        jsonObject1.getString("district"));
                                                list.add("腾讯地图 邮政编码：" + jsonObject1.getString("adcode"));
                                                list.add("腾讯地图 经度：" + jsonObject2.getString("lng"));
                                                list.add("腾讯地图 纬度：" + jsonObject2.getString("lat"));
                                                list.add("腾讯地图 经纬格式：" + jsonObject2.getString("lng") + "," + jsonObject2.getString("lat"));
                                                list.add(" ");
                                            }
                                        }
                                        else{
                                            list.add("腾讯地图：IP查询失败");
                                        }
                                        list_f5();
                                        return;
                                    } catch (JSONException e) {
                                        list.add("腾讯地图：IP查询失败");
                                        e.printStackTrace();
                                    }
                                }
                            });


                            http_get_list(public_class.baidu_api + editText.getText().toString(), new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    list.add("百度地图：IP查询失败");
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    String text = response.body().string();
                                    try {
                                        JSONObject jsonObject = new JSONObject(text);
                                        if (jsonObject.getString("status").equals("0")){
                                            list.add("百度地图 接入点：" + jsonObject.getString("address"));
                                            jsonObject = new JSONObject(jsonObject.getString("content"));
                                            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("address_detail"));
                                            JSONObject jsonObject2 = new JSONObject(jsonObject.getString("point"));
                                            list.add("百度地图 地址：" + jsonObject1.getString("province")+
                                                    jsonObject1.getString("city")+
                                                    jsonObject1.getString("district")+
                                                    jsonObject1.getString("street")+
                                                    jsonObject1.getString("street_number"));
                                            list.add("百度地图 城市三字码：" + jsonObject1.getString("city_code"));
                                            list.add("百度地图 经度：" + jsonObject2.getString("x"));
                                            list.add("百度地图 纬度：" + jsonObject2.getString("y"));
                                            list.add("百度地图 经纬格式：" + jsonObject2.getString("x") + "," + jsonObject2.getString("y"));
                                            list.add(" ");
                                        }
                                        else{
                                            list.add("百度地图：IP查询失败");
                                        }
                                        list_f5();
                                        return;
                                    } catch (JSONException e) {
                                        list.add("百度地图：IP查询失败");
                                        e.printStackTrace();
                                    }
                                }
                            });


                            return;
                        }


                        if (public_class.isIpv6(editText.getText().toString())){
                            list.clear();
                            http_get_list(public_class.gaode_apiv2 + editText.getText().toString() + "&type=6", new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    public_class.shouToast(function_sdcb.this,"请检查网络连接");
                                }
                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    String text = response.body().string();;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                JSONObject jsonObject = new JSONObject(text);
                                                if (jsonObject.getString("status").equals("1")){
                                                    if (jsonObject.getString("info").equals("OK")){
                                                        list.add("高德地图v2 IP：" + jsonObject.getString("ip"));
                                                        list.add("高德地图v2 地址：" + jsonObject.getString("country") +
                                                                jsonObject.getString("province") +
                                                                jsonObject.getString("city") +
                                                                jsonObject.getString("district"));
                                                        list.add("高德地图v2 运营商：" + jsonObject.getString("isp"));
                                                        list.add("高德地图v2 经度：" + jsonObject.getString("location").split(",")[0]);
                                                        list.add("高德地图v2 纬度：" + jsonObject.getString("location").split(",")[1]);
                                                        list.add("高德地图v2 经纬格式：" + jsonObject.getString("location"));
                                                    }
                                                }
                                                else{
                                                    list.add("高德地图v2：IP查询失败");
                                                }
                                                list_f5();
                                                return;
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });
                                }
                            });
                            return;
                        }
                        public_class.shouToast(function_sdcb.this,"请自行检查ip地址是否正确");
                    }
                });
            }
        });

        if (!editText.getText().equals("")){
            button.performClick();
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void list_create(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GridLayoutManager gridLayoutManager=new GridLayoutManager(function_sdcb.this,1,GridLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(gridLayoutManager);
                sdcbAdapter = new sdcbAdapter(function_sdcb.this,list);
                sdcbAdapter.setmOnItemClickListener(new sdcbAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        public_class.copyContentToClipboard(function_sdcb.this,list.get(position));
                        public_class.shouToast(function_sdcb.this,"复制至剪切板");
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                recyclerView.setAdapter(sdcbAdapter);
                list_f5();
            }
        });
    }

    public void list_f5(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sdcbAdapter.notifyDataSetChanged();
            }
        });
    }


    public void http_get_list(String url,Callback back) {
        new Thread("线程"+url){
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();
                Request.Builder request = new Request.Builder().url(url);
                Call call = client.newCall(request.build());
                call.enqueue(back);
            }
        }.start();
    }
}


class sdcbAdapter extends RecyclerView.Adapter<sdcbAdapter.MyviewHolder>{

    private sdcbAdapter.OnItemClickListener mOnItemClickListener;
    private List<String> list;
    private Context context;


    @NonNull
    @Override
    public sdcbAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sdcb_lists,parent,false);
        sdcbAdapter.MyviewHolder myviewHolder=new sdcbAdapter.MyviewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull sdcbAdapter.MyviewHolder holder, int position) {
        holder.textView.setText(list.get(position));
        if (mOnItemClickListener!=null){
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
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setmOnItemClickListener(sdcbAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public sdcbAdapter(Context context,List<String> list){
        this.list = list;
        this.context = context;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.sdcb_lists_text);
        }
    }

}