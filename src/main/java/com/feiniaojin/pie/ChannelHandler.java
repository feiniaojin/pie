package com.feiniaojin.pie;

/**
 * ChannelHandler
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
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
