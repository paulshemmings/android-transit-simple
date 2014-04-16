package com.razor.transit.comms.builders;


import com.razor.transit.comms.IHandler;
import com.razor.transit.comms.IMessageResourceMapper;
import com.razor.transit.comms.IProvider;
import com.razor.transit.comms.IReplyBuilder;
import com.razor.transit.comms.IRequest;
import com.razor.transit.comms.IResponse;

public class ProviderBuilder
{
    private IRequest request = null;
    private IResponse response = null;
    private IHandler handler = null;
    private IProvider provider = null;
    private IReplyBuilder replyBuilder = null;

    public ProviderBuilder addRequest(final IRequest request) {
        this.request = request;
        return this;
    }

    public ProviderBuilder addResponse(final IResponse response) {
        this.response = response;
        return this;
    }

    public ProviderBuilder addHandler(final IHandler handler) {
        this.handler = handler;
        return this;
    }

    public ProviderBuilder addReplyBuilder(final IReplyBuilder replyBuilder) {
        this.replyBuilder = replyBuilder;
        return this;
    }

    public ProviderBuilder addProvider(final IProvider provider) {
        this.provider = provider;
        return this;
    }

    public IProvider buildProvider() {
        this.provider.setHandler(this.handler);
        this.provider.setReplyBuilder(this.replyBuilder);
        this.provider.setRequest(this.request);
        this.provider.setResponse(this.response);
        return this.provider;
    }

}
