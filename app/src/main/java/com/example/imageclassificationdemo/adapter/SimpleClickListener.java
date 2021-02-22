package com.example.imageclassificationdemo.adapter;

public interface SimpleClickListener<T> {
    void onItemClick(T item);
    boolean onItemLongClick(T item);
}
