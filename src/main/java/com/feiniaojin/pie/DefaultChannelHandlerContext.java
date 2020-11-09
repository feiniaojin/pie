package com.feiniaojin.pie;

/**
 * DefaultChannelHandlerContext默认实现
 *
 * @author: <a href=mailto:qinyujie3@jd.com>qinyujie3</a>
 */
public class DefaultChannelHandlerContext extends AbstractChannelHandlerContext {

    private final ChannelHandler handler;

    DefaultChannelHandlerContext(DefaultChannelPipeline pipeline, String name, ChannelHandler handler) {
        super(pipeline, name, handler.getClass());
        this.handler = handler;
    }

    @Override
    public ChannelHandler handler() {
        return handler;
    }
}
