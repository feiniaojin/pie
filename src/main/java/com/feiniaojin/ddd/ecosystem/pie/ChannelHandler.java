package com.feiniaojin.ddd.ecosystem.pie;

/**
 * ChannelHandler
 *
 */
public interface ChannelHandler<I, O> {

    void channelProcess(ChannelHandlerContext<I, O> ctx,
                        I in,
                        O out) throws Exception;

    void exceptionCaught(ChannelHandlerContext<I, O> ctx,
                         Throwable cause,
                         I in,
                         O out) throws Exception;
}
