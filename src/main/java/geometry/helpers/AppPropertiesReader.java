package geometry.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class AppPropertiesReader {
    final static Logger logger = LogManager.getLogger(AppPropertiesReader.class.getName());
    final static String PROPS_FILE = "/app.properties";
    final static String CONGIG_FILE = "/config.txt";

    public static Properties getProperties() throws IOException{
        logger.info("Reading properties from the file " + PROPS_FILE);
        Properties props = new Properties();

        InputStream is = AppPropertiesReader.class.getResourceAsStream( PROPS_FILE );
        props.load( is );
        return props;
    }

    public static  Map<String, String> getPropsFromFile() throws IOException {
        logger.info("Reading properties from the file " + CONGIG_FILE);
        Map<String, String> properties = new HashMap<>();
        String line;
        String path = AppPropertiesReader.class.getResource( CONGIG_FILE ).getPath();

        FileReader fileReader = new FileReader( path );
        BufferedReader bufferedReader = new BufferedReader( fileReader );

        while ((line = bufferedReader.readLine()) != null) {
            String[] lineParts = line.split("=");
            properties.put( lineParts[0], lineParts[1] );
        }
        return properties;
    }
}
