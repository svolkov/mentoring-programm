package geometry.helpers;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import geometry.figures.Parallelogram;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;

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

    public static <E> void saveObjectsToJsonFile(String filePath, List<E> objects) throws IOException{
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

    public static List<Parallelogram> getParallelogramsFromExcelFile( String filePath ) throws IOException {
        logger.info( "Parallelograms deserialization from EXCEL-file: " + filePath );
        List<Parallelogram> listOfElements = new ArrayList<>();
        Workbook workbook;
        int cellNumber = 1;
        try(FileInputStream fileInputStream = new FileInputStream(filePath)){
            workbook = new XSSFWorkbook(fileInputStream);
            Iterator<Sheet> sheets = workbook.sheetIterator();
            sheets.forEachRemaining(sh -> {
                int startRow = 2;
                listOfElements.add(new Parallelogram(
                        (int) sh.getRow(startRow++).getCell(cellNumber).getNumericCellValue(),
                        (int) sh.getRow(startRow++).getCell(cellNumber).getNumericCellValue(),
                        (int) sh.getRow(startRow).getCell(cellNumber).getNumericCellValue()));
            } );
            workbook.close();
        }catch(IOException ex){
            logger.error( "Error has happened while reading from file: " + filePath );
            throw ex;
        }
        if ( listOfElements.size() == 0 ) {
            logger.error( "Error: wrong content of the file " + filePath );
            throw new IllegalArgumentException();
        }
        return listOfElements;
    }

    public static void saveParallelogramAreasToExcelFile(String filePath, List<Integer> areas) throws IOException{
        logger.info( "Saving parallelograms to excel-file: " + filePath );
        Workbook workbook = new XSSFWorkbook();
        for(int i = 0; i < areas.size(); i++) {
            String header = "Parallelogram # " + i;
            Sheet sheet = workbook.createSheet(header);
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue(header);
            row = sheet.createRow(1);
            cell = row.createCell(0);
            cell.setCellValue("Area");
            cell = row.createCell(1);
            cell.setCellValue(areas.get(i));
        }
        Path path = Paths.get(filePath);
        if(!Files.exists( path )){
            Files.createFile( path );
        }
        try( FileOutputStream fileOutputStream = new FileOutputStream(filePath, false) ){
            workbook.write(fileOutputStream);
        }catch ( IOException ex ){
            logger.error( "Error has happened while writing to file: " + filePath );
            throw ex;
        }
    }
}
