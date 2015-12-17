package survivor;

public class GameSettings {
    public enum Difficulty {
        Easy,Normal,Hard;
    }

    public GameSettings(Difficulty diff) {
        this.diff = diff;
    }
    
    private Difficulty diff;

    public Difficulty getDiff() {
        return diff;
    }
    
    
}
