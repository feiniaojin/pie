package com.feiniaojin.pie;

/**
 * ChannelPipeline
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
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
