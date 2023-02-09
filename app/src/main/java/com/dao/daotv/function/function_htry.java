package com.dao.daotv.function;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dao.daotv.R;
import com.dao.daotv.public_class;
import com.dao.daotv.publicclass.DateUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class function_htry extends AppCompatActivity implements View.OnClickListener {
    private GridView record_gridView;//定义gridView
    private DateAdapter dateAdapter;//定义adapter
    private ImageView record_left;//左箭头
    private ImageView record_right;//右箭头
    private TextView record_title;//标题
    private int year;
    private int month;
    private String title;
    private int[][] days = new int[6][7];
    RecyclerView recyclerView;
    sdcbAdapter sdcbAdapter;
    List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_htry);

        public_class.days =  new int[42];
        public_class.day = DateUtils.getDay();

        recyclerView = (RecyclerView)findViewById(R.id.htry_list);

        //初始化日期数据
        initData();
        //初始化组件
        initView();
        //组件点击监听事件
        initEvent();
    }

    private void initData() {
        year = DateUtils.getYear();
        month = DateUtils.getMonth();
        get_info(year,month,DateUtils.getDay());
    }

    public void list_f5(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sdcbAdapter.notifyDataSetChanged();
            }
        });
    }

    public void get_info(int year,int month,int day){
        list.clear();
        http_get(public_class.wnlapi +
                "type=0&year=" + String.valueOf(year) +
                "&month=" + String.valueOf(month) +
                "&day=" + String.valueOf(day), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        public_class.shouToast(function_htry.this,"网络超时，请重试");
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String text = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    if (jsonObject.getString("code").contains("200")){
//                        Log.d("数据",jsonObject.getString("text"));
                        jsonObject = jsonObject.getJSONArray("text").getJSONObject(0);
                        list.add("阳历：" + String.valueOf(year) + "年" + String.valueOf(month) + "月" + String.valueOf(day) + "日    " + jsonObject.getString("XingWest"));
                        if (jsonObject.getString("GJie").length()!=0){
                            list.add("节日：" + jsonObject.getString("GJie"));
                        }

                        if (jsonObject.getString("SolarTermName").length()!=0){
                            list.add("阴历：" + jsonObject.getString("LMonth") + jsonObject.getString("LDay") + "    "+"节气：" + jsonObject.getString("SolarTermName"));
                        }else {
                            list.add("阴历：" + jsonObject.getString("LMonth") + jsonObject.getString("LDay"));
                        }
                        if (jsonObject.getString("LJie").length()!=0){
                            list.add("节日：" + jsonObject.getString("LJie"));
                        }
                        list.add(jsonObject.getString("TianGanDiZhiYear") + jsonObject.getString("LYear") + "年    " + jsonObject.getString("TianGanDiZhiMonth") + "月    " + jsonObject.getString("TianGanDiZhiDay") + "日");
                        list.add("月相：" + jsonObject.getString("MoonName") + "    " + "月名：" + jsonObject.getString("LMonthName"));
                        list.add("宜：" + jsonObject.getString("Yi"));
                        list.add("忌：" + jsonObject.getString("Ji"));
                        list.add("冲煞：" + jsonObject.getString("Chong"));
                        list.add("岁煞：" + jsonObject.getString("SuiSha"));
                        list.add("神位：" + jsonObject.getString("ShenWei"));
                        list.add("胎神：" + jsonObject.getString("Taishen"));
                        list.add("星宿：" + jsonObject.getString("XingEast"));
                        list.add("值日天神：" + jsonObject.getString("JianShen"));
                        list.add("彭祖百忌：" + jsonObject.getString("PengZu"));
                        list.add("五行纳甲：" + jsonObject.getString("WuxingJiazi"));
                        list.add("纳音五行年：" + jsonObject.getString("WuxingNaYear"));
                        list.add("纳音五行月：" + jsonObject.getString("WuxingNaMonth"));
                        list.add("纳音五行日：" + jsonObject.getString("WuxingNaDay"));
                        list_f5();
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                public_class.shouToast(function_htry.this,"网络超时，请重试");
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void http_get(String url, Callback callback){
        new Thread("线程"+url){
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();
                Request.Builder request = new Request.Builder().url(url);
                Call call = client.newCall(request.build());
                call.enqueue(callback);
            }
        }.start();
    }



    private void initEvent() {
        record_left.setOnClickListener(this);
        record_right.setOnClickListener(this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(function_htry.this,1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        sdcbAdapter = new sdcbAdapter(function_htry.this,list);
        sdcbAdapter.setmOnItemClickListener(new sdcbAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                public_class.copyContentToClipboard(function_htry.this,list.get(position));
                public_class.shouToast(function_htry.this,"复制至剪切板");
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(sdcbAdapter);
    }

    private void initView() {
        /**
         * 以下是初始化GridView
         */
        record_gridView = (GridView) findViewById(R.id.record_gridView);
        days = DateUtils.getDayOfMonthFormat(2021, 8);
        dateAdapter = new DateAdapter(this, days, year, month);//传入当前月的年

        record_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                public_class.day = public_class.days[i];
                if (i < 7 && public_class.days[i] > 20) {
                    days = prevMonth();
                    dateAdapter = new DateAdapter(function_htry.this, days, year, month);
                    record_gridView.setAdapter(dateAdapter);
                    dateAdapter.notifyDataSetChanged();
                    setTile();
                } else if (i > 20 && public_class.days[i] < 15) {
                    days = nextMonth();
                    dateAdapter = new DateAdapter(function_htry.this, days, year, month);
                    record_gridView.setAdapter(dateAdapter);
                    dateAdapter.notifyDataSetChanged();
                    setTile();
                }
                get_info(year,month,public_class.day);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dateAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        record_gridView.setAdapter(dateAdapter);
        record_gridView.setVerticalSpacing(60);
        record_gridView.setEnabled(true);

        /**
         * 以下是初始化其他组件
         */
        record_left = (ImageView) findViewById(R.id.record_left);
        record_right = (ImageView) findViewById(R.id.record_right);
        record_title = (TextView) findViewById(R.id.record_title);
        record_title.setText(String.valueOf(year) + "年" + String.valueOf(month) + "月");

    }

    /**
     * 下一个月
     *
     * @return
     */
    private int[][] nextMonth() {
        if (month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }

        if (DateUtils.getDaysOfMonth(year,month)<public_class.day){
            public_class.day = 0;
        }

        days = DateUtils.getDayOfMonthFormat(year, month);
        return days;
    }

    /**
     * 上一个月
     *
     * @return
     */
    private int[][] prevMonth() {
        if (month == 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        days = DateUtils.getDayOfMonthFormat(year, month);
        if (DateUtils.getDaysOfMonth(year,month)<public_class.day){
            public_class.day = 0;
        }
        return days;
    }

    /**
     * 设置标题
     */
    private void setTile() {
        title = year + "年" + month + "月";
        record_title.setText(title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_left:
                days = prevMonth();
                dateAdapter = new DateAdapter(this, days, year, month);
                record_gridView.setAdapter(dateAdapter);
                dateAdapter.notifyDataSetChanged();
                setTile();
                break;
            case R.id.record_right:
                days = nextMonth();
                dateAdapter = new DateAdapter(this, days, year, month);
                record_gridView.setAdapter(dateAdapter);
                dateAdapter.notifyDataSetChanged();
                setTile();
                break;
        }
    }
}


class DateAdapter extends BaseAdapter {
    private int[] days = new int[42];
    private Context context;
    private int year;
    private int month;

    public DateAdapter(Context context, int[][] days, int year, int month) {
        this.context = context;
        int dayNum = 0;
        //将二维数组转化为一维数组，方便使用
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                this.days[dayNum] = days[i][j];
                dayNum++;
            }
        }
        public_class.days = this.days;
        this.year = year;
        this.month = month;
    }

    @Override
    public int getCount() {
        return days.length;
    }

    @Override
    public Object getItem(int i) {
        return days[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.date_list, null);
            viewHolder = new ViewHolder();
            viewHolder.date_item = (TextView) view.findViewById(R.id.date_item);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (i < 7 && days[i] > 20) {
            viewHolder.date_item.setTextColor(Color.rgb(204, 204, 204));//将上个月的和下个月的设置为灰色
        } else if (i > 20 && days[i] < 15) {
            viewHolder.date_item.setTextColor(Color.rgb(204, 204, 204));
        }else {
            if (days[i] == public_class.day){
                viewHolder.date_item.setTextColor(Color.rgb(65, 7, 255));
            }else {
                viewHolder.date_item.setTextColor(Color.rgb(255, 152, 0));
            }
        }

        if (days[i] == DateUtils.getDay()&&month == DateUtils.getMonth()&&year==DateUtils.getYear()){
            viewHolder.date_item.setTextColor(Color.rgb(255, 255, 255));
        }
        viewHolder.date_item.setText(days[i] + "");
        return view;
    }
}

class ViewHolder {
    TextView date_item;
}