package cz.polacek.game.config;

public  class Config {

    // Window/Screen Settings
    static public String windowTitle = "Game";
    static public boolean windowResizable = true;
    static public boolean windowVisible = true;
    static public int scale = 2;
    static public int tile = 16;
    static public double playerSpeed = (tile / 8) * scale;
    static public double gravity = 1.02;
    static public int tileComputed = tile * scale;
    static public int windowWidth = (tileComputed * 20);
    static public int windowHeight = (tileComputed * 15);

    // Utils Settings
    public String srcOut = "src/cz/polacek/game/assets/"; // I just wanted to have smaller Image Sources

}
