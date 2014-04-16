package com.razor.transit.comms;

public interface IMessageResourceMapper {
    public String getRootUrl();
    public String getMessageUrl(final int messageType);
}
