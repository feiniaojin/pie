package com.feiniaojin.ddd.ecosystem.pie;

public abstract class ChannelHandlerAdapter<I, O> implements ChannelHandler<I, O> {

    @Override
    public void channelProcess(ChannelHandlerContext<I, O> ctx, I in, O out) throws Exception {
        ctx.fireChannelProcess(in, out);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext<I, O> ctx, Throwable cause, I in, O out) throws Exception {
        ctx.fireExceptionCaught(cause, in, out);
    }
}
