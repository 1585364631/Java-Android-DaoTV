package com.dao.daotv;

import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.dao.daotv.dao.dao_celestial;
import com.dao.daotv.function.function_tiandao;
import com.dao.daotv.sql.App;
import com.dao.daotv.sql.DaoSession;
import com.dao.daotv.sql.dao_celestialDao;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String update_url = "http://47.106.68.150/dingyue/update.txt";
    private String Dpath,Dpath1;
    private App app;
    private DaoSession daoSession;
    private float update_number = 1.4f;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = App.getApp();
        daoSession = app.getDaoSession();
        dao_celestialDao dao_celestialDao = daoSession.getDao_celestialDao();
        public_class.dao_gbook = dao_celestialDao.queryBuilder().where(com.dao.daotv.sql.dao_celestialDao.Properties.Dao.eq("dao_gbook")).unique().getStatus();
        public_class.dao_time = dao_celestialDao.queryBuilder().where(com.dao.daotv.sql.dao_celestialDao.Properties.Dao.eq("dao_device")).unique().getStatus();

        if (public_class.timetotime(public_class.dao_time)){
            dao_celestialDao.insertOrReplace(new dao_celestial(1l,"dao_gbook","0"));
            dao_celestialDao.insertOrReplace(new dao_celestial(2l,"dao_device","0"));
            public_class.dao_time="0";
            public_class.dao_gbook="0";
        }

        update_number = public_class.App_version;
        public_class.now_class = MainActivity.this;
        Dpath = String.valueOf(MainActivity.this.getFilesDir());
        public_class.dpath = Dpath;
        Dpath1 = "/storage/emulated/0/";
        MainActivity.PermissionUtils.isGrantExternalRW(this, 1);
    }


    public void load(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent main_face = new Intent(MainActivity.this, index.class);
                OkHttpClient update_http = new OkHttpClient();
                Request.Builder update_request = new Request.Builder().url(update_url);
                Call update_call = update_http.newCall(update_request.build());
                update_call.enqueue(new Callback() {

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "版本检查读取失败", Toast.LENGTH_LONG).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(main_face);
                                        update_call.cancel();
                                        finish();
                                    }
                                }, 500);
                            }
                        });
                    }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String text = response.body().string();
                        String[] update_text = text.split("\\(\\*\\)");
                        float now_update = Float.parseFloat(update_text[0]);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (now_update == update_number) {
                                    Toast.makeText(MainActivity.this, "当前软件为最新版本", Toast.LENGTH_SHORT).show();
                                    public_class.banner_id.clear();
                                    public_class.banner_url.clear();
                                    public_class.banner_title.clear();
                                    public_class.banner_url.add(update_text[5]);
                                    public_class.banner_title.add(update_text[6]);
                                    public_class.banner_id.add(update_text[7]);
                                    public_class.banner_url.add(update_text[8]);
                                    public_class.banner_title.add(update_text[9]);
                                    public_class.banner_id.add(update_text[10]);
                                    public_class.banner_url.add(update_text[11]);
                                    public_class.banner_title.add(update_text[12]);
                                    public_class.banner_id.add(update_text[13]);
                                    public_class.banner_url.add(update_text[14]);
                                    public_class.banner_title.add(update_text[15]);
                                    public_class.banner_id.add(update_text[16]);
                                    public_class.banner_url.add(update_text[17]);
                                    public_class.banner_title.add(update_text[18]);
                                    public_class.banner_id.add(update_text[19]);
                                    File update_apk = new File(Dpath + "/update.apk");
                                    if (update_apk.exists()) {
                                        update_apk.delete();
                                    }
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(main_face);
                                            update_call.cancel();
                                            finish();
                                        }
                                    }, 500);
                                } else {
                                    Toast.makeText(MainActivity.this, "当前软件需要更新", Toast.LENGTH_SHORT).show();
                                    LinearLayout update_body = (LinearLayout) findViewById(R.id.update_box);
                                    update_body.setVisibility(View.VISIBLE);
                                    TextView update_title = (TextView) findViewById(R.id.update_title);
                                    TextView update_content = (TextView) findViewById(R.id.update_content);
                                    Button update_button = (Button) findViewById(R.id.update_button);
                                    update_title.setText(update_text[1]);
                                    update_content.setText(update_text[2]);
                                    update_button.setText(update_text[3]);

                                    update_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            public_class.update_schedule = (ProgressBar) findViewById(R.id.update_schedule);
                                            public_class.Download(String.valueOf(update_text[4]), Dpath, "/update.apk");
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    //授权类
    public static class PermissionUtils {
        //这是要申请的权限
        private static String[] PERMISSIONS_CAMERA_AND_STORAGE = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.GET_TASKS,
        };

        public static boolean isGrantExternalRW(Activity activity, int requestCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                int storagePermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int cameraPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
                //检测是否有权限，如果没有权限，就需要申请
                if (storagePermission != PackageManager.PERMISSION_GRANTED ||
                        cameraPermission != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    activity.requestPermissions(PERMISSIONS_CAMERA_AND_STORAGE, requestCode);
                    //返回false。说明没有授权
                    return false;
                }
            }
            //说明已经授权
            return true;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //检验是否获取权限，如果获取权限，外部存储会处于开放状态，会弹出一个toast提示获得授权
                    String sdCard = Environment.getExternalStorageState();
                    if (sdCard.equals(Environment.MEDIA_MOUNTED)){
//                        Toast.makeText(this,"获得授权",Toast.LENGTH_LONG).show();
                            load();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load();
                        }
                    });
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}