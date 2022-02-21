package com.tekion.cricket;

public interface Subject {
    void registerObserver(Observer observer, boolean isBattingTeam);
    void removeObserver(Observer observer, boolean isBattingTeam);
    void notifyObservers();
}
