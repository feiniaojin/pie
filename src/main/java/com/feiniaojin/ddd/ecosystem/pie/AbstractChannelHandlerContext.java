package com.feiniaojin.ddd.ecosystem.pie;

/**
 * 抽象ChannelHandlerContext
 *
 */
public abstract class AbstractChannelHandlerContext<I, O> implements ChannelHandlerContext<I, O> {
    volatile AbstractChannelHandlerContext<I, O> next;
    volatile AbstractChannelHandlerContext<I, O> prev;
    private DefaultChannelPipeline<I, O> pipeline;
    private String name;

    AbstractChannelHandlerContext(DefaultChannelPipeline<I, O> pipeline,
                                  String name, Class<?> handlerClass) {
        this.name = (String) ObjectUtil.checkNotNull(name, "name");
        this.pipeline = pipeline;
    }

    @Override
    public Channel<I, O> channel() {
        return pipeline.channel();
    }

    @Override
    public ChannelPipeline<I, O> pipeline() {
        return pipeline;
    }

    @Override
    public ChannelHandlerContext<I, O> fireExceptionCaught(Throwable cause,
                                                           I in,
                                                           O out) {
        invokeExceptionCaught(this.next, cause, in, out);
        return this;
    }

    @Override
    public ChannelHandlerContext<I, O> fireChannelProcess(I in,
                                                          O out) {
        invokeChannelProcess(this.next, in, out);
        return this;
    }


    private void invokeExceptionCaught(final Throwable cause,
                                       I in,
                                       O out) {
        try {
            handler().exceptionCaught(this, cause, in, out);
        } catch (Throwable error) {

        }
    }

    private void invokeChannelProcess(I in,
                                      O out) {
        try {
            handler().channelProcess(this, in, out);
        } catch (Throwable throwable) {
            invokeExceptionCaught(throwable, in, out);
        }
    }

    static <I, O> void invokeExceptionCaught(final AbstractChannelHandlerContext<I, O> next,
                                       final Throwable cause,
                                       I in,
                                       O out) {
        next.invokeExceptionCaught(cause, in, out);
    }

    static <I, O> void invokeChannelProcess(final AbstractChannelHandlerContext<I, O> next,
                                            I in,
                                            O out) {
        next.invokeChannelProcess(in, out);
    }

    @Override
    public ChannelHandlerContext<I, O> process(I in,
                                               O out) {

        try {
            handler().channelProcess(this, in, out);
        } catch (Throwable t) {
            invokeExceptionCaught(t, in, out);
        }
        return this;
    }
}
