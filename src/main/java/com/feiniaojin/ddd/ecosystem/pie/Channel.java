package com.feiniaojin.ddd.ecosystem.pie;

/**
 * 通道
 *
 */
public interface Channel<I, O> {

    Channel<I, O> process(I in,
                          O out);

    ChannelPipeline<I, O> pipeline();

    interface ChannelProcessor<I, O> {
        void doProcess(I in,
                       O out);
    }
}
