package com.razor.transit.comms.requests;

import com.razor.transit.comms.IRequest;
import com.razor.transit.comms.IRequestContent;
import com.google.gson.Gson;

public class SimpleJsonRequest implements IRequest
{
    private boolean isPost = true;
    private String commandPath = null;
    private IRequestContent requestContent;

    public SimpleJsonRequest(final IRequestContent content, final String commandPath) {
        this.commandPath = commandPath;
        this.requestContent = content;
    }

    @Override
    public String getCommand() {
        return this.commandPath;
    }

    @Override
    public void setCommand(final String command) {
        this.commandPath = command;
    }

    public boolean isPost() {
        return isPost;
    }

    public void setPost(final boolean isPost) {
        this.isPost = isPost;
    }

    @Override
    public String getJSON() {
        String json = null;
        try{
            Gson gson = new Gson();
            json = gson.toJson(this.requestContent);
        } catch (Exception ex){
            // LogHelper.e(TAG, "getJSON failed with %s", ex.getMessage());
        }
        return json;
    }

    public int hashCode(){
        int hashCode = 1;
        hashCode = hashCode & (this.getCommand() == null ? hashCode : this.getCommand().hashCode());
        hashCode = hashCode & (this.getJSON() == null ? hashCode : this.getJSON().hashCode());
        return hashCode;
    }

    public boolean equals(final Object obj){
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        SimpleJsonRequest rhs = (SimpleJsonRequest) obj;
        return
            (this.getCommand() == null && rhs.getCommand() == null && this.getJSON() == null && rhs.getJSON() == null)
            ||
            (this.getCommand().equals(rhs.getCommand()) && this.getJSON().equals(rhs.getJSON()));
    }

}
