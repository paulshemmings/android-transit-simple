package com.razor.transit.viewmodels;

import java.io.Serializable;


public class ModelContainer<T> implements Serializable {
	private T model = null;
	public T getModel(){return this.model;}
	public void setModel(T model){this.model = model;}
}
