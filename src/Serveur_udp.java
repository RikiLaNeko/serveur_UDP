import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Serveur_udp {
    private DatagramSocket serverSocket;
    private int port_service;
    private String request = null, response = null;
    private DatagramPacket serverPacket;
    private int taille = 2024, port_client;
    private InetAddress ip_Client;

    public Serveur_udp(int port_service) throws SocketException, IOException {
        this.port_service = port_service;
        serverSocket = new DatagramSocket(this.port_service);
    }

    public int getPort_service() {
        return port_service;
    }

    public void setPort_service(int port_service) {
        this.port_service = port_service;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getPort_client() {
        return port_client;
    }

    public void setPort_client(int port_client) {
        this.port_client = port_client;
    }

    public InetAddress getIp_Client() {
        return ip_Client;
    }

    public void setIp_Client(InetAddress ip_Client) {
        this.ip_Client = ip_Client;
    }

    public void service_udp () throws IOException {
        serverPacket = new DatagramPacket(new byte[taille], taille);
        serverSocket.receive(serverPacket);
        request = new String(serverPacket.getData(), 0, serverPacket.getLength());
        ip_Client = serverPacket.getAddress();
        port_client = serverPacket.getPort();
        //Traitement de la requette
        response = new StringBuilder(request).toString();
        response = response.trim();
        if (response.contains("echo")){
            response = response.replaceFirst("echo", "");
        }else {
            switch (response) {
                case "HELLO", "hello" -> response = "Welcome to the server";
                case "TIME", "time" -> {
                    response = "Nous somme le :" + java.time.LocalDateTime.now().getDayOfMonth() + " " + java.time.LocalDateTime.now().getMonth() + " " + java.time.LocalDateTime.now().getYear() + " est il es:" + java.time.LocalDateTime.now().getHour() + ":" + java.time.LocalDateTime.now().getMinute() + ":" + java.time.LocalDateTime.now().getSecond();
                }
                case "ME", "me", "WHOAMI", "whoami" -> {
                    response = ip_Client + ":" + port_client;
                }
                case "YOU", "you", "WHOAREYOU", "whoareyou" -> {
                    response = InetAddress.getLocalHost() + ":" + serverSocket.getLocalPort();
                }
                case "END", "end" -> {
                    response = "goodbye";
                    serverSocket.close();
                }
                //case "ECHO", "echo" -> { response = response.replaceFirst("echo", "");}
                default -> response = "Command : HELLO,TIME,ECHO ...a sentance, YOU || WHOAREYOU, ME||WHOAMI,END";
            }
        }


        //Envoie de la reponse
        serverPacket = new DatagramPacket(response.getBytes(), response.length(), ip_Client, port_client);
        serverSocket.send(serverPacket);
    }
}
