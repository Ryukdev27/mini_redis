package mini_redis.Src.Server;

import java.io.IOException;
import java.util.List;

public class SaveCommand implements Command {
    private final PersistenceManager persistenceManager;
    public SaveCommand(PersistenceManager persistenceManager){
        this.persistenceManager = persistenceManager;
    }
    @Override
    public String execute(List<String> arguments, RedisDatabase database, ClientSession session){
        if(arguments.size() != 0){
            return RespEncoder.error("wrong number of arguments for 'save' command");
        }
        try{
            persistenceManager.save(database);
            return RespEncoder.simpleString("OK");

        }catch(IOException e){
            return RespEncoder.error("failed to save database");
        }
    }
}
