package com.perfect.netty.handler;


import com.perfect.netty.dto.PersonDTO;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.*;

/**
 * Created by XiaoWei on 2014/12/11.
 */
public class MessageServerHandler extends SimpleChannelUpstreamHandler {
    private static final Logger logger=Logger.getLogger(MessageServerHandler.class.getName());

    @Override
    public void channelConnected(ChannelHandlerContext ctx,ChannelStateEvent e) throws Exception {
        PersonDTO personDTO=new PersonDTO("肖伟",23,"男",67.5);
        ChannelFuture future=e.getChannel().write(personDTO);
        future.addListener(ChannelFutureListener.CLOSE);
    }
}
