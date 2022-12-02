package cz.polacek.game.view;

import cz.polacek.game.config.Config;

import javax.swing.*;

public class Settings {

    private Config config = new Config();
    JFrame frame;
    Panel panel;

    public Settings(JFrame frame, Panel panel) {
        this.frame = frame;
        this.panel = panel;
    }

    public void settings() {
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(config.windowResizable);
        frame.setTitle(config.windowTitle);
        frame.add(panel);
        frame.setSize(config.windowWidth, config.windowHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(config.windowVisible);
        frame.setLayout(null);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            new WindowError(e.toString());
        }


    }
}
