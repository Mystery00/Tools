package com.mystery0.toolsdemo;

public class Response
{
    int status;
    String mes;

    public Response(int status, String mes)
    {
        this.status = status;
        this.mes = mes;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getMes()
    {
        return mes;
    }

    public void setMes(String mes)
    {
        this.mes = mes;
    }

    @Override
    public String toString()
    {
        return "Response{" +
                "status=" + status +
                ", mes='" + mes + '\'' +
                '}';
    }
}
