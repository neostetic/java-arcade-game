package cz.polacek.game.view;

import cz.polacek.game.config.Config;
import cz.polacek.game.utils.Utils;
import cz.polacek.game.view.entity.Background;
import cz.polacek.game.view.entity.Enemy;
import cz.polacek.game.view.entity.GUI;
import cz.polacek.game.view.entity.player.Bullet;
import cz.polacek.game.view.entity.Interval;
import cz.polacek.game.view.entity.player.Player;
import cz.polacek.game.view.keylistener.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel implements Runnable {

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyHandler);
//    Enemy enemy = new Enemy(this, 0, 0, 1, 1);
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    Interval enemySpawnInterval = new Interval(Config.enemySpawnInterval);
    Background background = new Background(this, player, keyHandler);
    GUI gui = new GUI(this, keyHandler, player);

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
        for (int i = 0; i < enemies.size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                if (i != j) {
                    if (enemies.get(i).getRect().intersects(enemies.get(j).getRect())) {
                        enemies.get(i).setxVel(0);
                        enemies.get(i).setyVel(0);
                        System.out.println(enemies.get(i).getxVel());
                    }
                }
            }
        }
        if (!enemies.isEmpty()) {
            for (Enemy enemy : enemies) {
                enemy.update();
                if (enemy.getRect().intersects(player.getRect())) {
                    player.getHit();
                    enemy.getHit();
                }
                if (!player.getBullets().isEmpty()) {
                    for (int i = 0; i < player.getBullets().size(); i++) {
                        if (enemy.getRect().intersects(player.getBullets().get(i).getRect())) {
                            enemy.getHit();
                            player.getBullets().get(i).setDestroyed(true);
                        }
                    }
                }
            }
            int bulletIndex = 0;
            while (bulletIndex < player.getBullets().size()) {
                Bullet bullet = player.getBullets().get(bulletIndex);
                if (bullet.isDestroyed()) {
                    player.getBullets().remove(bulletIndex);
                    continue;
                }
                bulletIndex++;
            }
            synchronized (this) {
                int enemyIndex = 0;
                while (enemyIndex < enemies.size()) {
                    Enemy enemy = enemies.get(enemyIndex);
                    if (enemy.isDestroyed()) {
                        enemies.remove(enemyIndex);
                        continue;
                    }
                    enemyIndex++;
                }
            }
        }
        gui.update();
        if (enemySpawnInterval.canDo()) {
            int[] randomPosition = Utils.randomSpawnLocation();
            enemies.add(new Enemy(this, randomPosition[0], randomPosition[1], Utils.randomDoubleBetween(-Config.maxEnemyVelocity,Config.maxEnemyVelocity), Utils.randomDoubleBetween(-Config.maxEnemyVelocity,Config.maxEnemyVelocity)));
            enemySpawnInterval.did();
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        background.draw(graphics2D);
        player.draw(graphics2D);
        if (!enemies.isEmpty()) {
            synchronized (this) {
                for (Enemy value : enemies) {
                    value.draw(graphics2D);
                }
            }
        }
        gui.draw(graphics2D);
        graphics2D.dispose();
    }
}
