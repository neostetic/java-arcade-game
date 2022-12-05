package cz.polacek.game.utils;

import cz.polacek.game.config.Config;

import javax.swing.*;

public class BufferImage extends JLabel {

    public String imgSrc;
    public Config config = new Config();

    public BufferImage(String imgSrc) {
        this.imgSrc = config.srcOut + imgSrc;
    }

    public JLabel toLabel() {
        return new JLabel(new ImageIcon(imgSrc));
    }
}
