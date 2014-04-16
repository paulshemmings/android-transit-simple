package com.razor.transit.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RouteDTO implements Serializable {

    @SerializedName("Name")
    private String name;

    @SerializedName("Code")
    private String code;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }
}
