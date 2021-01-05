package com.feiniaojin.pie;

/**
 * ChannelHandlerContext
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
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
