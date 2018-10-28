package geometry;

import geometry.figures.Parallelogram;
import geometry.figures.Square;
import geometry.helpers.AppPropertiesReader;
import geometry.helpers.FileWorker;

import java.io.IOException;
import java.lang.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class GeometryDemo {
    final static String PARALLELOGRAM_JSON_FILEPATH = GeometryDemo.class.getResource( "/deserialization/parallelogram.txt" ).getPath();
    final static String SQUARE_JSON_FILEPATH = GeometryDemo.class.getResource( "/deserialization/square.txt" ).getPath();
    final static String JSON_ARRAY_FILEPATH = GeometryDemo.class.getResource( "/deserialization/parallelogram_array.txt" ).getPath();
    final static String EXCEL_ARRAY_FILEPATH = GeometryDemo.class.getResource( "/deserialization/Parallelograms.xlsx" ).getPath();
    final static String AREAS_FILENAME = "areas.json";
    final static String AREAS_EXCEL_FILENAME = "areas.xlsx";

    public static void main(String[] args) throws IOException {
        final String PROPS_BASE_SIDE = "baseSide";
        final String PROPS_EDGE_SIDE = "edgeSide";
        final String PROPS_ANGLE = "angle";

        Parallelogram parallelogram1 = new Parallelogram( 2, 8, 90 );
        System.out.println( "Area of parallelogram: " + parallelogram1.calculateArea() );
        Square square = new Square( 50 );
        System.out.println( "Area of square: " + square.calculateArea() );

        Properties appProperties = AppPropertiesReader.getProperties();
        Parallelogram parallelogramOnAppProps = new Parallelogram(Integer.parseInt( appProperties.getProperty( PROPS_BASE_SIDE )),
                                                                  Integer.parseInt( appProperties.getProperty( PROPS_EDGE_SIDE )),
                                                                  Integer.parseInt( appProperties.getProperty( PROPS_ANGLE )));
        System.out.println( "Area of parallelogram based on app.properties: " + parallelogramOnAppProps.calculateArea() );

        Map<String, String> propertiesFromFile = AppPropertiesReader.getPropsFromFile();
        Parallelogram parallelogramOnPropsFromFile = new Parallelogram(Integer.parseInt( propertiesFromFile.get( PROPS_BASE_SIDE )),
                                                                       Integer.parseInt( propertiesFromFile.get( PROPS_EDGE_SIDE )),
                                                                       Integer.parseInt( propertiesFromFile.get( PROPS_ANGLE )));
        System.out.println( "Area of parallelogram based on properties from file: " + parallelogramOnPropsFromFile.calculateArea() );

        Parallelogram parallelogramFromJson = FileWorker.getObjectFromJsonFile( PARALLELOGRAM_JSON_FILEPATH,
                                                                                Parallelogram.class );
        System.out.println( "Area of parallelogram based on Json from file: " + parallelogramFromJson.calculateArea() );
        Square squareFromJson = FileWorker.getObjectFromJsonFile( SQUARE_JSON_FILEPATH, Square.class );
        System.out.println( "Area of square based on Json from file: " + squareFromJson.calculateArea() );

        List<Parallelogram> listOfParallelograms = FileWorker.getParallelogramsFromJsonFile( JSON_ARRAY_FILEPATH );
        List<Integer> areas = listOfParallelograms.stream().map( Parallelogram::calculateArea ).collect( Collectors.toList() );
        FileWorker.saveObjectsToJsonFile( AREAS_FILENAME, areas );

        List<Parallelogram> listOfParallelogramsFormExcel = FileWorker.getParallelogramsFromExcelFile(EXCEL_ARRAY_FILEPATH);
        List<Integer> areasOfParallelogramsFormExcel = listOfParallelogramsFormExcel.stream().map( Parallelogram::calculateArea )
                                                                                    .collect( Collectors.toList() );
        FileWorker.saveParallelogramAreasToExcelFile(AREAS_EXCEL_FILENAME, areasOfParallelogramsFormExcel);
    }
}
