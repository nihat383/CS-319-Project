package survivor;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Bait extends Collidable {

    Position deltaPosition;
    BaitType type;

    public Position getDeltaPosition() {
        return deltaPosition;
    }

    public void setDeltaPosition(Position deltaPosition) {
        this.deltaPosition = deltaPosition;
    }

    public BaitType getType() {
        return type;
    }
    
    public Bait(Position initialPos, Position deltaPos , BaitType type) {
        super(initialPos, Util.imageForBaitType(type));
        this.type = type;
        this.deltaPosition = deltaPos;
    }
    
    public void update() {
        currentPosition.setX(currentPosition.getX() + deltaPosition.getX());
        currentPosition.setY(currentPosition.getY() + deltaPosition.getY());
    }
    
    public enum BaitType {
        Banana,PlusOne,Bomb,Rock;
        
        private static final List<BaitType> kValues = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int kSize = kValues.size();
        private static final Random rand = new Random();

        public static BaitType randomType()  {
            return kValues.get(rand.nextInt(kSize));
        }
    }
}


