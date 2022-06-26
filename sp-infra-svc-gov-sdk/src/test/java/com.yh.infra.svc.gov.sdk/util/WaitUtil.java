package com.sp.infra.svc.gov.sdk.util;

import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;

public class WaitUtil {
    private WaitUtil() {
        throw new IllegalStateException("unable to instance");
    }

    public static void wait(int milliseconds){
    	
        await().timeout(1, HOURS).pollDelay(milliseconds, MILLISECONDS).until(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return true;
            }
        });
    }
}
