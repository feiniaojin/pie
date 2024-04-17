package com.feiniaojin.ddd.ecosystem.pie.ChannelHandler;

import com.feiniaojin.ddd.ecosystem.pie.ChannelHandlerContext.ChannelHandlerContext;

/**
 * ChannelHandler
 *
 */
public interface ChannelHandler {

    void channelProcess(ChannelHandlerContext ctx,
                        Object in,
                        Object out) throws Exception;

    void exceptionCaught(ChannelHandlerContext ctx,
                         Throwable cause,
                         Object in,
                         Object out) throws Exception;
}
