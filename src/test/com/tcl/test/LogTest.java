package com.tcl.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yangqj on 2017/1/19.
 */
public class LogTest {
    Logger log = LoggerFactory.getLogger(this.getClass());
    public static void main(String[] args) {
        LogTest test =  new LogTest();
        test.log.trace("======trace");
        test.log.debug("======debug");
        test.log.info("======info");
        test.log.warn("======warn");
        test.log.error("======error");

    }

}
