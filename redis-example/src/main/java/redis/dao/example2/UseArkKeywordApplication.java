package redis.dao.example2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.etc.example.ArkKeyword;
import redis.etc.example.JacksonJsonRedisTemplate;

@Configuration
@SpringBootApplication
public class UseArkKeywordApplication implements CommandLineRunner {

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setDatabase(1); // 1번 ==> db1
		return jedisConnectionFactory;
	}
	
	@Bean
	JacksonJsonRedisTemplate redisTemplate() {
		JacksonJsonRedisTemplate jacksonJsonRedisTemplate = new JacksonJsonRedisTemplate(); 
		jacksonJsonRedisTemplate.setConnectionFactory(jedisConnectionFactory());
		return jacksonJsonRedisTemplate;
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(UseArkKeywordApplication.class, args).close();
	}
	
	@Override
	public void run(String... args) throws Exception {
		ArkKeyword arkKeyword = new ArkKeyword();
		arkKeyword.setCid("유아");
		arkKeyword.setKeyword("젖병");
		redisTemplate().opsForZSet().add("aaaa1", arkKeyword, 100);
	}
	
}
