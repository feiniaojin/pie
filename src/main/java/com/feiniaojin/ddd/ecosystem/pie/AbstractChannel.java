package com.feiniaojin.ddd.ecosystem.pie;

import com.feiniaojin.ddd.ecosystem.pie.Channel.Channel;
import com.feiniaojin.ddd.ecosystem.pie.ChannelPipeline.ChannelPipeline;
import com.feiniaojin.ddd.ecosystem.pie.ChannelPipeline.impl.DefaultChannelPipeline;

/**
 * 抽象通道
 *
 */
public abstract class AbstractChannel implements Channel {

    private DefaultChannelPipeline pipeline;

    private ChannelProcessor processor = new DefaultChannelProcessorImpl();

    protected AbstractChannel() {
        pipeline = newChannelPipeline();
    }

    protected DefaultChannelPipeline newChannelPipeline() {
        return new DefaultChannelPipeline(this);
    }

    @Override
    public ChannelPipeline pipeline() {
        return pipeline;
    }

    public Channel process(Object inWrapper, Object outWrapper) {
        processor.doProcess(inWrapper, outWrapper);
        return this;
    }

    private class DefaultChannelProcessorImpl implements ChannelProcessor {

        @Override
        public void doProcess(Object inWrapper, Object outWrapper) {
            pipeline.process(inWrapper, outWrapper);
        }
    }
}
