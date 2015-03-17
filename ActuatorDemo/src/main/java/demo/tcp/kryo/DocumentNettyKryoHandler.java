package demo.tcp.kryo;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import demo.model.Document;

@Component
@Qualifier("documentNettyKryoHandler")
@Sharable
public class DocumentNettyKryoHandler extends SimpleChannelInboundHandler<Document>{

	private final Logger logger = LoggerFactory.getLogger(DocumentNettyKryoHandler.class);
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel is active\n");
		super.channelActive(ctx);
	}
	

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Document msg) throws Exception {
		logger.debug("##############################################################################");
		logger.debug(String.format("SERVER::channelRead0 : %s, %s", msg.getTitle(), msg.getContents()));
		logger.debug("##############################################################################");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("\nChannel is disconnected");
		super.channelInactive(ctx);
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	System.out.println("exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }
	
}
