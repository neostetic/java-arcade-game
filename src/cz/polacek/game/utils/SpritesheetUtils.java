package cz.polacek.game.utils;

import cz.polacek.game.config.Config;
import cz.polacek.game.view.WindowError;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class SpritesheetUtils {
    public BufferedImage[][] spritesheetToSprites(String url) {
        try {
            BufferedImage spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(url)));
            BufferedImage[][] sprites = new BufferedImage[spritesheet.getWidth()/16][spritesheet.getHeight()/16];
            for (int i = 0; i < sprites.length; i++) {
                for (int j = 0; j < sprites[0].length; j++) {
                    sprites[i][j] = spritesheet.getSubimage(
                            i * Config.tile,
                            j * Config.tile,
                            Config.tile,
                            Config.tile
                    );
                }
            }
            return sprites;
        } catch (IOException e) {
            new WindowError(e.toString());
        }
        return null;
    }
}
