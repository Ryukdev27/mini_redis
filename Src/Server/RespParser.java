package mini_redis.Src.Server;

import java.io.*;
import java.util.*;

public class RespParser {
    private final BufferedReader reader;
    public RespParser(BufferedReader reader) {
        this.reader = reader;
    }
    public List<String> readCommand() throws IOException {
        String line = reader.readLine();
        if(line == null)
            return null;
        // RESP array starts with *
        if(line.charAt(0) != '*') {
            throw new IOException("Invalid RESP command");
        }
        int numberOfArguments =Integer.parseInt(line.substring(1));
        List<String> command =new ArrayList<>();
        for(int i = 0; i < numberOfArguments; i++) {
            String lengthLine =reader.readLine();
            if(lengthLine.charAt(0) != '$') {
                throw new IOException("Expected bulk string");
            }
            // length is not needed yet
            int length =
                    Integer.parseInt(lengthLine.substring(1));
            String argument =reader.readLine();
            command.add(argument);
        }
        return command;
    }
}
