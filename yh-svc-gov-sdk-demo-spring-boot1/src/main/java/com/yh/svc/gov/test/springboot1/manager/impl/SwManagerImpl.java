package com.yh.svc.gov.test.springboot1.manager.impl;

import com.yh.svc.gov.test.springboot1.dao.PgAppSystemDao;
import com.yh.svc.gov.test.springboot1.manager.SwManager;
import com.yh.svc.gov.test.springboot1.model.PgAppSystem;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwManagerImpl implements SwManager {

    private static final Logger logger = LoggerFactory.getLogger(SwManagerImpl.class);

    @Autowired
    private PgAppSystemDao dao;

    @Override
    public void swTest(long time, String url) {
        logger.info("swTest start.........{} ", url);
        doHttp(url);
//        doDao();
        logger.info("swTest finish........." );
    }

    private void doHttp(String url){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            // print result
            logger.info("response: {}", content);
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDao(){
        PgAppSystem byId = dao.findById(1l);
        logger.info("byId {}",byId);
    }
}
