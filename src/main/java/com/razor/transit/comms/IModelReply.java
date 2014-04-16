package com.razor.transit.comms;

import java.util.List;

public interface IModelReply<T> extends IReply {
    List<T> getItems();
    T getModel();
    int addItem(T item);
    int size();
    Integer getTotalCount();
}
