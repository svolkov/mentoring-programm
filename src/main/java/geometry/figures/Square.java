package geometry.figures;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Square extends Parallelogram {
    static final Logger logger = LogManager.getLogger(Square.class.getName());
    private int side;

    public Square(int side){
        super(side, side, 90);
        this.side = side;
    }

    @Override
    public int calculateArea() {
        logger.info("Calculating area of the square.");
        return side * side;
    }
}
