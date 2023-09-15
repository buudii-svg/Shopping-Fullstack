package com.example.shopping.service;

public interface Auth <T>{
    public String login(String username, String password);
    public boolean addUser(T t);
}
