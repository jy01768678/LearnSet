package com.lorin.netty.protocol;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * Created by lorin on 17/5/18.
 */
public class LuckDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 获取协议的版本
        int version = byteBuf.readInt();
        // 获取消息长度
        int contentLength = byteBuf.readInt();
        // 获取SessionId
        byte[] sessionByte = new byte[36];
        byteBuf.readBytes(sessionByte);
        String sessionId = new String(sessionByte);

        // 组装协议头
        LuckHeader header = new LuckHeader(version, contentLength, sessionId);

        // 读取消息内容
        byte[] content = byteBuf.readBytes(byteBuf.readableBytes()).array();

        LuckMessage message = new LuckMessage(header, new String(content));

        list.add(message);
    }
}
