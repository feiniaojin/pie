package com.feiniaojin.ddd.ecosystem.pie;


/**
 * 面向业务的责任链执行门面。
 *
 * @param <I> 入参类型
 * @param <O> 出参类型
 */
public interface HandlerChain<I, O> {

    /**
     * 执行责任链。
     *
     * @param input 入参
     * @return 出参
     */
    O execute(I input);

    /**
     * 创建责任链建造者。
     *
     * @param <I> 入参类型
     * @param <O> 出参类型
     * @return 责任链建造者
     */
    static <I, O> Builder<I, O> builder(Channel<I, O> channel) {
        return new Builder<>(channel);
    }

    /**
     * 责任链建造者。
     *
     * @param <I> 入参类型
     * @param <O> 出参类型
     */
    class Builder<I, O> {

        private final Channel<I, O> channel;

        private OutboundFactory<O> outboundFactory;

        private boolean built;

        private Builder(Channel<I, O> channel) {
            if (channel == null) {
                throw new IllegalArgumentException("channel must not be null");
            }
            this.channel = channel;
        }

        public Builder<I, O> addHandler(String name, ChannelHandler<I, O> handler) {
            ensureNotBuilt();
            if (name == null) {
                throw new IllegalArgumentException("name must not be null");
            }
            if (handler == null) {
                throw new IllegalArgumentException("handler must not be null");
            }
            channel.pipeline().addLast(name, handler);
            return this;
        }

        public Builder<I, O> outboundFactory(OutboundFactory<O> outboundFactory) {
            ensureNotBuilt();
            this.outboundFactory = outboundFactory;
            return this;
        }

        public HandlerChain<I, O> build() {
            ensureNotBuilt();
            if (outboundFactory == null) {
                throw new IllegalStateException("outboundFactory must not be null");
            }

            built = true;
            return new DefaultHandlerChain<>(channel, outboundFactory);
        }

        private void ensureNotBuilt() {
            if (built) {
                throw new IllegalStateException("handler chain has already been built");
            }
        }
    }

}

final class DefaultHandlerChain<I, O> implements HandlerChain<I, O> {

    private final Channel<I, O> channel;

    private final OutboundFactory<O> outboundFactory;

    DefaultHandlerChain(Channel<I, O> channel, OutboundFactory<O> outboundFactory) {
        this.channel = channel;
        this.outboundFactory = outboundFactory;
    }

    @Override
    public O execute(I input) {
        O output = outboundFactory.newInstance();
        channel.process(input, output);
        return output;
    }

}
