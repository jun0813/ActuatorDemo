package redis.dao.example4;

import java.util.concurrent.TimeUnit;

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
public class PipelineArkKeywordApplication2 implements CommandLineRunner {

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
		SpringApplication.run(PipelineArkKeywordApplication2.class, args).close();
	}
	
	@Override
	public void run(String... args) throws Exception {
		RedisTemplate<String, ArkKeyword> tempLate = redisTemplate();
		
		SessionCallback<ArkKeyword> sessionCallback = new SessionCallback<ArkKeyword>() {  
            @Override  
            public ArkKeyword execute(RedisOperations operations) throws DataAccessException {  
                operations.multi();  

                BoundValueOperations<String, ArkKeyword> oper = operations.boundValueOps("arkkeyword1");
                ArkKeyword a1 = new ArkKeyword();
                a1.setCid("111");
                a1.setKeyword("111name");
                oper.set(a1);  
                oper.expire(60, TimeUnit.MINUTES);  
                
                
                
                BoundValueOperations<String, ArkKeyword> oper2 = operations.boundValueOps("arkkeyword2"); 
                ArkKeyword a2 = new ArkKeyword();
                a2.setCid("222");
                a2.setKeyword("222name");  
                oper2.set(a2);  
                oper2.expire(60, TimeUnit.MINUTES);
                
                operations.exec();  
                return null;  
            }  
        };  
        tempLate.execute(sessionCallback);  
	}

}
