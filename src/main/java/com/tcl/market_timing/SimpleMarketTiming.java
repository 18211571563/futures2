package com.tcl.market_timing;

import com.tcl.bean.DataBean;
import com.tcl.capital_management.fixed_proportion.annotation.FixedProportionCapitalManagement;
import com.tcl.data.FuturesData;
import com.tcl.utils.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangqj on 2016/12/5.
 * 择时(选择交易时机)
 */
@Component
public class SimpleMarketTiming {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    ArrayList<DataBean> data4excel;

    public SimpleMarketTiming() throws Exception {
        // 初始化历史数据
        this.data4excel = FuturesData.getData4excel();
    }

    @FixedProportionCapitalManagement
    public boolean process(DataBean dataBean, int day) throws Exception {
        // 获取今日价格
        double close = MathUtil.formatDouble(dataBean.getClose());
        // 获取近三天平均数
        List<DataBean> subData4excel = day > 2 ? data4excel.subList(day - 3, day) : new ArrayList<DataBean>();
        double add = 0;
        for(DataBean d : subData4excel){
            add += d.getClose();
        }
        double average = MathUtil.formatDouble(add / 3);
        log.info("SimpleMarketTiming >>> {}", "average:" + average);
        // 如果今日价格大于平均值，请求资金做交易
        return average!= 0 && close > average;
    }

}
