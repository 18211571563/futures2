package com.tcl.test;

import com.tcl.bean.DataBean;
import com.tcl.capital_management.fixed_proportion.FixedProportionCapitalManagement;
import com.tcl.data.FuturesData;
import com.tcl.market_timing.SimpleMarketTiming;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangqj on 2017/1/18.
 */
public class TestSpring extends BaseSimpleTest {

    @Autowired
    FixedProportionCapitalManagement fixedProportionCapitalManagement;
    @Autowired
    SimpleMarketTiming simpleMarketTiming;
    @Test
    public void testSimpleMarketTiming() throws Exception{
        // >>>>>>>>>>>>>>>>>>>>>>>>>> 初始化基础信息 BEGIN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // 获取数据流
        ArrayList<DataBean> data4excel = FuturesData.getData4excel();
        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> END >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        List<String> list = new ArrayList<String>();
        for(double i = 0.05 ; i < 1; i = i + 0.01){
            int day = 0;
            for(DataBean dataBean : data4excel){
                list.add(i + " >>>>>>>>>>:" + simpleMarketTiming.process(dataBean, ++day));
            }

        }
    }

}
