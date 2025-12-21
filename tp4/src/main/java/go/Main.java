package go;

import go.client.GoClient;
import go.logic.Protocol;
import go.server.GoServer;

import java.io.IOException;
import java.net.ServerSocket;

// Klasa odpowiedzialna za uruchomienie gry - zarowno programu serwera oraz graczy

public class Main {
    public static void main(String[] args) {
        if (isPortAvailable()) { // sprawdzamy, czy serwer zostal juz uruchomiony i zajal port
            System.out.println("Nie wykryto serwera. Uruchamianie w trybie SERWERA");
            GoServer.main(args);
        } else { // jesli tak, uruchamiany jest program klienta
            System.out.println("Serwer już działa. Uruchamianie w trybie KLIENTA");
            GoClient.main(args);
        }
    }

    private static boolean isPortAvailable() {
        try (ServerSocket serverSocket = new ServerSocket(Protocol.Port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
