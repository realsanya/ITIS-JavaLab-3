import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<ClientHandler> clients;

    @SuppressWarnings("InfiniteLoopStatement")
    public Server(int PORT) throws IOException {
        clients = new ArrayList<>();
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            clients.add(new ClientHandler(this, socket));
        }
    }


    public void broadcast(String message) {
        clients.forEach(clientHandler -> clientHandler.sendMessage(message));
    }

}
