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
    final static String CONFIG_FILE = "/config.txt";

    public static Properties getProperties() throws IOException{
        logger.info("Reading properties from the file " + PROPS_FILE);
        Properties props = new Properties();
        InputStream is;

        if ((is = AppPropertiesReader.class.getResourceAsStream( PROPS_FILE )) == null){
            logger.error( "Error has happened while locating the file " + PROPS_FILE);
            throw new FileNotFoundException(  );
        }
        try {
            props.load( is );
        }finally {
           // logger.error( "Error has happened while getting properties from " + PROPS_FILE);
            if (is != null){
                is.close();
            }
        }
        return props;
    }

    public static  Map<String, String> getPropsFromFile() throws IOException {
        logger.info("Reading properties from the file " + CONFIG_FILE );
        Map<String, String> properties = new HashMap<>();
        String line;
        String path = AppPropertiesReader.class.getResource( CONFIG_FILE ).getPath();

        FileReader fileReader = new FileReader( path );
        BufferedReader bufferedReader = new BufferedReader( fileReader );

        try {
            while ( ( line = bufferedReader.readLine() ) != null ) {
                String[] lineParts = line.split( "=" );
                properties.put( lineParts[0], lineParts[1] );
            }
        }finally {
          //  logger.error( "Error has happened while reading properties from file " + CONFIG_FILE);
            if(bufferedReader != null) {
                bufferedReader.close();
            }
        }
        if(properties.size() < 3){
            logger.error( "Error: wrong content of the file " + PROPS_FILE);
            throw new IllegalArgumentException(  );
        }
        return properties;
    }
}
