package com.razor.transit.comms;

public interface IReply {

    String getErrorMessage();
    void setErrorMessage(String errorMessage);

    int getMessageType();
    void setMessageType(int messageType);

    long getResponseCode();
    void setResponseCode(long responseCode);
}