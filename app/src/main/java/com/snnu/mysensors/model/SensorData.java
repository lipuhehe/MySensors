package com.snnu.mysensors.model;

import java.io.Serializable;
import java.util.Date;

public class SensorData implements Serializable {

    public static final String TABLE_NAME = "sensor_data";
    public static final String ACCELEROMETER_DATAX = "accelerometer_datax";
    public static final String ACCELEROMETER_DATAY = "accelerometer_datay";
    public static final String ACCELEROMETER_DATAZ = "accelerometer_dataz";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String LIGHT_DATA = "light_data";
    public static final String TEMPERATURE_DATA = "temperature_data";
    public static final String MAGNETIC_DATAX = "magnetic_datax";
    public static final String MAGNETIC_DATAY = "magnetic_datay";
    public static final String MAGNETIC_DATAZ = "magnetic_dataz";
    public static final String PRESSURE_DATA = "pressure_data";
    public static final String PROXIMITY_DATA = "proximity_data";
    public static final String GRAVITY_DATAX = "gravity_datax";
    public static final String GRAVITY_DATAY = "gravity_datay";
    public static final String GRAVITY_DATAZ = "gravity_dataz";
    public static final String LINEAR_ACCELERATION_DATAX = "linear_acceleration_datax";
    public static final String LINEAR_ACCELERATION_DATAY = "linear_acceleration_datay";
    public static final String LINEAR_ACCELERATION_DATAZ = "linear_acceleration_dataz";
    public static final String ROTATION_VECTOR_DATAX = "rotation_vector_datax";
    public static final String ROTATION_VECTOR_DATAY = "rotation_vector_datay";
    public static final String ROTATION_VECTOR_DATAZ = "rotation_vector_dataz";
    public static final String HUMIDITY_DATA = "humidity_data";
    public static final String GYROSCOPE_DATA = "gyroscope_data";
    public static final String STEP_COUNT_DATA = "step_counter_data";
    public static final String ADDRESS = "address";
    public static final String WEATHER = "weather";
    public static final String ALTITUDE = "altitude";
    public static final String FLOOR = "floor";
    public static final String PHONE_MODEL = "phone_model";
    public static final String DEVICE_BRAND = "device_brand";
    public static final String ANDROID_VERSION = "android_version";



    private Integer id;
    private float accelerometer_datax;
    private float accelerometer_datay;
    private float accelerometer_dataz;
    private double latitude;

    private double longitude;

    private float light_data;

    private float temperature_data;

    private float magnetic_datax;

    private float magnetic_datay;

    private float magnetic_dataz;

    private float pressure_data;

    private float proximity_data;

    private float gravity_datax;

    private float gravity_datay;

    private float gravity_dataz;

    private float linear_acceleration_datax;

    private float linear_acceleration_datay;

    private float linear_acceleration_dataz;

    private float rotation_vector_datax;

    private float rotation_vector_datay;

    private float rotation_vector_dataz;

    private float humidity_data;

    private float gyroscope_data;

    private float step_counter_data;

    private String address;

    private String weather;

    private double altitude;

    private String floor;

    private String create_time;

    private String phone_model;

    private String device_brand;

    private String android_version;

    public SensorData() {
    }

