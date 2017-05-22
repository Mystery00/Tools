package com.mystery0.tools.MysteryNetFrameWork;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class HttpUtils
{
    private Context context;
    private RequestMethod requestMethod;//请求方式
    private String url;//请求地址
    private Map<String, String> map;//输入数据
    private ResponseListener responseListener;//回调
	private boolean isFileRequest = false;

    public enum RequestMethod
    {
        POST, GET
    }

	public HttpUtils(Context context)
	{
        this.context = context;
    }

	public HttpUtils setRequestMethod(RequestMethod requestMethod)
	{
        this.requestMethod = requestMethod;
        return this;
    }

	public HttpUtils setUrl(String url)
	{
        this.url = url;
        return this;
    }

	public HttpUtils setResponseListener(ResponseListener responseListener)
	{
        this.responseListener = responseListener;
        return this;
    }

	public HttpUtils setMap(Map<String, String> map)
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
                });
        requestQueue.add(stringRequest);
    }
}
