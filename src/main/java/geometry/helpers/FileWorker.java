package geometry.helpers;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import geometry.figures.Parallelogram;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileWorker {
    static final Logger logger = LogManager.getLogger( FileWorker.class.getName() );

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

    public static List<Parallelogram> getParallelogramsFromJsonFile( String filePath ) throws IOException{
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

    public static <E> void  saveObjectsToJsonFile(String filePath, List<E> objects) throws IOException{
        logger.info( "Object serialization to file: " + filePath );
        Gson gson = new Gson();
        String json = gson.toJson( objects );
        Path path = Paths.get(filePath);
        if(!Files.exists( path )){
            Files.createFile( path );
        }
        try( FileWriter fileWriter = new FileWriter( filePath, false ) ){
            fileWriter.write( json );
        }catch ( IOException ex ){
            logger.error( "Error has happened while writing to file: " + filePath );
            throw ex;
        }
    }
}
