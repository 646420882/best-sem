package com.perfect.netty.server;

import com.perfect.netty.coder.MessageDecoder;
import com.perfect.netty.coder.MessageEncoder;
import com.perfect.netty.handler.MessageServerHandler;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by XiaoWei on 2014/12/11.
 */
public class MessageServer {
    public static void main(String[] args) throws  Exception{
        ServerBootstrap bootstrap=new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()
                )
        );
        bootstrap.getPipeline().addLast("decoder", new MessageDecoder());
        bootstrap.getPipeline().addLast("encoder", new MessageEncoder());
        bootstrap.getPipeline().addLast("handler", new MessageServerHandler());
        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(9550));
    }
}
