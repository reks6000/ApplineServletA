package ru.appline.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Model implements Serializable {
    private AtomicInteger counter = new AtomicInteger(4);
    private  static  final Model instance = new Model();

    private  final Map<Integer, User> model;

    public static Model getInstance() {
        return instance;
    }

    private Model() {
        model = new HashMap<>();
        model.put(1, new User("Tigran", "Mantashyan", 55555));
        model.put(2, new User("Anton", "Osipov", 66666));
        model.put(3, new User("Zoya", "Andreeva", 77777));
    }

    public int getAndIncrementCounter() {
        return counter.getAndIncrement();
    }

    public int getAndDecrementCounter() {
        return counter.getAndDecrement();
    }

    public void add(User user, int id) {
        model.put(id, user);
    }

    public Map<Integer, User> getFromList() {
        return model;
    }

    public void deleteLast() {
        model.remove(instance.getFromList().size());
    }

    //При удалении элемента из модели происходит смещение оставщихся по id на один назад, чтобы не менять порядок и добавлять новые в конец, но при этом не получать пробелы в id
    //С этой же целью переменная counter перемещена в Model из ServletAdd
    public void delete(int id) {
        model.remove(id);
        for (int i = id; i <= instance.getFromList().size(); i++) {
               model.put(i, model.get(i+1));
        }
        instance.deleteLast();
        getAndDecrementCounter();
    }

    public void update(int id, String name, String surname, double salary) {
        model.put(id, new User(name, surname, salary));
    }


}
