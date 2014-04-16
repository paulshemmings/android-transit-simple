package com.razor.transit.viewmodels;

import java.io.Serializable;

import com.razor.transit.models.AgencyDTO;

public class AgencyViewModel
        extends ModelContainer<AgencyDTO>
        implements IViewModel, Serializable {

    @Override
    public Long getID()
    {
        return null;
    }

    @Override
    public String getCode() {
        return this.getModel().getName();
    }

    @Override
    public String getTitle()
    {
        return super.getModel().getName();
    }

    @Override
    public String getDetails()
    {
        return "";
    }
}
