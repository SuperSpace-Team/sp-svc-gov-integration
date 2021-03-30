
INSERT INTO `pg_retry_config`
(`version`, `cfg_version`, `app_key`, `cfg_info`)
VALUES
(1, 25, "pg-app-demo2", '[{"code": "strategy_normal", "name": "retry_test", "appKey": "pg-app-demo2", "lifeCycle": 1, "type": 1, "retryInterval": "15s", "timeout": "10s",  "expireTime": "10m", "maxRetry": 5},{"code": "strategy_same_method", "name": "retry_test", "appKey": "pg-app-demo2", "lifeCycle": 1, "type": 1, "retryInterval": "15s", "timeout": "10s",  "expireTime": "10m", "maxRetry": 5},{"code": "strategy_async", "name": "retry_test", "appKey": "pg-app-demo2", "lifeCycle": 1, "type": 1, "retryInterval": "15s", "timeout": "10s",  "expireTime": "10m", "maxRetry": 5}]');


