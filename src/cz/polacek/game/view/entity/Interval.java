package cz.polacek.game.view.entity;

import cz.polacek.game.view.WindowError;

public class Interval extends Thread {

    private int interval;
    private volatile boolean canDo;
    private volatile boolean running;

    public Interval(int interval) {
        this.interval = interval;
        this.running = true;
        this.canDo = true;
    }

    public void run() {
        while (running) {
            boolean canDo;
            synchronized (this) {
                canDo = this.canDo;
            }
            if(!canDo) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    new WindowError(e.toString());
                }
                synchronized (this) {
                    this.canDo = true;
                }
            }
        }
    }

    public synchronized void did() {
        this.canDo = false;
    }

    public synchronized boolean canDo() {
        return canDo;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
