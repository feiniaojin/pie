package com.feiniaojin.ddd.ecosystem.pie;

/**
 * 启动配置类
 *
 */
public class BootStrap<I, O> {

    private Channel<I, O> channel;

    private I in;

    private OutboundFactory<O> outboundFactory;

    /**
     * 创建启动配置建造者。
     *
     * @param <I> 入参类型
     * @param <O> 出参类型
     * @return 启动配置建造者
     */
    public static <I, O> Builder<I, O> builder() {
        return new Builder<>();
    }

    public BootStrap<I, O> channel(Channel<I, O> channel) {
        this.channel = channel;
        return this;
    }

    public BootStrap<I, O> inboundParameter(I in) {
        this.in = in;
        return this;
    }

    public BootStrap<I, O> outboundFactory(OutboundFactory<O> outboundFactory) {
        this.outboundFactory = outboundFactory;
        return this;
    }

    public BootStrap<I, O> addChannelHandlerAtLast(String name,
                                                   ChannelHandler<I, O> channelHandler) {
        if (channel == null) {
            throw new IllegalStateException("channel must be configured before adding a handler");
        }
        this.channel.pipeline().addLast(name, channelHandler);
        return this;
    }

    public O process() {
        O out = outboundFactory.newInstance();
        channel.process(in, out);
        return out;
    }

    /**
     * 启动配置建造者。
     */
    public static class Builder<I, O> {

        private final BootStrap<I, O> bootStrap = new BootStrap<>();

        public Builder<I, O> channel(Channel<I, O> channel) {
            bootStrap.channel(channel);
            return this;
        }

        public Builder<I, O> inboundParameter(I in) {
            bootStrap.inboundParameter(in);
            return this;
        }

        public Builder<I, O> outboundFactory(OutboundFactory<O> outboundFactory) {
            bootStrap.outboundFactory(outboundFactory);
            return this;
        }

        public Builder<I, O> addChannelHandlerAtLast(String name,
                                                     ChannelHandler<I, O> channelHandler) {
            bootStrap.addChannelHandlerAtLast(name, channelHandler);
            return this;
        }

        public BootStrap<I, O> build() {
            if (bootStrap.in == null) {
                throw new IllegalStateException("inboundParameter must not be null");
            }
            if (bootStrap.outboundFactory == null) {
                throw new IllegalStateException("outboundFactory must not be null");
            }
            if (bootStrap.channel == null) {
                bootStrap.channel(new DefaultChannel<I, O>() {});
            }
            return bootStrap;
        }
    }
}
