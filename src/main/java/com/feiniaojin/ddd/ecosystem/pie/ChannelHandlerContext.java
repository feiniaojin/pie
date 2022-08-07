package com.feiniaojin.ddd.ecosystem.pie;

/**
 * ChannelHandlerContext
 *
 */
public interface ChannelHandlerContext {

    Channel channel();

    ChannelHandler handler();

    ChannelPipeline pipeline();

    ChannelHandlerContext process(Object in,
                                  Object out);

    ChannelHandlerContext fireExceptionCaught(Throwable cause,
                                              Object in,
                                              Object out);

    ChannelHandlerContext fireChannelProcess(Object in,
                                             Object out);
}
