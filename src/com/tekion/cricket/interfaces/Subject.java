package com.tekion.cricket.interfaces;

import com.tekion.cricket.interfaces.Observer;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
