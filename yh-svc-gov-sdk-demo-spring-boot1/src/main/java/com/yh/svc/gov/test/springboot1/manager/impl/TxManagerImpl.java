//package com.yh.svc.gov.test.springboot1.manager.impl;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.interceptor.TransactionAspectSupport;
//import org.springframework.transaction.support.TransactionSynchronizationManager;
//
//import com.yh.svc.gov.test.springboot1.command.OrderVo;
//import com.yh.svc.gov.test.springboot1.dao.PgAppSystemDao;
//import com.yh.svc.gov.test.springboot1.manager.BaseManager;
//import com.yh.svc.gov.test.springboot1.model.OrderCancel;
//import com.yh.svc.gov.test.springboot1.model.PgAppSystem;
//import com.yh.infra.svc.gov.sdk.retry.annotation.RetryConfirm;
//import com.yh.infra.svc.gov.sdk.retry.annotation.RetryRegistry;
//
///**
// * @author luchao 2018-08-06
// */
//@Service("txManagerImpl")
//@Transactional
//public class TxManagerImpl  {
//    private static final Logger logger = LoggerFactory.getLogger(TxManagerImpl.class);
//
//    @Autowired
//    private BaseManager<OrderCancel> processManager;
//
//    @Autowired
//    private PgAppSystemDao dao;
//
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public String receiveDataNested(String appId, String node, List<OrderVo> orders, String comment, int nested) {
//        logger.info("TxManagerImpl nested call : {}, {}", this.getClass().getName(), this);
//    	return processManager.receiveDataNested(appId, node, orders, comment, nested);
//    }
//    private void updateApp() {
//		PgAppSystem app = dao.getByAppId("test-rb-1");
//
//		if (app == null) {
//			app = new PgAppSystem();
//			app.setAppId("test-rb-1");
//			app.setCode(12321);
//			app.setLifecycle(true);
//			app.setVersion(1);
//			app.setOperator("test user");
//			dao.insert(app);
//		} else {
//			app.setLifecycle(! app.getLifecycle());
//			dao.update(app);
//		}
//
//    }
//
//	@RetryRegistry(code = "strategy_normal2", beanName="retryManager2", uuidExp = "#P2", bizKeyExp = "#P3", dbTagExp = "\"db1\"")
//	@RetryConfirm(uuidExp = "#P2", confirmExp = "#EXCEPT == NULL && ! #RESULT", dbTagExp = "\"db1\"")
//	public boolean testRollback(int type, String uuid, String bizKey, int timeout) throws InterruptedException {
//		logger.info("enter testRollback manager {}, {}, {}, {}", type, uuid, bizKey, timeout);
//
//		updateApp();
//
//		if (type == 3) {
//    		throw new RuntimeException("test rollback");
//    	}
//
//		logger.info("finished testRollback manager {}, {}, {}, {}", type, uuid, bizKey, timeout);
//    	return true;
//    }
//	@RetryRegistry(code = "strategy_normal2", beanName="retryManager2", uuidExp = "#P2", bizKeyExp = "#P3", dbTagExp = "\"db1\"")
//	public boolean testRollback2(int type, String uuid, String bizKey, int timeout) throws InterruptedException {
//		logger.info("enter testRollback manager testRollback2 {}, {}, {}, {}", type, uuid, bizKey, timeout);
//
//		updateApp();
//		TransactionStatus ts = TransactionAspectSupport.currentTransactionStatus();
//		if (ts != null) {
//			logger.info("tx status: {}, {}", ts.isCompleted(), ts.isNewTransaction());
//		} else {
//			logger.info("tx status: null");
//		}
//
//		logger.info("tx info: {}, {}, {} ", TransactionSynchronizationManager.isActualTransactionActive(), TransactionSynchronizationManager.getCurrentTransactionName(), TransactionSynchronizationManager.getCurrentTransactionIsolationLevel());
//
//
//    	if (type == 3) {
//    		throw new RuntimeException("test rollback2");
//    	}
//
//		logger.info("finished testRollback manager {}, {}, {}, {}", type, uuid, bizKey, timeout);
//    	return true;
//    }
//
//	/**
//	 *
//	 * 独立事务模式，异常后， 任务还存在。  正常时任务存在。
//	 *
//	 * @param type
//	 * @param uuid
//	 * @param bizKey
//	 * @param timeout
//	 * @return
//	 * @throws InterruptedException
//	 */
//	@RetryRegistry(code = "strategy_normal2", beanName="retryManager2", uuidExp = "#P2", bizKeyExp = "#P3", dbTagExp = "\"db1\"")
//	public boolean testNonTx(int type, String uuid, String bizKey, int timeout) throws InterruptedException {
//		logger.info("enter testRollback manager testNonTx {}, {}, {}, {}", type, uuid, bizKey, timeout);
//
//		updateApp();
//
//    	if (type == 3) {
//    		throw new RuntimeException("test rollback");
//    	}
//
//		logger.info("finished testRollback manager {}, {}, {}, {}", type, uuid, bizKey, timeout);
//    	return true;
//    }
//
//	/**
//	 * 参与业务事务， 异常时回滚。。  正常时任务存在。
//	 *
//	 * @param type
//	 * @param uuid
//	 * @param bizKey
//	 * @param timeout
//	 * @return
//	 * @throws InterruptedException
//	 */
//	@RetryRegistry(code = "strategy_normal2", beanName="retryManager2", uuidExp = "#P2", bizKeyExp = "#P3", dbTagExp = "\"db1\"", nonTx = false)
//	public boolean testTx(int type, String uuid, String bizKey, int timeout) throws InterruptedException {
//		logger.info("enter testRollback manager testTx {}, {}, {}, {}", type, uuid, bizKey, timeout);
//
//		updateApp();
//
//    	if (type == 3) {
//    		throw new RuntimeException("test rollback");
//    	}
//
//		logger.info("finished testRollback manager {}, {}, {}, {}", type, uuid, bizKey, timeout);
//    	return true;
//    }
//}
