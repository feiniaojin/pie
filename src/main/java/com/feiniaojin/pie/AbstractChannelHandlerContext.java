package com.feiniaojin.pie;

/**
 * 抽象ChannelHandlerContext
 *
 * @author: <a href=mailto:qinyujie3@jd.com>qinyujie3</a>
 */
public abstract class AbstractChannelHandlerContext<IN, OUT> implements ChannelHandlerContext<IN, OUT> {
    volatile AbstractChannelHandlerContext<IN, OUT> next;
    volatile AbstractChannelHandlerContext<IN, OUT> prev;
    private DefaultChannelPipeline<IN, OUT> pipeline;
    private String name;

    AbstractChannelHandlerContext(DefaultChannelPipeline<IN, OUT> pipeline,
                                  String name, Class<? extends ChannelHandler> handlerClass) {
        this.name = ObjectUtil.checkNotNull(name, "name");
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
                                                     InWrapper<IN> inWrapper,
                                                     OutWrapper<OUT> outWrapper) {
        invokeExceptionCaught(this.next, cause, inWrapper, outWrapper);
        return this;
    }

    @Override
    public ChannelHandlerContext fireChannelProcess(InWrapper<IN> inWrapper,
                                                    OutWrapper<OUT> outWrapper) {
        invokeChannelProcess(this.next, inWrapper, outWrapper);
        return this;
    }


    private void invokeExceptionCaught(final Throwable cause,
                                       InWrapper<IN> inWrapper,
                                       OutWrapper<OUT> outWrapper) {
        try {
            handler().exceptionCaught(this, cause, inWrapper, outWrapper);
        } catch (Throwable error) {

        }
    }

    private void invokeChannelProcess(InWrapper<IN> inWrapper,
                                      OutWrapper<OUT> outWrapper) {
        try {
            handler().channelProcess(this, inWrapper, outWrapper);
        } catch (Throwable throwable) {
            invokeExceptionCaught(throwable, inWrapper, outWrapper);
        }
    }

    static <IN, OUT> void invokeExceptionCaught(final AbstractChannelHandlerContext next,
                                                final Throwable cause,
                                                InWrapper<IN> inWrapper,
                                                OutWrapper<OUT> outWrapper) {
        next.invokeExceptionCaught(cause, inWrapper, outWrapper);
    }

    static <IN, OUT> void invokeChannelProcess(final AbstractChannelHandlerContext next,
                                               InWrapper<IN> inWrapper,
                                               OutWrapper<OUT> outWrapper) {
        next.invokeChannelProcess(inWrapper, outWrapper);
    }

    @Override
    public ChannelHandlerContext<IN, OUT> process(InWrapper<IN> inWrapper,
                                                  OutWrapper<OUT> outWrapper) {

        try {
            handler().channelProcess(this, inWrapper, outWrapper);
        } catch (Throwable t) {
            invokeExceptionCaught(t, inWrapper, outWrapper);
        }
        return this;
    }
}
