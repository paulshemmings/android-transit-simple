package com.razor.transit.comms.replies;

import com.razor.transit.comms.IModelReply;

import java.util.ArrayList;
import java.util.List;

public class ModelReply<T> extends StatusReply implements IModelReply<T>
{
    private Integer totalCount;
    private List<T> items;

    public ModelReply(int messageType){
        this.setMessageType(messageType);
        this.items = new ArrayList<T>();
    }

    public ModelReply(int messageType, List<T> newItems){
        this(messageType);
        this.items.addAll(newItems);
    }


    public void setTotalCount(Integer value) { this.totalCount = value; }
    public Integer getTotalCount() { return this.totalCount; }

    public List<T> getItems() {return this.items;}

    public int addItem(T n){
        this.items.add(n);
        return this.items.size();
    }

    public int size(){ return this.items == null ? 0 : this.items.size();}

    public T getModel(){
        return (this.items != null
                &&
                this.items.size() > 0) ? this.items.get(0) : null;
    }
}
