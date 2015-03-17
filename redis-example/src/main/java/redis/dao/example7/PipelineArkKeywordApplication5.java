package redis.dao.example7;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.etc.example.ArkKeyword;

@Configuration
@SpringBootApplication
public class PipelineArkKeywordApplication5 implements CommandLineRunner {


	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		return jedisConnectionFactory;
	}
	
	@Bean
	RedisTemplate<String, ArkKeyword> redisTemplate() {

		final RedisTemplate<String, ArkKeyword> template =  new RedisTemplate<String, ArkKeyword>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JacksonJsonRedisSerializer<ArkKeyword>(ArkKeyword.class));	
		return template;
		
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(PipelineArkKeywordApplication5.class, args).close();
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		RedisTemplate<String, ArkKeyword> tempLate = redisTemplate();
		
		SessionCallback<List<ArkKeyword>> sessionCallback = new SessionCallback<List<ArkKeyword>>() {  
            @Override  
            public List<ArkKeyword> execute(RedisOperations operations) throws DataAccessException {  
                operations.multi();  
                
                BoundValueOperations<String, ArkKeyword> oper1 = operations.boundValueOps("arkkeyword1");
                ArkKeyword result1 = oper1.get();
                
                BoundValueOperations<String, ArkKeyword> oper2 = operations.boundValueOps("arkkeyword2");
                ArkKeyword result2 = oper2.get();
                
                List<ArkKeyword> ss = operations.exec();
                	
                return ss;  
            }  
        };  
        List<ArkKeyword> lists = tempLate.execute(sessionCallback);
        for(int i = 0 ; i < lists.size() ; i ++) System.out.println(lists.get(i).getCid());
	}
	
}
