package com.feiniaojin.ddd.ecosystem.pie.Channel;

import com.feiniaojin.ddd.ecosystem.pie.ChannelPipeline.ChannelPipeline;

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
