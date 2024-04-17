package com.feiniaojin.ddd.ecosystem.pie;

import com.feiniaojin.ddd.ecosystem.pie.Channel.Channel;
import com.feiniaojin.ddd.ecosystem.pie.ChannelHandler.ChannelHandler;

/**
 * 启动配置类
 *
 */
public class BootStrap {

    private Channel channel;

    private Object in;

    private OutboundFactory outboundFactory;

    public BootStrap channel(Channel channel) {
        this.channel = channel;
        return this;
    }

    public BootStrap inboundParameter(Object in) {
        this.in = in;
        return this;
    }


    public BootStrap outboundFactory(OutboundFactory outboundFactory) {
        this.outboundFactory = outboundFactory;
        return this;
    }

    public BootStrap addChannelHandlerAtLast(String name, ChannelHandler channelHandler) {
        this.channel.pipeline().addLast(name, channelHandler);
        return this;
    }

    public Object process() {
        Object out = outboundFactory.newInstance();
        channel.process(in, out);
        return out;
    }
}
