package com.feiniaojin.pie;

/**
 * ChannelHandler
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
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
