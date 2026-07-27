package mini_redis.Src.Server;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RespParser {


    public List<String> readCommand(ByteBuffer buffer) {


        buffer.flip();


        try {


            if(!buffer.hasRemaining()) {
                return null;
            }


            if(buffer.get() != '*') {
                throw new RuntimeException(
                        "Expected RESP array"
                );
            }


            int args =
                    readNumber(buffer);



            List<String> command =
                    new ArrayList<>();


            for(int i = 0; i < args; i++) {


                if(!buffer.hasRemaining())
                    return null;


                if(buffer.get() != '$') {
                    throw new RuntimeException(
                            "Expected bulk string"
                    );
                }



                int length =
                        readNumber(buffer);



                if(buffer.remaining() < length + 2) {

                    return null;

                }



                byte[] data =
                        new byte[length];



                buffer.get(data);



                // remove \r\n
                buffer.get();
                buffer.get();



                command.add(
                        new String(
                                data,
                                StandardCharsets.UTF_8
                        )
                );
            }


            return command;


        }
        finally {

            buffer.compact();

        }
    }




    private int readNumber(ByteBuffer buffer) {


        StringBuilder number =
                new StringBuilder();


        while(buffer.hasRemaining()) {


            byte b =
                    buffer.get();



            if(b == '\r') {


                buffer.get(); // consume \n

                break;

            }


            number.append((char)b);
        }


        return Integer.parseInt(
                number.toString()
        );
    }
}
