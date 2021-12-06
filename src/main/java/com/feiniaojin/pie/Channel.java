package com.feiniaojin.pie;

/**
 * 通道
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
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
