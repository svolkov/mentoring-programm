package geometry;

import geometry.figures.Parallelogram;
import geometry.figures.Square;
import geometry.helpers.AppPropertiesReader;

import java.io.IOException;
import java.lang.*;
import java.util.Map;
import java.util.Properties;

public class GeometryDemo {

    public static void main(String[] args) throws IOException {
        final String PROPS_BASE_SIDE = "baseSide";
        final String PROPS_EDGE_SIDE = "edgeSide";
        final String PROPS_ANGLE = "angle";

        Parallelogram parallelogram1 = new Parallelogram( 2, 8, 80 );
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
    }
}
