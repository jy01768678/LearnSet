package com.lorin.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by lorin on 17/5/18.
 */
@ChannelHandler.Sharable
public class LuckEncoder extends MessageToByteEncoder<LuckMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, LuckMessage message, ByteBuf byteBuf) throws Exception {
        // 将Message转换成二进制数据
        LuckHeader header = message.getLuckHeader();

        // 这里写入的顺序就是协议的顺序.

        // 写入Header信息
        byteBuf.writeInt(header.getVersion());
        byteBuf.writeInt(message.getContent().length());
        byteBuf.writeBytes(header.getSessionId().getBytes());

        // 写入消息主体信息
        byteBuf.writeBytes(message.getContent().getBytes());
    }
}
