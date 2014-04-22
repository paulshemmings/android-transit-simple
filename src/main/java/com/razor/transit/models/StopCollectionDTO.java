package com.razor.transit.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class StopCollectionDTO implements Serializable
{
    @SerializedName("stops")
    private List<StopDTO> stops;

    public List<StopDTO> getStops()
    {
        return stops;
    }
}
