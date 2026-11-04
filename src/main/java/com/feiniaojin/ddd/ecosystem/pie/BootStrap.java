package com.feiniaojin.ddd.ecosystem.pie;

/**
 * 启动配置类
 *
 */
public class BootStrap {

    private Channel channel;

    private Object in;

    private OutboundFactory outboundFactory;

    /**
     * 创建启动配置建造者。
     *
     * @return 启动配置建造者
     */
    public static Builder builder() {
        return new Builder();
    }

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

    /**
     * 启动配置建造者。
     */
    public static class Builder {

        private final BootStrap bootStrap = new BootStrap();

        public Builder channel(Channel channel) {
            bootStrap.channel(channel);
            return this;
        }

        public Builder inboundParameter(Object in) {
            bootStrap.inboundParameter(in);
            return this;
        }

        public Builder outboundFactory(OutboundFactory outboundFactory) {
            bootStrap.outboundFactory(outboundFactory);
            return this;
        }

        public Builder addChannelHandlerAtLast(String name, ChannelHandler channelHandler) {
            bootStrap.addChannelHandlerAtLast(name, channelHandler);
            return this;
        }

        public BootStrap build() {
            if (bootStrap.channel == null) {
                bootStrap.channel(new AbstractChannel() {
                });
            }
            return bootStrap;
        }
    }
}
