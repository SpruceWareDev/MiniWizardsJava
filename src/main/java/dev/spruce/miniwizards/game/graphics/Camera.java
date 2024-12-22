package dev.spruce.miniwizards.game.graphics;

public class Camera {

    private int x, y;

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void centerOn(int x, int y) {
        this.x = x - Window.getInstance().getWindow().getWidth() / 2;
        this.y = y - Window.getInstance().getWindow().getHeight() / 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
