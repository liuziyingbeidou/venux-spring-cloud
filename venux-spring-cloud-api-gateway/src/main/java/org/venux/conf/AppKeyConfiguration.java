package org.venux.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 秘钥key配置类
 */
@Component
@RefreshScope
public class AppKeyConfiguration {

	@Value("${appKey.fsAppKey}")
    private String fsAppKey;
	
	@Value("${servletPath}")
	private String servletPath;
	
	public String getServletPath() {
		return servletPath;
	}

	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

	public String getFsAppKey() {
		return fsAppKey;
	}

	public void setFsAppKey(String fsAppKey) {
		this.fsAppKey = fsAppKey;
	}
}

