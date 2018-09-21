package geometry.figures;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Parallelogram {
    static final Logger logger = LogManager.getLogger(Parallelogram.class.getName());
    private int baseSide;
    private int edgeSide;
    private int angle;

    public Parallelogram( int baseSide, int edgeSide, int angle ){
        this.angle = angle;
        this.baseSide = baseSide;
        this.edgeSide = edgeSide;
    }

    public int calculateArea(){
        logger.info("Calculating area of the parallelogram.");
        return (int) Math.round(baseSide * edgeSide * Math.sin(Math.toRadians( angle )));
    }
}
