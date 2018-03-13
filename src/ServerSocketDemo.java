import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketDemo {

    private static final String MSG = "Hello";
    public static void main(String[] args) throws IOException {
        int port = 9999;
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket serverSocket = serverSocketChannel.socket();

        serverSocket.bind(new InetSocketAddress(port));

        serverSocketChannel.configureBlocking(false);
        System.out.println("Server is on");

        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();
        }


    }
}
