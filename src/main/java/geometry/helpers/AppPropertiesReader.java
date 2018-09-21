package geometry.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AppPropertiesReader {
    final static String PROPS_FILE = "app.properties";

    public static Properties getProperties(){
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream(PROPS_FILE)) {
            props.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
