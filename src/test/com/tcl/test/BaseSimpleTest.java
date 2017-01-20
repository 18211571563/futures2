package com.tcl.test;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner. class)
@ContextConfiguration(locations = { "classpath:spring/root-context.xml" })
public class BaseSimpleTest extends Assert{

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	protected void toString(Object obj) {
		this.log.info(ToStringBuilder.reflectionToString(obj));
	}
}
