package com.snnu.mysensors.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.snnu.mysensors.model.SensorData;

import java.util.ArrayList;

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
                "accelerometer_datax float  DEFAULT NULL, " +
                "accelerometer_datay float  DEFAULT NULL," +
                "accelerometer_dataz float  DEFAULT NULL," +
                "address varchar(255)  DEFAULT NULL," +
                "altitude double  DEFAULT NULL," +
                "create_time TimeStamp DEFAULT (datetime('now', 'localtime'))," +
                "floor varchar(255)   DEFAULT NULL," +
                "gravity_datax float  DEFAULT NULL," +
                "gravity_datay float  DEFAULT NULL," +
                "gravity_dataz float  DEFAULT NULL," +
                "gyroscope_data float  DEFAULT NULL," +
                "humidity_data float  DEFAULT NULL," +
                "latitude double  DEFAULT NULL," +
                "light_data float  DEFAULT NULL," +
                "linear_acceleration_datax float  DEFAULT NULL," +
                "linear_acceleration_datay float  DEFAULT NULL," +
                "linear_acceleration_dataz float  DEFAULT NULL," +
                "longitude double  DEFAULT NULL," +
                "magnetic_datax float  DEFAULT NULL," +
                "magnetic_datay float  DEFAULT NULL," +
                "magnetic_dataz float  DEFAULT NULL," +
                "phone_model varchar(255)  DEFAULT NULL," +
                "pressure_data float  DEFAULT NULL," +
                "proximity_data float  DEFAULT NULL," +
                "rotation_vector_datax float  DEFAULT NULL," +
                "rotation_vector_datay float  DEFAULT NULL," +
                "rotation_vector_dataz float  DEFAULT NULL," +
                "step_counter_data float  DEFAULT NULL," +
                "temperature_data float  DEFAULT NULL," +
                "weather varchar(255) DEFAULT NULL)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertSensorData(SensorData sensorData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("accelerometer_datax",sensorData.getAccelerometer_datax());
        values.put("accelerometer_datay",sensorData.getAccelerometer_datay());
        values.put("accelerometer_dataz",sensorData.getAccelerometer_dataz());
        values.put("latitude",sensorData.getAltitude());
        values.put("longitude",sensorData.getLongitude());
        values.put("light_data",sensorData.getLight_data());
        values.put("temperature_data",sensorData.getTemperature_data());
        values.put("magnetic_datax",sensorData.getMagnetic_datax());
        values.put("magnetic_datay",sensorData.getMagnetic_datay());
        values.put("magnetic_dataz",sensorData.getMagnetic_dataz());
        values.put("pressure_data",sensorData.getPressure_data());
        values.put("proximity_data",sensorData.getProximity_data());
        values.put("gravity_datax",sensorData.getGravity_datax());
        values.put("gravity_datay",sensorData.getGravity_datay());
        values.put("gravity_dataz",sensorData.getGravity_dataz());
        values.put("linear_acceleration_datax",sensorData.getAccelerometer_dataz());
        values.put("linear_acceleration_datay",sensorData.getAccelerometer_datay());
        values.put("linear_acceleration_dataz",sensorData.getAccelerometer_dataz());
        values.put("rotation_vector_datax",sensorData.getRotation_vector_datax());
        values.put("rotation_vector_datay",sensorData.getRotation_vector_datay());
        values.put("rotation_vector_dataz",sensorData.getAccelerometer_dataz());
        values.put("humidity_data",sensorData.getHumidity_data());
        values.put("gyroscope_data",sensorData.getGyroscope_data());
        values.put("step_counter_data",sensorData.getStep_counter_data());
        values.put("address",sensorData.getAddress());
        values.put("weather",sensorData.getWeather());
        values.put("altitude",sensorData.getAltitude());
        values.put("floor",sensorData.getFloor());
        values.put("phone_model",sensorData.getPhone_model());
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
               sensorData.setAccelerometer_datax(cursor.getFloat(cursor.getColumnIndex(SensorData.ACCELEROMETER_DATAX)));
               sensorData.setAccelerometer_datay(cursor.getFloat(cursor.getColumnIndex(SensorData.ACCELEROMETER_DATAY)));
               sensorData.setAccelerometer_dataz(cursor.getFloat(cursor.getColumnIndex(SensorData.ACCELEROMETER_DATAZ)));
               sensorData.setAltitude(cursor.getFloat(cursor.getColumnIndex(SensorData.ALTITUDE)));
               sensorData.setLongitude(cursor.getFloat(cursor.getColumnIndex(SensorData.LONGITUDE)));
               sensorData.setTemperature_data(cursor.getFloat(cursor.getColumnIndex(SensorData.TEMPERATURE_DATA)));
               sensorData.setLight_data(cursor.getFloat(cursor.getColumnIndex(SensorData.LIGHT_DATA)));
               sensorData.setMagnetic_datax(cursor.getFloat(cursor.getColumnIndex(SensorData.MAGNETIC_DATAX)));
               sensorData.setMagnetic_datay(cursor.getFloat(cursor.getColumnIndex(SensorData.MAGNETIC_DATAY)));
               sensorData.setMagnetic_dataz(cursor.getFloat(cursor.getColumnIndex(SensorData.MAGNETIC_DATAZ)));
               sensorData.setPressure_data(cursor.getFloat(cursor.getColumnIndex(SensorData.PRESSURE_DATA)));
               sensorData.setProximity_data(cursor.getFloat(cursor.getColumnIndex(SensorData.PROXIMITY_DATA)));
               sensorData.setGravity_datax(cursor.getFloat(cursor.getColumnIndex(SensorData.GRAVITY_DATAX)));
               sensorData.setGravity_datay(cursor.getFloat(cursor.getColumnIndex(SensorData.GRAVITY_DATAY)));
               sensorData.setGravity_dataz(cursor.getFloat(cursor.getColumnIndex(SensorData.GRAVITY_DATAZ)));
               sensorData.setLinear_acceleration_datax(cursor.getFloat(cursor.getColumnIndex(SensorData.LINEAR_ACCELERATION_DATAX)));
               sensorData.setLinear_acceleration_datay(cursor.getFloat(cursor.getColumnIndex(SensorData.LINEAR_ACCELERATION_DATAY)));
               sensorData.setLinear_acceleration_dataz(cursor.getFloat(cursor.getColumnIndex(SensorData.LINEAR_ACCELERATION_DATAZ)));
               sensorData.setRotation_vector_datax(cursor.getFloat(cursor.getColumnIndex(SensorData.ROTATION_VECTOR_DATAX)));
               sensorData.setRotation_vector_datay(cursor.getFloat(cursor.getColumnIndex(SensorData.ROTATION_VECTOR_DATAY)));
               sensorData.setRotation_vector_dataz(cursor.getFloat(cursor.getColumnIndex(SensorData.ROTATION_VECTOR_DATAZ)));
               sensorData.setHumidity_data(cursor.getFloat(cursor.getColumnIndex(SensorData.HUMIDITY_DATA)));
               sensorData.setGyroscope_data(cursor.getFloat(cursor.getColumnIndex(SensorData.GYROSCOPE_DATA)));
               sensorData.setStep_counter_data(cursor.getColumnIndex(SensorData.STEP_COUNT_DATA));
               sensorData.setAddress(cursor.getString(cursor.getColumnIndex(SensorData.ADDRESS)));
               sensorData.setWeather(cursor.getString(cursor.getColumnIndex(SensorData.WEATHER)));
               sensorData.setFloor(cursor.getString(cursor.getColumnIndex(SensorData.FLOOR)));
               sensorData.setPhone_model(cursor.getString(cursor.getColumnIndex(SensorData.PHONE_MODEL)));
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
