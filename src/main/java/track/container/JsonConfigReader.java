package track.container;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.List;

import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import track.container.config.Property;
import track.container.config.ValueType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Реализовать
 */
public class JsonConfigReader implements ConfigReader {

    @Override
    public List<Bean> parseBeans(File file) throws InvalidConfigurationException {
        String jsonStr = "";
        StringBuilder build = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String str = br.readLine();
            while (str != null) {
                build.append(str);
                str = br.readLine();
            }
            jsonStr = build.toString();
        }  catch (Exception e) {
            throw new InvalidConfigurationException(e.getMessage());
        }

        JSONArray beans = (new JSONObject(jsonStr)).getJSONArray("beans");
        List<Bean> answer = new ArrayList<>();
        for (int i = 0; i < beans.length(); i++) {
            JSONObject jsonBeanObj = beans.getJSONObject(i);
            JSONObject jsonPropertiesObj = jsonBeanObj.getJSONObject("properties");

            String beanId = jsonBeanObj.getString("id");
            Map<String, Property> mapOfProperties = new HashMap<>();

            switch (beanId) {
                case "carBean":
                    mapOfProperties.put("gear", readPropertyFromJson(jsonPropertiesObj.getJSONObject("gear")));
                    mapOfProperties.put("engine", readPropertyFromJson(jsonPropertiesObj.getJSONObject("engine")));
                    break;
                case "engineBean":
                    mapOfProperties.put("power", readPropertyFromJson(jsonPropertiesObj.getJSONObject("power")));
                    break;
                case "gearBean":
                    mapOfProperties.put("count", readPropertyFromJson(jsonPropertiesObj.getJSONObject("count")));
                    break;
                default:
                    throw new InvalidConfigurationException("Invalid id");
            }

            Bean bean = new Bean(jsonBeanObj.getString("id"),
                    jsonBeanObj.getString("className"), mapOfProperties);
            answer.add(bean);
        }

        return answer;
    }

    private Property readPropertyFromJson(JSONObject jsonProperty) {
        return new Property(jsonProperty.getString("name"), jsonProperty.getString("value"),
                jsonProperty.getEnum(ValueType.class, "type"));
    }

}
