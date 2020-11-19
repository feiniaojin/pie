package com.feiniaojin.pie;

/**
 * 通道
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
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
