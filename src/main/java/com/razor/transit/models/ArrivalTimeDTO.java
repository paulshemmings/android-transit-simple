package com.razor.transit.models;

/*
    "routeName": "24th St. Mission",
    "stopCode": "10",
    "arrivalTimes": "34,47",
    "selected": false,
    "absoluteTimes": "1398128174237,1398128954237"
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ArrivalTimeDTO implements Serializable
{
    @SerializedName("routeName")
    public String routeName;

    @SerializedName("stopCode")
    public String stopCode;

    @SerializedName("arrivalTimes")
    public String arrivalTimes;

    @SerializedName("selected")
    public boolean selected;

    @SerializedName("absoluteTimes")
    public String absoluteTimes;

    public String getRouteName()
    {
        return routeName;
    }

    public void setRouteName(String routeName)
    {
        this.routeName = routeName;
    }

    public String getStopCode()
    {
        return stopCode;
    }

    public void setStopCode(String stopCode)
    {
        this.stopCode = stopCode;
    }

    public String[] getAbsoluteTimeList()
    {
        return arrivalTimes.trim().length() > 0 ? arrivalTimes.split(",") : null;
    }

    public String getAbsoluteTimes() {
        return this.arrivalTimes;
    }

    public void setArrivalTimes(String arrivalTimes)
    {
        this.arrivalTimes = arrivalTimes;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public void setAbsoluteTimes(String absoluteTimes)
    {
        this.absoluteTimes = absoluteTimes;
    }
}
