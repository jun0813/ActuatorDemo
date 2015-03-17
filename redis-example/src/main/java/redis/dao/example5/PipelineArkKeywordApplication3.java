package redis.dao.example5;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.etc.example.ArkKeyword;

@Configuration
@SpringBootApplication
public class PipelineArkKeywordApplication3 implements CommandLineRunner {

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
		SpringApplication.run(PipelineArkKeywordApplication3.class, args).close();
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		RedisTemplate<String, ArkKeyword> tempLate = redisTemplate();
		
		SessionCallback<ArkKeyword> sessionCallback = new SessionCallback<ArkKeyword>() {  
            @Override  
            public ArkKeyword execute(RedisOperations operations) throws DataAccessException {  
                operations.multi();  

                BoundZSetOperations<String, ArkKeyword> oper1 = operations.boundZSetOps("arkkeyword1z");
                ArkKeyword a1 = new ArkKeyword();
                a1.setCid("111");
                a1.setKeyword("111name");
                oper1.add(a1, 100);
                oper1.expire(60, TimeUnit.MINUTES);  
                
                BoundZSetOperations<String, ArkKeyword> oper2 = operations.boundZSetOps("arkkeyword2z"); 
                ArkKeyword a2 = new ArkKeyword();
                a2.setCid("222");
                a2.setKeyword("222name");  
                oper2.add(a2, 101);
                oper2.expire(60, TimeUnit.MINUTES);
                
                operations.exec();  
                return null;  
            }  
        };  
        tempLate.execute(sessionCallback);
        
	}
	
}
