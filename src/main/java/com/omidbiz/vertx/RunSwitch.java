package com.omidbiz.vertx;

import org.jpos.iso.ISOMsg;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

/**
 * @author omidp
 * <p>
 *  telnet 127.0.0.1 4321
 *  paste isomsg then click enter
 * </p>
 */
public class RunSwitch extends AbstractVerticle
{

    @Override
    public void start() throws Exception
    {
        NetServerOptions options = new NetServerOptions().setPort(4321);
        NetServer server = vertx.createNetServer(options);
        ISOParser parser = new ISOParser();
        System.out.println(parser.pack("0200", "201234", "10000", "110722", "999991", "80001", "774550000022"));
        server.connectHandler(socket ->
        {
            socket.handler(buffer ->
            {
                String msg8583 = buffer.toString();
                System.out.println("I received some bytes: " + buffer.length());
                try
                {
                    ISOMsg unpack = parser.unpack(msg8583);      
                    System.out.println("terminal code : " + unpack.getString(41));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                
            });
        });
        server.listen(res ->
        {
            if (res.succeeded())
            {
                System.out.println("Server is now listening!");
            }
            else
            {
                System.out.println("Failed to bind!");
            }
        });
    }

    public static void main(String[] args)
    {
        Runner.runExample(RunSwitch.class);
    }

}
