package cz.polacek.game.config;

public  class Config {

    // Window/Screen Settings
    public String windowTitle = "Game";
    public boolean windowResizable = true;
    public boolean windowVisible = true;
    public int scale = 2;
    public int tile = 32;
    public int playerSpeed = 10 * scale;
    public int tileComputed = tile * scale;
    public int windowWidth = (tileComputed * 20);
    public int windowHeight = (tileComputed * 15);

    // Utils Settings
    public String srcOut = "src/cz/polacek/game/assets/"; // I just wanted to have smaller Image Sources

}
