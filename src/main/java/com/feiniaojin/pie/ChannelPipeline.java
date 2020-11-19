package com.feiniaojin.pie;

/**
 * ChannelPipeline
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
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
