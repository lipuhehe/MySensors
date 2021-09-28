package com.snnu.mysensors.activaty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qweather.sdk.view.HeConfig;
import com.snnu.mysensors.R;
import com.snnu.mysensors.db.DBHelper;
import com.snnu.mysensors.service.LongRunningService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SensorManager sm;
    private TextView txt_show;
    private Button location;
    private final int REQUEST_GPS = 1;
    private AlertDialog dialog = null;
    private AlertDialog.Builder builder = null;
    private final String[] addresses = {"教室","实验室","宿舍","图书馆","食堂","体育馆","户外","超市","体育场"};
    private String address = "";
    private String floor = "";
    private DBHelper dbHelper;
    private Map<Integer,String> sensorMap= new HashMap<Integer,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HeConfig.init("HE2105120908221467", "8b6e6192a124431689bd75e13623779d");

        if (!this.isTaskRoot()) {
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }

        //初始化数据库
        dbHelper = new DBHelper(this);
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
            initGPS();
            isConn(MainActivity.this);
            startMyService();
        }
    }

    @Override
    public boolean moveTaskToBack(boolean nonRoot) {
        return super.moveTaskToBack(nonRoot);
    }

    public void startMyService(){
        //获取传感器管理器
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        txt_show = findViewById(R.id.txt_show);
        location = findViewById(R.id.location);
        location.setOnClickListener(this);
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
            sensorMap.put(s.getType(),s.getName());
        }
        txt_show.setText(sb.toString());
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


    private void initGPS() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(MainActivity.this, "请打开GPS", Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("请打开GPS连接");
            dialog.setMessage("为了方便您使用本软件，请先打开GPS");
            dialog.setPositiveButton("设置",  new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // 转到手机设置界面，用户设置GPS
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    Toast.makeText(MainActivity.this, "打开后直接点击返回键即可，若不打开返回下次将再次出现", Toast.LENGTH_SHORT).show();
                    startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                }
            });
            dialog.setNeutralButton("取消", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }
    }

    /**
     * 判断网络连接是否已开
     * true 已打开  false 未打开
     */
    public static boolean isConn(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
            searchNetwork(context); //弹出提示对话框
        }
        return false;
    }

    /**
     * 判断网络是否连接成功，连接成功不做任何操作
     * 未连接则弹出对话框提示用户设置网络连接
     */
    public static void searchNetwork(final Context context) {
        //提示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?").setPositiveButton("设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                //判断手机系统的版本  即API大于10 就是3.0或以上版本
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                context.startActivity(intent);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public void intendForService(){
        Intent intent = new Intent(getApplicationContext(), LongRunningService.class);
        startService(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.location){
            //Toast.makeText(MainActivity.this, "点击了巴腾", Toast.LENGTH_SHORT).show();
            builder = new AlertDialog.Builder(MainActivity.this).setTitle("请选择当前所在位置")
                    .setSingleChoiceItems(addresses, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            address = addresses[i];
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(!address.equals("")){
                                EditText editText = new EditText(MainActivity.this);
                                editText.setHint("填写数字即可");
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("请填写楼层")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                floor = editText.getText().toString();
                                                Intent intent = new Intent(MainActivity.this,LongRunningService.class);
                                                intent.putExtra("address",address);
                                                intent.putExtra("floor",floor);
                                                intent.putExtra("sensorMap",(Serializable) sensorMap);
                                                startService(intent);
                                            }
                                        })
                                        .setView(editText);
                                AlertDialog dialog =builder.create();
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.show();
                            }else{
                                Toast.makeText(MainActivity.this, "选择选项后才能发送!", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }



}