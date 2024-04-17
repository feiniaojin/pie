package com.feiniaojin.ddd.ecosystem.pie.ChannelHandlerContext;

import com.feiniaojin.ddd.ecosystem.pie.ChannelPipeline.ChannelPipeline;
import com.feiniaojin.ddd.ecosystem.pie.ChannelPipeline.impl.DefaultChannelPipeline;
import com.feiniaojin.ddd.ecosystem.pie.ObjectUtil;
import com.feiniaojin.ddd.ecosystem.pie.Channel.Channel;
import com.feiniaojin.ddd.ecosystem.pie.ChannelHandler.ChannelHandler;

/**
 * 抽象ChannelHandlerContext
 *
 */
public abstract class AbstractChannelHandlerContext implements ChannelHandlerContext {
    public volatile AbstractChannelHandlerContext next;
    public volatile AbstractChannelHandlerContext prev;
    private DefaultChannelPipeline pipeline;
    private String name;

    protected AbstractChannelHandlerContext(DefaultChannelPipeline pipeline,
                                            String name, Class<? extends ChannelHandler> handlerClass) {
        this.name = (String) ObjectUtil.checkNotNull(name, "name");
        this.pipeline = pipeline;
    }

    @Override
    public Channel channel() {
        return pipeline.channel();
    }

    @Override
    public ChannelPipeline pipeline() {
        return pipeline;
    }

    @Override
    public ChannelHandlerContext fireExceptionCaught(Throwable cause,
                                                     Object in,
                                                     Object out) {
        invokeExceptionCaught(this.next, cause, in, out);
        return this;
    }

    @Override
    public ChannelHandlerContext fireChannelProcess(Object in,
                                                    Object out) {
        invokeChannelProcess(this.next, in, out);
        return this;
    }


    private void invokeExceptionCaught(final Throwable cause,
                                       Object in,
                                       Object out) {
        try {
            handler().exceptionCaught(this, cause, in, out);
        } catch (Throwable error) {

        }
    }

    private void invokeChannelProcess(Object in,
                                      Object out) {
        try {
            handler().channelProcess(this, in, out);
        } catch (Throwable throwable) {
            invokeExceptionCaught(throwable, in, out);
        }
    }

    public static void invokeExceptionCaught(final AbstractChannelHandlerContext next,
                                             final Throwable cause,
                                             Object in,
                                             Object out) {
        next.invokeExceptionCaught(cause, in, out);
    }

    public static void invokeChannelProcess(final AbstractChannelHandlerContext next,
                                            Object in,
                                            Object out) {
        next.invokeChannelProcess(in, out);
    }

    @Override
    public ChannelHandlerContext process(Object in,
                                         Object out) {

        try {
            handler().channelProcess(this, in, out);
        } catch (Throwable t) {
            invokeExceptionCaught(t, in, out);
        }
        return this;
    }
}
