package com.library.service;

public interface Searchable<T> {

    T searchById(int id);
}