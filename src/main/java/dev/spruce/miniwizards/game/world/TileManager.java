package dev.spruce.miniwizards.game.world;

import dev.spruce.miniwizards.game.assets.Textures;

public class TileManager {

    private static TileManager instance;

    public final Tile GRASS = new Tile(0, Tile.SIZE, false, Textures.GRASS);

    public static TileManager getInstance() {
        if (instance == null) {
            instance = new TileManager();
        }
        return instance;
    }
}
