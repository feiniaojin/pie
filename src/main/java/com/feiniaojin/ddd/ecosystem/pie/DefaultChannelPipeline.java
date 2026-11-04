package com.feiniaojin.ddd.ecosystem.pie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ChannelPipeline默认实现
 *
 */
public class DefaultChannelPipeline<I, O> implements ChannelPipeline<I, O> {

    AbstractChannelHandlerContext<I, O> head;
    AbstractChannelHandlerContext<I, O> tail;

    private static final String HEAD_NAME = generateName0(HeadContext.class);
    private static final String TAIL_NAME = generateName0(TailContext.class);

    private final Channel<I, O> channel;

    protected DefaultChannelPipeline(Channel<I, O> channel) {
        this.channel = channel;
        tail = new TailContext<>(this);
        head = new HeadContext<>(this);
        head.next = tail;
        tail.prev = head;
    }

    public ChannelPipeline<I, O> addLast(String name, ChannelHandler<I, O> handler) {
        AbstractChannelHandlerContext<I, O> newCtx = new DefaultChannelHandlerContext<>(this, name, handler);
        AbstractChannelHandlerContext<I, O> prev = tail.prev;
        newCtx.prev = prev;
        newCtx.next = tail;
        prev.next = newCtx;
        tail.prev = newCtx;
        return this;
    }

    public Channel<I, O> channel() {
        return channel;
    }

    @Override
    public ChannelPipeline<I, O> fireExceptionCaught(Throwable cause,
                                                     I in,
                                                     O out) {
        AbstractChannelHandlerContext.invokeExceptionCaught(head, cause, in, out);
        return this;
    }

    @Override
    public ChannelPipeline<I, O> fireChannelProcess(I in,
                                                    O out) {
        AbstractChannelHandlerContext.invokeChannelProcess(head, in, out);
        return this;
    }

    private static String generateName0(Class<?> handlerType) {
        return handlerType.getSimpleName() + "#0";
    }

    final static class TailContext<I, O> extends AbstractChannelHandlerContext<I, O>
            implements ChannelHandler<I, O> {

        private final Logger logger = LoggerFactory.getLogger(TailContext.class);

        TailContext(DefaultChannelPipeline<I, O> pipeline) {
            super(pipeline, TAIL_NAME, TailContext.class);
        }

        @Override
        public ChannelHandler<I, O> handler() {
            return this;
        }

        @Override
        public void channelProcess(ChannelHandlerContext<I, O> ctx, I in, O out) throws Exception {
            if (logger.isDebugEnabled()) {
                logger.debug("tail:channelProcess:there is no more handler");
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext<I, O> ctx,
                                    Throwable cause,
                                    I in,
                                    O out) throws Exception {
            if (logger.isDebugEnabled()) {
                logger.debug("tail:exceptionCaught:there is no more handler");
            }
        }
    }

    final static class HeadContext<I, O> extends AbstractChannelHandlerContext<I, O>
            implements ChannelHandler<I, O> {

        private final Logger logger = LoggerFactory.getLogger(HeadContext.class);

        HeadContext(DefaultChannelPipeline<I, O> pipeline) {
            super(pipeline, HEAD_NAME, HeadContext.class);
        }

        @Override
        public ChannelHandler<I, O> handler() {
            return this;
        }


        @Override
        public void channelProcess(ChannelHandlerContext<I, O> ctx,
                                   I in,
                                   O out) throws Exception {
            if(logger.isDebugEnabled()){
                logger.debug("head:channelProcess");
            }
            ctx.fireChannelProcess(in, out);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext<I, O> ctx,
                                    Throwable cause,
                                    I in,
                                    O out) throws Exception {
            logger.info("head:exceptionCaught");
        }
    }

    @Override
    public ChannelPipeline<I, O> process(I in,
                                         O out) {
        head.process(in, out);
        return this;
    }

    @Override
    public ChannelHandlerContext<I, O> head() {
        return head;
    }

    @Override
    public ChannelHandlerContext<I, O> tail() {
        return tail;
    }

}
