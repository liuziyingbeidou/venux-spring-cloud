package com.venux.redis.utils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 *
 * <p>
 * <b>公共工具类Public</b>
 *
 * <ul>
 * <li>
 * </ul>
 *
 * <p>
 * <p>
 *
 * @version 1.0
 * @since 1.0
 */
public class PubUtils {

    // 空
    static public String STRING_NULL = "_________N/A________";

    // 0
    public static final Integer ZERO_INTEGER = Integer.valueOf(0);
    // 1
    public static final Integer ONE_INTEGER = Integer.valueOf(1);

    public static final BigDecimal ZERO_DECIMAL = new BigDecimal("0");
    public static final BigDecimal ONE_DECIMAL = new BigDecimal("1");

    /**
     * 判断一个或多个对象是否为非空
     *
     * @param values
     *            可变参数，要判断的一个或多个对象
     * @return 只有要判断的一个或多个对象都不为空则返回true,否则返回false
     */
    public static boolean isNotNull(Object... values) {
	if (!PubUtils.isNotNullAndNotEmpty(values)) {
	    return false;
	}
	for (Object value : values) {
	    boolean flag = true;
	    if (value instanceof Object[]) {
		flag = isNotNullAndNotEmpty((Object[]) value);
	    } else if (value instanceof Collection<?>) {
		flag = isNotNullAndNotEmpty((Collection<?>) value);
	    } else if (value instanceof String) {
		flag = !isOEmptyOrNull(value);
	    } else {
		flag = (null != value);
	    }
	    if (!flag) {
		return false;
	    }
	}
	return true;
    }

    /**
     * 判断一个或多个对象是否为空
     * 
     * @param values
     *            可变参数，要判断的一个或多个对象
     * @return 只有要判断的一个对象都为空则返回true,否则返回false
     */
    public static boolean isNull(Object... values) {
	if (!PubUtils.isNotNullAndNotEmpty(values)) {
	    return true;
	}
	for (Object value : values) {
	    boolean flag = false;
	    if (value instanceof Object[]) {
		flag = !isNotNullAndNotEmpty((Object[]) value);
	    } else if (value instanceof Collection<?>) {
		flag = !isNotNullAndNotEmpty((Collection<?>) value);
	    } else if (value instanceof String) {
		flag = isOEmptyOrNull(value);
	    } else {
		flag = (null == value);
	    }
	    if (flag) {
		return true;
	    }
	}
	return false;
    }

    /**
     * 判断对象数组是否为空并且数量大于0
     *
     * @param value
     * @return
     */
    public static Boolean isNotNullAndNotEmpty(Object[] value) {
	boolean bl = false;
	if (null != value && 0 < value.length) {
	    bl = true;
	}
	return bl;
    }

    /**
     * 判断对象集合（List,Set）是否为空并且数量大于0
     *
     * @param value
     * @return
     */
    public static Boolean isNotNullAndNotEmpty(Collection<?> value) {
	boolean bl = false;
	if (null != value && 0 < value.size()) {
	    bl = true;
	}
	return bl;
    }

    /**
     *
     * @return boolean
     * @param o
     */
    public static boolean isOEmptyOrNull(Object o) {
	return o == null ? true : isSEmptyOrNull(o.toString());
    }

    /**
     *
     * @return java.lang.String
     * @param s
     */
    public static String trimAndEmptyAsNull(String s) {
	if (s == null) {
	    return null;
	}
	s = s.trim();
	return s.length() <= 0 ? null : s;
    }

    /**
     *
     * @return boolean
     * @param s
     */
    public static boolean isSEmptyOrNull(String s) {
	return trimAndNullAsEmpty(s).length() <= 0 ? true : false;
    }

    /**
     *
     * @return java.lang.String
     * @param s
     */
    public static String trimAndNullAsEmpty(String s) {
	if (s != null && !s.trim().equals(STRING_NULL)) {
	    return s.trim();
	} else {
	    return "";
	}
	// return s == null ? "" : s.trim();
    }

    /**
     * 得到当前调用所在的文件名，类名方法名和行号
     * 
     * @return
     */
    public static String getTraceInfo() {
	StringBuffer sb = new StringBuffer();
	StackTraceElement[] stacks = new Throwable().getStackTrace();
	sb.append(stacks[1].getClassName()).append(";")
		.append(stacks[1].getMethodName()).append("; number: ")
		.append(stacks[1].getLineNumber());
	return sb.toString();
    }

	public static boolean isEqualObject(Object otemp1, Object otemp2) {
		String s1 = null, s2 = null;
		if (otemp1 == null) {
			s1 = "";
		} else {
			s1 = otemp1.toString().trim();
		}
		if (otemp2 == null) {
			s2 = "";
		} else {
			s2 = otemp2.toString().trim();
		}
		return s1.equals(s2);
	}

