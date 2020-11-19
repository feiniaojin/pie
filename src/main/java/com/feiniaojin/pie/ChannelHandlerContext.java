package com.feiniaojin.pie;

/**
 * ChannelHandlerContext
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
 */
public interface ChannelHandlerContext<IN, OUT> {

    Channel channel();

    ChannelHandler handler();

    ChannelPipeline pipeline();

    ChannelHandlerContext<IN, OUT> process(InWrapper<IN> inWrapper,
                                                                                         OutWrapper<OUT> outWrapper);

    ChannelHandlerContext<IN, OUT> fireExceptionCaught(Throwable cause,
                                                                                                     InWrapper<IN> inWrapper,
                                                                                                     OutWrapper<OUT> outWrapper);

    ChannelHandlerContext<IN, OUT> fireChannelProcess(InWrapper<IN> inWrapper,
                                                                                                    OutWrapper<OUT> outWrapper);
}
