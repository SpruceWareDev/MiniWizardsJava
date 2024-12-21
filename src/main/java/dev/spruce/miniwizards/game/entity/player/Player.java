package dev.spruce.miniwizards.game.entity.player;

import dev.spruce.miniwizards.game.entity.Entity;

import java.awt.*;

public abstract class Player extends Entity {

    public static final float WALK_SPEED = 5f;
    private String username;

    public Player(String username, float x, float y, float width, float height, int health) {
        super(x, y, width, height, health);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
