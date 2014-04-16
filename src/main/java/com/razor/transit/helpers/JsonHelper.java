package com.razor.transit.helpers;

import com.google.gson.Gson;

import java.util.Map;
import java.util.Set;

public class JsonHelper<T>{

    private final Class<T> type;

    public JsonHelper(Class<T> type) {
        this.type = type;
    }

    public Class<T> getMyType() {
        return this.type;
    }

    public T fromJson(String json){
        return new Gson().fromJson(json,this.type);
    }

    public T fromNameValuePair(Map<String,String> nameValuePair){
        StringBuilder sb = new StringBuilder();
        Set<String> keySet = nameValuePair.keySet();
        for(String key:keySet){
            if(sb.toString().length()>0){
                sb.append(",");
            }
            sb.append("\"" + key + "\":\"" + nameValuePair.get(key) + "\"");
        }
        return this.fromJson( "{" + sb.toString() + "}");
    }

    public String toJson(T object){
        return new Gson().toJson(object);
    }

}
