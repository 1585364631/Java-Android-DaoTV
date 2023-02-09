package com.dao.daotv.function;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.dao.daotv.R;
import com.dao.daotv.public_class;

import java.time.LocalDate;

public class function_ftxd extends AppCompatActivity {

    AMap aMap;
    MapView mMapView = null;
    MyLocationStyle myLocationStyle;
    Button button;
    int map_status = 0;




    private UiSettings mUiSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_function_ftxd);

        button = (Button)findViewById(R.id.ftxd_button);

        mMapView = (MapView) findViewById(R.id.ftxd_map);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.showIndoorMap(true);
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setLogoBottomMargin(-50);//隐藏logo
        mUiSettings.setScaleControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setScaleControlsEnabled(true);



        CameraUpdate mUpdata = CameraUpdateFactory.newCameraPosition(new CameraPosition(aMap.getCameraPosition().target, aMap.getCameraPosition().zoom, 45, aMap.getCameraPosition().bearing));//这是地理位置，就是经纬度。
        aMap.moveCamera(mUpdata);

        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
        myLocationStyle.interval(1000);
        myLocationStyle.strokeColor(0);
        myLocationStyle.radiusFillColor(0);
        myLocationStyle.strokeWidth(0);
        aMap.setMyLocationStyle(myLocationStyle);





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (map_status==0){
                    map_status=1;
                    button.setText("关闭定位");
                    myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
                    aMap.setMyLocationStyle(myLocationStyle);
                    aMap.setMyLocationEnabled(true);
                }else {
                    map_status=0;
                    button.setText("开启定位");
                    myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
                    aMap.setMyLocationStyle(myLocationStyle);
                    aMap.setMyLocationEnabled(true);
                }
            }
        });

        if (public_class.latLngss.size()!=0){
            Polyline polyline =aMap.addPolyline(new PolylineOptions().
                    addAll(public_class.latLngss).width(10).color(Color.argb(255, 1, 1, 1)));
        }


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();

    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            map_status=0;
            button.setText("开启定位");
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.setMyLocationEnabled(false);
            moveTaskToBack(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}