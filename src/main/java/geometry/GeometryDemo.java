package geometry;

import geometry.figures.Parallelogram;
import geometry.figures.Square;

import java.lang.*;

public class GeometryDemo {

    public static void main(String[] args) {
        Parallelogram parallelogram1 = new Parallelogram(10, 8, 30);
        System.out.println("Area of parallelogram: " + parallelogram1.calculateArea());
        Square square = new Square(10);
        System.out.println("Area of square: " + square.calculateArea());

    }
}
