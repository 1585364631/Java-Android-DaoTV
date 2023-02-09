package com.dao.daotv.function;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dao.daotv.R;
import com.dao.daotv.public_class;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class function_dzxy extends AppCompatActivity {

    List<String> list = new ArrayList<>();
    RecyclerView recyclerView;
    sdcbAdapter sdcbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_dzxy);
        recyclerView = (RecyclerView)findViewById(R.id.dzxy_list);
        if (public_class.dzxy_lisy.size()==0){
            get_http();
        }else {
            list = public_class.dzxy_lisy;
            list_create();
        }

    }

    public void list_f5(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sdcbAdapter.notifyDataSetChanged();
            }
        });
    }

    public void list_create(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GridLayoutManager gridLayoutManager=new GridLayoutManager(function_dzxy.this,1,GridLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(gridLayoutManager);
                sdcbAdapter = new sdcbAdapter(function_dzxy.this,list);
                sdcbAdapter.setmOnItemClickListener(new sdcbAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        public_class.copyContentToClipboard(function_dzxy.this,list.get(position));
                        public_class.shouToast(function_dzxy.this,"复制至剪切板");
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


    public void get_http(){
        public_class.http_get("https://interesting-sky.china-vo.org/", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                list.add("网络请求失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String text = response.body().string();
                text = text.split("<aside id=\"text-29\" class=\"widget widget_text\">")[1].split("</aside>")[0];
                String [] context = text.split("<b>太阳：</b>");
                String pattern = "<span style=\"co(.*?);\">(.*?)(?=</span>)";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(context[0]);
                while(m.find()) {
                    if (m.group().split(">").length >1){
                        String text_cache = m.group().split(">")[1];
                        if (!text_cache.equals(" ")){
                            list.add(text_cache);
                        }
                    }
                }
                Matcher n = r.matcher(context[1].split("<span style=\"font-size: small;\">")[0]);
                int i=0;
                String [] type = {"太阳：","水星：","金星：","火星：","木星：","土星：","天王星：","海王星："};
                while (n.find()){
                    if (!n.group().contains("：</b>")){
                        if (n.group().split(">").length > 1){
                            String text_cache = n.group().split(">")[1];
                            list.add(type[i] + text_cache);
                            i++;
                        }
                    }
                }
                list_create();
            }
        });


    }
}