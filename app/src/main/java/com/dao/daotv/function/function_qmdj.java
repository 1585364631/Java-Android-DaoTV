package com.dao.daotv.function;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.dao.daotv.R;
import com.dao.daotv.public_class;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.liulishuo.filedownloader.model.FileDownloadStatus.progress;

public class function_qmdj extends AppCompatActivity {
    private RadioGroup rg;
    RadioButton radioButton0,radioButton1,radioButton2;
    Button button;
    EditText editText;
    List<String> list = new ArrayList<>();
    RecyclerView recyclerView;
    sdcbAdapter sdcbAdapter;
    private int but_status = 0;

    UiSettings mUiSettings;
    CameraUpdate mUpdata;
    AMap aMap;
    MapView mMapView = null;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_function_qmdj);
        recyclerView = (RecyclerView)findViewById(R.id.qmdj_list);
        list_create();
        button = (Button)findViewById(R.id.qmdj_button);
        editText = (EditText)findViewById(R.id.qmdj_edittext);
        rg = (RadioGroup) findViewById(R.id.qmdj_more_button);
        radioButton0 = (RadioButton)findViewById(R.id.qmdj_button0);
        radioButton1 = (RadioButton)findViewById(R.id.qmdj_button1);
        radioButton2 = (RadioButton)findViewById(R.id.qmdj_button2);
        rg.setOnCheckedChangeListener(new MyRadioButtonListener());

        mMapView = (MapView)findViewById(R.id.qmdj_map);
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        UiSettings uiSettings =  aMap.getUiSettings();
        uiSettings.setLogoBottomMargin(-50);//隐藏logo
        uiSettings.setScaleControlsEnabled(true);




        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().length()==0){
                    public_class.shouToast(function_qmdj.this,"请输入经纬度");
                    return;
                }

                if (editText.getText().toString().contains("：")){
                    editText.setText(editText.getText().toString().split("：")[1]);
                }

                if (editText.getText().toString().contains(";")){
                    editText.setText(editText.getText().toString().split(";")[0]);
                }

                if (!editText.getText().toString().contains(",")){
                    public_class.shouToast(function_qmdj.this,"请输入正确格式，例：经度,纬度");
                    return;
                }

                String [] cache = editText.getText().toString().split(",");
                if (cache.length!=2){
                    public_class.shouToast(function_qmdj.this,"请输入正确格式，例：经度,纬度");
                    return;
                }
                list.clear();
                if (but_status==0){
                    try {
                        if (Float.valueOf(cache[0]) > Float.valueOf(cache[1])){
                            String text_cache = cache[0];
                            cache[0] = cache[1];
                            cache[1] = text_cache;
                        }
                    } catch (NumberFormatException e) {
                        public_class.shouToast(function_qmdj.this,"请输入正确格式，例：经度,纬度");
                        return;
                    }

                    public_class.http_get(public_class.baidu_map + cache[0] + "," + cache[1], new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            list.add("查询失败，请检查网络");
                            list_f5();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String text = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(text);
                                if (jsonObject.getString("status").equals("0")){
                                    list.add("经度：" + cache[1]);
                                    list.add("纬度：" + cache[0]);
                                    list.add("位置：" + jsonObject.getJSONObject("result").getString("formatted_address"));
                                    if (jsonObject.getJSONObject("result").getString("business").length()!=0){
                                        list.add("别名：" + jsonObject.getJSONObject("result").getString("business"));
                                    }
                                    JSONArray jsonArray = new JSONArray(jsonObject.getJSONObject("result").getString("pois"));
                                    list.add(" ");
                                    list.add("附近：");
                                    for (int i=0;i<jsonArray.length();i++){
                                        list.add(jsonArray.getJSONObject(i).getString("addr") +
                                                "\n经度：" + jsonArray.getJSONObject(i).getJSONObject("point").getString("x") +
                                                "\n纬度：" + jsonArray.getJSONObject(i).getJSONObject("point").getString("y"));
                                    }
                                    map_create(cache[1],cache[0]);
                                }else {
                                    list.add("查询失败，请检查网络");
                                }
                                list_f5();
                            } catch (JSONException e) {
                                list.add("查询失败，程序异常，请反馈修复");
                                list_f5();
                                e.printStackTrace();
                            }
                        }
                    });
                    return;
                }
                if (but_status==1){
                    if (Float.valueOf(cache[0]) > Float.valueOf(cache[1])){
                        String text_cache = cache[0];
                        cache[0] = cache[1];
                        cache[1] = text_cache;
                    }
                    public_class.http_get(public_class.qq_map + cache[0] + "," + cache[1], new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            list.add("查询失败，请检查网络");
                            list_f5();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String text = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(text);
                                if (jsonObject.getString("status").equals("0")&&jsonObject.getString("message").equals("query ok")){
                                    list.add("经度：" + cache[1]);
                                    list.add("纬度：" + cache[0]);
                                    list.add("地址：" + jsonObject.getJSONObject("result").getString("address"));
                                    list.add("位置：" + jsonObject.getJSONObject("result").getJSONObject("formatted_addresses").getString("recommend"));
                                    list.add(" ");
                                    list.add("附近：");
                                    JSONArray jsonArray = new JSONArray(jsonObject.getJSONObject("result").getString("pois"));
                                    for (int i=0;i<jsonArray.length();i++){
                                        list.add(jsonArray.getJSONObject(i).getString("address") + jsonArray.getJSONObject(i).getString("title")+
                                                "\n经度：" + jsonArray.getJSONObject(i).getJSONObject("location").getString("lng") +
                                                "\n纬度：" + jsonArray.getJSONObject(i).getJSONObject("location").getString("lat"));
                                    }
                                    map_create(cache[1],cache[0]);
                                }else {
                                    list.add("查询失败，请检查网络");
                                }
                            } catch (JSONException e) {
                                list.add("查询失败，程序异常，请反馈修复");
                                e.printStackTrace();
                            }
                            list_f5();
                        }
                    });

                    return;
                }
                if (but_status==2){
                    if (Float.valueOf(cache[0]) < Float.valueOf(cache[1])){
                        String text_cache = cache[0];
                        cache[0] = cache[1];
                        cache[1] = text_cache;
                    }
                    public_class.http_get(public_class.gaode_map + cache[0] + "," + cache[1], new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            list.add("查询失败，请检查网络");
                            list_f5();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String text = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(text);
                                if (jsonObject.getString("status").equals("1")){
                                    list.add("经度：" + cache[0]);
                                    list.add("纬度：" + cache[1]);
                                    list.add("地址：" + jsonObject.getJSONObject("regeocode").getString("formatted_address"));
                                    String road = "别名：";
                                    JSONArray jsonArray = jsonObject.getJSONObject("regeocode").getJSONArray("roads");
                                    for (int i=0;i<jsonArray.length();i++){
                                        road = road + jsonArray.getJSONObject(i).getString("name") + " ";
                                    }
                                    list.add(road);
                                    list.add(" ");
                                    list.add("附近：");
                                    jsonArray = jsonObject.getJSONObject("regeocode").getJSONArray("pois");
                                    for (int i=0;i<jsonArray.length();i++){
                                        road = jsonArray.getJSONObject(i).getString("location");
                                        list.add(jsonArray.getJSONObject(i).getString("address") +
                                                jsonArray.getJSONObject(i).getString("name") +
                                                "\n经度：" + road.split(",")[0] +
                                                "\n纬度：" + road.split(",")[1]
                                                );
                                    }
                                    map_create(cache[0],cache[1]);
                                }else {
                                    list.add("查询失败，请检查网络");
                                }
                            } catch (JSONException e) {
                                list.add("查询失败，程序异常，请反馈修复");
                                e.printStackTrace();
                            }
                            list_f5();
                        }
                    });
                    return;
                }

            }
        });
    }

    public void map_create(String x,String y){
        aMap.clear();
        Double jing,wei;
        if (Double.valueOf(x)<Double.valueOf(y)){
            jing = Double.valueOf(y);
            wei = Double.valueOf(x);
        }else {
            jing = Double.valueOf(x);
            wei = Double.valueOf(y);
        }


        mUiSettings = aMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setCompassEnabled(true);
        mUpdata = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(wei,jing), 12, 45, 30));//这是地理位置，就是经纬度。
        aMap.moveCamera(mUpdata);

        drawMarkers(Double.valueOf(x),Double.valueOf(y));
    }

    public void drawMarkers(Double x,Double y) {
        LatLng latLng = new LatLng(Double.valueOf(y),Double.valueOf(x));


        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("天眼显圣")
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.drawable.yan)))
                .draggable(true));
        marker.showInfoWindow();// 设置默认显示一个infowinfow

        Circle circle = aMap.addCircle(new CircleOptions().
                center(latLng).
                radius(4000).
                fillColor(Color.argb(100, 255, 235, 59)).
                strokeColor(Color.argb(100, 10, 10, 10)).
                strokeWidth(15));
        circle.setVisible(true);

        if (public_class.latLngss.size()!=0){
            Polyline polyline =aMap.addPolyline(new PolylineOptions().
                    addAll(public_class.latLngss).width(10).color(Color.argb(255, 1, 1, 1)));
        }
    }

    public void list_create(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GridLayoutManager gridLayoutManager=new GridLayoutManager(function_qmdj.this,1,GridLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(gridLayoutManager);
                sdcbAdapter = new sdcbAdapter(function_qmdj.this,list);
                sdcbAdapter.setmOnItemClickListener(new sdcbAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        public_class.copyContentToClipboard(function_qmdj.this,list.get(position));
                        public_class.shouToast(function_qmdj.this,"复制至剪切板");
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK){

            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }





    class MyRadioButtonListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // 选中状态改变时被触发
            switch (checkedId) {
                case R.id.qmdj_button0:
                    but_status=0;
                    break;
                case R.id.qmdj_button1:
                    but_status=1;
                    break;
                case R.id.qmdj_button2:
                    but_status=2;
                    break;
            }
        }
    }
}

