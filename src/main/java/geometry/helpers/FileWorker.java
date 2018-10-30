package geometry.helpers;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import geometry.figures.Parallelogram;
import geometry.figures.Square;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

    public static List<Parallelogram> getParallelogramsFromXmlFile( String filePath )
                            throws IOException, ParserConfigurationException, SAXException {
        logger.info( "Parallelograms deserialization from xml-file: " + filePath );
        List<Parallelogram> parallelogramList = new ArrayList<>();
        try(FileInputStream fileInputStream = new FileInputStream(filePath)){
                getObjectsFromXml(fileInputStream, parallelogramList);
        }catch ( IOException ex ){
            logger.error( "Error has happened while writing to file: " + filePath );
            throw ex;
        }
        if ( parallelogramList.size() == 0 ) {
            logger.error( "Error: wrong content of the file " + filePath );
            throw new IllegalArgumentException();
        }
        return parallelogramList;
    }

    private static void getObjectsFromXml(FileInputStream fileInputStream, List<Parallelogram> listOfFigures)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(fileInputStream);
        NodeList nodeList = doc.getElementsByTagName( "figure" );
        for( int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item( i );
            Element elementFigure = (Element) node;
            String figureType = elementFigure.getAttribute( "type" );
            if(!figureType.equals( "" )){
                listOfFigures.add( makeFigureObject(figureType, elementFigure) );
            }else{
                logger.error( "XML document error: element 'figure' has no attribute " );
                throw new SAXException();
            }
        }
    }

    private static Parallelogram makeFigureObject(String typeofFigure, Element element)
            throws SAXException {
        Parallelogram parallelogram;
        switch(typeofFigure){
              case "parallelogram": NodeList baseSideParams = element.getElementsByTagName( "baseSide" );
                                    NodeList edgeSideParams = element.getElementsByTagName( "edgeSide" );
                                    NodeList angleParams = element.getElementsByTagName( "angle" );
                                    if(baseSideParams.getLength() == 1 && edgeSideParams.getLength() == 1 && angleParams.getLength() == 1) {
                                           parallelogram = new Parallelogram( Integer.parseInt( baseSideParams.item( 0 ).getTextContent()),
                                                                           Integer.parseInt( edgeSideParams.item( 0 ).getTextContent()),
                                                                           Integer.parseInt( angleParams.item( 0 ).getTextContent()));
                                    }else{
                                        logger.error( "XML document error: element 'figure' of type 'parallelogram' has wrong parameters number." );
                                        throw new SAXException();
                                    }
                                    break;
              case "square":        NodeList sideParams = element.getElementsByTagName( "side" );
                                    if(sideParams.getLength() == 1) {
                                         parallelogram = new Square( Integer.parseInt( sideParams.item( 0 ).getTextContent()));
                                    }else{
                                        logger.error( "XML document error: element 'figure' of type 'square' has wrong parameters number." );
                                        throw new SAXException();
                                       }
                    break;
                                    default: logger.error( "XML document error: element 'figure' has wrong attribute " );
              throw new SAXException();
        }
        return parallelogram;
    }
}
