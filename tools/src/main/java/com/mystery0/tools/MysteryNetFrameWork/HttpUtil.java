package com.mystery0.tools.MysteryNetFrameWork;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class HttpUtil
{
    private Context context;
    private int method;
    private String url;
    private Map<String, String> map;
    private ResponseListener responseListener;

    public HttpUtil(Context context)
    {
        this.context=context;
    }

    public HttpUtil setMethod(int method)
    {
        this.method = method;
        return this;
    }

    public HttpUtil setUrl(String url)
    {
        this.url = url;
        return this;
    }

    public HttpUtil setResponse(ResponseListener responseListener)
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
}
