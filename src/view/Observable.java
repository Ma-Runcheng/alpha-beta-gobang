package view;

import java.util.List;

public interface Observable {
    List<Observer> observerList = null;
    void Notify(String state);
    void register(Observer observer);
}
