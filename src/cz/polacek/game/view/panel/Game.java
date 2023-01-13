package cz.polacek.game.view.panel;

import cz.polacek.game.config.Config;
import cz.polacek.game.utils.Utils;
import cz.polacek.game.view.Panel;
import cz.polacek.game.view.entity.*;
import cz.polacek.game.view.entity.player.Bullet;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Game {

    Panel panel;

    public Game(Panel panel) {
        this.panel = panel;
    }

    public void update() {
        if (panel.getPlayer().getPLAYER_HEALTH() > 0) { panel.setScore(panel.getScore() + 1); }
        else {
            if (panel.getScore() > panel.getHighScore()) {
                try {
                    FileWriter fileWriter = new FileWriter(Config.recordFile);
                    fileWriter.write(Integer.toString(panel.getScore()));
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        panel.getGameBackground().update();
        panel.getPlayer().update();
//        for (int i = 0; i < panel.getEnemies().size(); i++) {
//            for (int j = 0; j < panel.getEnemies().size(); j++) {
//                if (i != j) {
//                    if (panel.getEnemies().get(i).getRect().intersects(panel.getEnemies().get(j).getRect())) {
//                        setVecCollision(enemies.get(i), enemies.get(j));
//                    }
//                }
//            }
//        }
        if (!panel.getEnemies().isEmpty()) {
            for (Enemy enemy : panel.getEnemies()) {
                enemy.update();
                if (enemy.getRect().intersects(panel.getPlayer().getRect())) {
                    panel.getPlayer().getHit();
                    enemy.getHit();
                }
                if (!panel.getPlayer().getBullets().isEmpty()) {
                    for (int i = 0; i < panel.getPlayer().getBullets().size(); i++) {
                        if (enemy.getRect().intersects(panel.getPlayer().getBullets().get(i).getRect())) {
                            enemy.getHit();
                            panel.getPlayer().getBullets().get(i).setDestroyed(true);
                            panel.setScore(panel.getScore() + 50);;
                        }
                    }
                }
            }
            int bulletIndex = 0;
            while (bulletIndex < panel.getPlayer().getBullets().size()) {
                Bullet bullet = panel.getPlayer().getBullets().get(bulletIndex);
                if (bullet.isDestroyed()) {
                    panel.getPlayer().getBullets().remove(bulletIndex);
                    continue;
                }
                bulletIndex++;
            }
            synchronized (this) {
                int enemyIndex = 0;
                while (enemyIndex < panel.getEnemies().size()) {
                    Enemy enemy = panel.getEnemies().get(enemyIndex);
                    if (enemy.isDestroyed()) {
                        panel.getEnemies().remove(enemyIndex);
                        continue;
                    }
                    enemyIndex++;
                }
                for (int i = 0; i < panel.getEnemies().size(); i++) {
                    if (panel.getEnemies().get(i).getX() < -200) {
                        panel.getEnemies().remove(panel.getEnemies().get(i));
                    } else if (panel.getEnemies().get(i).getX() > Config.windowWidth + 200) {
                        panel.getEnemies().remove(panel.getEnemies().get(i));
                    } else if (panel.getEnemies().get(i).getY() < -200) {
                        panel.getEnemies().remove(panel.getEnemies().get(i));
                    } else if (panel.getEnemies().get(i).getY() > Config.windowHeight + 200) {
                        panel.getEnemies().remove(panel.getEnemies().get(i));
                    }
                }
            }
        }
        panel.getGui().update();
        if (panel.getEnemySpawnInterval().canDo()) {
            int[] randomPosition = Utils.randomSpawnLocation();
            boolean doesSpawnCollide = false;
            while (!doesSpawnCollide) {
                for (Enemy enemy : panel.getEnemies()) {
                    if (enemy.getRect().intersects(new Rectangle(randomPosition[0], randomPosition[1], Config.tileComputed, Config.tileComputed))) {
                        doesSpawnCollide = true;
                    }
                }
                if (!doesSpawnCollide) {
                    panel.getEnemies().add(new Enemy(panel, randomPosition[0], randomPosition[1], Utils.randomDoubleBetween(-Config.maxEnemyVelocity, Config.maxEnemyVelocity), Utils.randomDoubleBetween(-Config.maxEnemyVelocity, Config.maxEnemyVelocity)));
                }
            }
            // enemies.add(new Enemy(this, randomPosition[0], randomPosition[1], Utils.randomDoubleBetween(-Config.maxEnemyVelocity,Config.maxEnemyVelocity), Utils.randomDoubleBetween(-Config.maxEnemyVelocity,Config.maxEnemyVelocity)));
            panel.getEnemySpawnInterval().did();
        }

        if (panel.getPowerupSpawnInterval().canDo()) {
            panel.setPowerup(new Powerup(panel, panel.getPlayer(), Utils.randomNumberBetween(0, Config.windowWidth - Config.tileComputed), Utils.randomNumberBetween(0, Config.windowHeight - Config.tileComputed), Utils.randomNumberBetween(0,5)));
            panel.getPowerupSpawnInterval().did();
            panel.getPlayer().setBulletIntervalInterval(Config.bulletInterval);
        }
        if (panel.getPlayer().getRect().intersects(panel.getPowerup().getRect())) {
            panel.getPowerup().pickUp();
        }
        GUITextEntity[] textEntities;
        if (Config.debugMode) {
            textEntities = new GUITextEntity[]{
                    new GUITextEntity("SCORE:" + panel.getScore(), 1, 0, 0),
                    new GUITextEntity("HP:" + panel.getPlayer().getPLAYER_HEALTH(), 2, 0, Config.tileComputed * 2),
                    new GUITextEntity("SP:" + panel.getPlayer().getPLAYER_SHIELD(), 2, 0, Config.tileComputed * 3),
                    new GUITextEntity("X():" + panel.getPlayer().getX(), 2, 0, Config.tileComputed * 4),
                    new GUITextEntity("Y():" + panel.getPlayer().getY(), 2, 0, Config.tileComputed * 5),
                    new GUITextEntity("XVEL():" + panel.getPlayer().getxVel(), 2, 0, Config.tileComputed * 6),
                    new GUITextEntity("YVEL():" + panel.getPlayer().getyVel(), 2, 0, Config.tileComputed * 7),
            };
        } else {
            if (panel.getScore() > panel.getHighScore()) {
                textEntities = new GUITextEntity[]{new GUITextEntity("SCORE:" + panel.getScore() + "k", 1, 0, 0)};
            } else {
                textEntities = new GUITextEntity[]{new GUITextEntity("SCORE:" + panel.getScore(), 1, 0, 0)};
            }
        }
        panel.getGui().setTextEntities(textEntities);
    }

    private void setVecCollision(Enemy enemy, Enemy enemy1) {
        double newXVec = enemy1.getxVel() + enemy.getxVel();
        double newYVec = enemy1.getyVel() + enemy.getyVel();

        double size = Math.sqrt(Math.pow(newXVec, 2) + Math.pow(newYVec, 2));
        enemy.setxVel(newXVec / size * Config.maxEnemyVelocity);
        enemy.setyVel(newYVec / size * Config.maxEnemyVelocity);
    }

    public void draw(Graphics2D graphics2D) {
        panel.getGameBackground().draw(graphics2D);
        panel.getPowerup().draw(graphics2D);
        panel.getPlayer().draw(graphics2D);
        if (!panel.getEnemies().isEmpty()) {
            synchronized (this) {
                for (Enemy value : panel.getEnemies()) {
                    value.draw(graphics2D);
                }
            }
        }
        panel.getGui().draw(graphics2D);

    }
}
