package mini_redis.Src;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", 7379)) {
            System.out.println("Connected to server");
           BufferedReader keyboard =new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverReader =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer =new PrintWriter(socket.getOutputStream());
            RespReader respReader = new RespReader(serverReader);
            while(true) {
                System.out.print("> ");
                String input = keyboard.readLine();
                if(input == null) {
                    break;
                }
                if(input.equalsIgnoreCase("EXIT")) {
                    writer.print(RespCommand.command("EXIT"));
                    writer.flush();
                    System.out.println(respReader.readResponse());
                    break;
                }
                // Convert user input:
                // SET name chad
                // into RESP:
                // *3\r\n$3\r\nSET\r\n$4\r\nname\r\n$4\r\nchad\r\n
                String[] parts = input.split("\\s+");
                writer.print(RespCommand.command(parts));
                writer.flush();
                String response =respReader.readResponse();
                System.out.println(response);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }
}
