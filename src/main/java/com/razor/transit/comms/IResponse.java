package com.razor.transit.comms;

public interface IResponse<T> {

    T getResponseContent();
    String getErrorMessage();
    Long getResponseCode();
    Integer getMessageType();

    void setResponseContent(T value);
    void setErrorMessage(String value);
    void setResponseCode(Long value);
    void setMessageType(Integer messageType);

}