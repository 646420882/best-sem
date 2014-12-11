package com.perfect.netty.coder;

import com.perfect.netty.dto.PersonDTO;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * Created by XiaoWei on 2014/12/11.
 */
public class MessageEncoder extends SimpleChannelHandler {
    private ChannelBuffer cb=ChannelBuffers.dynamicBuffer();
    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        PersonDTO personDTO= (PersonDTO) e.getMessage();
        cb.writeInt(personDTO.getName().getBytes("UTF-8").length);
        cb.writeBytes(personDTO.getName().getBytes());

        cb.writeInt(personDTO.getSex().getBytes("UTF-8").length);
        cb.writeBytes(personDTO.getSex().getBytes());

        cb.writeInt(personDTO.getAge());
        cb.writeDouble(personDTO.getWeight());
        Channels.write(ctx,e.getFuture(),cb);
    }

}
