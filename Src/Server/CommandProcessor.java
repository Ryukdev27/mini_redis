package mini_redis.Src.Server;

import java.util.*;

public class CommandProcessor {
    private final Map<String, Command> commands = new HashMap<>();
    public CommandProcessor() {
        commands.put("PING", new Ping());
        commands.put("GET", new GetCommand());
        commands.put("SET", new SetCommand());
        commands.put("EXISTS", new ExistsCommand());
        commands.put("DELETE", new DeleteCommand());
    }
    public String process(List<String> parts,RedisDatabase database){
        if(parts == null || parts.isEmpty()) {
            return RespEncoder.error("empty command");
        }
        String cmdName =parts.get(0).toUpperCase();
        List<String> arguments =parts.subList(1, parts.size());
        Command cmd =commands.get(cmdName);
        if(cmd == null) {
            return RespEncoder.error("unknown command");
        }
        return cmd.execute(arguments,database);
    }
}
