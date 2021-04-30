package com.yh.infra.svc.gov.sdk.alm.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qinzhiyuan
 * @email 80961464@yonghui.cn
 * @date 2021/4/25 6:00 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class LogMessage {
    private int logType;
}
