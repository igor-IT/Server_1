package org.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("WebSocketServer is starting...");
        EventLoopGroup boss = new NioEventLoopGroup(1);
        Bootstrap boot = new Bootstrap();
        boot.group(boss)
                .channel(NioDatagramChannel.class)
                .handler(new CustomUdpServerHandler());
        ChannelFuture future = boot.bind(9099).sync();
        future.addListener(evt -> System.out.println("Started ws server, active port:{9099}"));
        future.channel().closeFuture().addListener((evt) -> {
            System.out.println("WebSocketSocket is closing...");
            boss.shutdownGracefully();
        }).sync();
    }
}