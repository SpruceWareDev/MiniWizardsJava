package dev.spruce.miniwizards.server.packet;

public abstract class Packet {

    public static final byte CLIENT_LOGIN_ID = 0x01;
    public static final byte CLIENT_LOGOUT_ID = 0x02;
    public static final byte SERVER_PLAYER_JOINED_ID = 0x03;
    public static final byte SERVER_PLAYER_LEFT_ID = 0x04;
    public static final byte SERVER_CONNECTED_PLAYERS = 0x05;
    public static final byte CLIENT_PLAYER_MOVED_ID = 0x06;
    public static final byte SERVER_PLAYER_MOVED_ID = 0x07;

    public byte id;

    public Packet(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }

    public abstract byte[] getData();

    public static byte[] getData(Packet packet) {
        byte[] data = new byte[packet.getData().length + 1];
        data[0] = packet.getId();
        System.arraycopy(packet.getData(), 0, data, 1, packet.getData().length);
        return data;
    }
}
