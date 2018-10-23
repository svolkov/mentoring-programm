package geometry.helpers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import geometry.figures.Parallelogram;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

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
            logger.error( "Error has happened while reading from file: " + filePath );
            throw ex;
        }
        if ( objectOfT == null ) {
            logger.error( "Error: wrong content of the file " + filePath );
            throw new IllegalArgumentException();
        }
        return objectOfT;
    }

    public static List<Parallelogram> getParallelogramsFromJsonFile( String filePath) throws IOException{
        logger.info( "Parallelograms array deserialization from file: " + filePath );
        Gson gson = new Gson();
        List<Parallelogram> listOfElements;
        Type listType = new TypeToken<List<Parallelogram>>(){}.getType();

        try ( FileReader fileReader = new FileReader( filePath ) ) {
            BufferedReader bufferedReader = new BufferedReader( fileReader );
            listOfElements = gson.fromJson( bufferedReader, listType );
        } catch ( IOException ex ) {
            logger.error( "Error has happened while reading from file: " + filePath );
            throw ex;
        }
        if ( listOfElements.size() == 0 ) {
            logger.error( "Error: wrong content of the file " + filePath );
            throw new IllegalArgumentException();
        }
        return listOfElements;
    }
}
