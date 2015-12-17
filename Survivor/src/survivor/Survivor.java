package survivor;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Survivor {

    public static void main(String[] args) {
        Util.initialize();
        JFrame frame = new JFrame("Survivor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainMenu menu = new MainMenu(frame);
        Util.navigateToView(frame, menu);
    }
    
}
