import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public Client(InetAddress address, int port) {
        try {
            Socket socket = new Socket(address, port);
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        read.start();
        write.start();
    }

    Thread read = new Thread(new Runnable() {
        public void run() {
            while (true) {
                try {
                    String message = dataInputStream.readUTF();
                    System.out.println(message);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    });

    Thread write = new Thread(new Runnable() {
        public void run() {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    dataOutputStream.writeUTF(scanner.nextLine());
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    });
}
