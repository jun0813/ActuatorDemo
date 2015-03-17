package redis.dao.example1;

import java.util.Iterator;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.etc.example.ArkKeyword;

@Configuration
@SpringBootApplication
public class SampleRedisApplication implements CommandLineRunner {
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}
	
	@Bean
	RedisTemplate< String, ArkKeyword > redisTemplate() {
		final RedisTemplate<String, ArkKeyword> template =  new RedisTemplate<String, ArkKeyword>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JacksonJsonRedisSerializer<ArkKeyword>(ArkKeyword.class));
		return template;
	}

	
	@Override
	public void run(String... args) throws Exception {
		
		// make a inserted Object 
		ArkKeyword arkeyword = new ArkKeyword();
		arkeyword.setCid("man");
		arkeyword.setKeyword("shirts");
		
		// make template Obejct
		RedisTemplate<String, ArkKeyword> template = redisTemplate();
		
		// insert
		boolean status = template.opsForZSet().add("man:shirts", arkeyword, 100);
		System.out.println(String.format("insert status is %s", status));
		System.out.println("#############################################");
		
		// select
		Set<ArkKeyword> sets = template.opsForZSet().reverseRange("man:shirts", 0, 2);
		Iterator<ArkKeyword> iterator = sets.iterator();
		while(iterator.hasNext()) {
			ArkKeyword setElement = iterator.next();
	    	System.out.println(String.format("%s : %s", setElement.getCid(), setElement.getKeyword())); 
	    }
		
	}

	public static void main(String[] args) throws Exception {
		// Close the context so it doesn't stay awake listening for redis
		SpringApplication.run(SampleRedisApplication.class, args).close();
	}
}