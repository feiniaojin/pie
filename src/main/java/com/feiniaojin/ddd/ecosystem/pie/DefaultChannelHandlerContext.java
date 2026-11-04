package com.feiniaojin.ddd.ecosystem.pie;

/**
 * DefaultChannelHandlerContext默认实现
 *
 */
public class DefaultChannelHandlerContext<I, O> extends AbstractChannelHandlerContext<I, O> {

    private final ChannelHandler<I, O> handler;

    DefaultChannelHandlerContext(DefaultChannelPipeline<I, O> pipeline,
                                 String name,
                                 ChannelHandler<I, O> handler) {
        super(pipeline, name, handler.getClass());
        this.handler = handler;
    }

    @Override
    public ChannelHandler<I, O> handler() {
        return handler;
    }
}
