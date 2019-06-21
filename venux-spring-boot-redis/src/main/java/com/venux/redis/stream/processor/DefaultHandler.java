package com.venux.redis.stream.processor;

/**
 * 配置信息
 * spring:
 *   cloud:
 *      stream:
 *        rabbit:
 *          bindings:
 *            output-default:
 *              producer:
 *                exchangeType: fanout
 *            input-default:
 *              consumer:
 *                exchangeType: fanout
 *       bindings:
 *         output-default:
 *           destination: zredisPubSub
 *         input-default:
 *           group: group-default
 *           destination: zredisPubSub
 */


/** 
 * @ClassName: DefaultHandler 
 * @Description: 生产者, 消费者处理类
 * @author liuzy
 * @date 2019年3月18日 下午3:21:51  
 */
@Deprecated
public interface DefaultHandler {

	/**
	 * channel
	 */
	public String getChannel();
	
	/**
	 * 消息发布
	 */
	public boolean publishMessage(String message);
	
	/**
	 * 接收消息
	 */
	public void receiveMessage(String message)  throws Exception;
	
	
}
