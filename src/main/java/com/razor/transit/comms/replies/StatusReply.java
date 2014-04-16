package com.razor.transit.comms.replies;

import com.google.gson.annotations.SerializedName;
import com.razor.transit.comms.IReply;

public class StatusReply implements IReply
{

    @SerializedName("MessageType")
    private int messageType;

    @SerializedName("ErrorMessage")
    private String errorMessage;

    @SerializedName("ResponseCode")
    private long responseCode;

    public boolean isSuccessful(){
        return this.responseCode == 0;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(long responseCode) {
        this.responseCode = responseCode;
    }


}
