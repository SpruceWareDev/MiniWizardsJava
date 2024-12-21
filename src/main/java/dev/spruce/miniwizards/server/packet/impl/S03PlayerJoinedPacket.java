package dev.spruce.miniwizards.server.packet.impl;

import dev.spruce.miniwizards.server.packet.Packet;

public class S03PlayerJoinedPacket extends Packet {

    private String username;

    public S03PlayerJoinedPacket(String username) {
        super(SERVER_PLAYER_JOINED_ID);
        this.username = username;
    }

    @Override
    public byte[] getData() {
        return username.getBytes();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
