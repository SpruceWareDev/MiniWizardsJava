package dev.spruce.miniwizards.game.entity.player;

import dev.spruce.miniwizards.game.assets.Fonts;
import dev.spruce.miniwizards.game.graphics.font.FontRenderer;
import dev.spruce.miniwizards.game.state.impl.GameState;

import java.awt.*;
import java.net.InetAddress;

public class PlayerMP extends Player {

    private InetAddress address;
    private int port;

    public PlayerMP(String username, int x, int y, int width, int height, int health, InetAddress address, int port) {
        super(username, x, y, width, height, health);
        this.address = address;
        this.port = port;
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Graphics graphics) {
        int x = (int) getX() - GameState.getCamera().getX();
        int y = (int) getY() - GameState.getCamera().getY();

        graphics.setColor(Color.red);
        graphics.fillRect(x, y, (int) getWidth(), (int) getHeight());
        FontRenderer.drawStringCentred(graphics, getUsername(), (int) (x + getWidth() / 2), y - 20, Color.white, Fonts.ARIAL_12);
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
