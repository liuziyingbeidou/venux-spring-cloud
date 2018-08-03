package org.venux.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * @ClassName: BaseAccessFilter 
 * @Description: 接口访问过滤器基础类
 * @author liuzy
 * @date 2018年8月3日 下午7:24:39
 */
@RestController
@RefreshScope
public abstract class BaseAccessFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(BaseAccessFilter.class);
    
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    protected abstract List<String> getServletPath();

    protected abstract List<String> getVenuxAppKeys();

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        String servletPath = request.getServletPath();
        String appKey = request.getHeader("appKey");
        if(!"POST".equals(request.getMethod())) {
        	return null;
        }

        //是否有效appkey
        for(String path : getServletPath()){
        	if(servletPath.startsWith(path)){
        		if(appKey == null){
					log.error(String.format("appKey is null %s request to %s ", request.getMethod(), request.getRequestURL().toString()));
					ctx.setSendZuulResponse(false);
					ctx.setResponseStatusCode(401);
					ctx.setResponseBody("appKey is null");
					return null;
				}
        		boolean isPass = false;
            	//appkey不空，有实现具体过滤器→走appkey校验
                List<String> venuxAppKeyList = getVenuxAppKeys();
                for (String venuxAppKey : venuxAppKeyList) {
                    if (venuxAppKey.equals(appKey)) {
                        isPass = true;
                        break;
                    }
                }
                if (!isPass) {
                    log.error(String.format("appKey is error %s request to %s ", request.getMethod(), request.getRequestURL().toString()));
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseStatusCode(401);
                    ctx.setResponseBody("appKey is error");
                    return null;
                }
            }
        }
        return null;
    }
}
