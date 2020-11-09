package com.feiniaojin.pie;

/**
 * ChannelHandler
 *
 * @author: <a href=mailto:qinyujie3@jd.com>qinyujie3</a>
 */
public interface ChannelHandler<IN, OUT> {

    void channelProcess(ChannelHandlerContext ctx,
                        InWrapper<IN> inWrapper,
                        OutWrapper<OUT> outWrapper) throws Exception;

    void exceptionCaught(ChannelHandlerContext ctx,
                         Throwable cause,
                         InWrapper<IN> inWrapper,
                         OutWrapper<OUT> outWrapper) throws Exception;
}
