package com.razor.transit.comms.responses;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.razor.transit.helpers.ExceptionHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class RequestResponse<T> implements Serializable {

    @SerializedName("ResponseCode")
    private long responseCode;

    @SerializedName("RequestDateTime")
    private long requestDateTime;

    @SerializedName("ErrorMessage")
    private String errorMessage;

    @SerializedName("ResponseData")
    private T responseData;

    @SerializedName("ResponseDictionary")
    private Map<String,T> responseDictionary;

    @SerializedName("ResponseMetaData")
    private String responseMetaData;

    public RequestResponse(){
        this.requestDateTime = new Date().getTime();
        this.responseCode = 0;
    }

    public RequestResponse(long responseCode, String errorMessage){
        this.requestDateTime = new Date().getTime();
        this.responseCode = responseCode;
        this.errorMessage = errorMessage;
    }

    public RequestResponse(Map<String,T> nameValuePair){
        this();
        this.setNameValuePair(nameValuePair);
    }

    public String toJson(){
        String json = new Gson().toJson(this);
        return json;
    }

    public void mergeResponse(final RequestResponse<T> rhs){
        this.setErrorMessage(rhs.getErrorMessage());
        this.setResponseData(rhs.getResponseData());
        if (rhs.getNameValuePair()!=null){
            for (String key:rhs.getNameValuePair().keySet()){
                this.addNameValuePair(key, rhs.getNameValuePair().get(key));
            }
        }
    }

    public boolean isSuccess() {
        return responseCode == 0 &&  (errorMessage==null || errorMessage.isEmpty());
    }

    public void setErrorMessage(long errorCode) {
        this.responseCode = errorCode;
    }
    public void setErrorMessage(long errorCode, Exception ex) {
        this.responseCode = errorCode;
        this.errorMessage = ExceptionHelper.getStackTrace(ex);
    }
    public void setErrorMessage(long errorCode, String format, Object ... objects){
        this.responseCode = errorCode;
        this.errorMessage = String.format(format, objects);
    }
    public void setErrorMessage(long errorCode, String errorMessage) {
        this.responseCode = errorCode;
        this.errorMessage = errorMessage;
    }
    public void setErrorMessage(String format, Object ... objects){
        String errorMessage = String.format(format, objects);
        this.errorMessage = errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public synchronized Map<String,T> getNameValuePair() {
        if (this.responseDictionary == null){
            this.responseDictionary = new HashMap<String,T>();
        }
        return this.responseDictionary;
    }

    public void setNameValuePair(Map<String,T> nameValuePair) {
        this.responseDictionary = nameValuePair;
    }

    public synchronized void addNameValuePair(String key, T value) {
        if(!this.getNameValuePair().containsKey(key)){
            this.getNameValuePair().put(key, value);
        }
    }

    public boolean containsKey(String key){
        return this.getNameValuePair().containsKey(key);
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }

    public T getResponseData() {
        return responseData;
    }

    public String getResponseMetaData() {
        return responseMetaData;
    }

    public void setResponseMetaData(String responseMetaData) {
        this.responseMetaData = responseMetaData;
    }

    public long getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(long responseCode) {
        this.responseCode = responseCode;
    }


}
