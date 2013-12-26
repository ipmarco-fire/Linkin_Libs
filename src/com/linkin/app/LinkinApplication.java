package com.linkin.app;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

public class LinkinApplication extends Application{
    private Map<String, Object> paramMap;
    
    @Override
    public void onCreate() {
        super.onCreate();
        paramMap = new HashMap<String, Object>();
    }
    
    public void setParam(String key, Object obj) {
        paramMap.put(key, obj);
    }

    public Object getParam(String key) {
        return paramMap.get(key);
    }
    
    public boolean hasParam(String key){
        return paramMap.containsKey(key);
    }
}
