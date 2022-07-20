package com.snnu.mysensors.model;

import java.io.Serializable;
import java.util.Date;

public class SensorData implements Serializable {

    public static final String TABLE_NAME = "sensor_data";
    public static final String CODE = "code";
    public static final String SENSOR_NAME = "sensor_name";
    public static final String SENSOR_TYPE = "sensor_type";
    public static final String SENSOR_VALUE1 = "sensor_value1";
    public static final String SENSOR_VALUE2 = "sensor_value2";
    public static final String SENSOR_VALUE3 = "sensor_value3";
    public static final String CREATE_TIME = "create_time";
    public static final String ADDRESS = "address";
    public static final String FLOOR = "floor";
    public static final String ALTITUDE = "altitude";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String PHONE_MODEL = "phone_model";
    public static final String DEVICE_BRAND = "device_brand";
    public static final String ANDROID_VERSION = "android_version";
    public static final String WEATHER = "weather";
    public static final String USERNAME = "userName";



    private Integer id;
    private String code;
    private String sensor_name;
    private int sensor_type;
    private double sensor_value1;
    private double sensor_value2;
    private double sensor_value3;

    private String create_time;

    private String  address;

    private String floor;

    private double latitude;

    private double longitude;

    private double altitude;

    private String phone_model;

    private String device_brand;

    private String  android_version;

    private String  weather;

    private String userName;


    public SensorData() {
    }

    public SensorData(String code, String sensor_name, int sensor_type, double sensor_value1, double sensor_value2,
                      double sensor_value3, String create_time, String address, String floor, double latitude, double longitude,
                      double altitude, String phone_model, String device_brand, String android_version, String weather, String userName) {
        this.code = code;
        this.sensor_name = sensor_name;
        this.sensor_type = sensor_type;
        this.sensor_value1 = sensor_value1;
        this.sensor_value2 = sensor_value2;
        this.sensor_value3 = sensor_value3;
        this.create_time = create_time;
        this.address = address;
        this.floor = floor;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.phone_model = phone_model;
        this.device_brand = device_brand;
        this.android_version = android_version;
        this.weather = weather;
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSensor_name() {
        return sensor_name;
    }

    public void setSensor_name(String sensor_name) {
        this.sensor_name = sensor_name;
    }

    public int getSensor_type() {
        return sensor_type;
    }

    public void setSensor_type(int sensor_type) {
        this.sensor_type = sensor_type;
    }

    public double getSensor_value1() {
        return sensor_value1;
    }

    public void setSensor_value1(double sensor_value1) {
        this.sensor_value1 = sensor_value1;
    }

    public double getSensor_value2() {
        return sensor_value2;
    }

    public void setSensor_value2(double sensor_value2) {
        this.sensor_value2 = sensor_value2;
    }

    public double getSensor_value3() {
        return sensor_value3;
    }

    public void setSensor_value3(double sensor_value3) {
        this.sensor_value3 = sensor_value3;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getPhone_model() {
        return phone_model;
    }

    public void setPhone_model(String phone_model) {
        this.phone_model = phone_model;
    }

    public String getDevice_brand() {
        return device_brand;
    }

    public void setDevice_brand(String device_brand) {
        this.device_brand = device_brand;
    }

    public String getAndroid_version() {
        return android_version;
    }

    public void setAndroid_version(String android_version) {
        this.android_version = android_version;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
