package com.mystery0.tools.MysteryNetFrameWork;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Map;

public class HttpUtil
{
    private Context context;
    private RequestMethod requestMethod;//请求方式
    private String url;//请求地址
    private Map<String, String> map;//输入数据
    private ResponseListener responseListener;//回调

    public enum RequestMethod
    {
        POST, GET
    }

    public HttpUtil(Context context)
    {
        this.context = context;
    }

    public HttpUtil setRequestMethod(RequestMethod requestMethod)
    {
        this.requestMethod = requestMethod;
        return this;
    }

    public HttpUtil setUrl(String url)
    {
        this.url = url;
        return this;
    }

    public HttpUtil setResponseListener(ResponseListener responseListener)
    {
        this.responseListener = responseListener;
        return this;
    }

    public HttpUtil setMap(Map<String, String> map)
    {
        this.map = map;
        return this;
    }

    public void open()
    {
        int method = Request.Method.GET;
        if (this.requestMethod == RequestMethod.POST)
        {
            method = Request.Method.POST;
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(method, this.url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        responseListener.onResponse(1, response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError)
                    {
                        responseListener.onResponse(0, volleyError.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public <T> T fromJson(String json, Class<T> classOfT)
    {
        return new Gson().fromJson(json, classOfT);
    }

    public String toJson(Object object)
    {
        return new Gson().toJson(object);
    }
}
