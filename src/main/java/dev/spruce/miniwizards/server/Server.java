package dev.spruce.miniwizards.server;

import dev.spruce.miniwizards.game.entity.player.PlayerMP;
import dev.spruce.miniwizards.server.packet.Packet;
import dev.spruce.miniwizards.server.packet.impl.S03PlayerJoinedPacket;
import dev.spruce.miniwizards.server.packet.impl.S04PlayerLeftPacket;
import dev.spruce.miniwizards.server.packet.impl.S05ConnectedPlayersPacket;
import dev.spruce.miniwizards.server.packet.impl.S07PlayerMovedPacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server extends Thread {

    private DatagramSocket socket;
    private CopyOnWriteArrayList<PlayerMP> players;
    private boolean running;

    public void init(int port) {
        // Create a new DatagramSocket with the specified port
        try {
            socket = new DatagramSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Initialize the player list
        players = new CopyOnWriteArrayList<>();
    }

    @Override
    public void run() {
        System.out.println("Server starting on port " + socket.getLocalPort() + "...");
        running = true;
        while (running) {
            // Create a new byte array to store the data
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            // Receive the packet and store it into the DatagramPacket
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            // Separate the packet data into the packetId and packetData
            byte packetId = packet.getData()[0];
            System.out.println("[Client@" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "] Packet ID: " + packetId);
            byte[] packetData = new byte[packet.getData().length - 1];
            System.arraycopy(packet.getData(), 1, packetData, 0, packetData.length);
            // Handle the packet
            handlePacket(packetId, packetData, packet.getAddress(), packet.getPort());
        }
    }

    private void handlePacket(byte packetId, byte[] packetData, InetAddress address, int port) {
        switch (packetId) {
            case 0x01:
                // Login packet
                String username = new String(packetData).trim();
                System.out.println("[Client@" + address.getHostAddress() + ":" + port + "] Username: " + username);
                PlayerMP player = new PlayerMP(username, 0, 0, 32, 32, 100, address, port);

                // Get currently connected players
                if (!players.isEmpty()) {
                    String[] connectedPlayers = new String[players.size()];
                    for (int i = 0; i < players.size(); i++) {
                        connectedPlayers[i] = players.get(i).getUsername();
                    }
                    sendPacket(Packet.getData(new S05ConnectedPlayersPacket(connectedPlayers)), address, port);
                }
                // Send to all other players
                sendToAll(Packet.getData(new S03PlayerJoinedPacket(username)));
                players.add(player);
                break;
            case 0x02:
                // Logout packet
                String disconnectingUsername = new String(packetData).trim();
                System.out.println("[Client@" + address.getHostAddress() + ":" + port + "] Disconnecting : " + disconnectingUsername);
                getPlayer(disconnectingUsername).ifPresent((playerMP) -> {
                    players.remove(playerMP);
                    sendToAll(Packet.getData(new S04PlayerLeftPacket(disconnectingUsername)));
                });
                break;
            case 0x06:
                // Client move packet
                String[] playerData = new String(packetData).trim().split(",");
                getPlayer(playerData[0]).ifPresent((playerMP) -> {
                    if (playerMP.getX() == Integer.parseInt(playerData[1]) && playerMP.getY() == Integer.parseInt(playerData[2])) {
                        return;
                    }
                    playerMP.setX(Integer.parseInt(playerData[1]));
                    playerMP.setY(Integer.parseInt(playerData[2]));
                    sendToAll(Packet.getData(
                            new S07PlayerMovedPacket(
                                    playerData[0],
                                    Integer.parseInt(playerData[1]),
                                    Integer.parseInt(playerData[2])
                            )
                    ));
                });
                break;
            default:
                break;
        }
    }

    public void stopServer() {
        System.out.println("Stopping server...");
        running = false;
    }

    public void sendToAll(byte[] data) {
        for (PlayerMP player : players) {
            sendPacket(data, player.getAddress(), player.getPort());
        }
    }

    public void sendPacket(byte[] data, InetAddress address, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<PlayerMP> getPlayer(String username) {
        for (PlayerMP player : players) {
            if (player.getUsername().equals(username)) {
                return Optional.ofNullable(player);
            }
        }
        return Optional.empty();
    }
}
