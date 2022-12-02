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
        this.setSize(new Dimension(config.windowWidth, config.windowHeight));
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

        System.out.println("x:" + playerX + " y:" + playerY + " w:" + (super.getWidth()) + " h:" + (super.getHeight()));
        if (keyHandler.upPressed) {
            playerY -= config.playerSpeed;
        }
        if (keyHandler.downPressed) {
            playerY += config.playerSpeed;
        }
        if (keyHandler.leftPressed) {
            playerX -= config.playerSpeed;
        }
        if (keyHandler.rightPressed) {
            playerX += config.playerSpeed;
        }

        if (playerY < 0) {
            playerY = 0;
            return;
        }
        if (playerY > (config.windowHeight - config.tileComputed)) {
            playerY = (config.windowHeight - config.tileComputed);
            return;
        }
        if (playerX < 0) {
            playerX = 0;
            return;
        }
        if (playerX + config.tileComputed > (config.windowWidth)) {
            playerX = (config.windowWidth - config.tileComputed);
            return;
        }


    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(Color.RED);
        graphics2D.fillRect(playerX, playerY, config.tileComputed, config.tileComputed);
        graphics2D.dispose();
    }
}