    public SensorData(float accelerometer_datax, float accelerometer_datay, float accelerometer_dataz, double latitude, double longitude, float light_data,
                      float temperature_data, float magnetic_datax, float magnetic_datay, float magnetic_dataz, float pressure_data, float proximity_data, float gravity_datax,
                      float gravity_datay, float gravity_dataz, float linear_acceleration_datax, float linear_acceleration_datay, float linear_acceleration_dataz,
                      float rotation_vector_datax, float rotation_vector_datay, float rotation_vector_dataz, float humidity_data, float gyroscope_data,
                      float step_counter_data, String address,String weather,String floor,double altitude,String create_time,String phone_model,String device_brand,String android_version) {
        this.accelerometer_datax = accelerometer_datax;
        this.accelerometer_datay = accelerometer_datay;
        this.accelerometer_dataz = accelerometer_dataz;
        this.latitude = latitude;
        this.longitude = longitude;
        this.light_data = light_data;
        this.temperature_data = temperature_data;
        this.magnetic_datax = magnetic_datax;
        this.magnetic_datay = magnetic_datay;
        this.magnetic_dataz = magnetic_dataz;
        this.pressure_data = pressure_data;
        this.proximity_data = proximity_data;
        this.gravity_datax = gravity_datax;
        this.gravity_datay = gravity_datay;
        this.gravity_dataz = gravity_dataz;
        this.linear_acceleration_datax = linear_acceleration_datax;
        this.linear_acceleration_datay = linear_acceleration_datay;
        this.linear_acceleration_dataz = linear_acceleration_dataz;
        this.rotation_vector_datax = rotation_vector_datax;
        this.rotation_vector_datay = rotation_vector_datay;
        this.rotation_vector_dataz = rotation_vector_dataz;
        this.humidity_data = humidity_data;
        this.gyroscope_data = gyroscope_data;
        this.step_counter_data = step_counter_data;
        this.address = address;
        this.weather = weather;
        this.floor = floor;
        this.altitude = altitude;
        this.phone_model = phone_model;
        this.device_brand = device_brand;
        this.android_version = android_version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getAccelerometer_datax() {
        return accelerometer_datax;
    }

    public void setAccelerometer_datax(float accelerometer_datax) {
        this.accelerometer_datax = accelerometer_datax;
    }

    public float getAccelerometer_datay() {
        return accelerometer_datay;
    }

    public void setAccelerometer_datay(float accelerometer_datay) {
        this.accelerometer_datay = accelerometer_datay;
    }

    public float getAccelerometer_dataz() {
        return accelerometer_dataz;
    }

    public void setAccelerometer_dataz(float accelerometer_dataz) {
        this.accelerometer_dataz = accelerometer_dataz;
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

    public float getLight_data() {
        return light_data;
    }

    public void setLight_data(float light_data) {
        this.light_data = light_data;
    }

    public float getTemperature_data() {
        return temperature_data;
    }

    public void setTemperature_data(float temperature_data) {
        this.temperature_data = temperature_data;
    }

    public float getMagnetic_datax() {
        return magnetic_datax;
    }

    public void setMagnetic_datax(float magnetic_datax) {
        this.magnetic_datax = magnetic_datax;
    }

    public float getMagnetic_datay() {
        return magnetic_datay;
    }

    public void setMagnetic_datay(float magnetic_datay) {
        this.magnetic_datay = magnetic_datay;
    }

    public float getMagnetic_dataz() {
        return magnetic_dataz;
    }

    public void setMagnetic_dataz(float magnetic_dataz) {
        this.magnetic_dataz = magnetic_dataz;
    }

    public float getPressure_data() {
        return pressure_data;
    }

    public void setPressure_data(float pressure_data) {
        this.pressure_data = pressure_data;
    }

    public float getProximity_data() {
        return proximity_data;
    }

    public void setProximity_data(float proximity_data) {
        this.proximity_data = proximity_data;
    }

    public float getGravity_datax() {
        return gravity_datax;
    }

    public void setGravity_datax(float gravity_datax) {
        this.gravity_datax = gravity_datax;
    }

    public float getGravity_datay() {
        return gravity_datay;
    }

    public void setGravity_datay(float gravity_datay) {
        this.gravity_datay = gravity_datay;
    }

    public float getGravity_dataz() {
        return gravity_dataz;
    }

    public void setGravity_dataz(float gravity_dataz) {
        this.gravity_dataz = gravity_dataz;
    }

    public float getLinear_acceleration_datax() {
        return linear_acceleration_datax;
    }

    public void setLinear_acceleration_datax(float linear_acceleration_datax) {
        this.linear_acceleration_datax = linear_acceleration_datax;
    }

    public float getLinear_acceleration_datay() {
        return linear_acceleration_datay;
    }

    public void setLinear_acceleration_datay(float linear_acceleration_datay) {
        this.linear_acceleration_datay = linear_acceleration_datay;
    }

    public float getLinear_acceleration_dataz() {
        return linear_acceleration_dataz;
    }

    public void setLinear_acceleration_dataz(float linear_acceleration_dataz) {
        this.linear_acceleration_dataz = linear_acceleration_dataz;
    }

    public float getRotation_vector_datax() {
        return rotation_vector_datax;
    }

    public void setRotation_vector_datax(float rotation_vector_datax) {
        this.rotation_vector_datax = rotation_vector_datax;
    }

    public float getRotation_vector_datay() {
        return rotation_vector_datay;
    }

    public void setRotation_vector_datay(float rotation_vector_datay) {
        this.rotation_vector_datay = rotation_vector_datay;
    }

    public float getRotation_vector_dataz() {
        return rotation_vector_dataz;
    }

    public void setRotation_vector_dataz(float rotation_vector_dataz) {
        this.rotation_vector_dataz = rotation_vector_dataz;
    }

    public float getHumidity_data() {
        return humidity_data;
    }

    public void setHumidity_data(float humidity_data) {
        this.humidity_data = humidity_data;
    }

    public float getGyroscope_data() {
        return gyroscope_data;
    }

    public void setGyroscope_data(float gyroscope_data) {
        this.gyroscope_data = gyroscope_data;
    }

    public float getStep_counter_data() {
        return step_counter_data;
    }

    public void setStep_counter_data(float step_counter_data) {
        this.step_counter_data = step_counter_data;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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
}
