package cz.polacek.game.view;

import cz.polacek.game.config.Config;
import cz.polacek.game.view.entity.Background;
import cz.polacek.game.view.entity.Enemy;
import cz.polacek.game.view.entity.GUI;
import cz.polacek.game.view.entity.player.Player;
import cz.polacek.game.view.keylistener.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel implements Runnable {

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyHandler);
    Enemy enemy = new Enemy(this, 0, 0, 1, 1);
    Background background = new Background(this, player, keyHandler);
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
        enemy.update();
        gui.update();
        if (enemy.getRect().intersects(player.getRect())) {
            player.setPLAYER_HEALTH(player.getPLAYER_HEALTH() - 1);
        }
        if (!player.getBullets().isEmpty()) {
            for (int i = 0; i < player.getBullets().size(); i++) {
                if (enemy.getRect().intersects(player.getBullets().get(i).getRect())) {
                    System.out.println("NS");
                    player.getBullets().remove(i);
                }
            }
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        background.draw(graphics2D);
        player.draw(graphics2D);
        enemy.draw(graphics2D);
        gui.draw(graphics2D);
        graphics2D.dispose();
    }
}
