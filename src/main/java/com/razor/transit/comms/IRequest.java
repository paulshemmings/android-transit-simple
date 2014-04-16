package com.razor.transit.comms;

public interface IRequest {
    boolean isPost();
    String getCommand();
    void setCommand(String command);
    String getJSON();
}