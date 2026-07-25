package mini_redis.Src.Server;

import java.net.ServerSocket;
import java.net.Socket;

public class RedisServer {

    private static final int PORT = 7379;

    private final RedisDatabase database;

    public RedisServer() {
        database = new RedisDatabase();
    }


    public void start() {

        try(ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Redis server started on port " + PORT);

            while(true) {

                Socket client = serverSocket.accept();

                System.out.println("Client connected");

                ClientHandler handler =
                        new ClientHandler(client, database);

                handler.handle();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        RedisServer server = new RedisServer();

        server.start();
    }
}
