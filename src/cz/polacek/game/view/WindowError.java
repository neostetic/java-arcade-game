package cz.polacek.game.view;

import javax.swing.*;

public class WindowError {
    public String error;
    public String title;

    public WindowError() {
        JOptionPane.showMessageDialog(null, "!Error","Something went wrong!", JOptionPane.ERROR_MESSAGE);
    }

    public WindowError(String error) {
        this.error = error;
        JOptionPane.showMessageDialog(null, error,"Something went wrong!", JOptionPane.ERROR_MESSAGE);
    }

    public WindowError(String error, String title) {
        this.error = error;
        this.title = title;
        JOptionPane.showMessageDialog(null, error,title, JOptionPane.ERROR_MESSAGE);
    }

}
