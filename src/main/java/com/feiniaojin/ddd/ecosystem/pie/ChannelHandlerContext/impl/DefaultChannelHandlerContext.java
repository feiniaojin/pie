package com.feiniaojin.ddd.ecosystem.pie.ChannelHandlerContext.impl;

import com.feiniaojin.ddd.ecosystem.pie.ChannelHandler.ChannelHandler;
import com.feiniaojin.ddd.ecosystem.pie.ChannelHandlerContext.AbstractChannelHandlerContext;
import com.feiniaojin.ddd.ecosystem.pie.ChannelPipeline.impl.DefaultChannelPipeline;

/**
 * DefaultChannelHandlerContext默认实现
 *
 */
public class DefaultChannelHandlerContext extends AbstractChannelHandlerContext {

    private final ChannelHandler handler;

    public DefaultChannelHandlerContext(DefaultChannelPipeline pipeline, String name, ChannelHandler handler) {
        super(pipeline, name, handler.getClass());
        this.handler = handler;
    }

    @Override
    public ChannelHandler handler() {
        return handler;
    }
}
