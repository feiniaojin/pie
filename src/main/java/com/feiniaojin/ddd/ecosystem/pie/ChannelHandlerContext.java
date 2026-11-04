package com.feiniaojin.ddd.ecosystem.pie;

/**
 * ChannelHandlerContext
 *
 */
public interface ChannelHandlerContext<I, O> {

    Channel<I, O> channel();

    ChannelHandler<I, O> handler();

    ChannelPipeline<I, O> pipeline();

    ChannelHandlerContext<I, O> process(I in,
                                        O out);

    ChannelHandlerContext<I, O> fireExceptionCaught(Throwable cause,
                                                    I in,
                                                    O out);

    ChannelHandlerContext<I, O> fireChannelProcess(I in,
                                                   O out);
}
