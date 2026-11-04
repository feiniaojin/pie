package com.feiniaojin.ddd.ecosystem.pie;

/**
 * ChannelPipeline
 *
 */
public interface ChannelPipeline<I, O> {

    ChannelPipeline<I, O> process(I in,
                                  O out);

    ChannelPipeline<I, O> addLast(String name, ChannelHandler<I, O> handler);

    Channel<I, O> channel();

    ChannelPipeline<I, O> fireExceptionCaught(Throwable cause,
                                              I in,
                                              O out);

    ChannelPipeline<I, O> fireChannelProcess(I in,
                                             O out);

    ChannelHandlerContext<I, O> head();

    ChannelHandlerContext<I, O> tail();
}
