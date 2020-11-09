package com.feiniaojin.pie;

/**
 * ChannelPipeline
 *
 * @author: <a href=mailto:qinyujie3@jd.com>qinyujie3</a>
 */
public interface ChannelPipeline<IN, OUT> {

    ChannelPipeline process(InWrapper<IN> inWrapper,
                                                                          OutWrapper<OUT> outWrapper);

    ChannelPipeline addLast(String name, ChannelHandler handler);

    Channel channel();

    ChannelPipeline fireExceptionCaught(Throwable cause,
                                                                                      InWrapper<IN> inWrapper,
                                                                                      OutWrapper<OUT> outWrapper);

    ChannelPipeline fireChannelProcess(InWrapper<IN> inWrapper,
                                                                                     OutWrapper<OUT> outWrapper);

    ChannelHandlerContext head();

    ChannelHandlerContext tail();
}
