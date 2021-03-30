package com.yh.infra.svc.gov.sdk.util;


import com.yh.infra.svc.gov.sdk.init.command.VersionQueryResp;

/**
 * 版本检查VO对象转换类
 */
public class VoUtils {
    public static VersionQueryResp versionQueryRespCopy(VersionQueryResp resp) {
    	VersionQueryResp ret = new VersionQueryResp();
    	ret.setCode(resp.getCode());
    	ret.setConfig(resp.getConfig());
    	ret.setRuntime(resp.getRuntime());
    	ret.setVersion(resp.getVersion());
        return ret;
    }
}
