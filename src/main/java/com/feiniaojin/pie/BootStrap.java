package com.feiniaojin.pie;

/**
 * 启动配置类
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
 */
public class BootStrap<IN, OUT> {

    private Channel<IN, OUT> channel;

    private IN in;

    private OutFactory<OUT> outFactory;

    private OutWrapperFactory<OUT> outWrapperFactory = new DefaultOutWrapperFactory<OUT>();

    private InWrapperFactory<IN> inWrapperFactory = new DefaultInWrapperFactory<IN>();

    public BootStrap<IN, OUT> channel(Channel<IN, OUT> channel) {
        this.channel = channel;
        return this;
    }

    public BootStrap<IN, OUT> inboundParameter(IN in) {
        this.in = in;
        return this;
    }

    public BootStrap<IN, OUT> inWrapperFactory(InWrapperFactory<IN> inWrapperFactory) {
        this.inWrapperFactory = inWrapperFactory;
        return this;
    }

    public BootStrap<IN, OUT> outWrapperFactory(OutWrapperFactory<OUT> outWrapperFactory) {
        this.outWrapperFactory = outWrapperFactory;
        return this;
    }

    public BootStrap<IN, OUT> outFactory(OutFactory<OUT> outFactory) {
        this.outFactory = outFactory;
        return this;
    }

    public BootStrap<IN, OUT> addChannelHandlerAtLast(String name, ChannelHandler channelHandler) {
        this.channel.pipeline().addLast(name, channelHandler);
        return this;
    }

    public OUT process() {
        InWrapper<IN> inInWrapper = inWrapperFactory.newInstance(in);
        OutWrapper<OUT> outWrapper = outWrapperFactory.newInstance(outFactory.newInstance());
        channel.process(inInWrapper, outWrapper);
        return outWrapper.getOut();
    }
}
