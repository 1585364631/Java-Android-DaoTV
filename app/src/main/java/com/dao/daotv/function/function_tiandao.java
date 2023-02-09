package com.dao.daotv.function;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dao.daotv.R;
import com.dao.daotv.dao.dao_celestial;
import com.dao.daotv.public_class;
import com.dao.daotv.sql.App;
import com.dao.daotv.sql.DaoSession;
import com.dao.daotv.sql.dao_celestialDao;
import com.google.android.exoplayer2.C;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class function_tiandao extends AppCompatActivity {

    LinearLayout linearLayout;
    TextView textView,textView1;
    EditText editText;
    Button button;
    private App app;
    private DaoSession daoSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_function_tiandao);

        app=App.getApp();

        linearLayout = (LinearLayout)function_tiandao.this.findViewById(R.id.dao_view);
        textView = (TextView)function_tiandao.this.findViewById(R.id.dao_title);
        textView1 = (TextView)function_tiandao.this.findViewById(R.id.dao_time);
        editText = (EditText)function_tiandao.this.findViewById(R.id.dao_key);
        button = (Button)function_tiandao.this.findViewById(R.id.dao_button);
        if (public_class.dao_gbook.equals("1")){
            Toast.makeText(function_tiandao.this,"复移小凳扶窗立,教识中天北斗星",Toast.LENGTH_SHORT).show();
            linearLayout.setVisibility(View.GONE);
            textView.setText("天道☯模式：已激活");
            textView1.setVisibility(View.VISIBLE);
            textView1.setText("有效期至：" + public_class.timetodate(public_class.dao_time) + "\n言出法随：评论免审发布" +
                    "\n遮掩天机：遮掩ip地址" +
                    "\n抱朴含真：清理缓存" +
                    "\n耳视目听：留言板" +
                    "\n奇门遁甲：ip定位" +
                    "\n撒豆成兵：经纬度逆推详细地址"+
                    "\n法天象地：简易3D地图" +
                    "\n安镇阴阳：公交查询" +
                    "\n壶天日月：万年历" +
                    "\n袖里乾坤：罗盘"+
                    "\n斗转星移：天象预测");
            public_class.setGradientFont(textView,"#FFC107","#FF9800");
        }else {
            textView.setText("天道☯模式：未激活");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().length()==0){
                    Toast.makeText(function_tiandao.this,"请输入密令",Toast.LENGTH_SHORT).show();
                    return;
                }
                public_class.http_get(public_class.dao_api + editText.getText(), new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(function_tiandao.this,"请检查网络",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String json_text = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(json_text);
                            json_text = jsonObject.getString("code");

                            if (json_text.equals("200")){
                                public_class.dao_gbook = "1";
                                public_class.dao_time = jsonObject.getString("device");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (public_class.timetotime(public_class.dao_time)){
                                            Toast.makeText(function_tiandao.this,"密令过期",Toast.LENGTH_SHORT).show();
                                            return;
                                        }


                                        daoSession = app.getDaoSession();
                                        dao_celestialDao dao_celestialDao = daoSession.getDao_celestialDao();
                                        dao_celestialDao.insertOrReplace(new dao_celestial(1l,"dao_gbook",public_class.dao_gbook));
                                        dao_celestialDao.insertOrReplace(new dao_celestial(2l,"dao_device",public_class.dao_time));

                                        Toast.makeText(function_tiandao.this,"激活成功",Toast.LENGTH_SHORT).show();
                                        textView1.setVisibility(View.VISIBLE);
                                        textView1.setText("有效期至：" + public_class.timetodate(public_class.dao_time) + "\n言出法随：评论免审发布" +
                                                "\n遮掩天机：遮掩ip地址" +
                                                "\n抱朴含真：清理缓存" +
                                                "\n耳视目听：留言板" +
                                                "\n奇门遁甲：ip定位" +
                                                "\n撒豆成兵：经纬度逆推详细地址"+
                                                "\n法天象地：简易3D地图" +
                                                "\n安镇阴阳：公交查询" +
                                                "\n壶天日月：万年历" +
                                                "\n袖里乾坤：罗盘"+
                                                "\n斗转星移：天象预测");
                                        public_class.setGradientFont(textView,"#FFC107","#FF9800");
                                        linearLayout.setVisibility(View.GONE);
                                    }
                                });
                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(function_tiandao.this,"密令错误",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
}