package mini_redis.Src.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class AofPersistenceManager implements PersistenceManager{
    private final String path;
    AofPersistenceManager(String path){
        this.path=path;
    }
    public void append(String command) throws IOException{
        try(BufferedWriter writer= new BufferedWriter(new FileWriter(path, true))){
            writer.write(command);
            writer.newLine();
        }
    }
    @Override
    public void save(RedisDatabase database) throws IOException{

    }
    @Override
    public void load(RedisDatabase database,CommandProcessor processor) throws IOException {
    File file = new File(path);
    if(!file.exists())
        return;
    try(BufferedReader reader =new BufferedReader(new FileReader(file))){
        String line;
        while((line = reader.readLine()) != null){
            List<String> command =Arrays.asList(line.split(" "));
            processor.replay(command, database);
        }
    }
}

}