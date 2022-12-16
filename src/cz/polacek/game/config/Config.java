package cz.polacek.game.config;

public  class Config {

    static public String windowTitle = "Game";
    static public boolean windowResizable = false;
    static public boolean windowVisible = true;
    static public int scale = 3;
    static public int tile = 16;
    static public double playerSpeed = (tile / 10) * scale;
    static public double playerSpeedSlowdown = 16;
    static public double bulletSpeed = (tile / 5) * scale;
    static public double gravity = 1.01; // 1.01
    static public int tileComputed = tile * scale;
    static public int windowWidth = (tileComputed * 20);
    static public int windowHeight = (tileComputed * 15);
    static public int enemySpawnInterval = 100;
    static public int maxEnemyVelocity = 2;
    static public int bulletInterval = 100;
    static public int prefferedFPS = 60;
}
