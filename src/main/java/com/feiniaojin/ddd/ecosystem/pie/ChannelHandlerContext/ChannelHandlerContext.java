package com.feiniaojin.ddd.ecosystem.pie.ChannelHandlerContext;

import com.feiniaojin.ddd.ecosystem.pie.Channel.Channel;
import com.feiniaojin.ddd.ecosystem.pie.ChannelHandler.ChannelHandler;
import com.feiniaojin.ddd.ecosystem.pie.ChannelPipeline.ChannelPipeline;

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
