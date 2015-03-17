package redis.etc.example;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class JacksonJsonRedisTemplate extends RedisTemplate<String, ArkKeyword> {
	
	public JacksonJsonRedisTemplate() {
		
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		JacksonJsonRedisSerializer<ArkKeyword> jacksonJsonRedisSerializer = new JacksonJsonRedisSerializer<ArkKeyword>(ArkKeyword.class);
		setKeySerializer(stringSerializer);
		setValueSerializer(jacksonJsonRedisSerializer);
		setHashKeySerializer(stringSerializer);
		setHashValueSerializer(jacksonJsonRedisSerializer);

	}
	
	public JacksonJsonRedisTemplate(RedisConnectionFactory connectionFactory) {
		this();
		setConnectionFactory(connectionFactory);
		afterPropertiesSet();
	}

	protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
		return new DefaultStringRedisConnection(connection);
	}
}