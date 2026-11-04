package com.feiniaojin.ddd.ecosystem.pie;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class HandlerChainTest {

    @Test
    public void shouldExecuteHandlersAndCreateOutput() {
        AtomicBoolean handled = new AtomicBoolean();
        HandlerChain<Object, String> chain = HandlerChain.builder(new DefaultChannel<Object, String>())
                .addHandler("handler", new ChannelHandlerAdapter<Object, String>() {
                    @Override
                    public void channelProcess(ChannelHandlerContext<Object, String> ctx,
                                               Object in,
                                               String out) {
                        handled.set(true);
                    }
                })
                .outboundFactory(() -> "outbound")
                .build();

        assertSame("outbound", chain.execute(new Object()));
        assertSame(true, handled.get());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldRejectMissingOutboundFactory() {
        HandlerChain.builder(new DefaultChannel<Object, String>()).build();
    }

    @Test
    public void shouldExecuteHandlersWithoutName() {
        HandlerChain<Object, List<String>> chain = HandlerChain.builder(new DefaultChannel<Object, List<String>>())
                .addHandler(new ChannelHandlerAdapter<Object, List<String>>() {
                    @Override
                    public void channelProcess(ChannelHandlerContext<Object, List<String>> ctx, Object in, List<String> out) {
                        out.add(this.getClass().getName());
                        ctx.fireChannelProcess(in, out);
                    }
                })
                .addHandler("second", new ChannelHandlerAdapter<Object, List<String>>() {
                    @Override
                    public void channelProcess(ChannelHandlerContext<Object, List<String>> ctx, Object in, List<String> out) {
                        out.add("second");
                        ctx.fireChannelProcess(in, out);
                    }
                })
                .outboundFactory(() -> new ArrayList<>(2))
                .build();

        List<String> res = chain.execute(new Object());

        System.out.println("结果: " + res);
        assertEquals(2, res.size());
        assertEquals("second", res.get(1));
    }

    @Test
    public void shouldExecuteHandlersInRegistrationOrder() {
        List<String> order = new ArrayList<>();

        HandlerChain<Object, String> chain = HandlerChain.builder(new DefaultChannel<Object, String>())
                .addHandler("first", new ChannelHandlerAdapter<Object, String>() {
                    @Override
                    public void channelProcess(ChannelHandlerContext<Object, String> ctx,
                                               Object in,
                                               String out) {
                        order.add("first");
                        ctx.fireChannelProcess(in, out);
                    }
                })
                .addHandler("second", new ChannelHandlerAdapter<Object, String>() {
                    @Override
                    public void channelProcess(ChannelHandlerContext<Object, String> ctx,
                                               Object in,
                                               String out) {
                        order.add("second");
                        ctx.fireChannelProcess(in, out);
                    }
                })
                .outboundFactory(() -> "outbound")
                .build();

        chain.execute(new Object());

        assertEquals(Arrays.asList("first", "second"), order);
    }

    @Test
    public void shouldSupportMultipleExecutions() {
        AtomicBoolean handled = new AtomicBoolean();

        HandlerChain<Object, String> chain = HandlerChain.builder(new DefaultChannel<Object, String>())
                .addHandler("handler", new ChannelHandlerAdapter<Object, String>() {
                    @Override
                    public void channelProcess(ChannelHandlerContext<Object, String> ctx,
                                               Object in,
                                               String out) {
                        handled.set(true);
                    }
                })
                .outboundFactory(() -> "outbound")
                .build();

        assertSame("outbound", chain.execute(new Object()));
        assertSame("outbound", chain.execute(new Object()));
        assertSame(true, handled.get());
    }

    @Test
    public void shouldRouteHandlerExceptionToExceptionCaught() {
        AtomicBoolean exceptionCaught = new AtomicBoolean();

        HandlerChain<Object, String> chain = HandlerChain.builder(new DefaultChannel<Object, String>())
                .addHandler("handler", new ChannelHandlerAdapter<Object, String>() {
                    @Override
                    public void channelProcess(ChannelHandlerContext<Object, String> ctx,
                                               Object in,
                                               String out) throws Exception {
                        throw new Exception("expected");
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext<Object, String> ctx,
                                                Throwable cause,
                                                Object in,
                                                String out) {
                        exceptionCaught.set(true);
                    }
                })
                .outboundFactory(() -> "outbound")
                .build();

        chain.execute(new Object());

        assertSame(true, exceptionCaught.get());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldRejectBuildingMoreThanOnce() {
        HandlerChain.Builder<Object, String> builder = HandlerChain.builder(new DefaultChannel<Object, String>())
                .outboundFactory(() -> "outbound");

        builder.build();
        builder.build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullHandlerName() {
        HandlerChain.builder(new DefaultChannel<Object, String>())
                .addHandler(null, new ChannelHandlerAdapter<Object, String>() {
                });
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullHandler() {
        HandlerChain.builder(new DefaultChannel<>())
                .addHandler("handler", null);
    }
}
