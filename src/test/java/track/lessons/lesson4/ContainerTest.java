package track.lessons.lesson4;

import org.junit.Assert;
import org.junit.Test;
import track.container.Container;
import track.container.JsonConfigReader;
import track.container.beans.Car;
import track.container.beans.Engine;
import track.container.beans.Gear;
import track.container.config.ConfigReader;

import java.io.File;

public class ContainerTest {

    @Test
    public void readJson() {
        ConfigReader reader = new JsonConfigReader();
        try {
            reader.parseBeans(new File("src/main/resources/config.json"));
        } catch (Exception e) {
            Assert.assertFalse(true);
        }
    }


    @Test
    public void getEngine() {
        ConfigReader reader = new JsonConfigReader();
        try {
            Container container = new Container(reader.parseBeans(new File("src/main/resources/config.json")));
            Engine engine = (Engine)container.getById("engineBean");
            Assert.assertEquals(engine.getPower(), 200);
        } catch (Exception e) {
            Assert.assertFalse(true);
        }
    }

    @Test
    public void getGear() {
        ConfigReader reader = new JsonConfigReader();
        try {
            Container container = new Container(reader.parseBeans(new File("src/main/resources/config.json")));
            Gear gear = (Gear)container.getById("gearBean");
            Assert.assertEquals(gear.getCount(), 6);
        } catch (Exception e) {
            Assert.assertFalse(true);
        }
    }

    @Test
    public void GetCar() {
        ConfigReader reader = new JsonConfigReader();
        try {
            Container container = new Container(reader.parseBeans(new File("src/main/resources/config.json")));
            Car car = (Car) container.getById("carBean");
            Engine engine = (Engine) container.getById("engineBean");
            Gear gear = (Gear) container.getById("gearBean");
            Assert.assertEquals(car.getEngine(), engine);
            Assert.assertEquals(car.getGear(), gear);
        } catch (Exception e) {
            Assert.assertFalse(true);
        }
    }

}