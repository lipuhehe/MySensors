package com.snnu.mysensors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JsonParse {

    public static List<SensorData> getSensorData(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<SensorData>>(){}.getType();
        List<SensorData> sensorDataList =gson.fromJson(json,listType);
        return sensorDataList;
    }
}
