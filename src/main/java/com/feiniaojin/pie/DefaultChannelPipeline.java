package com.feiniaojin.pie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ChannelPipeline默认实现
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
 */
public class DefaultChannelPipeline<IN, OUT> implements ChannelPipeline<IN, OUT> {

    AbstractChannelHandlerContext<IN, OUT> head;
    AbstractChannelHandlerContext<IN, OUT> tail;

    private static final String HEAD_NAME = generateName0(HeadContext.class);
    private static final String TAIL_NAME = generateName0(TailContext.class);

    private Channel channel;

    protected DefaultChannelPipeline(Channel channel) {
        this.channel = channel;
        tail = new TailContext(this);
        head = new HeadContext(this);
        head.next = tail;
        tail.prev = head;
    }

    public ChannelPipeline addLast(String name, ChannelHandler handler) {
        AbstractChannelHandlerContext newCtx = new DefaultChannelHandlerContext(this, name, handler);
        AbstractChannelHandlerContext prev = tail.prev;
        newCtx.prev = prev;
        newCtx.next = tail;
        prev.next = newCtx;
        tail.prev = newCtx;
        return this;
    }

    public Channel channel() {
        return channel;
    }

    @Override
    public ChannelPipeline fireExceptionCaught(Throwable cause, InWrapper<IN> inWrapper, OutWrapper<OUT> outWrapper) {
        AbstractChannelHandlerContext.invokeExceptionCaught(head, cause, inWrapper, outWrapper);
        return this;
    }

    @Override
    public ChannelPipeline fireChannelProcess(InWrapper<IN> inWrapper, OutWrapper<OUT> outWrapper) {
        AbstractChannelHandlerContext.invokeChannelProcess(head, inWrapper, outWrapper);
        return this;
    }

    private static String generateName0(Class<?> handlerType) {
        return handlerType.getSimpleName() + "#0";
    }

    final static class TailContext extends AbstractChannelHandlerContext implements ChannelHandler {

        private Logger logger = LoggerFactory.getLogger(TailContext.class);

        TailContext(DefaultChannelPipeline pipeline) {
            super(pipeline, TAIL_NAME, TailContext.class);
        }

        @Override
        public ChannelHandler handler() {
            return this;
        }

        @Override
        public void channelProcess(ChannelHandlerContext ctx, InWrapper inWrapper, OutWrapper outWrapper) throws Exception {
            if (logger.isDebugEnabled()) {
                logger.debug("tail:channelProcess:there is no more handler");
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause, InWrapper inWrapper, OutWrapper outWrapper) throws Exception {
            if (logger.isDebugEnabled()) {
                logger.debug("tail:exceptionCaught:there is no more handler");
            }
        }
    }

    final static class HeadContext<IN, OUT> extends AbstractChannelHandlerContext<IN, OUT> implements ChannelHandler<IN, OUT> {

        private Logger logger = LoggerFactory.getLogger(TailContext.class);

        HeadContext(DefaultChannelPipeline<IN, OUT> pipeline) {
            super(pipeline, HEAD_NAME, HeadContext.class);
        }

        @Override
        public ChannelHandler handler() {
            return this;
        }


        @Override
        public void channelProcess(ChannelHandlerContext ctx, InWrapper<IN> inWrapper, OutWrapper<OUT> outWrapper) throws Exception {
            logger.info("head:channelProcess");
            ctx.fireChannelProcess(inWrapper, outWrapper);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause, InWrapper<IN> inWrapper, OutWrapper<OUT> outWrapper) throws Exception {
            logger.info("head:exceptionCaught");
        }
    }

    @Override
    public ChannelPipeline process(InWrapper<IN> inWrapper,
                                   OutWrapper<OUT> outWrapper) {
        head.process(inWrapper, outWrapper);
        return this;
    }

    @Override
    public ChannelHandlerContext head() {
        return head;
    }

    @Override
    public ChannelHandlerContext tail() {
        return tail;
    }

}
