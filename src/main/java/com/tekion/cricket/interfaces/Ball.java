package com.tekion.cricket.interfaces;

public interface Ball {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
