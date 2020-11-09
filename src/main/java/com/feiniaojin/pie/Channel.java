package com.feiniaojin.pie;

/**
 * 通道
 *
 * @author: <a href=mailto:qinyujie3@jd.com>qinyujie3</a>
 */
public interface Channel<IN, OUT> {

    Channel process(InWrapper<IN> inWrapper,
                                                                  OutWrapper<OUT> outWrapper);

    ChannelPipeline<IN, OUT> pipeline();

    interface ChannelProcessor<IN, OUT> {
        void doProcess(InWrapper<IN> inWrapper,
                       OutWrapper<OUT> outWrapper);
    }
}
