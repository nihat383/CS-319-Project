package survivor;

public class HighScore implements java.io.Serializable {
    private int score;
    private String name;

    public HighScore(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
    
    
}
