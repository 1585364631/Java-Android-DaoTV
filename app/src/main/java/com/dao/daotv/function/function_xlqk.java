package com.dao.daotv.function;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dao.daotv.R;

public class function_xlqk extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    int gif = 0;
    private float currentDegree = 0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_xlqk);
        imageView = (ImageView)findViewById(R.id.xlqk_img);
        textView = (TextView)findViewById(R.id.xlqk_lp);

        // 传感器管理器
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        // 注册传感器(Sensor.TYPE_ORIENTATION(方向传感器);SENSOR_DELAY_FASTEST(0毫秒延迟);
        // SENSOR_DELAY_GAME(20,000毫秒延迟)、SENSOR_DELAY_UI(60,000毫秒延迟))
        sm.registerListener(new SensorEventListener() {
                                @Override
                                public void onSensorChanged(SensorEvent event) {
                                    if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                                        float degree = event.values[0];
                                          /*
                                          RotateAnimation类：旋转变化动画类
                                          参数说明:
                                          fromDegrees：旋转的开始角度。
                                          toDegrees：旋转的结束角度。
                                          pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
                                          pivotXValue：X坐标的伸缩值。
                                          pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
                                          pivotYValue：Y坐标的伸缩值
                                          */
                                        if (gif==0){
                                            gif=1;

                                            RotateAnimation ra = new RotateAnimation(0, -degree-720-180,
                                                    Animation.RELATIVE_TO_SELF, 0.5f,
                                                    Animation.RELATIVE_TO_SELF, 0.5f);
                                            //旋转过程持续时间
                                            ra.setDuration(2000);
                                            ra.setAnimationListener(new Animation.AnimationListener() {
                                                @Override
                                                public void onAnimationStart(Animation animation) {

                                                }

                                                @Override
                                                public void onAnimationEnd(Animation animation) {
                                                    gif=2;
                                                }

                                                @Override
                                                public void onAnimationRepeat(Animation animation) {

                                                }
                                            });
                                            //罗盘图片使用旋转动画
                                            imageView.startAnimation(ra);
                                            currentDegree = -degree-180;
                                            return;
                                        }

                                        if (gif==2){
                                            float data = (float)(Math.round(degree*10))/10;
//                                            int data = (int)degree;
                                            if ((degree>=0&&degree<22.5)||(degree>=337.5&&degree<=360)){
                                                textView.setText("向北 " + String.valueOf(data));
                                            }
                                            if ((degree>=22.5&&degree<67.5)){
                                                textView.setText("向东北 "  + String.valueOf(data));
                                            }
                                            if ((degree>=67.5&&degree<112.5)){
                                                textView.setText("向东 "  + String.valueOf(data));
                                            }
                                            if ((degree>=112.5&&degree<157.5)){
                                                textView.setText("向东南 " + String.valueOf(data));
                                            }
                                            if ((degree>=157.5&&degree<202.5)){
                                                textView.setText("向南 " + String.valueOf(data));
                                            }
                                            if ((degree>=202.5&&degree<247.5)){
                                                textView.setText("向西南 " + String.valueOf(data));
                                            }
                                            if ((degree>=247.5&&degree<292.5)){
                                                textView.setText("向西 " + String.valueOf(data));
                                            }
                                            if ((degree>=292.5&&degree<337.5)){
                                                textView.setText("向西北 " + String.valueOf(data));
                                            }


                                            RotateAnimation ra = new RotateAnimation(currentDegree, -degree-180,
                                                    Animation.RELATIVE_TO_SELF, 0.5f,
                                                    Animation.RELATIVE_TO_SELF, 0.5f);
                                            //旋转过程持续时间
                                            ra.setDuration(200);
                                            //罗盘图片使用旋转动画
                                            imageView.startAnimation(ra);
                                            currentDegree = -degree-180;
                                        }


                                    }
                                }
                                @Override
                                public void onAccuracyChanged(Sensor sensor, int i) {

                                }
                            },
                sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_FASTEST);
        
    }

}