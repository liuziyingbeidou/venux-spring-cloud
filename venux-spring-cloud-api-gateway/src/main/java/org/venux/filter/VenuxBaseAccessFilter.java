package org.venux.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.venux.conf.AppKeyConfiguration;

/**
 * @ClassName: VenuxBaseAccessFilter 
 * @Description: 接口访问过滤器
 * @author liuzy
 * @date 2018年8月3日 下午7:24:07
 */
public class VenuxBaseAccessFilter extends BaseAccessFilter {

    @Autowired
    AppKeyConfiguration appKeyConfiguration;

    @Override
    protected List<String> getServletPath() {
    	List<String> servletPathList = new ArrayList<>();
    	String servletPath = appKeyConfiguration.getServletPath();
    	servletPathList = Arrays.asList(servletPath.split(","));
        return servletPathList;
    }
    
    @Override
    protected List<String> getVenuxAppKeys() {
        List<String> hadesAppKeyList = new ArrayList<>();
        hadesAppKeyList.add(appKeyConfiguration.getFsAppKey());
        return hadesAppKeyList;
    }


}
