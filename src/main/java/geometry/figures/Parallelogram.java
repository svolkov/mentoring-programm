package geometry.figures;

public class Parallelogram {
    private int baseSide;
    private int edgeSide;
    private int angle;

    public Parallelogram( int baseSide, int edgeSide, int angle ){
        this.angle = angle;
        this.baseSide = baseSide;
        this.edgeSide = edgeSide;
    }

    public int calculateArea(){
        return (int) Math.round(baseSide * edgeSide * Math.sin(Math.toRadians( angle )));

    }
}
