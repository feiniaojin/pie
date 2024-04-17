package com.feiniaojin.ddd.ecosystem.pie.ChannelHandler;

import com.feiniaojin.ddd.ecosystem.pie.ChannelHandlerContext.ChannelHandlerContext;

public abstract class ChannelHandlerAdapter implements ChannelHandler {

    @Override
    public void channelProcess(ChannelHandlerContext ctx, Object in, Object out) throws Exception {
        ctx.fireChannelProcess(in, out);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause, Object in, Object out) throws Exception {
        ctx.fireExceptionCaught(cause, in, out);
    }
}