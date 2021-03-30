package com.yh.svc.gov.test.springboot1.manager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.yh.svc.gov.test.springboot1.dao.PgAppSystemDao;
import com.yh.svc.gov.test.springboot1.manager.impl.ProcessManagerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("sit")
public class ProcessManagerImplTest {
	
	@Autowired
	ProcessManagerImpl mgr;

	@Mock
	private PgAppSystemDao dao;
	 
	@Before
	public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
    	ReflectionTestUtils.setField(mgr, "dao", dao);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		// no tests
	}

}
