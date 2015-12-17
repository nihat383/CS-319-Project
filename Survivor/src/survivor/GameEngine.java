package survivor;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;
import survivor.Bait.BaitType;

public class GameEngine extends javax.swing.JPanel {
    
    private JFrame container;

    public void setContainer(JFrame container) {
        this.container = container;
    }

    public JFrame getContainer() {
        return container;
    }
    
    private final int timerDelay = 50;
    private Timer updateTimer;
    private int score;
    private int lifeCount;
    
    private Player player;
    private int playerPositionY = 10;
    
    private int maxBaitHeight = 40;
    private int maxXVelocity = 3;
    private int maxYVelocity = 3;
    private int speedMultiplier = 1;
    private int increasedAt = 10000;
    
    private ArrayList<Bait> fallers;
    private int targetFallerCount = 20;
    private boolean isGameOver = false;
    private boolean isThereCollision = false;
    private Position latestMousePosition = null;
    private Random rand;
    
    public GameEngine() {
        initComponents();
        score = 0;
        lifeCount = 3;
        updateTimer = new Timer(this.timerDelay,new TimerListener());
        rand = new Random();
        fallers = new ArrayList<Bait>();
        player = new Player(new Position(300,550));
        completeFallers();
        addMouseMotionListener(new MouseMotionListener());
        updateTimer.start();
    }
    
    public void completeFallers() {
        for(int i = this.fallers.size()-1;i<this.targetFallerCount;i++) {
            fallers.add(createRandomFaller());
        }
    }
    
    private Bait createRandomFaller() {
        BaitType type = BaitType.randomType();
        Position delta = randomDeltaPosition();
        Position initialPos = randomInitPosition();
        Bait bait = new Bait(initialPos,delta,type);
        return bait;
    }
    
    class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            update();
        }
    } 
    
    class MouseMotionListener extends MouseAdapter {
        public void mouseMoved(MouseEvent e) {
            latestMousePosition = new Position(e.getX(),e.getY());
            player.currentPosition.setX(latestMousePosition.getX());
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        paintFallers(g);
        paintPlayer(g);
        paintScore(g);
    }
    
    private void paintFallers(Graphics g){
        for(int i=0;i<this.fallers.size();i++) {
            this.fallers.get(i).draw(g);
        }
    }
    
    private void paintPlayer(Graphics g){
        player.draw(g);
    }
    
    private void paintScore(Graphics g) {
        g.drawString("Score : " + score,80,550);
        g.drawString("Life :" + lifeCount,80,570); 
    }
   
    
    private void update() {
        updateFallers();
        checkCollisions();
        isGameOver = (lifeCount < 0);
        if(!isGameOver) {
            adjustDifficulty();
            repaint();
        } else {
            GameOverView goView = new GameOverView(this.score,this.container);
            Util.navigateToView(this.container, goView);
            System.out.println("OVER BABY");
            this.updateTimer.stop();
        }
    }
    
    private void updateFallers() {
        completeFallers();
        for(int i = 0;i<this.fallers.size();i++) {
            fallers.get(i).update();
        }
    }
    
    private void checkCollisions() {
        int collision = 0;
        for(int i = 0;i<this.fallers.size();i++) {
            Bait current = fallers.get(i);
            if(player.isCollided(current)) {
                collision++;
                BaitType type = current.getType();
                if (type == Bait.BaitType.Banana) {
                    score+=1500;
                } else if (type == Bait.BaitType.Rock) {
                    score+=-1000;
                } else if (type == Bait.BaitType.PlusOne) {
                    lifeCount+=1;
                } else if (type == Bait.BaitType.Bomb) {
                    lifeCount+=-1;
                }
                current.setCurrentPosition(randomInitPosition());               
                current.setDeltaPosition(randomDeltaPosition());
            } else if(isOutOfBounds(current)) {
                current.setCurrentPosition(randomInitPosition());
                current.setDeltaPosition(randomDeltaPosition());
            }
        }
        System.out.println(collision);
    }
    
    private boolean isOutOfBounds(Bait bait) {
        return (bait.currentPosition.getX() > Util.screenWidth || bait.currentPosition.getY() > Util.screenHeight);
    }
    
    private Position randomInitPosition() {
        int posX = rand.nextInt(Util.screenWidth);
        int posY = -rand.nextInt(100) - this.maxBaitHeight;
        return new Position(posX,posY);
    }
    
    private Position randomDeltaPosition() {
        int deltaX = rand.nextInt(this.maxXVelocity);
        int deltaY = 1 + rand.nextInt(this.maxYVelocity);
        Position delta = new Position(this.speedMultiplier * deltaX,this.speedMultiplier * deltaY);
        return delta;
    }
    
    private void adjustDifficulty() { 
        if (score - increasedAt >= 10000) {
            targetFallerCount+=1;
            speedMultiplier+=1;
            increasedAt = score;
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(600, 600));
        setPreferredSize(new java.awt.Dimension(600, 600));
        setSize(new java.awt.Dimension(600, 600));
        setLayout(new java.awt.BorderLayout());
    }
}
