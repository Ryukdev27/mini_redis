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
        if(line.charAt(0) != '*') {
            throw new IOException("Expected RESP array");
        }
        int arguments =Integer.parseInt(line.substring(1));
        List<String> command =new ArrayList<>();
        for(int i = 0; i < arguments; i++) {
            String lengthLine =reader.readLine();
            if(lengthLine.charAt(0) != '$') {
                throw new IOException("Expected bulk string");
            }
            int length =Integer.parseInt(lengthLine.substring(1));
            // Null bulk string
            if(length == -1) {
                command.add(null);
                continue;
            }
            String value =reader.readLine();
            if(value.length() != length) {
                throw new IOException("Invalid bulk string length");
            }
            command.add(value);
        }
        return command;
    }
}
