package cz.polacek.game.view.entity.player;

import cz.polacek.game.view.WindowError;

public class BulletInterval extends Thread {

    private int interval = 500;
    private volatile boolean canShot;
    private volatile boolean running;

    public BulletInterval() {
        this.running = true;
        this.canShot = true;
    }

    public void run() {
        while (running) {
            boolean canShot;
            synchronized (this) {
                canShot = this.canShot;
            }
            if(!canShot) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    new WindowError(e.toString());
                }
                synchronized (this) {
                    this.canShot = true;
                }
            }
        }
    }

    public synchronized void shot() {
        this.canShot = false;
    }

    public synchronized boolean canShot() {
        return canShot;
    }
}
