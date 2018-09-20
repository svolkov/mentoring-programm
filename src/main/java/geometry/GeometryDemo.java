package geometry;

import geometry.figures.Parallelogram;
import java.lang.*;

public class GeometryDemo {

    public static void main(String[] args) {
        Parallelogram parallelogram1 = new Parallelogram(10, 8, 90);
        System.out.println(parallelogram1.calculateArea());
    }
}
