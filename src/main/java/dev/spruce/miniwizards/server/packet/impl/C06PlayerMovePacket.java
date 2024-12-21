package dev.spruce.miniwizards.server.packet.impl;

import dev.spruce.miniwizards.server.packet.Packet;

public class C06PlayerMovePacket extends Packet {

    private String username;
    private int x, y;

    public C06PlayerMovePacket(String username, int x, int y) {
        super(CLIENT_PLAYER_MOVED_ID);
        this.username = username;
        this.x = x;
        this.y = y;
    }

    @Override
    public byte[] getData() {
        return (username + "," + x + "," + y).getBytes();
    }

    public String getUsername() {
        return username;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
