package com.dao.daotv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.dao.daotv.clear.Cache_Data_Clear;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import fragment.Fragment_My;
import fragment.Fragment_index;
import fragment.Fragment_list;
import fragment.Fragment_video;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class index extends AppCompatActivity {

    private static TextView index_title;
    private TabLayout tablayout;
    private List<Fragment> list;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_index);
        public_class.latLngss = new ArrayList<LatLng>();

        public_class.http_get("http://47.106.68.150/dingyue/getip.php", new Callback() {
            @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    public_class.gbook_ip = response.body().string();
                    return;
                }
            });



        tablayout = (TabLayout)findViewById(R.id.nav_foot);
        viewpager = (ViewPager)findViewById(R.id.viewpager);
        list = new ArrayList<>();
        list.add(new Fragment_index());
        list.add(new Fragment_video());
        list.add(new Fragment_list());
        list.add(new Fragment_My());


        viewpager.setAdapter(new Myviewpageradapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewpager);


        tablayout.removeAllTabs();
        tablayout.addTab(tablayout.newTab().setText("天地玄宗"));
        tablayout.addTab(tablayout.newTab().setText("万炁本根"));
        tablayout.addTab(tablayout.newTab().setText("广修亿劫"));
        tablayout.addTab(tablayout.newTab().setText("证吾神通"));

        public_class.now_class = index.this;
        public_class.now_act = index.this;

        index_title = (TextView)findViewById(R.id.index_title);
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



    }

    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出APP", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class Myviewpageradapter extends FragmentPagerAdapter {
        public Myviewpageradapter(@NonNull FragmentManager fm) {
            super(fm);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }
        @Override
        public int getCount() {
            return list.size();
        }
    }

}