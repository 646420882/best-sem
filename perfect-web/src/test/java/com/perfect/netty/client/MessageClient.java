package com.perfect.netty.client;

import com.perfect.netty.coder.MessageDecoder;
import com.perfect.netty.coder.MessageEncoder;
import com.perfect.netty.handler.MessageClientHandler;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by XiaoWei on 2014/12/11.
 */
public class MessageClient {
    public static void main(String[] args){
        create();
    }

    private static void create() {
        String host="127.0.0.1";
        int port=9550;
        ClientBootstrap bootstrap=new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newSingleThreadExecutor()
                )
        );
        bootstrap.getPipeline().addLast("decoder",new MessageDecoder());
        bootstrap.getPipeline().addLast("encoder",new MessageEncoder());
        bootstrap.getPipeline().addLast("handler",new MessageClientHandler());
        ChannelFuture future=bootstrap.connect(new InetSocketAddress(host,port));
        future.getChannel().getCloseFuture().awaitUninterruptibly();
        bootstrap.releaseExternalResources();
    }
}
