package redis.dao.example9;

import java.util.Iterator;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@SpringBootApplication
public class ChangeDatabaseApplication implements CommandLineRunner {

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}
	
	@Bean
	StringRedisTemplate redisTemplate() {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(jedisConnectionFactory());		
		return template;
		
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ChangeDatabaseApplication.class, args).close();
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		StringRedisTemplate template = redisTemplate();
		String key = "1111111111111111";
		
		template.opsForZSet().add(key, "db1-contensts1", 101);
		template.opsForZSet().add(key, "db1-contensts2", 102);
		template.opsForZSet().add(key, "db1-contensts3", 103);
		
		JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) template.getConnectionFactory();
		jedisConnectionFactory.setDatabase(1);
		
		template.opsForZSet().add(key, "db2-contensts1", 101);
		template.opsForZSet().add(key, "db2-contensts2", 102);
		template.opsForZSet().add(key, "db2-contensts3", 103);
		
		
		Set<String> set = template.opsForZSet().reverseRange(key, 0, 10);
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		
		System.out.println("change 0 ######## ");
		jedisConnectionFactory.setDatabase(0);
		
		set = template.opsForZSet().reverseRange(key, 0, 10);
		iterator = set.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		
	}
}