    /**
     * 
     * 方法功能描述：全角转换为半角
     * <p>
     * <b>参数说明</b>
     * 
     * @param inputStr
     *            需要转换字符串
     * @return <p>
     * @since 1.0
     * @time 2016年5月10日 上午10:15:33
     */
    public static String sbcTodbcChange(String inputStr) {
	if (!PubUtils.isNull(inputStr)) {
	    StringBuffer outBuffer = new StringBuffer();
	    String tempStr = null;
	    byte[] b = null;

	    for (int i = 0; i < inputStr.length(); i++) {
		try {
		    tempStr = inputStr.substring(i, i + 1);
		    b = tempStr.getBytes("unicode");
		} catch (java.io.UnsupportedEncodingException e) {
		    e.printStackTrace();
		}

		if (b[2] == -1) {
		    b[3] = (byte) (b[3] + 32);
		    b[2] = 0;

		    try {
			outBuffer.append(new String(b, "unicode"));
		    } catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
		    }
		} else {
		    outBuffer.append(tempStr);
		}
	    }
	    return outBuffer.toString();
	}
	return null;
    }
    
	/**
	 * getBigDecimalByStr:(类型转换). <br/>  
	 * @author liuzy  
	 * @param srcObj
	 * @return  
	 * @since JDK 1.8
	 */
	public static BigDecimal getBigDecimalByStr(String srcObj){
		return (srcObj == null || "".equals(srcObj)) ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(srcObj));
	}
	
	/**
	 * 验证当前字符串等于空 null 或者 “null”
	 * @param str
	 * @return
	 */
	public static boolean validIsNull(String str){
		return null == str ||"".equals(str) || "null".equals(str) ? true : false; 
	}
	
	/**
	 * getBigDecimalIsNullToZero:(一个BigDecimal类型数据为空返货 0). <br/>  
	 * @author liuzy  
	 * @param fromValue
	 * @return  
	 * @since JDK 1.8
	 */
	public static BigDecimal getBigDecimalIsNullToZero(BigDecimal fromValue){
		return (fromValue == null || "".equals(fromValue)) ? BigDecimal.ZERO : fromValue;
	}
	/**
	 * getBigDecimalIsNullToZero:(一个BigDecimal类型数据为空返货 0). <br/>  
	 * @author liuzy  
	 * @param fromValue
	 * @return  
	 * @since JDK 1.8
	 */
	public static BigDecimal getBigDecimalIsZeroToNull(BigDecimal fromValue){
		if(isNull(fromValue))
			return fromValue;
		if(BigDecimal.ZERO.compareTo(fromValue) == 0){
			return null;
		}
		return fromValue;
	}
	/**
	 * getBigDecimalByStr:(类型转换). <br/>  
	 * @author liuzy  
	 * @param srcObj
	 * @return  
	 * @since JDK 1.8
	 */
	public static BigDecimal getBigDecimalByStr(Double srcObj){
		return (srcObj == null) ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(srcObj));
	}
	
	/**
	 * getDoubleByBigDecimal:(BigDecimal类型转换Double). <br/>  
	 * @author liuzy  
	 * @param srcObj
	 * @return  
	 * @since JDK 1.8
	 */
	public static Double getDoubleByBigDecimal(BigDecimal srcObj){
		return (srcObj == null || "".equals(srcObj)) ? 0.0 : srcObj.doubleValue();
	}
	
	/**
	 * @Description: TODO(校验是否负数) 
	 * @param @param sourceObj
	 * @param @return
	 * @return boolean
	 */
	public static boolean isNegateNumber(BigDecimal sourceObj){
		boolean isNegate = false;
		BigDecimal  targetObj = getBigDecimalIsNullToZero(sourceObj);
		if(targetObj.negate().compareTo(BigDecimal.ZERO) > 0){
			isNegate = true;
		}
		return isNegate;
	}
	
	/**
	 * @Description: TODO(将源数据根据 ‘regex’分割后转成List) 
	 * @param @param srcData
	 * @param @param regex
	 * @param @return
	 * @return List<String>
	 */
	public static List<String> splitStrToList(String srcData,String regex){
		if(isNull(srcData))
			return Lists.newArrayList();
		Iterable<String> splitData = Splitter.on(regex).omitEmptyStrings().trimResults().split(srcData.trim());  
		if(isNull(splitData))
			return Lists.newArrayList();
		List<String> targetData = Lists.newArrayList(splitData);
		return targetData;
	}
	
	/**
	 * @Description: TODO(字符串替换) 
	 * @param orgStr 源字符
	 * @param regex 匹配查找
	 * @param replacement 所要替换
	 * @return String
	 */
	public static String replaceStr(String orgStr,String regex,String replacement){
		if(isNull(orgStr))
			return orgStr;
		return orgStr.replaceAll(regex, replacement);
	}
	
	/**
	 * @Description: TODO(四舍五入) 
	 * @param objTarget
	 * @return
	 * @return BigDecimal
	 */
	public static BigDecimal  setScale(BigDecimal objTarget){
		if(isNull(objTarget))
			return objTarget;
		return objTarget.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}
