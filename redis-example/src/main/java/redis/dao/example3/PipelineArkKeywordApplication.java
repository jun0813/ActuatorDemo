package redis.dao.example3;

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
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@SpringBootApplication
public class PipelineArkKeywordApplication<V> implements CommandLineRunner {

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setDatabase(1); // 1번 ==> db1
		return jedisConnectionFactory;
	}
	
	@Bean
	StringRedisTemplate redisTemplate() {
		
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(jedisConnectionFactory());
		/*final RedisTemplate<String, ArkKeyword> template =  new RedisTemplate<String, ArkKeyword>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JacksonJsonRedisSerializer<ArkKeyword>(ArkKeyword.class));*/
		
		return template;
		
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(PipelineArkKeywordApplication.class, args).close();
	}
	
	@Override
	public void run(String... args) throws Exception {
		StringRedisTemplate tempLate = redisTemplate();
		
		SessionCallback<String> sessionCallback = new SessionCallback<String>() {  
            @Override  
            public String execute(RedisOperations operations) throws DataAccessException {  
                operations.multi();  

                BoundValueOperations<String, String> oper = operations.boundValueOps("sdhfksdhfkhsdkfhs3");  
                oper.set("jsdkfhsdkfhds1");  
                oper.expire(60, TimeUnit.MINUTES);  
                
                
                
                BoundValueOperations<String, String> oper2 = operations.boundValueOps("sdhfksdhfkhsdkfhs4");  
                oper2.set("jsdkfhsdkfhds2");  
                oper2.expire(60, TimeUnit.MINUTES);
                
                operations.exec();  
                return null;  
            }  
        };  
        tempLate.execute(sessionCallback);  
		
		/*List<Object> results = tempLate.executePipelined(
				
				  new RedisCallback<Object>() {
					  @Override
				    public Object doInRedis(RedisConnection connection) throws DataAccessException {
	;
				      for(int i=0; i< 3; i++) {
				        stringRedisConn.rPop("myqueue");
				        
				      }
				connection.get  Range("ark:f:1111".getBytes(), 0, 10);
				      //
				    return null;
				  }
				});*/
		//System.out.println(results);
		/*RedisConnectionFactory conFactory = tempLate.getConnectionFactory();
		RedisConnection con = RedisConnectionUtils.bindConnection(conFactory);
		con.openPipeline();*/
		
		
		//tempLate.boundZSetOps("ark:f:1111").reverseRange(0, 1);
		//Set<String> set = tempLate.opsForZSet().reverseRange("ark:f:1111", 0, 10);		// 전방검색
		
		/*List<Object> result = null;			

		RedisConnectionFactory conFactory = tempLate.getConnectionFactory();

		RedisConnection con = RedisConnectionUtils.bindConnection(conFactory);

		try {

			con.openPipeline();
					

			tempLate.boundValueOps("aaa").;
			tempLate.boundValueOps("bbb").get();

			} finally {

				result = con.closePipeline();

			}

					

		    RedisConnectionUtils.unbindConnection(conFactory);

	
		for(Object str : result){
			System.out.println((String)str);
		}*/
		/*Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}*/
		/*tempLate.opsForZSet().reverseRange("ark:b:1112", 0, 1);// 후방검색
		tempLate.opsForZSet().reverseRange("ark:c:1113", 0, 1);		// 카테고리 검색
*/		/*List<Object> results = *///con.closePipeline();
		
		/*for(int i  = 0 ; i < results.size() ; i++ ){
			LinkedHashSet linkedHashSet = (LinkedHashSet) results.get(i);
			Iterator iterator = linkedHashSet.iterator();
			while(iterator.hasNext()){
				System.out.println((String)iterator.next());
			}
			//System.out.println(.);
		}*/
		System.out.println("dsfsd");
		//JacksonJsonRedisTemplate jacksonJsonRedisTemplate = redisTemplate();
		
		/*List<Object> results = jacksonJsonRedisTemplate.executePipelined(
				  new RedisCallback<Object>() {
				    public Object doInRedis(RedisConnection connection) throws DataAccessException {
				     
				    	return connection.get;
				  }
				});*/
		
		/*ArkKeyword arkKeyword1 = new ArkKeyword();
		arkKeyword1.setCategory("유아");
		arkKeyword1.setName("젖병");
		jacksonJsonRedisTemplate.opsForZSet().add("ark:f:11114", arkKeyword1, 100);
		
		ArkKeyword arkKeyword2 = new ArkKeyword();
		arkKeyword2.setCategory("의상");
		arkKeyword2.setName("남성의류");
		jacksonJsonRedisTemplate.opsForZSet().add("ark:b:11124", arkKeyword1, 100);
		
		ArkKeyword arkKeyword3 = new ArkKeyword();
		arkKeyword3.setCategory("의상");
		arkKeyword3.setName("남성의류");
		jacksonJsonRedisTemplate.opsForZSet().add("ark:c:11134", arkKeyword1, 100);*/
		
		
		
		/*List<Object> results = jacksonJsonRedisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.g
				return null;
			}
		});*/
		
		/*RedisConnectionFactory redisConnectionFactory = redisTemplate().getConnectionFactory();
		RedisConnection redisConnection = RedisConnectionUtils.bindConnection(redisConnectionFactory);
		List<Object> result = null;*/
		//ObjectMapper mapper = new ObjectMapper();
		//mapper.registerSubtypes(ArkKeywordResult.class);
		/*jacksonJsonRedisTemplate.multi();
		List<Object> txResults = jacksonJsonRedisTemplate.execute(new SessionCallback<List<Object>>() {

			@Override
			public <String, Object> List<java.lang.Object> execute(RedisOperations<String, Object> operations) throws DataAccessException {
				operations.boundZSetOps((String) "ark:f:1111").reverseRange(0, 1);		// 전방검색
		    	operations.boundZSetOps((String) "ark:b:1112").reverseRange(0, 1);		// 후방검색
		    	operations.boundZSetOps((String) "ark:c:1113").reverseRange(0, 1);		// 카테고리 검색
		        return operations.exec();
			}
		});
		
		System.out.println("dfds");*/
		/*
		try {
			redisConnection.openPipeline();
			try {
				jacksonJsonRedisTemplate.
				jacksonJsonRedisTemplate.boundZSetOps("ark:f:1111").reverseRange(0, 1);		// 전방검색
				jacksonJsonRedisTemplate.boundZSetOps("ark:b:1112").reverseRange(0, 1);		// 후방검색
				jacksonJsonRedisTemplate.boundZSetOps("ark:c:1113").reverseRange(0, 1);		// 카테고리 검색

				//jacksonJsonRedisTemplate.
			} catch(Exception e){
				e.printStackTrace();
			} finally {
				
				result = redisConnection.closePipeline();*/
				/*for(int i  = 0 ; i < result.size() ; i++ ){
					Set<Object> sets = (Set<Object>) result.get(i);
					Iterator<Object> iterator = sets.iterator();
					while(iterator.hasNext()) {
						Object object = iterator.next();
						System.out.println(object);
						//ArkKeyword arkKeyword = mapper.convertValue(object, ArkKeyword.class);
						//System.out.println(String.format("%s : %s", arkKeyword.getCategory(), arkKeyword.getName())); 
					}
					Object object = result.get(i);
					mapper.convertValue(object, ArkKeyword.class);
					Set<ArkKeyword> sets = (Set<ArkKeyword>) result.get(i);
					Iterator<ArkKeyword> iterator = sets.iterator();
					while(iterator.hasNext()) {
						ArkKeyword setElement = iterator.next();
				    	System.out.println(String.format("%s : %s", setElement.getCategory(), setElement.getName())); 
				    }
				}*/
			//}
		/*} catch(Exception e){
			e.printStackTrace();
		} finally {
			RedisConnectionUtils.unbindConnection(redisConnectionFactory);
		}*/
	}

}
