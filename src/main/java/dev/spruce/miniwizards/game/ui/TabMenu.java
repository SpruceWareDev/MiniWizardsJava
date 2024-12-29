package dev.spruce.miniwizards.game.ui;

import dev.spruce.miniwizards.game.Client;
import dev.spruce.miniwizards.game.assets.Fonts;
import dev.spruce.miniwizards.game.entity.player.PlayerMP;
import dev.spruce.miniwizards.game.graphics.Window;
import dev.spruce.miniwizards.game.graphics.font.FontRenderer;

import java.awt.*;

public class TabMenu {

    private static final Integer WIDTH = 280;
    private static final Integer PLAYER_INFO_HEIGHT = 26;

    public static void render(Graphics graphics, Client client) {
        int screenWidth = Window.getInstance().getWindow().getWidth();
        int screenHeight = Window.getInstance().getWindow().getHeight();

        int backgroundX = (screenWidth / 2) - (WIDTH / 2);

        graphics.setColor(new Color(0, 0, 0, 128));
        graphics.fillRect(backgroundX, 12, WIDTH, 200);

        int i = 0;
        for (PlayerMP playerMP : client.getPlayers()) {
            String username = playerMP.getUsername();
            int health = playerMP.getHealth();

            int y = 18 + (i * (PLAYER_INFO_HEIGHT + 4));

            graphics.setColor(Color.white);
            graphics.fillRect(backgroundX + 6, y, WIDTH - 12, PLAYER_INFO_HEIGHT);

            FontRenderer.drawString(graphics, username, backgroundX + 10, y, false, Color.black, Fonts.ARIAL_18);
            i++;
        }
    }
}
