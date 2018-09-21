package geometry.helpers;

import java.io.*;
import java.util.Properties;

public class AppPropertiesReader {
    final static String PROPS_FILE = "/app.properties";

    public static Properties getProperties() throws IOException{
        Properties props = new Properties();

        InputStream is = AppPropertiesReader.class.getResourceAsStream( PROPS_FILE );
        props.load( is );
        return props;
    }
}
