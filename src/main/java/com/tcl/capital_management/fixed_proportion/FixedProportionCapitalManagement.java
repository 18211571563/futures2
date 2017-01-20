package com.tcl.capital_management.fixed_proportion;


import com.tcl.bean.CapitalBean;
import com.tcl.bean.TradeRecordBean;
import com.tcl.capital_management.CapitalManagement;
import com.tcl.capital_management.aop.accounting.annotation.Accounting;
import org.springframework.stereotype.Component;

/**
 * Created by yangqj on 2016/12/1.
 * 固定比率
 */
@Component
public class FixedProportionCapitalManagement extends CapitalManagement {

    /**
      AHTHOR : 杨清杰
      DATE   : 2016/12/2
      DESC   : 获取最新交易资金
    */
    @Accounting // 引入核算模块
    @Override
    public TradeRecordBean getCapital(double singlePrice){
        CapitalBean capitalBean = capitalBeanList.get(0); // 获取最新数据
        // >>>>>>>>>>>>>>>>>>>>>>>>>> 交易:计算下一次交易总信息 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // 可交易总金额
        double tradablePrice = capitalBean.getTotalCapital() * capitalBean.getRiskRatio();
        // 可交易总数量（100做为一个单位）
        int tradableCount = ((int)(tradablePrice / singlePrice))/100*100;

        // >>>>>>>>>>>>>>>>>>>>>>>>>> 交易:计算本次需要增加投资还是减少投资，更新资产属性 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // 获取交易增量
        int increaseOrDecreaseCount = tradableCount - capitalBean.getTradeCount();
        capitalBeanList.get(0).setAddTradeCount(increaseOrDecreaseCount);
        capitalBeanList.get(0).setSinglePrice(singlePrice);
        return new TradeRecordBean(increaseOrDecreaseCount, singlePrice);
    }
}
