package com.sp.infra.svc.gov.metrics.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.micrometer.core.instrument.Tag;

public class TagUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_normal() {
		List<String> tagList = new ArrayList<>();
		tagList.add("tag1=value1,  tag2=");
		tagList.add("tag1=value2,  tag2=value3");
		List<Collection<Tag>> ret = TagUtil.buildDimensionTags(tagList);
		assertEquals(2, ret.size());
	}

	@Test
	public void test_invalid() {
		List<String> tagList = new ArrayList<>();
		tagList.add("tag1=value1,  tag2=");
		tagList.add("tag1=value2,  tag3=value3");
		List<Collection<Tag>> ret = TagUtil.buildDimensionTags(tagList);
		assertNull(ret);
	}
}
