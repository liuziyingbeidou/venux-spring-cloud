package com.venux.redis.constant;

/** 
 * @ClassName: ZredisPrefixConstant 
 * @Description: redis存储前缀
 * @author liuzy
 * @date 2018年10月11日 上午8:58:16  
 */
public interface ZredisConstant {

	public static enum CommunicationModeEnum{
		/**
		 * ,广播, 单播
		 */
		BROADCAST(1),UNICAST(2);
		
		private Integer value;
		
		private CommunicationModeEnum(int value){
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}
		
	}
	
	/**
	 * key 间分隔符
	 */
	public static String KEY_SEPARATOR = ":";
	
	/**
	 * Redis 数据类型 string(字符串)
	 */
	public static String ZREDIS_TYPE_STRING = "STR";
	
	/**
	 * Redis 数据类型 hash(哈希)
	 */
	public static String ZREDIS_TYPE_HASH = "HASH";
	
	/**
	 * Redis 数据类型 list(列表)
	 */
	public static String ZREDIS_TYPE_LIST = "LIST";
	
	/**
	 * Redis 数据类型 set(集合)
	 */
	public static String ZREDIS_TYPE_SET = "SET";
	
	/**
	 * Redis 数据类型 zset(有序集合)
	 */
	public static String ZREDIS_TYPE_ZSET = "ZSET";
	
	/**
	 * Redis 订阅发布
	 */
	public static String ZREDIS_TYPE_PUBSUB = "PUBSUB";
	
	/**
	 * 缓存默认超时时间
	 */
	public final static long CACHE_EXPIRESECONDS = 60L * 1 * 60;
	
	/**
	 * 默认超时时间
	 */
	public static long DISTLOCK_EXPIRESECONDS = 60L * 2;
	
	/**
	 * 最大重试次数,如果获取锁失败
	 */
	public static  int DISTLOCK_MAX_RETRY_TIMES = 3;
	
	/**
	 * 每次重试之前sleep等待的毫秒数
	 */
	public static  long DISTLOCK_RETRY_INTERVAL_TIME_MILLIS = 1000L;
	
	/**
	 * hystrix配置超时时间
	 */
	public long HYSTRIX_TIMEOUT = 1000L;
	
	/**
	 * 加锁重试接口hystrix超时时间
	 * @return the distlockRetryApiHystrixTimeout
	 */
	public static final String DISTLOCK_RETRY_API_HYSTRIX_TIMEOUT = (ZredisConstant.DISTLOCK_RETRY_INTERVAL_TIME_MILLIS  * (ZredisConstant.DISTLOCK_MAX_RETRY_TIMES+2) )+ HYSTRIX_TIMEOUT +"";

	/**
	 * 仓储接口返回成功状态码
	 */
	public static final Integer API_CODE = 0;
	
	/**
	 * redis 增量
	 */
	public static final double DELTA_DEFAULT = 1L;
	
	/**
	 * redis操作返回码 0
	 */
	public static final int REDIS_RETURN_CODE_0 = 0;
	
	/**
	 * RestTemplate实例化方式 
	 * 1-注入
	 * 2-new
	 */
	public static final int TEMPLATE_TYPE_REMOTE = 1;
	public static final int TEMPLATE_TYPE_LOCAL = 2;
	
	/**
	 * 通讯模式
	 * 1-广播
	 * 2-单播
	 */
	public static final int MODE_BROADCAST = 1;
	public static final int MODE_UNICAST = 2;
	
}
