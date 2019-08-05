package com.fpuente.simple_ripley.api;


import com.fpuente.simple_ripley.config.Config;

import org.json.JSONObject;





public class ApiRest {

    private Config config = new Config();
    private String TAG = "Config";



    public AsyncTaskResult<JSONObject> getProducts(int page){
        try {
            JSONObject input = new JSONObject();
            input.put("url", config.base_url+"?page="+page);
            input.put("method", "GET");

            return new Apicaller().execute(input).get();
        }catch (Exception e){
            return new  AsyncTaskResult<>(e);
        }
    }


}
