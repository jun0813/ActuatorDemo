package redis.dao.example6;

import java.util.List;
import java.util.Set;

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
public class PipelineArkKeywordApplication4 implements CommandLineRunner {

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
		SpringApplication.run(PipelineArkKeywordApplication4.class, args).close();
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		RedisTemplate<String, ArkKeyword> tempLate = redisTemplate();
		
		SessionCallback<List<Set<ArkKeyword>>> sessionCallback = new SessionCallback<List<Set<ArkKeyword>>>() {  
            @Override  
            public List<Set<ArkKeyword>> execute(RedisOperations operations) throws DataAccessException {  
                operations.multi();  

                BoundZSetOperations<String, ArkKeyword> oper1 = operations.boundZSetOps("arkkeyword1z");
                oper1.reverseRange(0, 10);
                
                BoundZSetOperations<String, ArkKeyword> oper2 = operations.boundZSetOps("arkkeyword2z");
                oper2.reverseRange(0, 10);
                
                //Set<ArkKeyword> sets = operations.boundZSetOps("arkkeyword1z").reverseRange(0, 2);
                //operations.boundZSetOps("arkkeyword2z").range(0, 10);
                //ist<ArkKeyword> lists = operations.exec();
                //for(int i = 0 ; i < lists.size() ; i ++) System.out.println(lists.get(i).getName());
                //for(int i = 0 ; i < lists.size() ; i ++) System.out.println(lists.get(i).iterator().next().getName());
                
                List<Set<ArkKeyword>> lists = operations.exec();
                
                return lists;  
            }  
        }; 
        int max = 500000;
        long startStamp1 = System.currentTimeMillis();
        for(int i = 0 ; i < max ; i++) {
        	List<Set<ArkKeyword>> lists = tempLate.execute(sessionCallback);
        }
        System.out.println((System.currentTimeMillis() - startStamp1) /1000);
        System.out.println("#####################################################################################################################");
        long startStamp2 = System.currentTimeMillis();
        for(int i = 0 ; i < max ; i++) {
        	Set<ArkKeyword> set1 = tempLate.opsForZSet().reverseRange("arkkeyword1z", 0, 10);
        	Set<ArkKeyword> set2 = tempLate.opsForZSet().reverseRange("arkkeyword2z", 0, 10);
        }
        System.out.println((System.currentTimeMillis() - startStamp2) /1000);
        
        /*for(int i = 0 ; i < lists.size() ; i ++) {
        	Set<ArkKeyword> set = lists.get(i);
        	Iterator<ArkKeyword> iterator = set.iterator();
        	while(iterator.hasNext()){
        		ArkKeyword arkKeyword = iterator.next();
        		System.out.println(String.format("%s, %s", arkKeyword.getName(), arkKeyword.getCategory()));
        	}
        }*/
	}
	
}
