package com.tekion.cricket.interfaces;


/*
as runs on a ball keeps getting change, observers
will observe ball to update their data
 */
public interface Ball {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
