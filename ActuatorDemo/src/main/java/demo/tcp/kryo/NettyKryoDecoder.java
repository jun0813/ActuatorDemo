package demo.tcp.kryo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import demo.model.User;

@Component
@Qualifier("nettyKryoDecoder")
public class NettyKryoDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		
		msg.readByte();  // reseve
		msg.readByte();  // type
		short bodySize = msg.readShort(); // body size
		byte[] body = new byte[bodySize];  
		int size = msg.readableBytes();
		Kryo kryo = new Kryo();  
		Input input = null;
		try {  
			input = new Input(msg.readBytes(size).array());
			//input.read();
			/*user = (User)kryo.readClassAndObject(input);*/
			/*if(user != null) out.add((Object)user);*/
			out.add(kryo.readClassAndObject(input));
		} finally {  
			input.close();
		}
	        
	}

}
