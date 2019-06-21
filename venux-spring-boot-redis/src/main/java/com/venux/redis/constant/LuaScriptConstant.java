package com.venux.redis.constant;


/** 
 * @ClassName: LuaScript 
 * @Description:  Lua脚本
 * @author liuzy
 * @date 2018年9月25日 下午3:15:25  
 */
public interface LuaScriptConstant {
	
	/**
	 * 解锁
	 */
    public static final String LUA_AND_UNLOCK =
            "if redis.call('get',KEYS[1]) == ARGV[1]\n" +
            "then\n" +
            "    return redis.call('del',KEYS[1])\n" +
            "else\n" +
            "    return 0\n" +
            "end";
    
    /**
     * 自增
     */
    public static final String LUA_INCRBY = "local current = redis.call('incrBy',KEYS[1],ARGV[1]);\n" +
		    " if current == tonumber(ARGV[1]) then\n" +
		    "     local t = redis.call('ttl',KEYS[1]);\n" +
		    "     if t == -1 then\n" +
		    "         redis.call('expire',KEYS[1],ARGV[2])\n" +
		    "     end;\n" +
		    " end;\n" +
		    " return current";
    
    /**
     * 含有效期
     * Lua实现 Redis Setnx（SET if Not eXists） 命令在指定的 key 不存在时，为 key 设置指定的值。
     * 设置成功，返回 1 。 设置失败，返回 0 
     */
    public static final String LUA_SETNX = "local sn = redis.call('SETNX',KEYS[1],ARGV[1]);\n"
    		+ "if sn == 1 then\n"
    		+ "   redis.call('expire',KEYS[1],ARGV[2])\n"
    		+ "end;"
    		+ "return sn;";
}
