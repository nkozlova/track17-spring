package track.container;

import java.util.List;
import java.io.File;

import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.beans.Car;
import track.container.config.ConfigReader;


/**
 *
 */
public class Main {

    public static void main(String[] args) throws InvalidConfigurationException {

        /*

        ПРИМЕР ИСПОЛЬЗОВАНИЯ

         */

//        // При чтении нужно обработать исключение
//        ConfigReader reader = new JsonReader();
//        List<Bean> beans = reader.parseBeans("config.json");
//        Container container = new Container(beans);
//
//        Car car = (Car) container.getByClass("track.container.beans.Car");
//        car = (Car) container.getById("carBean");

        ConfigReader reader = new JsonConfigReader();
        List<Bean> beans = reader.parseBeans(new File("src/main/resources/config.json"));
        Container container = new Container(beans);

        Car car = (Car) container.getByClass("track.container.beans.Car");
        Car anotherCar = (Car) container.getById("carBean");

        System.out.println(car);

        System.out.println(car == anotherCar);

    }
}
