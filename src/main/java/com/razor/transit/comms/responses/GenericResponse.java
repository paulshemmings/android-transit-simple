package com.razor.transit.comms.responses;

import com.razor.transit.comms.IResponse;

public class GenericResponse<T> implements IResponse<T>
{

    private T responseContent;
    private String errorMessage;
    private Long responseCode;
    private Integer messageType;

    public GenericResponse(){
    }

    public GenericResponse(Integer messageType){
        this.messageType = messageType;
    }

    public void setResponseContent(T responseContent) {
        this.responseContent = responseContent;
    }
    public T getResponseContent() {
        return responseContent;
    }
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }
    public Integer getMessageType() {
        return messageType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Long responseCode) {
        this.responseCode = responseCode;
    }
}
