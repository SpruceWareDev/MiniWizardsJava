package dev.spruce.miniwizards.server.packet.impl;

import dev.spruce.miniwizards.server.packet.Packet;

public class C01LoginPacket extends Packet {

    private String username;

    public C01LoginPacket(String username) {
        super(CLIENT_LOGIN_ID);
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
