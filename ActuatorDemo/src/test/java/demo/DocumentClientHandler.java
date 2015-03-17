package demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import demo.model.Document;
import demo.model.User;

public class DocumentClientHandler extends SimpleChannelInboundHandler<Document> {

	@Override
    public void channelActive(ChannelHandlerContext ctx) {
		System.out.println("channelActive");
		Document msg = new Document();
		msg.setTitle("Title::netty in action");
		msg.setContents("Content::netty in action");
        ctx.write(msg);
    }
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Document msg) throws Exception {
		System.out.println("channelRead0");
	}
	

	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
    	System.out.println("channelReadComplete");
    	ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	System.out.println("exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }

}
