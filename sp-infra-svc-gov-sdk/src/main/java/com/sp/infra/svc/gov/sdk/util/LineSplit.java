package com.sp.infra.svc.gov.sdk.util;

import java.util.ArrayList;
import java.util.List;


/**
 * 字符串分隔, 如果带引号,则保留
 * @author zan.ma
 * @link http://blog.csdn.net/myzhithot/article/details/53220131
 */
public class LineSplit {
    
	/**
     * 引号
     */
    private static final String seq = "\"";
    /**
     * 逗号
     */
    private static final String comma = ",";

   private LineSplit() {
   }
    /**
     * 逐渐取值,直到取尽
     * 
     * <p>Description: 解析一行文本, 默认使用逗号</p>
     * @param line 字符串
     * @return
     */
    public static List<String> getLineList(String line) {
        return getLineList(line, null);
    }

    /**
     * 逐渐取值,直到取尽
     * 
     * <p>Description: 解析一行文本</p>
     * @param line 字符串
     * @param spliter 自定义分隔符
     * @return
     */
    public static List<String> getLineList(String line, final String spliter) {
        //默认使用逗号
    	String defaultSpliter = comma;
    	//如果自定义分隔符不为空,则使用自定义分隔符
    	if (StringUtils.isNotBlank(spliter)) {
    		defaultSpliter = spliter;
    	}
    	//定义索引
    	Index index = new Index();
        //先按照逗号分隔
        String[] prop = line.split(defaultSpliter);
        //结果列表
        List<String> p = new ArrayList<String>();

        while(index.i < prop.length) {
        	p.add(mergeName(index, prop, defaultSpliter));
        }
        return p;
    }
    /**
     * 逐渐取值,直到取尽
     * 
     * <p>Description: 解析一行文本</p>
     * @param line 行
     * @return
     */
    public static String[] getLineArray(String line) {
        return getLineArray(line, null);
    }
    
    /**
     * 逐渐取值,直到取尽
     * 
     * <p>Description: 解析一行文本</p>
     * @param line 待解析的字符串
     * @param spliter 自定义分隔符
     * @return
     */
    public static String[] getLineArray(String line, final String spliter) {
    	
        List<String> p = getLineList(line, spliter);
        String[] array = new String[p.size()];
        p.toArray(array);
        return array;
    }
    
    
    //--------------------------------------------------------//
    /**
     * 按照逗号分隔后,再根据属性是否含有引号进行配对
     * <p>Description: 取得属性值</p>
     * @param index Index
     * @param prop 一行数据
     * @param spliter 自定义分隔符
     * @return 属性值
     */
    private static String mergeName(Index index, String[] prop, final String spliter) {
    	//默认使用逗号
    	String defaultSpliter = comma;
    	//如果自定义分隔符不为空,则使用自定义分隔符
    	if (StringUtils.isNotBlank(spliter)) {
    		defaultSpliter = spliter;
    	}
    	
        String p = prop[index.i++];
        //字段仅包含左引号【如果左右都包含,则直接返回】
        if (p.startsWith(seq) && !(p.startsWith(seq) && p.endsWith(seq))) {
            for (int i = index.i; i < prop.length; i++) {
            	//下一个字段包含右引号
                if (prop[i].endsWith(seq)) {
                    p = p + defaultSpliter + prop[i];
                    index.i++;
                    break;
                } else {
                    p = p + defaultSpliter + prop[i];
                    index.i++;
                }
            }
        }
        return trimSeq(p);
    }

    /**
     * 去除首尾引号
     * @param p
     * @return
     */
    private static String trimSeq(String p) {
        String result = p;
        int size = seq.length();
        if (result.startsWith(seq)) {
            result = result.substring(size);
        }
        
        if (result.endsWith(seq)) {
            result = result.substring(0,result.length() - size);
        }
        return result;
        
    }




}


class Index {

    /**
     * i
     */
    public int i = 0;

    /**
     * start
     */
    public int start = 0;

    /**
     * end
     */
    public int end = 0;
}

