import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class serveur_udp {
    private DatagramSocket serverSocket;
    private int port_service;
    private String request = null, response = null;
    private DatagramPacket serverPacket;
    private int taille = 2024, port_client;
    private InetAddress ip_Client;

    public serveur_udp(int port_service) throws SocketException {
        this.port_service = port_service;
        serverSocket = new DatagramSocket(this.port_service);
    }
}
