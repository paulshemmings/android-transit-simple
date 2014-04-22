package com.razor.transit.viewmodels;

import com.razor.transit.models.ArrivalTimeDTO;

import java.io.Serializable;

public class ArrivalTimeViewModel
        extends ModelContainer<ArrivalTimeDTO>
        implements IViewModel, Serializable
{

    @Override
    public Long getID()
    {
        return null;
    }

    @Override
    public String getCode()
    {
        return this.getModel().getStopCode();
    }

    @Override
    public String getTitle()
    {
        return this.getModel().getRouteName();
    }

    @Override
    public String getDetails()
    {
        return this.getModel().getAbsoluteTimes();
    }
}
