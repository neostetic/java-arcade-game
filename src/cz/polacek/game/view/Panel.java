package cz.polacek.game.view;

import cz.polacek.game.config.Config;
import cz.polacek.game.view.keylistener.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements Runnable {

    final Config config = new Config();

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    private int playerX = 0;
    private int playerY = 0;

    private double playerXvel = 0;
    private double playerYvel = 0;

    private int FPS = 60;

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
        while (gameThread != null) {
            update();
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
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

//        if (keyHandler.upPressed) { playerY -= Config.playerSpeed; }
//        if (keyHandler.downPressed) { playerY += Config.playerSpeed; }
//        if (keyHandler.leftPressed) { playerX -= Config.playerSpeed; }
//        if (keyHandler.rightPressed) { playerX += Config.playerSpeed; }

        if (keyHandler.upPressed) { playerYvel = -Config.playerSpeed; }
        else { playerYvel = playerYvel/Config.gravity; }
        if (keyHandler.downPressed) { playerYvel = Config.playerSpeed; }
        else { playerYvel = playerYvel/Config.gravity; }
        if (keyHandler.leftPressed) { playerXvel = -Config.playerSpeed; }
        else { playerXvel = playerXvel/Config.gravity; }
        if (keyHandler.rightPressed) { playerXvel = Config.playerSpeed; }
        else { playerXvel = playerXvel/Config.gravity; }

        playerY = playerY + (int) playerYvel;
        playerX = playerX + (int) playerXvel;


        if (playerY < 0) { playerY = 0; }
        if (playerY > (Config.windowHeight - Config.tileComputed)) {
            playerY = (Config.windowHeight - Config.tileComputed);
        }
        if (playerX < 0) { playerX = 0; }
        if (playerX + Config.tileComputed > (Config.windowWidth)) {
            playerX = (Config.windowWidth - Config.tileComputed);
        }


    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(Color.RED);
        graphics2D.fillRect(playerX, playerY, Config.tileComputed, Config.tileComputed);
        graphics2D.dispose();
    }
}
