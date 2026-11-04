package com.feiniaojin.ddd.ecosystem.pie;

/**
 * 抽象通道
 *
 */
public abstract class AbstractChannel<I, O> implements Channel<I, O> {

    private DefaultChannelPipeline<I, O> pipeline;

    private ChannelProcessor<I, O> processor = new DefaultChannelProcessorImpl();

    protected AbstractChannel() {
        pipeline = newChannelPipeline();
    }

    protected DefaultChannelPipeline<I, O> newChannelPipeline() {
        return new DefaultChannelPipeline(this);
    }

    @Override
    public ChannelPipeline<I, O> pipeline() {
        return pipeline;
    }

    public Channel<I, O> process(I inWrapper, O outWrapper) {
        processor.doProcess(inWrapper, outWrapper);
        return this;
    }

    private class DefaultChannelProcessorImpl implements ChannelProcessor<I, O> {

        @Override
        public void doProcess(I inWrapper, O outWrapper) {
            pipeline.process(inWrapper, outWrapper);
        }
    }
}
