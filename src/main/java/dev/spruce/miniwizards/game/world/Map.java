package dev.spruce.miniwizards.game.world;

import dev.spruce.miniwizards.game.graphics.Camera;
import dev.spruce.miniwizards.game.graphics.Window;

import java.awt.*;

public class Map {

    private Tile[][] tiles;
    private int width, height;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;

        tiles = new Tile[width][height];
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                tiles[x][y] = TileManager.getInstance().GRASS;
            }
        }
    }

    public void render(Graphics graphics, Camera camera) {
        Graphics2D g2d = (Graphics2D) graphics;

        int screenWidth = Window.getInstance().getWindow().getWidth();
        int screenHeight = Window.getInstance().getWindow().getHeight();

        int startX = Math.max(0, camera.getX() / Tile.SIZE);
        int startY = Math.max(0, camera.getY() / Tile.SIZE);
        int endX = Math.min(width, (camera.getX() + screenWidth) / Tile.SIZE + 1);
        int endY = Math.min(height, (camera.getY() + screenHeight) / Tile.SIZE + 1);

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                Tile tile = tiles[x][y];
                g2d.drawImage(tile.getTexture(),
                        (x * tile.getSize()) - camera.getX(),
                        (y * tile.getSize()) - camera.getY(),
                        tile.getSize(), tile.getSize(), null
                );
            }
        }
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }
}
