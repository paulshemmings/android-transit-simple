package com.razor.transit.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StopCollectionDTO
{
    @SerializedName("stops")
    private List<StopDTO> stops;

    public List<StopDTO> getStops()
    {
        return stops;
    }
}
