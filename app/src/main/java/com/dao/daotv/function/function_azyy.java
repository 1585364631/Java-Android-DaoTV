package com.dao.daotv.function;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.busline.BusLineQuery;
import com.amap.api.services.busline.BusLineResult;
import com.amap.api.services.busline.BusLineSearch;
import com.amap.api.services.busline.BusStationQuery;
import com.amap.api.services.busline.BusStationResult;
import com.amap.api.services.busline.BusStationSearch;
import com.dao.daotv.R;
import com.dao.daotv.public_class;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class function_azyy extends AppCompatActivity implements BusStationSearch.OnBusStationSearchListener, BusLineSearch.OnBusLineSearchListener {


    BusStationQuery busStationQuery;
    BusStationSearch busStationSearch;
    BusLineSearch busLineSearch;
    EditText editText;
    Button button;
    RecyclerView recyclerView;
    sdcbAdapter sdcbAdapter;
    int but_status = 0;
    private RadioGroup rg;
    RadioButton radioButton0,radioButton1,radioButton2;

    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_azyy);
        editText = (EditText)findViewById(R.id.azyy_edittext);
        recyclerView = (RecyclerView)findViewById(R.id.azyy_list);
        button = (Button)findViewById(R.id.azyy_button);
        rg = (RadioGroup) findViewById(R.id.azyy_more_button);
        radioButton0 = (RadioButton)findViewById(R.id.azyy_button0);
        radioButton1 = (RadioButton)findViewById(R.id.azyy_button1);
        radioButton2 = (RadioButton)findViewById(R.id.azyy_button2);
        rg.setOnCheckedChangeListener(new MyRadioButtonListener());
        list_create();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().length()==0){
                    public_class.shouToast(function_azyy.this,"请输入内容");
                    return;
                }
                if (!editText.getText().toString().contains(",")){
                    public_class.shouToast(function_azyy.this,"请输入正确格式。例如：\n韶关,韶关东站\n韶关,20");
                    return;
                }
                String [] text = editText.getText().toString().split(",");
                if (but_status==0){
                    busStationQuery = new BusStationQuery(text[1], text[0]);
                    busStationSearch = new BusStationSearch(function_azyy.this, busStationQuery);
                    busStationSearch.setOnBusStationSearchListener(function_azyy.this);
                    busStationSearch.searchBusStationAsyn();
                    return;
                }
                if (but_status == 1) {
                    busLineSearch = new BusLineSearch(function_azyy.this,new BusLineQuery(text[1],BusLineQuery.SearchType.BY_LINE_NAME,text[0]));
                    busLineSearch.setOnBusLineSearchListener(function_azyy.this);
                    busLineSearch.searchBusLineAsyn();
                    return;
                }

                if (but_status == 2){
                    busLineSearch = new BusLineSearch(function_azyy.this,new BusLineQuery(text[1],BusLineQuery.SearchType.BY_LINE_ID,text[0]));
                    busLineSearch.setOnBusLineSearchListener(new BusLineSearch.OnBusLineSearchListener() {
                        @Override
                        public void onBusLineSearched(BusLineResult busLineResult, int i) {
                            if (i!=1000){
                                public_class.shouToast(function_azyy.this,"查询失败");
                                return;
                            }
                            list.clear();
                            for (int ii=0;ii<busLineResult.getBusLines().size();ii++){
                                list.add("ID：" + busLineResult.getBusLines().get(ii).getBusLineId());
                                list.add("哈希值：" + String.valueOf(busLineResult.getBusLines().get(ii).hashCode()));
                                list.add("城市代码：" + busLineResult.getBusLines().get(ii).getCityCode());
                                list.add("类型：" + busLineResult.getBusLines().get(ii).getBusLineType());
                                list.add(busLineResult.getBusLines().get(ii).getBusLineName());
                                list.add("首站：" + busLineResult.getBusLines().get(ii).getOriginatingStation());
                                list.add("终站：" + busLineResult.getBusLines().get(ii).getTerminalStation());
                                if (!String.valueOf(busLineResult.getBusLines().get(ii).getTotalPrice()).equals("0.0")){
                                    list.add("总费：" + String.valueOf(busLineResult.getBusLines().get(ii).getTotalPrice()));
                                }
                                if (!String.valueOf(busLineResult.getBusLines().get(ii).getBasicPrice()).equals("0.0")){
                                    list.add("票价：" + String.valueOf(busLineResult.getBusLines().get(ii).getBasicPrice()));
                                }

                                if (!String.valueOf(busLineResult.getBusLines().get(ii).getDistance()).equals("0.0")){
                                    list.add("距离：" + String.valueOf(busLineResult.getBusLines().get(ii).getDistance()));
                                }


                                if (busLineResult.getBusLines().get(ii).getFirstBusTime()!=null){
                                    list.add(String.valueOf(busLineResult.getBusLines().get(ii).getFirstBusTime().getTime()));
                                }
                                if (busLineResult.getBusLines().get(ii).getLastBusTime()!=null){
                                    list.add(String.valueOf(busLineResult.getBusLines().get(ii).getLastBusTime().getTime()));
                                }



                                list.add("\n公交线路已绘制至地图，可通过撒豆成兵或法天象地进行查看");

                                for (int iii=0;iii<busLineResult.getBusLines().get(ii).getBusStations().size();iii++){
                                    list.add(busLineResult.getBusLines().get(ii).getBusStations().get(iii).getBusStationName());
                                }
                                list.add(" ");
                                public_class.latLngss.clear();
                                for (int iii=0;iii<busLineResult.getBusLines().get(ii).getDirectionsCoordinates().size();iii++){
                                    String text = busLineResult.getBusLines().get(ii).getDirectionsCoordinates().get(iii).toString();
                                    public_class.latLngss.add(new LatLng(Double.valueOf(text.split(",")[0]),Double.valueOf(text.split(",")[1])));
                                    list.add(text);
                                }


                                list.add(" ");



                            }
                            list_f5();


                        }
                    });
                    busLineSearch.searchBusLineAsyn();
                }

            }
        });

    }

    @Override
    public void onBusStationSearched(BusStationResult busStationResult, int i) {

        if (i!=1000){
            public_class.shouToast(function_azyy.this,"查询失败");
            return;
        }
        list.clear();
        for (int ii=0;ii<busStationResult.getBusStations().size();ii++){
            list.add(busStationResult.getBusStations().get(ii).getBusStationName());
            list.add("纬经度：" + busStationResult.getBusStations().get(ii).getLatLonPoint());
            List list1 = new ArrayList(busStationResult.getBusStations().get(ii).getBusLineItems());
            for (int iii=0;iii<list1.size();iii++){
                list.add(list1.get(iii).toString());
            }
            list.add(" ");
        }
        list_f5();


    }


    public void list_create(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GridLayoutManager gridLayoutManager=new GridLayoutManager(function_azyy.this,1,GridLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(gridLayoutManager);
                sdcbAdapter = new sdcbAdapter(function_azyy.this,list);
                sdcbAdapter.setmOnItemClickListener(new sdcbAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        public_class.copyContentToClipboard(function_azyy.this,list.get(position));
                        public_class.shouToast(function_azyy.this,"复制至剪切板");
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

    @Override
    public void onBusLineSearched(BusLineResult busLineResult, int i) {
        if (i!=1000){
            public_class.shouToast(function_azyy.this,"查询失败");
            return;
        }
        list.clear();
        for (int ii=0;ii<busLineResult.getBusLines().size();ii++){
            list.add(busLineResult.getBusLines().get(ii).getBusLineName());
            list.add(busLineResult.getBusLines().get(ii).getBusLineId());
        }
        list_f5();


    }

    class MyRadioButtonListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // 选中状态改变时被触发
            switch (checkedId) {
                case R.id.azyy_button0:
                    but_status=0;
                    break;
                case R.id.azyy_button1:
                    but_status=1;
                    break;
                case R.id.azyy_button2:
                    but_status=2;
                    break;
            }
        }
    }
}