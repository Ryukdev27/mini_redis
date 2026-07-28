package mini_redis.Src.Server;

import java.io.*;
import java.util.*;

public class FilePersistenceManager implements PersistenceManager {
    private final String path;
    public FilePersistenceManager(String path){
        this.path = path;
    }
    @Override
    public void save(RedisDatabase database) throws IOException {
        Map<String, RedisEntry> entries = database.getEntries();
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for(Map.Entry<String, RedisEntry> item : entries.entrySet()){
            String key = item.getKey();
            RedisEntry entry = item.getValue();
            if(entry.isExpired())
                continue;
            String type = entry.getType().name();
            String value = serializeValue(entry);
            String expiration =entry.getExpirationTime() == null? "null": entry.getExpirationTime().toString();

            writer.write(key + "|" +type + "|" +value + "|" +expiration);
            writer.newLine();
        }
        writer.close();
    }
    private String serializeValue(RedisEntry entry){
        switch(entry.getType()){
            case STRING:
                return entry.getString();
            case LIST:
                return String.join(",",entry.getList());
            default:throw new IllegalStateException("Unsupported type");
        }
    }
    @Override
    public void load(RedisDatabase database, CommandProcessor processor) throws IOException {
        File file = new File(path);
        if(!file.exists())
            return;
        BufferedReader reader =new BufferedReader(new FileReader(file));
        String line;
        while((line = reader.readLine()) != null){
            String[] parts = line.split("\\|");
            String key = parts[0];
            RedisType type =RedisType.valueOf(parts[1]);
            String value = parts[2];
            Long expiration = null;
            if(!parts[3].equals("null")){
                expiration =Long.parseLong(parts[3]);
            }
            if(expiration != null &&System.currentTimeMillis() > expiration){
                continue;
            }
            RedisEntry entry =deserializeEntry(type,value,expiration);
            database.putEntry(key, entry);
        }
        reader.close();
    }

    private RedisEntry deserializeEntry(RedisType type,String value,Long expiration){
        switch(type){
            case STRING:
                return new RedisEntry(RedisType.STRING,value,expiration);
            case LIST:
                LinkedList<String> list =new LinkedList<>(Arrays.asList(value.split(",")));
                return new RedisEntry(RedisType.LIST,list,expiration);
            default:throw new IllegalStateException("Unsupported type");
        }
        
    }
    public void append(String command) throws IOException{
        }
}
