package dev.spruce.miniwizards.server.packet.impl;

import dev.spruce.miniwizards.server.packet.Packet;

public class S05ConnectedPlayersPacket extends Packet {

    private String[] players;

    public S05ConnectedPlayersPacket(String[] players) {
        super(SERVER_CONNECTED_PLAYERS);
        this.players = players;
    }

    @Override
    public byte[] getData() {
        return String.join(",", players).getBytes();
    }

    public String[] getPlayers() {
        return players;
    }

}
