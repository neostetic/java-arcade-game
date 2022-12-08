package cz.polacek.game.view;

import cz.polacek.game.config.Config;
import cz.polacek.game.view.entity.Background;
import cz.polacek.game.view.entity.GUI;
import cz.polacek.game.view.entity.Player;
import cz.polacek.game.view.keylistener.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements Runnable {

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyHandler);
    Background background = new Background(this, keyHandler);
    GUI gui = new GUI(this, keyHandler, player);

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
        background.update();
        player.update();
        gui.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        background.draw(graphics2D);
        player.draw(graphics2D);
        gui.draw(graphics2D);
        graphics2D.dispose();
    }
}
