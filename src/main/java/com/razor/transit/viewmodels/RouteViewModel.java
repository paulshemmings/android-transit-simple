package com.razor.transit.viewmodels;

import com.razor.transit.models.RouteDTO;

import java.io.Serializable;

public class RouteViewModel  extends ModelContainer<RouteDTO>
                            implements IViewModel, Serializable
{

    private String agencyName;

    @Override
    public Long getID()
    {
        return null;
    }

    @Override
    public String getCode()
    {
        return this.getModel().getCode();
    }

    @Override
    public String getTitle()
    {
        return this.getModel().getName();
    }

    @Override
    public String getDetails()
    {
        return this.getModel().getName();
    }

    public String getAgencyName()
    {
        return agencyName;
    }

    public void setAgencyName(final String agencyName)
    {
        this.agencyName = agencyName;
    }
}