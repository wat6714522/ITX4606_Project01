package gdd.scene;

import gdd.AudioPlayer;
import gdd.Game;
import static gdd.Global.*;
import gdd.SpawnDetails;

import gdd.powerup.PowerUp;
import gdd.powerup.SpeedUp;
import gdd.powerup.MultiShot;
import gdd.powerup.PlayerArmor;

import gdd.sprite.Enemy;
import gdd.sprite.EnemyShot;
import gdd.sprite.Bomb; 
import gdd.sprite.BombEnemy;
import gdd.sprite.Missile;
import gdd.sprite.MissileEnemy;

import gdd.sprite.Player;
import gdd.sprite.PlayerShot;
import gdd.sprite.ShootingEnemy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Scene1 extends JPanel {

    private int frame = 0;
    private List<PowerUp> powerups;
    private List<Enemy> enemies;
    private List<EnemyShot> enemyShots;
    private List<Bomb> bombs;
    private List<Missile> missiles; 
    private List<PlayerShot> shots;
    private Player player;
    // private Shot shot;

    final int BLOCKHEIGHT = 50;
    final int BLOCKWIDTH = 50;

    final int BLOCKS_TO_DRAW = BOARD_HEIGHT / BLOCKHEIGHT;

    private int direction = -1;
    private int deaths = 0;

    private boolean inGame = true;
    private String message = "Game Over";

    private final Dimension d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
  

    private Timer timer;
    private final Game game;

    private int currentRow = -1;
    // TODO load this map from a file
    private int mapOffset = 0;
    private final int[][] MAP = {
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
    };

    private HashMap<Integer, SpawnDetails> spawnMap = new HashMap<>();
    private AudioPlayer audioPlayer;
    private int lastRowToShow;
    private int firstRowToShow;

    private int lastEnemySpawnFrame = 0; 
    private int enemySpawnInterval = 100; 
    private Random random = new Random(); 

    public Scene1(Game game) {
        this.game = game;
    }

    private void initAudio() {
        try {
            String filePath = "src/audio/Scene1Audio.wav";
            audioPlayer = new AudioPlayer(filePath);
            audioPlayer.play();
        } catch (Exception e) {
            System.err.println("Error initializing audio player: " + e.getMessage());
        }
    }

    private void randomSpawnEnemies(){
        if (lastEnemySpawnFrame >= enemySpawnInterval) {
            int enemyType = random.nextInt(3); // 0 = Shooting, 1 = Missile, 2 = Bomb

            switch (enemyType) {
                case 0:
                    ShootingEnemy enemy = new ShootingEnemy(random.nextInt(BOARD_WIDTH-61), 0);
                    enemy.setTarget(player);
                    enemy.setEnemyShots(enemyShots);
                    enemies.add(enemy);
                    break;
                case 1:
                    MissileEnemy missileEnemy = new MissileEnemy(random.nextInt(BOARD_WIDTH-61), 0);
                    missileEnemy.setTarget(player);
                    missileEnemy.setMissilesList(missiles);
                    enemies.add(missileEnemy);
                    break;
                case 2:
                    BombEnemy  bombEnemy = new BombEnemy(random.nextInt(BOARD_WIDTH-61), 0);
                    bombEnemy.setTarget(player);
                    bombEnemy.setBombsList(bombs);
                    enemies.add(bombEnemy); 
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + enemyType);
            }
            lastEnemySpawnFrame = 0;
        }
        lastEnemySpawnFrame++;
    }

    private void initBoard() {

    }

    public void start() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.black);

        timer = new Timer(1000 / 60, new GameCycle());
        timer.start();

        gameInit();
        initAudio();
    }

    public void stop() {
        timer.stop();
        try {
            if (audioPlayer != null) {
                audioPlayer.stop();
            }
        } catch (Exception e) {
            System.err.println("Error closing audio player.");
        }
    }

    private void gameInit() {
        enemyShots = new ArrayList<>();
        enemies = new ArrayList<>();
        bombs = new ArrayList<>();
        missiles = new ArrayList<>();

        powerups = new ArrayList<>();
        
        shots = new ArrayList<>();
        player = new Player();
    }

    private void drawMap(Graphics g) {
        // Draw scrolling starfield background

        // Calculate smooth scrolling offset (1 pixel per frame)
        int scrollOffset = (frame) % BLOCKHEIGHT;

        // Calculate which rows to draw based on screen position
        int baseRow = (frame) / BLOCKHEIGHT;
        int rowsNeeded = (BOARD_HEIGHT / BLOCKHEIGHT) + 2; // +2 for smooth scrolling

        // Loop through rows that should be visible on screen
        for (int screenRow = 0; screenRow < rowsNeeded; screenRow++) {
            // Calculate which MAP row to use (with wrapping)
            int mapRow = (baseRow + screenRow) % MAP.length;

            // Calculate Y position for this row
            // int y = (screenRow * BLOCKHEIGHT) - scrollOffset;
            int y = BOARD_HEIGHT - ( (screenRow * BLOCKHEIGHT) - scrollOffset );

            // Skip if row is completely off-screen
            if (y > BOARD_HEIGHT || y < -BLOCKHEIGHT) {
                continue;
            }

            // Draw each column in this row
            for (int col = 0; col < MAP[mapRow].length; col++) {
                if (MAP[mapRow][col] == 1) {
                    // Calculate X position
                    int x = col * BLOCKWIDTH;

                    // Draw a cluster of stars
                    drawStarCluster(g, x, y, BLOCKWIDTH, BLOCKHEIGHT);
                }
            }
        }

    }

    private void drawStarCluster(Graphics g, int x, int y, int width, int height) {
        // Set star color to white
        g.setColor(Color.WHITE);

        // Draw multiple stars in a cluster pattern
        // Main star (larger)
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        g.fillOval(centerX - 2, centerY - 2, 4, 4);

        // Smaller surrounding stars
        g.fillOval(centerX - 15, centerY - 10, 2, 2);
        g.fillOval(centerX + 12, centerY - 8, 2, 2);
        g.fillOval(centerX - 8, centerY + 12, 2, 2);
        g.fillOval(centerX + 10, centerY + 15, 2, 2);

        // Tiny stars for more detail
        g.fillOval(centerX - 20, centerY + 5, 1, 1);
        g.fillOval(centerX + 18, centerY - 15, 1, 1);
        g.fillOval(centerX - 5, centerY - 18, 1, 1);
        g.fillOval(centerX + 8, centerY + 20, 1, 1);
    }

    private void drawEnemies(Graphics g) {

        for (Enemy enemy : enemies) {

            if (enemy.isVisible()) {

                g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
            }

            if (enemy.isDying()) {

                enemy.die();
            }
        }
    }

    private void drawPowerUps(Graphics g) {

        for (PowerUp p : powerups) {

            if (p.isVisible()) {

                g.drawImage(p.getImage(), p.getX(), p.getY(), this);
            }

            if (p.isDying()) {

                p.die();
            }
        }
    }

    private void drawPlayer(Graphics g) {

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            inGame = false;
        }
    }

    private void drawShots(Graphics g) {
        for (PlayerShot shot : shots) {
            if (shot.isVisible()) {
                g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
            }
        }
    }

    private void drawBombs(Graphics g){
        for(Bomb bomb : bombs){
            if(bomb.isVisible()){
                g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), this); 
            }
        }
    }

    private void drawEnemyShots(Graphics g){
        for(EnemyShot shot : enemyShots){
            if(shot.isVisible()){
                g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this); 
            }
        }
    }

    private void drawMissiles(Graphics g){
        for(Missile missile : missiles){
            if(missile.isVisible()){
                g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
            }
        }
    }

        private void doDrawing(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);

        g.setColor(Color.white);
        g.drawString("FRAME: " + frame, 10, 10);
        g.drawString("Scene 1", 10, 30);


        g.setColor(Color.green);

        if (inGame) {

            drawMap(g);  // Draw background stars first
            drawPowerUps(g);
            drawEnemies(g);
            drawPlayer(g);
            drawShots(g);
            drawBombs(g); 
            drawEnemyShots(g); 
            drawMissiles(g); 

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void update() {
        randomSpawnEnemies();

        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
            game.loadScene2();
        }

        player.act();

        for (PowerUp powerup : powerups) {
            if (powerup.isVisible()) {
                powerup.act();
                if (powerup.collidesWith(player)) {
/*                     String filePath = "src/audio/Powerups.wav"; 
                    AudioPlayer powerupAudio = new AudioPlayer(filePath); 
                    powerupAudio.play(); */
                    powerup.upgrade(player);
                    
                }
            }
        }

        List<Enemy> enemiesToRemove = new ArrayList<>();
        for(Enemy enemy : enemies){
            if(enemy.isVisible()){
                enemy.act();
            }

            if(enemy.isDying() || !enemy.isVisible() || enemy.getY() > BOARD_HEIGHT){
                enemy.die(); 
                enemiesToRemove.add(enemy);
            }
        }
        enemiesToRemove.removeAll(enemiesToRemove); 

        List<PlayerShot> shotsToRemove = new ArrayList<>();
        for (PlayerShot shot : shots) {

            if (shot.isVisible()) {
                int shotX = shot.getX();
                int shotY = shot.getY();

                for (Enemy enemy : enemies) {
                    // Collision detection: shot and enemy
                    int enemyX = enemy.getX();
                    int enemyY = enemy.getY();

                    if (enemy.isVisible() && shot.isVisible()
                            && shotX >= (enemyX)
                            && shotX <= (enemyX + 128)
                            && shotY >= (enemyY)
                            && shotY <= (enemyY + 128)) {

                        enemy.setDying(true); 
                        deaths++; 
                        shot.onHitTarget();
                        break;
                    }
                }

                int y = shot.getY();
                // y -= 4;
                y -= 20;

                if (y < 0) {
                    shot.die();
                    shotsToRemove.add(shot);
                } else {
                    shot.setY(y);
                }
            }
        }
        shots.removeAll(shotsToRemove);

        List<Bomb> bombsToRemove = new ArrayList<>();
        for(Bomb bomb : bombs){
            if(bomb.isVisible()){
                bomb.act();
            
                if(bomb.collidesWith(player)){
                    bomb.explode();
                    player.setDying(true);
                    player.explode();
                }
                if(!bomb.isVisible() || bomb.getY() > BOARD_HEIGHT){
                    bombsToRemove.add(bomb); 
                }
            }else{
                bombsToRemove.add(bomb);
            }
        }
        bombs.removeAll(bombsToRemove);

        List<EnemyShot> enemyShotsToRemove = new ArrayList<>();
        for (EnemyShot enemyShot : enemyShots) {
            if (enemyShot.isVisible()) {
                enemyShot.act();
                
                if (enemyShot.collidesWith(player)) {
                    player.setDying(true); // Instant kill - no health system
                    player.explode();
                    enemyShot.die();
                    enemyShotsToRemove.add(enemyShot);
                }
                
                if (enemyShot.getY() > BOARD_HEIGHT) {
                    enemyShotsToRemove.add(enemyShot);
                }
            }
        }
        enemyShots.removeAll(enemyShotsToRemove);

        List<Missile> missilesToRemove = new ArrayList<>();
        for (Missile missile : missiles) {
            if (missile.isVisible()) {
                missile.act();
                
                if (missile.collidesWith(player)) {
                    missile.explode();
                    player.setDying(true); // Instant kill - no health system
                    player.explode();
                }
                
                if (missile.getY() > BOARD_HEIGHT) {
                    missilesToRemove.add(missile);
                }
            }
        }
        missiles.removeAll(missilesToRemove);

                // Check if player is dying and all animations are complete at the end of update
        if(player.dying){
            boolean allExplosionsFinished = true; 
            for(Bomb bomb: bombs){
                if(bomb.isExploding()){
                    allExplosionsFinished = false; 
                    break;
                }
            }

            // Also wait for player death animation to finish
            boolean playerDeathAnimationFinished = !player.isExploding() || !player.isVisible();

            if(allExplosionsFinished && playerDeathAnimationFinished){
                inGame = false;
                message = "Game Over!";
            }
        }
        

    }

    private void doGameCycle() {
        frame++;
        update();
        repaint();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Scene2.keyPressed: " + e.getKeyCode());

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE && inGame) {
                System.out.println("Shots: " + shots.size());
                if (shots.size() < 4) {
                    // Create a new shot and add it to the list
                    PlayerShot shot = new PlayerShot(x, y);
                    shot.startShotAnimation();
                    shots.add(shot);
                }
            }

            if(key == KeyEvent.VK_ESCAPE){
                System.exit(0);
            }

        }
    }
}
