package dev.spruce.miniwizards.server.packet.impl;

import dev.spruce.miniwizards.server.packet.Packet;

import java.nio.charset.StandardCharsets;

public class C02LogoutPacket extends Packet {

    private String username;

    public C02LogoutPacket(String username) {
        super(CLIENT_LOGOUT_ID);
        this.username = username;
    }

    @Override
    public byte[] getData() {
        return username.getBytes(StandardCharsets.UTF_8);
    }
}
