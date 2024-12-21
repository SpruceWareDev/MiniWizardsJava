package dev.spruce.miniwizards.game.entity.player;

import dev.spruce.miniwizards.game.input.InputManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerSP extends Player {

    public PlayerSP(String username, float x, float y, float width, float height, int health) {
        super(username, x, y, width, height, health);
    }

    @Override
    public void update(double delta) {
        setDx(0);
        setDy(0);
        if (InputManager.getInstance().isKeyDown(KeyEvent.VK_W)) {
            setDy(-1);
        } else if (InputManager.getInstance().isKeyDown(KeyEvent.VK_S)) {
            setDy(1);
        }
        if (InputManager.getInstance().isKeyDown(KeyEvent.VK_A)) {
            setDx(-1);
        } else if (InputManager.getInstance().isKeyDown(KeyEvent.VK_D)) {
            setDx(1);
        }
        setX((float) (getX() + (getDx() * WALK_SPEED * delta)));
        setY((float) (getY() + (getDy() * WALK_SPEED * delta)));
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.blue);
        graphics.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }
}
