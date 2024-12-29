package dev.spruce.miniwizards.game.state.impl;

import dev.spruce.miniwizards.game.Client;
import dev.spruce.miniwizards.game.Game;
import dev.spruce.miniwizards.game.entity.player.PlayerMP;
import dev.spruce.miniwizards.game.entity.player.PlayerSP;
import dev.spruce.miniwizards.game.graphics.Camera;
import dev.spruce.miniwizards.game.input.InputManager;
import dev.spruce.miniwizards.game.state.State;
import dev.spruce.miniwizards.game.world.Map;
import dev.spruce.miniwizards.server.Server;
import dev.spruce.miniwizards.server.packet.Packet;
import dev.spruce.miniwizards.server.packet.impl.C01LoginPacket;
import dev.spruce.miniwizards.server.packet.impl.C02LogoutPacket;
import dev.spruce.miniwizards.server.packet.impl.C06PlayerMovePacket;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;

public class GameState extends State {

    private Map map;
    private PlayerSP player;

    private static Camera camera;

    private Server server;
    private Client client;

    private boolean hosting;

    public GameState(boolean hosting) {
        this.hosting = hosting;
    }

    @Override
    public void init() {
        if (hosting) {
            server = new Server();
            server.init(25565);
            server.start();
        }

        player = new PlayerSP("joe", 0, 0, 32, 32, 100);

        camera = new Camera(0, 0);
        map = new Map(128, 128);

        client = new Client(player);
        client.init("localhost", 25565);
        client.start();

        client.sendPacket(Packet.getData(new C01LoginPacket(player.getUsername())));
    }

    @Override
    public void update(double delta) {
        player.update(delta);
        if (player.getDx() != 0 || player.getDy() != 0) {
            client.sendPacket(Packet.getData(new C06PlayerMovePacket(player.getUsername(), (int) player.getX(), (int) player.getY())));
        }

        for (PlayerMP player : client.getPlayers()) {
            player.update(delta);
        }

        if (InputManager.getInstance().isKeyDown(KeyEvent.VK_ESCAPE)) {
            Game.getStateManager().setState(new MainMenuState());
        }
    }

    public void disconnect() {
        client.disconnect();
        if (hosting) {
            server.stopServer();
        }
    }

    @Override
    public void render(Graphics graphics) {
        map.render(graphics, camera);
        player.render(graphics);

        for (PlayerMP player : client.getPlayers()) {
            player.render(graphics);
        }
    }

    @Override
    public void dispose() {
        disconnect();
    }

    public static Camera getCamera() {
        return camera;
    }
}
