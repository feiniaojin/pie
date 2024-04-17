package com.feiniaojin.ddd.ecosystem.pie.ChannelPipeline;

import com.feiniaojin.ddd.ecosystem.pie.Channel.Channel;
import com.feiniaojin.ddd.ecosystem.pie.ChannelHandler.ChannelHandler;
import com.feiniaojin.ddd.ecosystem.pie.ChannelHandlerContext.ChannelHandlerContext;

/**
 * ChannelPipeline
 *
 */
public interface ChannelPipeline {

    ChannelPipeline process(Object in,
                            Object out);

    ChannelPipeline addLast(String name, ChannelHandler handler);

    Channel channel();

    ChannelPipeline fireExceptionCaught(Throwable cause,
                                        Object in,
                                        Object out);

    ChannelPipeline fireChannelProcess(Object in,
                                       Object out);

    ChannelHandlerContext head();

    ChannelHandlerContext tail();
}
