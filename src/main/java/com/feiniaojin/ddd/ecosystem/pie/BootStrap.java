package com.feiniaojin.ddd.ecosystem.pie;

/**
 * 启动配置类
 *
 */
public class BootStrap<T> {

    private Channel channel;

    private Object in;

    private OutboundFactory<T> outboundFactory;

    /**
     * 创建启动配置建造者。
     *
     * @return 启动配置建造者
     */
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * 根据出参工厂创建启动配置建造者。
     *
     * @param outboundFactory 出参工厂
     * @param <T> 出参类型
     * @return 启动配置建造者
     */
    public static <T> Builder<T> builder(OutboundFactory<T> outboundFactory) {
        return new Builder<T>().outboundFactory(outboundFactory);
    }

    public BootStrap<T> channel(Channel channel) {
        this.channel = channel;
        return this;
    }

    public BootStrap<T> inboundParameter(Object in) {
        this.in = in;
        return this;
    }


    public BootStrap<T> outboundFactory(OutboundFactory<T> outboundFactory) {
        this.outboundFactory = outboundFactory;
        return this;
    }

    public BootStrap<T> addChannelHandlerAtLast(String name, ChannelHandler channelHandler) {
        this.channel.pipeline().addLast(name, channelHandler);
        return this;
    }

    public T process() {
        T out = outboundFactory.newInstance();
        channel.process(in, out);
        return out;
    }

    /**
     * 启动配置建造者。
     */
    public static class Builder<T> {

        private final BootStrap<T> bootStrap = new BootStrap<>();

        public Builder<T> channel(Channel channel) {
            bootStrap.channel(channel);
            return this;
        }

        public Builder<T> inboundParameter(Object in) {
            bootStrap.inboundParameter(in);
            return this;
        }

        public Builder<T> outboundFactory(OutboundFactory<T> outboundFactory) {
            bootStrap.outboundFactory(outboundFactory);
            return this;
        }

        public Builder<T> addChannelHandlerAtLast(String name, ChannelHandler channelHandler) {
            bootStrap.addChannelHandlerAtLast(name, channelHandler);
            return this;
        }

        public BootStrap<T> build() {
            if (bootStrap.in == null) {
                throw new IllegalStateException("inboundParameter must not be null");
            }
            if (bootStrap.outboundFactory == null) {
                throw new IllegalStateException("outboundFactory must not be null");
            }
            if (bootStrap.channel == null) {
                bootStrap.channel(new AbstractChannel() {});
            }
            return bootStrap;
        }
    }
}
