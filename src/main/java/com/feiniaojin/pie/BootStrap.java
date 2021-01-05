package com.feiniaojin.pie;

/**
 * 启动配置类
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
 */
public class BootStrap {

    private Channel channel;

    private Object in;

    private OutFactory outFactory;

    public BootStrap channel(Channel channel) {
        this.channel = channel;
        return this;
    }

    public BootStrap inboundParameter(Object in) {
        this.in = in;
        return this;
    }


    public BootStrap outFactory(OutFactory outFactory) {
        this.outFactory = outFactory;
        return this;
    }

    public BootStrap addChannelHandlerAtLast(String name, ChannelHandler channelHandler) {
        this.channel.pipeline().addLast(name, channelHandler);
        return this;
    }

    public Object process() {
        Object out = outFactory.newInstance();
        channel.process(in, out);
        return out;
    }
}
