package geometry.figures;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Parallelogram {
    static final Logger logger = LogManager.getLogger(Parallelogram.class.getName());
    private int baseSide;
    private int edgeSide;
    private int angle;

    public Parallelogram( int baseSide, int edgeSide, int angle ){
        if( angle <= 0 || angle >= 180 ){
            throw new IllegalArgumentException( "Wrong input data for parallelogram: the angle must be > 0 and < 180 degrees." );
        }
        this.angle = angle;
        if( baseSide <= 0 || edgeSide <= 0 ){
            throw new IllegalArgumentException( "Wrong input data for parallelogram: the side must be > 0." );
        }
        this.baseSide = baseSide;
        this.edgeSide = edgeSide;
    }

    public int calculateArea(){
        logger.info("Calculating area of the parallelogram.");
        return (int) Math.round(baseSide * edgeSide * Math.sin(Math.toRadians( angle )));
    }
}
