package com.snnu.mysensors.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.snnu.mysensors.model.SensorData;

import java.util.ArrayList;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mysensors";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table if not exists sensor_data "+
                        "(id integer primary key autoincrement,"+
                "code float  DEFAULT NULL, " +
                "sensor_name varchar(255)  DEFAULT NULL," +
                "sensor_type integer  DEFAULT NULL," +
                "sensor_value1 double  DEFAULT NULL," +
                "sensor_value2 double  DEFAULT NULL," +
                "sensor_value3 double  DEFAULT NULL," +
                "create_time varchar(255)   DEFAULT NULL," +
                "address varchar(255)  DEFAULT NULL," +
                "floor varchar(10)  DEFAULT NULL," +
                "altitude double  DEFAULT NULL," +
                "longitude double  DEFAULT NULL," +
                "latitude double  DEFAULT NULL," +
                "phone_model varchar(50)  DEFAULT NULL," +
                "device_brand varchar(50)  DEFAULT NULL," +
                "android_version varchar(50)  DEFAULT NULL," +
                "userName varchar(50)  DEFAULT NULL," +
                "weather varchar(255) DEFAULT NULL)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertSensorData(Map<String,Object> sensorData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("code",(String)sensorData.get("code"));
        values.put("sensor_name",(String)sensorData.get("sensor_name"));
        values.put("sensor_type",(String)sensorData.get("sensor_type"));
        values.put("sensor_value1",(double)sensorData.get("sensor_value1"));
        values.put("sensor_value2",(double)sensorData.get("sensor_value2"));
        values.put("sensor_value3",(double)sensorData.get("sensor_value3"));
        values.put("create_time",(String)sensorData.get("create_time"));
        values.put("address",(String)sensorData.get("address"));
        values.put("floor",(String)sensorData.get("floor"));
        values.put("altitude",(double)sensorData.get("altitude"));
        values.put("longitude",(double)sensorData.get("longitude"));
        values.put("latitude",(double)sensorData.get("latitude"));
        values.put("phone_model",(String)sensorData.get("phone_model"));
        values.put("device_brand",(String)sensorData.get("device_brand"));
        values.put("android_version",(String)sensorData.get("android_version"));
        values.put("userName",(String)sensorData.get("userName"));
        values.put("weather",(String)sensorData.get("weather"));
        long id = db.insert("sensor_data",null,values);
        db.close();
        return id;
    }

    public ArrayList<SensorData> getAllSensorData(){
        ArrayList<SensorData> sensorDataList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+SensorData.TABLE_NAME+ " ORDER BY id ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor!=null){
           while(cursor.moveToNext()){
               SensorData sensorData = new SensorData();
               sensorData.setCode(cursor.getString(cursor.getColumnIndex(SensorData.CODE)));
               sensorData.setSensor_name(cursor.getString(cursor.getColumnIndex(SensorData.SENSOR_NAME)));
               sensorData.setSensor_type(cursor.getInt(cursor.getColumnIndex(SensorData.SENSOR_TYPE)));
               sensorData.setSensor_value1(cursor.getDouble(cursor.getColumnIndex(SensorData.SENSOR_VALUE1)));
               sensorData.setSensor_value2(cursor.getDouble(cursor.getColumnIndex(SensorData.SENSOR_VALUE2)));
               sensorData.setSensor_value3(cursor.getDouble(cursor.getColumnIndex(SensorData.SENSOR_VALUE3)));
               sensorData.setCreate_time(cursor.getString(cursor.getColumnIndex(SensorData.CREATE_TIME)));
               sensorData.setAddress(cursor.getString(cursor.getColumnIndex(SensorData.ADDRESS)));
               sensorData.setFloor(cursor.getString(cursor.getColumnIndex(SensorData.FLOOR)));
               sensorData.setAltitude(cursor.getDouble(cursor.getColumnIndex(SensorData.ALTITUDE)));
               sensorData.setLongitude(cursor.getDouble(cursor.getColumnIndex(SensorData.LONGITUDE)));
               sensorData.setLatitude(cursor.getDouble(cursor.getColumnIndex(SensorData.LATITUDE)));
               sensorData.setPhone_model(cursor.getString(cursor.getColumnIndex(SensorData.PHONE_MODEL)));
               sensorData.setDevice_brand(cursor.getString(cursor.getColumnIndex(SensorData.DEVICE_BRAND)));
               sensorData.setAndroid_version(cursor.getString(cursor.getColumnIndex(SensorData.ANDROID_VERSION)));
               sensorData.setUserName(cursor.getString(cursor.getColumnIndex(SensorData.USERNAME)));
               sensorData.setWeather(cursor.getString(cursor.getColumnIndex(SensorData.WEATHER)));
               sensorDataList.add(sensorData);
           }
        }
        db.close();
        return sensorDataList;
    }

    public int deleteALLSensorData(){
        SQLiteDatabase db = getWritableDatabase();
        int idReturnByDelete = db.delete(SensorData.TABLE_NAME,String.valueOf(1),null);
        db.close();
        return idReturnByDelete;
    }
}
