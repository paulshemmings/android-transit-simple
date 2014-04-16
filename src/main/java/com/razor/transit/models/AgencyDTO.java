package com.razor.transit.models;

import java.io.Serializable;

// "Name":"AC Transit","HasDirection":"True","Mode":"Bus"
public class AgencyDTO implements Serializable {

    private String Name;
    private String HasDirection;
    private String Mode;

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public String getHasDirection()
    {
        return HasDirection;
    }

    public void setHasDirection(String hasDirection)
    {
        HasDirection = hasDirection;
    }

    public String getMode()
    {
        return Mode;
    }

    public void setMode(String mode)
    {
        Mode = mode;
    }
}