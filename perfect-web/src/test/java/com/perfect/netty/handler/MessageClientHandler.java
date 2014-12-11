package com.perfect.netty.handler;

import com.perfect.netty.dto.PersonDTO;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.*;

/**
 * Created by XiaoWei on 2014/12/11.
 */
public class MessageClientHandler extends SimpleChannelUpstreamHandler {


    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        PersonDTO personDTO = (PersonDTO)e.getMessage();
        System.out.println(personDTO);
        Thread.sleep(1000);
        e.getChannel().write(e.getMessage());
    }

}
