package com.razor.transit.models;

import com.google.gson.annotations.SerializedName;

public class StopDTO
{
    public static class StopDtoLocation {
        public double d;
        public double e;
    }

    @SerializedName("name")
    private String name;

    @SerializedName("StopCode")
    private String stopCode;

    @SerializedName("location")
    private StopDtoLocation location;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getStopCode()
    {
        return stopCode;
    }

    public void setStopCode(String stopCode)
    {
        this.stopCode = stopCode;
    }

    public StopDtoLocation getLocation()
    {
        return location;
    }

    public void setLocation(StopDtoLocation location)
    {
        this.location = location;
    }
}
