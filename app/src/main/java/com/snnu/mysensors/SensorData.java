package com.snnu.mysensors;

public class SensorData {
    private float accelerometer_datax;
    private float accelerometer_datay;
    private float accelerometer_dataz;

    public SensorData() {
    }

    public SensorData(float accelerometer_datax, float accelerometer_datay, float accelerometer_dataz) {
        this.accelerometer_datax = accelerometer_datax;
        this.accelerometer_datay = accelerometer_datay;
        this.accelerometer_dataz = accelerometer_dataz;
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
}
