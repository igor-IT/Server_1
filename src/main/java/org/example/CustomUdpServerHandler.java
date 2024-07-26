package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.example.gen.UserMovementOuterClass;

public class CustomUdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        // Получаем данные из DatagramPacket
        ByteBuf buf = msg.content();
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        UserMovementOuterClass.UserMovement receivedMessage = UserMovementOuterClass.UserMovement.parseFrom(data);
        System.out.println("UDP message received: "+receivedMessage.toString());

        // Пример ответа может выглядеть следующим образом:
        // ctx.write(new DatagramPacket(Unpooled.copiedBuffer("Response message", CharsetUtil.UTF_8), msg.sender()));
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
