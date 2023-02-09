package com.dao.daotv.function;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dao.daotv.R;
import com.dao.daotv.public_class;
import com.dao.daotv.video_list_player;
import com.dao.daotv.video_player_list;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import fragment.video_list;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class gbook extends AppCompatActivity {

    List<gbook_context> gbook_list;
    Button button,button1;
    LinearLayout linearLayout;
    EditText editText_name,editText_text;
    private String page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_gbook);
        button = (Button)findViewById(R.id.button_gbook);
        button1 = (Button)findViewById(R.id.gbook_post);
        linearLayout = (LinearLayout)findViewById(R.id.gbook_view);
        editText_name = (EditText)findViewById(R.id.gbook_inputname);
        editText_text = (EditText)findViewById(R.id.gbook_inputtext);
        load();
    }

    public void load(){
        public_class.http_get(public_class.gbook_api + "?status=1", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json_text = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(json_text);
                    public_class.gbook_ip = jsonObject.getString("ip");
                    String ctext = "注：提交留言会记录ip,请小心行驶,切莫行违法犯罪之事,评论在审核后发布\n当前ip：" + public_class.gbook_ip;
                    if (public_class.dao_gbook.equals("1")){
                        ctext = ctext + "\n状态：天道模式\n言出法随：评论免审发布\n遮掩天机：屏蔽记录ip";
                    }
                    editText_text.setHint(ctext);
                    page = jsonObject.getString("total");
                    TextView textView = (TextView)findViewById(R.id.gbook_int);
                    textView.setText("共" + page + "条留言");
                    json_text = jsonObject.getString("text");
                    JSONArray jsonArray = new JSONArray(json_text);
                    gbook_list = new ArrayList();
                    for (int i=0;i<jsonArray.length();i++){
                        String text_cache = String.valueOf(jsonArray.get(i));
                        JSONObject jsonObject1 = new JSONObject(text_cache);
                        gbook_list.add(new gbook_context(
                                jsonObject1.getString("gbook_id"),
                                jsonObject1.getString("gbook_name"),
                                jsonObject1.getString("gbook_ip"),
                                public_class.timetodate(jsonObject1.getString("gbook_time")),
                                jsonObject1.getString("gbook_reply_time"),
                                jsonObject1.getString("gbook_content"),
                                jsonObject1.getString("gbook_reply")
                        ));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.gbook_list);
                            GridLayoutManager gridLayoutManager=new GridLayoutManager(gbook.this,1,GridLayoutManager.VERTICAL,false);
                            recyclerView.setLayoutManager(gridLayoutManager);
                            GbookAdapter gbookAdapter = new GbookAdapter(gbook.this,gbook_list);
                            gbookAdapter.setmOnItemClickListener(new GbookAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    public_class.shouToast(gbook.this,"有意留言，不符solo");
                                }

                                @Override
                                public void onItemLongClick(View view, int position) {

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
                                            && visibleItemCount > 0){
                                        public_class.shouToast(gbook.this,"已显示全部");
                                    }
                                }
                            });
                            recyclerView.setAdapter(gbookAdapter);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (linearLayout.getVisibility()==View.INVISIBLE){
                            linearLayout.setVisibility(View.VISIBLE);
                            button.setText("取消");
                            editText_name.setText(null);
                            editText_text.setText(null);
                            return;
                        }
                        if (linearLayout.getVisibility()==View.VISIBLE){
                            linearLayout.setVisibility(View.INVISIBLE);
                            button.setText("留言");
                            return;
                        }
                    }
                });
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editText_name.length()==0||editText_text.length()==0){
                            public_class.shouToast(gbook.this,"请输入名称和内容");
                            return;
                        }
                        String ip_cache = public_class.iptoint(public_class.gbook_ip);

                        //ip清空
                        if (public_class.dao_gbook.equals("1")){
                            ip_cache="0";
                        }

                        String url = public_class.gbook_api + "?dao=1&gbook_status=" + public_class.dao_gbook + "&gbook_name=" +
                                editText_name.getText() + "&gbook_ip=" + ip_cache +
                                "&gbook_time=" + public_class.datetotime() + "&gbook_content=" + editText_text.getText();
                        Log.d("url",url);
                        public_class.http_get(url, new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        public_class.shouToast(gbook.this,"提交失败");
                                    }
                                });
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                String text = response.body().toString();
                                if (text.equals("")||text.length()==0||text==null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            public_class.shouToast(gbook.this,"提交失败");
                                        }
                                    });
                                    return;
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        linearLayout.setVisibility(View.INVISIBLE);
                                        button.setText("留言");

                                        public_class.shouToast(gbook.this,"提交成功");
                                    }
                                });
                                load();
                            }
                        });
                    }
                });
            }
        });


    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

class GbookAdapter extends RecyclerView.Adapter<GbookAdapter.MyviewHolder>{
    private GbookAdapter.OnItemClickListener mOnItemClickListener;
    public List<gbook_context> gbook_list;
    private Context context;

    @NonNull
    @Override
    public GbookAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.gbook_textlist,parent,false);
        GbookAdapter.MyviewHolder myviewHolder=new GbookAdapter.MyviewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GbookAdapter.MyviewHolder holder, int position) {
        holder.name.setText(gbook_list.get(position).getGbook_name());
        holder.text.setText(gbook_list.get(position).getGbook_content());
        holder.time.setText(gbook_list.get(position).getGbook_time());
        if (gbook_list.get(position).getGbook_reply().length()!=0){
            holder.reply.setText("管理员："+gbook_list.get(position).getGbook_reply());
            holder.reply.setVisibility(View.VISIBLE);
        }
        if (gbook_list.get(position).getGbook_reply().length()==0){
            holder.reply.setText("");
            holder.reply.setVisibility(View.GONE);
        }
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
        return gbook_list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setmOnItemClickListener(GbookAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public GbookAdapter(Context context,List<gbook_context> gbook_list){
        this.gbook_list = gbook_list;
        this.context = context;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        TextView name,text,reply,time;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.gbook_name);
            text = (TextView)itemView.findViewById(R.id.gbook_text);
            time = (TextView)itemView.findViewById(R.id.gbook_time);
            reply = (TextView)itemView.findViewById(R.id.gbook_reply);
        }
    }
}