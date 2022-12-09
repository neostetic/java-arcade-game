package cz.polacek.game.view;

import javax.swing.*;
import java.awt.*;

public class Window {

    protected JFrame frame = new JFrame();
    protected Panel panel = new Panel();
    protected Settings settings = new Settings(frame, panel);

    public Window() {
        settings.settings();
        panel.startGameThread();
    }

}
