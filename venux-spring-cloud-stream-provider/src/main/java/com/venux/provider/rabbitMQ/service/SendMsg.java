package com.venux.provider.rabbitMQ.service;

import org.springframework.integration.core.MessageSource;

public interface SendMsg {

	public MessageSource<Integer> timerMessageSource();
}
