package com.razor.transit.comms;

public interface IHandler {
    void populateResponse(IRequest restRequest, IResponse restResponse);
}
