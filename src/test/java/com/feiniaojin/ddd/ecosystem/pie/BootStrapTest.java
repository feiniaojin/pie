package com.feiniaojin.ddd.ecosystem.pie;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertSame;

public class BootStrapTest {

    @Test
    public void shouldBuildConfiguredBootStrap() {
        DefaultChannel channel = new DefaultChannel();
        Object inbound = new Object();
        Object outbound = new Object();

        BootStrap bootStrap = BootStrap.builder()
                .channel(channel)
                .inboundParameter(inbound)
                .outboundFactory(() -> outbound)
                .build();

        assertSame(outbound, bootStrap.process());
    }

    @Test
    public void shouldAddChannelHandlerFromBuilder() {
        AtomicBoolean handled = new AtomicBoolean();

        BootStrap bootStrap = BootStrap.builder()
                .channel(new DefaultChannel())
                .inboundParameter(new Object())
                .outboundFactory(Object::new)
                .addChannelHandlerAtLast("handler", new ChannelHandlerAdapter() {
                    @Override
                    public void channelProcess(ChannelHandlerContext ctx, Object in, Object out) {
                        handled.set(true);
                    }
                })
                .build();

        bootStrap.process();

        assertSame(true, handled.get());
    }

    @Test
    public void shouldUseDefaultChannelWhenChannelIsNotConfigured() {
        Object outbound = new Object();

        BootStrap bootStrap = BootStrap.builder()
                .inboundParameter(new Object())
                .outboundFactory(() -> outbound)
                .build();

        assertSame(outbound, bootStrap.process());
    }
}
