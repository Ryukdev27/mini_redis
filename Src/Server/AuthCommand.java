package mini_redis.Src.Server;

import java.util.List;

public class AuthCommand implements Command{
    private final String password;
    public AuthCommand(String password){
        this.password=password;
    }
    @Override
    public String execute(List<String> arguments, RedisDatabase database, ClientSession session){
        if(arguments.size()!=1)
            return RespEncoder.error("Wrong number of arguments for authentication");
        if(arguments.get(0).equals(password)){
            session.authenticate();
            return RespEncoder.simpleString("OK");}
        return RespEncoder.error("Invalid Password");
    }
    
}
