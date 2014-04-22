package com.razor.transit.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ArrivalTimeCollectionDTO implements Serializable
{
    @SerializedName("routes")
    private ArrayList<ArrivalTimeDTO> times;

    public ArrayList<ArrivalTimeDTO> getTimes()
    {
        return times;
    }

    public void setTimes(ArrayList<ArrivalTimeDTO> times)
    {
        this.times = times;
    }
}
