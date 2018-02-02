package com.lorin.netty.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by lorin on 17/5/18.
 */
public class NettyLuckHandler extends SimpleChannelInboundHandler<LuckMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LuckMessage luckMessage) throws Exception {
        // 简单地打印出server接收到的消息
        System.out.println(luckMessage.toString());
    }
}
