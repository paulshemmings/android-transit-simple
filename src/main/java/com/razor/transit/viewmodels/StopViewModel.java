package com.razor.transit.viewmodels;

import com.razor.transit.models.StopDTO;

public class StopViewModel extends ModelContainer<StopDTO> implements IViewModel
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
        return this.getModel().getName();
    }

    public String getDetails()
    {
        return String.format("%d %d",
                this.getModel().getLocation().d,
                this.getModel().getLocation().e);
    }
}
