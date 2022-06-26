package com.sp.infra.svc.gov.sdk.alm.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luchao
 * @date 2021/4/25 6:00 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class LogMessage {
    private int logType;
}
