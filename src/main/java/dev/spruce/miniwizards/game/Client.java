package dev.spruce.miniwizards.game;

import dev.spruce.miniwizards.game.entity.player.PlayerMP;
import dev.spruce.miniwizards.game.entity.player.PlayerSP;
import dev.spruce.miniwizards.server.packet.Packet;
import dev.spruce.miniwizards.server.packet.impl.C02LogoutPacket;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client extends Thread {

    private PlayerSP player;
    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    private boolean running;

    private CopyOnWriteArrayList<PlayerMP> players;

    public Client(PlayerSP player) {
        this.player = player;
    }

    public void init(String ip, int port) {
        this.port = port;
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName(ip);
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
        players = new CopyOnWriteArrayList<>();
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Separate the packet data into the packetId and packetData
            byte packetId = packet.getData()[0];
            System.out.println("[Server@" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "] Packet ID: " + packetId);
            byte[] packetData = new byte[packet.getData().length - 1];
            System.arraycopy(packet.getData(), 1, packetData, 0, packetData.length);
            // Handle the packet
            handlePacket(packetId, packetData);
        }
    }

    private void handlePacket(byte packetId, byte[] packetData) {
        System.out.println("[Server] Handling packet: " + packetId);
        switch (packetId) {
            case 0x03:
                // Player joined packet
                String username = new String(packetData, StandardCharsets.UTF_8).trim();
                System.out.println("[Server] Player joined: " + username);
                players.add(new PlayerMP(username, 0, 0, 32, 32, 100, address, port));
                break;
            case 0x04:
                // Player left packet
                String leftUsername = new String(packetData, StandardCharsets.UTF_8).trim();
                System.out.println("[Server] Player left: " + leftUsername);
                getPlayer(leftUsername).ifPresent(players::remove);
                break;
            case 0x05:
                // Connected players packet
                String[] connectedPlayers = splitData(new String(packetData, StandardCharsets.UTF_8), ",");
                for (String player : connectedPlayers) {
                    System.out.println("[Server] Connected player: " + player);
                    players.add(new PlayerMP(player, 0, 0, 32, 32, 100, address, port));
                }
                break;
            case 0x07:
                // Player moved packet
                String[] moveData = splitData(new String(packetData, StandardCharsets.UTF_8), ",");
                String moveUsername = moveData[0];
                int moveX = Integer.parseInt(moveData[1]);
                int moveY = Integer.parseInt(moveData[2]);
                getPlayer(moveUsername).ifPresent(player -> {
                    player.setX(moveX);
                    player.setY(moveY);
                });
                break;
        }
    }

    public void sendPacket(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        sendPacket(Packet.getData(new C02LogoutPacket(player.getUsername())));
        running = false;
    }

    private String[] splitData(String data, String delimiter) {
        return data.trim().split(delimiter);
    }

    public Optional<PlayerMP> getPlayer(String username) {
        for (PlayerMP player : players) {
            if (player.getUsername().equals(username)) {
                return Optional.ofNullable(player);
            }
        }
        return Optional.empty();
    }

    public CopyOnWriteArrayList<PlayerMP> getPlayers() {
        return players;
    }
}
