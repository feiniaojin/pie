package com.feiniaojin.pie;

/**
 * 抽象通道
 *
 * @author: <a href=mailto:qinyujie3@jd.com>qinyujie3</a>
 */
public abstract class AbstractChannel<IN, OUT> implements Channel<IN, OUT> {

    private DefaultChannelPipeline pipeline;

    private ChannelProcessor processor = new DefaultChannelProcessorImpl();

    protected AbstractChannel() {
        pipeline = newChannelPipeline();
    }

    protected DefaultChannelPipeline newChannelPipeline() {
        return new DefaultChannelPipeline(this);
    }

    @Override
    public ChannelPipeline<IN, OUT> pipeline() {
        return pipeline;
    }

    public Channel process(InWrapper<IN> inWrapper, OutWrapper<OUT> outWrapper) {
        processor.doProcess(inWrapper, outWrapper);
        return this;
    }

    private class DefaultChannelProcessorImpl implements ChannelProcessor {

        @Override
        public void doProcess(InWrapper inWrapper, OutWrapper outWrapper) {
            pipeline.process(inWrapper, outWrapper);
        }
    }
}
