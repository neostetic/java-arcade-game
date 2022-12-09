package cz.polacek.game.view.entity.player;

import cz.polacek.game.view.WindowError;

public class BulletInterval implements Runnable {

    int interval = 1000;

    public void run() {
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            new WindowError(e.toString());
        }
    }
}
