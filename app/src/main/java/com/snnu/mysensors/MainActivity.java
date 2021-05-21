package com.snnu.mysensors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.qweather.sdk.view.HeConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private SensorManager sm;
    private TextView txt_show;
    private final int REQUEST_GPS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HeConfig.init("HE2105120908221467", "8b6e6192a124431689bd75e13623779d");
        //切换至开发版服务
        HeConfig.switchToDevService();
        //检查权限
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);
        }else{
            startMyService();
        }
    }


    public void startMyService(){
        //获取传感器管理器
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        txt_show = findViewById(R.id.txt_show);
        List<Sensor> allSensors = sm.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sb = new StringBuilder();
        sb.append("此手机有"+allSensors.size()+"个传感器，分别有：\n\n");
        for(Sensor s:allSensors){
            switch (s.getType()){
                case Sensor.TYPE_ACCELEROMETER:
                    sb.append(s.getType()+"加速度传感器(Accelerometer sensor)"+"\n");
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    sb.append(s.getType()+"陀螺仪传感器(Gyroscope sensor)"+"\n");
                    break;
                case Sensor.TYPE_LIGHT:
                    sb.append(s.getType()+"光线传感器(Light sensor)"+"\n");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    sb.append(s.getType()+"磁场传感器(Magnetic field sensor)"+"\n");
                    break;
                case Sensor.TYPE_ORIENTATION:
                    sb.append(s.getType()+"方向传感器(Orientation sensor)"+"\n");
                    break;
                case Sensor.TYPE_PRESSURE:
                    sb.append(s.getType()+"气压传感器(Pressure sensor)"+"\n");
                    break;
                case Sensor.TYPE_PROXIMITY:
                    sb.append(s.getType()+"距离传感器(Proximity sensor)"+"\n");
                    break;
                case Sensor.TYPE_TEMPERATURE:
                    sb.append(s.getType()+"温度传感器(Temperature  sensor)"+"\n");
                    break;
                case Sensor.TYPE_GRAVITY:
                    sb.append(s.getType()+"重力传感器(gravity sensor)"+"\n");
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    sb.append(s.getType()+"线性加速度传感器(linear_acceleration sensor)"+"\n");
                    break;
                case Sensor.TYPE_ROTATION_VECTOR:
                    sb.append(s.getType()+"翻转传感器(rotation vector sensor)"+"\n");
                    break;
                case Sensor.TYPE_RELATIVE_HUMIDITY:
                    sb.append(s.getType()+"相对湿度传感器(relative_humidity sensor)"+"\n");
                    break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    sb.append(s.getType()+"ambient_temperature sensor"+"\n");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                    sb.append(s.getType()+"magnetic_field_uncalibrated sensor"+"\n");
                    break;
                case Sensor.TYPE_GAME_ROTATION_VECTOR:
                    sb.append(s.getType()+"game_rotation_vector sensor"+"\n");
                    break;
                case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                    sb.append(s.getType()+"gyroscope_uncalibrated sensor"+"\n");
                    break;
                case Sensor.TYPE_SIGNIFICANT_MOTION:
                    sb.append(s.getType()+"significant_motion sensor"+"\n");
                    break;
                case Sensor.TYPE_STEP_DETECTOR:
                    sb.append(s.getType()+"step_detector sensor"+"\n");
                    break;
                case Sensor.TYPE_STEP_COUNTER:
                    sb.append(s.getType()+"step_counter sensor"+"\n");
                    break;
                case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                    sb.append(s.getType()+"geomagnetic_rotation_vector sensor"+"\n");
                    break;
                case Sensor.TYPE_HEART_RATE:
                    sb.append(s.getType()+"heart_rate sensor"+"\n");
                    break;
                case Sensor.TYPE_POSE_6DOF:
                    sb.append(s.getType()+"pose_6dof sensor"+"\n");
                    break;
                case Sensor.TYPE_STATIONARY_DETECT:
                    sb.append(s.getType()+"stationary_detect sensor"+"\n");
                    break;
                case Sensor.TYPE_MOTION_DETECT:
                    sb.append(s.getType()+"motion_detect sensor"+"\n");
                    break;
                case Sensor.TYPE_HEART_BEAT:
                    sb.append(s.getType()+"heart_beat sensor"+"\n");
                    break;
                case Sensor.TYPE_LOW_LATENCY_OFFBODY_DETECT:
                    sb.append(s.getType()+"low_latency_offbody_detect sensor"+"\n");
                    break;
                case Sensor.TYPE_ACCELEROMETER_UNCALIBRATED:
                    sb.append(s.getType()+"relative_humidity sensor"+"\n");
                    break;
               /* case Sensor.TYPE_HINGE_ANGLE:
                    sb.append(s.getType()+"hinge_angle sensor"+"\n");
                    break;*/
                default:
                    sb.append(s.getType()+"其他传感器"+"\n");
                    break;
            }
            sb.append("设备名称："+s.getName()+"\n 设备版本："+s.getVersion()+"\n 供应商："+s.getVendor()+"\n\n");
        }
        txt_show.setText(sb.toString());
        Intent intent = new Intent(getApplicationContext(),LongRunningService.class);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    startMyService();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

}