package survivor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import survivor.Bait.BaitType;
import survivor.GameSettings.Difficulty;


public class Util {
    private final static String plusOnePath = "plusone.png";
    private final static String bananaPath = "banana.png";
    private final static String rockPath = "rock.png";
    private final static String bombPath = "bomb.png";
    public final static String playerPath = "cursor-default.png";
    private final static String scoresPath = "";
    
    
    private static ArrayList<HighScore> scores;
    private static GameSettings settings;
    
    public final static int screenWidth = 600;
    public final static int screenHeight = 600;
    
    public static void initialize() {
        loadScores();
        settings = new GameSettings(Difficulty.Normal);
    }
    
    public static void setDifficulty(Difficulty diff) {
        settings = new GameSettings(diff);
    }
    
    public static Difficulty getDifficulty() {
        return settings.getDiff();
    }
    
    public static ArrayList<HighScore> getScores() {
        return scores;
    }
    
    public static void loadScores() {
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(new FileInputStream(scoresPath));
            ArrayList<HighScore> loadedScores = (ArrayList<HighScore>)in.readObject();
            in.close();
            scores = loadedScores;
        } catch (IOException ex) {
            scores = new ArrayList<HighScore>();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void saveScores() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(
                    new FileOutputStream(scoresPath)
            );  
            out.writeObject(scores);
            out.flush();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void addScore(HighScore score) {
        scores.add(score);
        saveScores();
    }
    
    public static BufferedImage imageForBaitType(BaitType type) {
        return Util.imageForPath(Util.imagePathForType(type));
    }
    
    private static String imagePathForType(BaitType type) {
        switch (type) {
            case PlusOne:
                return plusOnePath;
            case Banana:
                return bananaPath;
            case Bomb:
                return bombPath;
            case Rock: 
                return rockPath;
        }
        
        return null;
    }
    
    public static BufferedImage imageForPath(String path) {
        try { 
            return ImageIO.read(new File(path));
        } catch (Exception ex) 
		{
            return null;
        }
    }
    
    public static void navigateToView(JFrame window , JPanel view) {
        window.setContentPane(view);
        window.pack();
        window.setVisible(true);
    }
    
}
