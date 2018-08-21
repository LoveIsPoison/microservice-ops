//package com.youyou.microservice.auth.server.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import redis.clients.jedis.JedisPoolConfig;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//
///**
// * @author joy
// */
//@Configuration
//public class RedisClusterConfig {
//   // <!--最大空闲数-->    
//	@Value("${redis.maxIdle}")
//    private int maxIdle;
//    //<!--连接池的最大数据库连接数  -->  
//	@Value("${redis.maxTotal}")
//    private int maxTotal;
//    //<!--最大建立连接等待时间-->    
//	@Value("${redis.maxWaitMillis}")
//    private int maxWaitMillis;
//    //<!--逐出连接的最小空闲时间 默认1800000毫秒(30分钟)-->  
//	@Value("${redis.minEvictableIdleTimeMillis}")
//    private int minEvictableIdleTimeMillis;
//    //<!--每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3-->  
//	@Value("${redis.numTestsPerEvictionRun}")
//    private int numTestsPerEvictionRun;
//    //<!--逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1-->  
//	@Value("${redis.timeBetweenEvictionRunsMillis}")
//    private int timeBetweenEvictionRunsMillis;
//    //<!--是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个-->  
//	@Value("${redis.testOnBorrow}")  
//    private boolean testOnBorrow;
//    //<!--在空闲时检查有效性, 默认false  -->  
//	@Value("${redis.testWhileIdle}")
//    private boolean testWhileIdle;
//
//	@Value("${redis.hostName}")
//	private String hostName;
//	@Value("${redis.port}")
//	private int port;
//	@Value("${redis.password}")
//	private String password;
//	@Value("${redis.timeout}")
//	private int timeout;
////	@Autowired
////    private ClusterConfigurationProperties clusterProperties;
//
////    @Bean
////    public RedisConnectionFactory connectionFactory() {
////        return new JedisConnectionFactory(new RedisClusterConfiguration(clusterProperties.getNodes()));
////    }
//
////	@Bean
////	public JedisPoolConfig jedisPoolConfig(){
////		JedisPoolConfig redis=new JedisPoolConfig();
////		redis.setMaxIdle(this.maxIdle);
////		redis.setMaxTotal(this.maxTotal);
////		redis.setMaxWaitMillis(this.maxWaitMillis);
////		redis.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
////		redis.setNumTestsPerEvictionRun(this.numTestsPerEvictionRun);
////		redis.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
////		redis.setTestOnBorrow(this.testOnBorrow);
////		redis.setTestWhileIdle(this.testWhileIdle);
////		return redis;
////	}
////	@Bean
////	public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig config){
////		JedisConnectionFactory factory=new JedisConnectionFactory();
////		factory.setPoolConfig(config);
////		factory.setHostName(this.hostName);
////		factory.setPort(port);
////		factory.setPassword(this.password);
////		factory.setTimeout(this.timeout);
////		return factory;
////	}
//	@Bean
//	public RedisTemplate RedisTemplate(JedisConnectionFactory factory){
//		RedisTemplate template =new RedisTemplate();
//		template.setConnectionFactory(factory);
//		StringRedisSerializer serializer=new StringRedisSerializer();
//		template.setKeySerializer(serializer);
//		GenericJackson2JsonRedisSerializer jsonSerializer=new GenericJackson2JsonRedisSerializer();
//		template.setValueSerializer(jsonSerializer);
//		StringRedisSerializer hashSerializer=new StringRedisSerializer();
//		template.setHashKeySerializer(hashSerializer);
//		GenericJackson2JsonRedisSerializer hashValueSerializer=new GenericJackson2JsonRedisSerializer();
//		template.setHashValueSerializer(hashValueSerializer);
//		return template;
//		
//	}
//}
