package geometry.helpers;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Deserializer {
    static final Logger logger = LogManager.getLogger( Deserializer.class.getName() );

    public static <T> T getObjectFromJsonFile( String filePath, Class<T> classOfT )
            throws IOException {
        logger.info( "Object deserialization from file: " + filePath );
        Gson gson = new Gson();
        T objectOfT;
        try ( FileReader fileReader = new FileReader( filePath ) ) {
            BufferedReader bufferedReader = new BufferedReader( fileReader );
            objectOfT = gson.fromJson( bufferedReader, classOfT );
        } catch ( IOException ex ) {
            logger.error( "Error has happened while getting properties from " + filePath );
            throw ex;
        }
        if ( objectOfT == null ) {
            logger.error( "Error: wrong content of the file " + filePath );
            throw new IllegalArgumentException();
        }
        return objectOfT;
    }
}
