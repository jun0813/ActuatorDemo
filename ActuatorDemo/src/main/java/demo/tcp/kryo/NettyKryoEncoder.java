package demo.tcp.kryo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

//http://dreamworker.iteye.com/blog/1946100
@Component
@Qualifier("nettyKryoEncoder")
public class NettyKryoEncoder extends MessageToByteEncoder {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

		Kryo kryo = new Kryo();  
        Output output = new Output(200);  
        
        try { 
	        kryo.writeClassAndObject(output, msg);  
	        output.flush();  
	        output.close();
        } catch (Exception e){
        	e.printStackTrace();
        }
        
        out.writeByte((byte) 0);  					// reseve
        out.writeByte((byte) 1);  					// type
        byte[] byteArray = output.toBytes();
		short bodySize = (short)byteArray.length; 	// body size
        out.writeShort((short)bodySize);
        out.writeBytes(byteArray);
        ctx.writeAndFlush(out);
	}


}