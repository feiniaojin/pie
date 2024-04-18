package com.feiniaojin.ddd.ecosystem.pie;

/**
 * 通道
 *
 */
public interface Channel {

    Channel process(Object in,
                    Object out);

    ChannelPipeline pipeline();

    interface ChannelProcessor {
        void doProcess(Object in,
                       Object out);
    }
}
