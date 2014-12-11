package com.perfect.netty.coder;

import com.perfect.netty.dto.PersonDTO;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * Created by XiaoWei on 2014/12/11.
 */
public class MessageDecoder extends FrameDecoder {
    private final ChannelBuffer cb= ChannelBuffers.dynamicBuffer();
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {

        if(buffer.readableBytes()<4){
            return null;
        }
        if(buffer.readable()){
           buffer.readBytes(cb,buffer.readableBytes());
        }
        int nameLength=cb.readInt();
        String name=new String(cb.readBytes(nameLength).array(),"UTF-8");
        int sexLength=cb.readInt();
        String sex=new String(cb.readBytes(sexLength).array(),"UTF-8");
        int age=cb.readInt();
        double weight=cb.readDouble();
        PersonDTO personDTO=new PersonDTO(name,age,sex,weight);
        return personDTO;
    }
}
