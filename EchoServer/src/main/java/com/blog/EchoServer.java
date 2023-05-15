package com.blog;

import java.net.InetSocketAddress;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.GlobalEventExecutor;

public class EchoServer {

    private static final int SERVER_PORT = 11011;

    private final ChannelGroup allChannels = new DefaultChannelGroup("server", GlobalEventExecutor.INSTANCE);
    private EventLoopGroup bossEventLoopGroup;
    private EventLoopGroup workerEventLoopGroup;

    public void startServer() {

        bossEventLoopGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("boss"));
        workerEventLoopGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("worker"));

        // Boss Thread는 ServerSocket을 Listen
        // Worker Thread는 만들어진 Channel에서 넘어온 이벤트를 처리
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossEventLoopGroup, workerEventLoopGroup);

        // Channel 생성시 사용할 클래스 (NIO 소켓을 이용한 채널)
        bootstrap.channel(NioServerSocketChannel.class);

        // accept 되어 생성되는 TCP Channel 설정
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

        // Client Request를 처리할 Handler 등록
        bootstrap.childHandler(new EchoServerInitializer());

        try {
            // Channel 생성후 기다림
            ChannelFuture bindFuture = bootstrap.bind(new InetSocketAddress(SERVER_PORT)).sync();
            Channel channel = bindFuture.channel();
            allChannels.add(channel);

            // Channel이 닫힐 때까지 대기
            bindFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    private void close() {

        allChannels.close().awaitUninterruptibly();
        workerEventLoopGroup.shutdownGracefully().awaitUninterruptibly();
        bossEventLoopGroup.shutdownGracefully().awaitUninterruptibly();
    }

    public static void main(String[] args) throws Exception {
        new EchoServer().startServer();
    }
}
