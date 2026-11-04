package com.feiniaojin.ddd.ecosystem.pie;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertSame;

public class BootStrapTest {

    @Test
    public void shouldBuildConfiguredBootStrap() {
        DefaultChannel channel = new DefaultChannel();
        Object inbound = new Object();
        BootStrap<String> bootStrap = BootStrap.builder(() -> "outbound")
                .channel(channel)
                .inboundParameter(inbound)
                .build();

        assertSame("outbound", bootStrap.process());
    }

    @Test
    public void shouldAddChannelHandlerFromBuilder() {
        AtomicBoolean handled = new AtomicBoolean();

        BootStrap<String> bootStrap = BootStrap.builder(() -> "outbound")
                .channel(new DefaultChannel())
                .inboundParameter(new Object())
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
        BootStrap<String> bootStrap = BootStrap.builder(() -> "outbound")
                .inboundParameter(new Object())
                .build();

        assertSame("outbound", bootStrap.process());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldRejectMissingInboundParameter() {
        BootStrap.builder(() -> "outbound")
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldRejectMissingOutboundFactory() {
        BootStrap.<String>builder()
                .inboundParameter(new Object())
                .build();
    }
}
