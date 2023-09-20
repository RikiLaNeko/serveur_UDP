import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        modeServeurUDP();
    }

    private static void modeServeurUDP() {
        try {
            Serveur_udp serveur_udp = new Serveur_udp(5000);
            new Thread(() -> {
                while (true) {
                    try {
                        serveur_udp.service_udp();
                        System.out.println("""
                                La requette du serveur est : %s \"%s\" a l'adresse : %s:%s
                                """.formatted(serveur_udp.getRequest(), serveur_udp.getResponse(), serveur_udp.getIp_Client(), serveur_udp.getPort_client()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}