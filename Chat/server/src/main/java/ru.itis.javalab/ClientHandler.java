import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ClientHandler(Server server, Socket socket) throws IOException {
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            while (true) {
                try {
                    String read = dataInputStream.readUTF();
                    System.out.println("from client: " + read);
                    server.broadcast(read);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMessage(String message){
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
