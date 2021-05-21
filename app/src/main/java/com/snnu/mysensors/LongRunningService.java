package com.snnu.mysensors;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.QWeather;

import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class LongRunningService extends Service implements SensorEventListener{

    AlertDialog.Builder builder = null;
    private EditText edit = null;
    private Spinner spinner = null;
    private String address;
    private Handler handler;

    private SensorManager sensorManager;
    private static final String SERVICE_URL = "http://120.26.199.60:8080/saveSensorData";
    //private static final String SERVICE_URL = "http://10.150.104.149:8080/saveSensorData";
    private RequestParams params = new RequestParams();
    private AsyncHttpClient client = new AsyncHttpClient();

    private LocationClient locationClient;
    private LocationClientOption option;
    private MyLocationListener myListener = new MyLocationListener();

    private float accelerometer_datax = 0;
    private float accelerometer_datay = 0;
    private float accelerometer_dataz = 0;

    private double latitude = 0;    //获取纬度信息
    private double longitude = 0;   //获取纬度信息
    private double altitude = 0;    //获取海拔高度
    private String floor;           //获取楼层
    private String weather;
    private String  phone_model;    //手机型号
    //光传感器值
    private float light_data = 0;
    //温度传感器值
    private float temperature_data = 0;
    //磁场值
    private float magnetic_datax = 0;
    private float magnetic_datay = 0;
    private float magnetic_dataz = 0;
    //压力值
    private float pressure_data = 0;
    //距离值
    private float proximity_data = 0;
    //重力值
    private float gravity_datax = 0;
    private float gravity_datay = 0;
    private float gravity_dataz = 0;
    //线性加速度值
    private float linear_acceleration_datax = 0;
    private float linear_acceleration_datay = 0;
    private float linear_acceleration_dataz = 0;
    //方向传感器值
    private float rotation_vector_datax = 0;
    private float rotation_vector_datay = 0;
    private float rotation_vector_dataz = 0;
    //湿度值
    private float humidity_data = 0;
    //陀螺仪值
    private float gyroscope_data = 0;
    //计步传感器值
    private float step_counter_data = 0;

    private boolean selected = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getWeather();
        phone_model = SystemUtil.getDeviceBrand();
        //handler = new Handler(Looper.getMainLooper());
        edit = new EditText(getApplicationContext());
        //spinner = new Spinner(getApplicationContext());
        //String [] addresses = this.getResources().getStringArray(R.array.address);
        edit.setHint("如：教室、图书馆、操场、宿舍");
        builder = new AlertDialog.Builder(getApplicationContext())
                .setTitle("请输入你的所在地点")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        address = edit.getText().toString();
                        startTimeTask(address);
                    }
                })
                .setView(edit);
        AlertDialog dialog = builder.create();
        //8.0系统加强后台管理，禁止在其他应用和窗口弹提醒弹窗，如果要弹，必须使用TYPE_APPLICATION_OVERLAY，否则弹不出
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
        }else {
            dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
       /* handler.post(new Runnable() {
            @Override
            public void run() {
                showDialog("请输入你的所在地点","",edit);
            }
        });*/
        return super.onStartCommand(intent, flags, startId);
    }

    public void startTimeTask(String address){
        //声明LocationClient类
        locationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        locationClient.registerLocationListener(myListener);
        option = new LocationClientOption();
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
        option.setCoorType("bd09ll");
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(1000);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setOpenGps(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setLocationNotify(true);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位
        option.setWifiCacheTimeOut(5*60*1000);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);
        //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
        option.setNeedNewVersionRgc(true);
        //设置是否需要海拔高度信息
        option.setIsNeedAltitude(true);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        locationClient.setLocOption(option);
        // 开启室内定位模式（重复调用也没问题），开启后，定位SDK会融合各种定位信息（GPS,WI-FI，蓝牙，传感器等）连续平滑的输出定位结果；
        locationClient.startIndoorMode();
        //mLocationClient为第二步初始化过的LocationClient对象
        //调用LocationClient的start()方法，便可发起定位请求
        locationClient.start();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),SensorManager.SENSOR_DELAY_NORMAL);
        params.put("accelerometer_datax",accelerometer_datax);
        params.put("accelerometer_datay",accelerometer_datay);
        params.put("accelerometer_dataz",accelerometer_dataz);
        params.put("latitude",latitude);
        params.put("longitude",longitude);
        params.put("light_data",light_data);
        params.put("temperature_data",temperature_data);
        params.put("magnetic_datax",magnetic_datax);
        params.put("magnetic_datay",magnetic_datay);
        params.put("magnetic_dataz",magnetic_dataz);
        params.put("pressure_data",pressure_data);
        params.put("proximity_data",proximity_data);
        params.put("gravity_datax",gravity_datax);
        params.put("gravity_datay",gravity_datay);
        params.put("gravity_dataz",gravity_dataz);
        params.put("linear_acceleration_datax",linear_acceleration_datax);
        params.put("linear_acceleration_datay",linear_acceleration_datay);
        params.put("linear_acceleration_dataz",linear_acceleration_dataz);
        params.put("rotation_vector_datax",rotation_vector_datax);
        params.put("rotation_vector_datay",rotation_vector_datay);
        params.put("rotation_vector_dataz",rotation_vector_dataz);
        params.put("humidity_data",humidity_data);
        params.put("gyroscope_data",gyroscope_data);
        params.put("step_counter_data",step_counter_data);
        params.put("address",address);
        params.put("weather",weather);
        params.put("altitude",altitude);
        params.put("floor",floor);
        params.put("phone_model",phone_model);
        client.post(SERVICE_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("数据发送","error code:",error);
            }
        });
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 60*1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this,LongRunningService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelerometer_datax = event.values[0];
            accelerometer_datay = event.values[1];
            accelerometer_dataz = event.values[2];
        }
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){                                      //光传感器
            light_data = event.values[0];
        }
        if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){                        //温度传感器
            temperature_data = event.values[0];
        }
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){                             //磁场传感器
            magnetic_datax = event.values[0];
            magnetic_datay = event.values[1];
            magnetic_dataz = event.values[2];
        }
        if(event.sensor.getType() == Sensor.TYPE_PRESSURE){                                   //压力传感器
            pressure_data = event.values[0];
        }
        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){                                  //距离传感器
            proximity_data = event.values[0];
        }
        if(event.sensor.getType() == Sensor.TYPE_GRAVITY){                                    //重力传感器
            gravity_datax = event.values[0];
            gravity_datay = event.values[1];
            gravity_dataz = event.values[2];
        }
        if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){                        //线性加速度
            linear_acceleration_datax = event.values[0];
            linear_acceleration_datay = event.values[1];
            linear_acceleration_dataz = event.values[2];
        }
        if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){                            //旋转矢量传感器
            rotation_vector_datax = event.values[0];
            rotation_vector_datay = event.values[1];
            rotation_vector_dataz = event.values[2];
        }
        if(event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){                          //相对湿度传感器
            humidity_data = event.values[0];
        }
        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){                                  //陀螺仪传感器
            gyroscope_data = event.values[0];
        }
        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){                               //计步传感器
            step_counter_data = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    


    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            latitude = bdLocation.getLatitude();    //获取纬度信息
            longitude = bdLocation.getLongitude();    //获取经度信息
            //floor = bdLocation.getFloor();// 室内定位的楼层信息，如 f1,f2,b1,b2
            float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f
            altitude = bdLocation.getAltitude();      //获取海拔高度
            String coorType = bdLocation.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = bdLocation.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果

            if (bdLocation.getFloor() != null) {
                // 当前支持高精度室内定位
                String buildingID = bdLocation.getBuildingID();// 百度内部建筑物ID
                String buildingName = bdLocation.getBuildingName();// 百度内部建筑物缩写
                floor = bdLocation.getFloor();// 室内定位的楼层信息，如 f1,f2,b1,b2
                locationClient.startIndoorMode();// 开启室内定位模式（重复调用也没问题），开启后，定位SDK会融合各种定位信息（GPS,WI-FI，蓝牙，传感器等）连续平滑的输出定位结果；
            }
        }
    }

    private void showDialog(String title, String msg, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext())
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        });
        if (view!=null){
            builder.setView(view);
        }
        //下面这行代码放到子线程中会 Can't create handler inside thread that has not called Looper.prepare()
        AlertDialog dialog = builder.create();
        //设置点击其他地方不可取消此 Dialog
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        //8.0系统加强后台管理，禁止在其他应用和窗口弹提醒弹窗，如果要弹，必须使用TYPE_APPLICATION_OVERLAY，否则弹不出
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
        }else {
            dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        }
        dialog.show();
    }

    public void getWeather(){
        String jingdu = String.format("%.2f", longitude);
        String weidu = String.format("%.2f", latitude);
        QWeather.getWeatherNow(getApplicationContext(),jingdu+","+weidu, Lang.ZH_HANS, Unit.METRIC,new QWeather.OnResultWeatherNowListener(){

            @Override
            public void onError(Throwable throwable) {
                Log.i("和风天气", "getWeather onError：" + throwable);
                weather="获取天气失败";
            }

            @Override
            public void onSuccess(WeatherNowBean weatherNowBean) {
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if (Code.OK == weatherNowBean.getCode()) {
                    WeatherNowBean.NowBaseBean now = weatherNowBean.getNow();
                    weather = now.getText();
                } else {
                    //在此查看返回数据失败的原因
                    Code code = weatherNowBean.getCode();
                    Log.i("和风天气", "failed code: " + code);
                }

            }
        });
    }


}
