/*package mini_redis.Src.Server;

import java.io.*;
import java.net.Socket;
import java.util.List;


public class ClientHandler {
    private final Socket client;
    private final RedisDatabase database;
    private final CommandProcessor processor;
    public ClientHandler(Socket client, RedisDatabase database) {
        this.client = client;
        this.database = database;
        this.processor = new CommandProcessor();
    }
    public void handle() {
    System.out.println("Handler started");
    try(
        BufferedReader reader =new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter writer =new PrintWriter(client.getOutputStream(),true)
    ){RespParser parser = new RespParser(reader);
    while(true) {
    List<String> command =parser.readCommand();
    if(command == null) {
        System.out.println("Client closed connection");
        break;
    }
    System.out.println("Received: " + command);
    String response =processor.process(command, database);
    writer.print(response);
    writer.flush();
}
    } catch(Exception e) {
        System.out.println("ERROR IN HANDLER");
        e.printStackTrace();

    }
    finally {
        try {
            client.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("Handler ended");
    }
}
}*/