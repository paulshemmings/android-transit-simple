package com.razor.transit.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RouteCollectionDTO implements Serializable {

    @SerializedName("agencyName")
    private String agencyName;

    @SerializedName("hasDirection")
    private String hasDirection;

    @SerializedName("routes")
    private List<RouteDTO> routes;

    public String getAgencyName()
    {
        return agencyName;
    }

    public void setAgencyName(final String agencyName)
    {
        this.agencyName = agencyName;
    }

    public String getHasDirection()
    {
        return hasDirection;
    }

    public void setHasDirection(final String hasDirection)
    {
        this.hasDirection = hasDirection;
    }

    public List<RouteDTO> getRoutes()
    {
        return routes;
    }

    public void setRoutes(final List<RouteDTO> routes)
    {
        this.routes = routes;
    }
}
