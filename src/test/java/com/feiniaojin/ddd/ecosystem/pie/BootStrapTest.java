package com.feiniaojin.ddd.ecosystem.pie;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertSame;

public class BootStrapTest {

    @Test
    public void shouldBuildConfiguredBootStrap() {
        DefaultChannel<Object, String> channel = new DefaultChannel<>();
        Object inbound = new Object();
        BootStrap<Object, String> bootStrap = BootStrap.<Object, String>builder()
                .channel(channel)
                .inboundParameter(inbound)
                .outboundFactory(() -> "outbound")
                .build();

        assertSame("outbound", bootStrap.process());
    }

    @Test
    public void shouldAddChannelHandlerFromBuilder() {
        AtomicBoolean handled = new AtomicBoolean();

        BootStrap<String, String> bootStrap = BootStrap.<String, String>builder()
                .channel(new DefaultChannel<>())
                .inboundParameter("str")
                .outboundFactory(() -> "outbound")
                .addChannelHandlerAtLast("handler", new ChannelHandlerAdapter<String, String>() {
                    @Override
                    public void channelProcess(ChannelHandlerContext<String, String> ctx,
                                               String in,
                                               String out) {
                        handled.set(true);
                    }
                })
                .build();

        bootStrap.process();

        assertSame(true, handled.get());
    }

    @Test
    public void shouldUseDefaultChannelWhenChannelIsNotConfigured() {
        BootStrap<Object, String> bootStrap = BootStrap.<Object, String>builder()
                .inboundParameter(new Object())
                .outboundFactory(() -> "outbound")
                .build();

        assertSame("outbound", bootStrap.process());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldRejectMissingInboundParameter() {
        BootStrap.<Object, String>builder()
                .inboundParameter(null)
                .outboundFactory(() -> "outbound")
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldRejectMissingOutboundFactory() {
        BootStrap.<Object, String>builder()
                .inboundParameter(new Object())
                .outboundFactory(null)
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldRejectAddingHandlerBeforeChannelIsConfigured() {
        BootStrap.<Object, String>builder()
                .inboundParameter(new Object())
                .outboundFactory(() -> "outbound")
                .addChannelHandlerAtLast("handler", new ChannelHandlerAdapter<Object, String>() {
                    @Override
                    public void channelProcess(ChannelHandlerContext<Object, String> ctx,
                                               Object in,
                                               String out) {
                    }
                });
    }
}
