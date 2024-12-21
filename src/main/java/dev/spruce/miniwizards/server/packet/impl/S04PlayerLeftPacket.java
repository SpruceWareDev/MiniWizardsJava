package dev.spruce.miniwizards.server.packet.impl;

import dev.spruce.miniwizards.server.packet.Packet;

public class S04PlayerLeftPacket extends Packet {

        private String username;

        public S04PlayerLeftPacket(String username) {
            super(S04PlayerLeftPacket.SERVER_PLAYER_LEFT_ID);
            this.username = username;
        }

        @Override
        public byte[] getData() {
            return username.getBytes();
        }
}
