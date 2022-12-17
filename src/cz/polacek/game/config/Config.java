package cz.polacek.game.config;

import java.io.File;

public  class Config {

    static public boolean debugMode = false;
    static public String windowTitle = "Game";
    static public boolean windowResizable = false;
    static public boolean windowVisible = true;
    static public int scale = 4;
    static public int tile = 16;
    static public double playerSpeed = (tile / 10) * scale;
    static public double playerSpeedSlowdown = 16;
    static public double bulletSpeed = (tile / 5) * scale;
    static public double gravity = 1.01; // 1.01
    static public int tileComputed = tile * scale;
    static public int windowWidth = (tileComputed * 20); // (tileComputed * 20)
    static public int windowHeight = (tileComputed * 15); // (tileComputed * 15)
    static public int enemySpawnInterval = 40;
    static public int powerupSpawnInterval = 10000;
    static public int maxEnemyVelocity = 2;
    static public int bulletInterval = 500;
    static public int bulletFasterInterval = 100;
    static public int prefferedFPS = 60;
    static public int maxPlayerHealth = 3;
    static public int maxPlayerShield = 2;
    static public int moneyPoints = 200;
    public static String recordFile = "record.txt";
}
