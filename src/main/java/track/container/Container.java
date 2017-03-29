package track.container;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;
import track.container.config.ValueType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {

    private Map<String, Object> objByName;
    private Map<String, Object> objByClassName;
    List<Bean> beans;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) throws InvalidConfigurationException {
        objByName = new HashMap<>();
        objByClassName = new HashMap<>();
        this.beans = beans;

    }

    private Object createObject(Bean bean) throws InvalidConfigurationException {
        try {
            objByName.put(bean.getId(), Class.forName(bean.getClassName()).newInstance());
            objByClassName.put(bean.getClassName(), objByName.get(bean.getId()));

            Object current = getById(bean.getId());
            Class cl = current.getClass();
            for (Property property : bean.getProperties().values()) {
                StringBuilder name = new StringBuilder(property.getName());
                Field currentField = cl.getDeclaredField(name.toString());
                name.setCharAt(0, Character.toUpperCase(name.charAt(0)));
                Method setMethod = cl.getDeclaredMethod("set" + name, currentField.getType());
                if (property.getType().equals(ValueType.VAL)) {
                    if (currentField.getType().toString().equals("int")) {
                        setMethod.invoke(current, Integer.parseInt(property.getValue()));
                    } else {
                        throw new Exception("Invalid type");
                    }
                } else {
                    setMethod.invoke(current, getByClass(currentField.getType().getTypeName()));
                }
            }
            return current;
        } catch (Exception e) {
            throw new InvalidConfigurationException(e.getMessage());
        }
    }

    /**
     * Вернуть объект по имени бина из конфига
     * Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) throws InvalidConfigurationException {
        if (objByName.containsKey(id)) {
            return objByName.get(id);
        } else {
            for (Bean bean : beans) {
                if (bean.getId().equals(id)) {
                    return createObject(bean);
                }
            }
            return null;
        }
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws InvalidConfigurationException {
        if (objByClassName.containsKey(className)) {
            return objByClassName.get(className);
        } else {
            for (Bean bean : beans) {
                if (bean.getClassName().equals(className)) {
                    return createObject(bean);
                }
            }
            return null;
        }
    }

}