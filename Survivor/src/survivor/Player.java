package survivor;

import java.awt.image.BufferedImage;

public class Player extends Collidable {

    public Player(Position initialPos) {
        super(initialPos, Util.imageForPath(Util.playerPath));
    }
    

}
