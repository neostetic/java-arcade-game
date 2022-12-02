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

    public Panel() {
        this.setPreferredSize(new Dimension(config.windowWidth, config.windowHeight));
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
        while (gameThread != null) {
            update();
            repaint();
        }
    }

    public void update() {
        if (keyHandler.upPressed) { playerY -= config.playerSpeed; }
        if (keyHandler.downPressed) { playerY += config.playerSpeed; }
        if (keyHandler.leftPressed) { playerX -= config.playerSpeed; }
        if (keyHandler.rightPressed) { playerX += config.playerSpeed; }


        if (playerY < 0) { playerY = 0; }
        else if (playerY > (config.windowHeight - config.tileComputed)) { playerY = (config.windowHeight - config.tileComputed); }
        if (playerX < 0) { playerX = 0; }
        else if (playerX > (config.windowWidth - config.tileComputed)) { playerX = (config.windowWidth - config.tileComputed); }
        System.out.println(config.windowWidth - config.tileComputed + " : " + config.windowWidth);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(playerX, playerY, config.tileComputed, config.tileComputed);
        graphics2D.dispose();
    }
}
