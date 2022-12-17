package cz.polacek.game.view;

import cz.polacek.game.config.Config;
import cz.polacek.game.view.entity.*;
import cz.polacek.game.view.entity.player.Player;
import cz.polacek.game.view.handler.KeyHandler;
import cz.polacek.game.view.panel.Game;
import cz.polacek.game.view.panel.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Panel extends JPanel implements Runnable {

    int score = 0;
    int highScore = 0;
    PanelState panelState = PanelState.MAINMENU;

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public PanelState getPanelState() {
        return panelState;
    }

    public void setPanelState(PanelState panelState) {
        this.panelState = panelState;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    Game game = new Game(this);
    MainMenu mainMenu = new MainMenu(this);

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyHandler);
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    Interval enemySpawnInterval = new Interval(Config.enemySpawnInterval);
    Powerup powerup = new Powerup(this, player, 0, 0, 0);
    Interval powerupSpawnInterval = new Interval(Config.powerupSpawnInterval);
    Background background = new Background(this, player);
    GUI gui = new GUI(this, keyHandler, player);

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public Thread getGameThread() {
        return gameThread;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Interval getEnemySpawnInterval() {
        return enemySpawnInterval;
    }

    public Powerup getPowerup() {
        return powerup;
    }

    public Interval getPowerupSpawnInterval() {
        return powerupSpawnInterval;
    }

    public Background getGameBackground() {
        return background;
    }

    public GUI getGui() {
        return gui;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setKeyHandler(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    public void setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void setEnemySpawnInterval(Interval enemySpawnInterval) {
        this.enemySpawnInterval = enemySpawnInterval;
    }

    public void setPowerup(Powerup powerup) {
        this.powerup = powerup;
    }

    public void setPowerupSpawnInterval(Interval powerupSpawnInterval) {
        this.powerupSpawnInterval = powerupSpawnInterval;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    private final int FPS = Config.prefferedFPS;

    public Panel() {
        this.setSize(new Dimension(Config.windowWidth, Config.windowHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Better render
        this.setLayout(null);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        enemySpawnInterval.start();
        powerupSpawnInterval.start();

        try {
            File file = new File(Config.recordFile);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                setHighScore(Integer.parseInt(scanner.nextLine()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (gameThread != null) {
            update();
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                new WindowError(e.toString());
            }
        }
    }

    public void update() {
        switch (panelState) {
            case GAME -> game.update();
            case MAINMENU -> mainMenu.update();
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        switch (panelState) {
            case GAME -> game.draw(graphics2D);
            case MAINMENU -> mainMenu.draw(graphics2D);
        }
        graphics2D.dispose();
    }
}
