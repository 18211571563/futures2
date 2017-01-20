package com.tcl.capital_management.aop.accounting;

import com.tcl.bean.CapitalBean;
import com.tcl.capital_management.CapitalManagement;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by yangqj on 2017/1/18.
 */
@Component
@Aspect
public class AccountingInterceptor {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Before("@annotation(com.tcl.capital_management.aop.accounting.annotation.Accounting) && args(singlePrice)")
    public void preProcess(JoinPoint joinPoint, double singlePrice){
        CapitalManagement capitalManagement = (CapitalManagement)joinPoint.getTarget();
        // >>>>>>>>>>>>>>>>>>>>>>>>>> 前置核算:非现金资产会根据市场发生变动，需要调整 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // 判断出是否第一个交易
        if(capitalManagement.capitalBeanList == null || capitalManagement.capitalBeanList.size() == 0){
            capitalManagement.capitalBeanList.add(new CapitalBean(100000,100000,0,0.05,0,0,0)); // 根据分配的资金，初始化第一条资金记录
        }else{

            // 计算盈亏额度 数量*(现价-上一次价格)
            double profitAndLoss = capitalManagement.capitalBeanList.get(0).getTradeCount() * (singlePrice - capitalManagement.capitalBeanList.get(0).getSinglePrice());

            // >>>>>>>>>>>>>>>>>>>>>>>>>> 调整后资产信息 BEGIN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            // 总资产
            double totalCapital = capitalManagement.capitalBeanList.get(0).getTotalCapital() + profitAndLoss;
            // 现金资产（不变）
            double cashCapital = capitalManagement.capitalBeanList.get(0).getCashCapital();
            // 非现金资产
            double nonCashAssets = capitalManagement.capitalBeanList.get(0).getNonCashAssets() + profitAndLoss;
            // 风险率（不变）
            double riskRatio = capitalManagement.capitalBeanList.get(0).getRiskRatio();
            // 交易量（不变）
            int tradeCount = capitalManagement.capitalBeanList.get(0).getTradeCount();
            // 交易增量（不变）
            int addTradeCount = capitalManagement.capitalBeanList.get(0).getAddTradeCount();
            // 交易单格（不变）
            double tradeprice = capitalManagement.capitalBeanList.get(0).getSinglePrice();

            // 保存最新资金记录
            CapitalBean capitalBean = new CapitalBean(totalCapital, cashCapital, nonCashAssets, riskRatio, tradeCount, addTradeCount, tradeprice);
            capitalManagement.capitalBeanList.add(capitalBean);
            // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> END >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        }

        log.info("前置核算 : {}",capitalManagement.capitalBeanList.get(0).toString());
    }

    @After("@annotation(com.tcl.capital_management.aop.accounting.annotation.Accounting) && args(singlePrice)")
    public void proProcess(JoinPoint joinPoint, double singlePrice) throws Throwable {
        // 获取目标类
        CapitalManagement capitalManagement = (CapitalManagement)joinPoint.getTarget();

        // >>>>>>>>>>>>>>>>>>>>>>>>>> 后置核算:根据需要资金管理，更新账户资产（总资产不变） >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // 总资产（不变）
        double totalCapital = capitalManagement.capitalBeanList.get(0).getTotalCapital();
        // 现金资产
        double cashCapital = capitalManagement.capitalBeanList.get(0).getCashCapital() - (capitalManagement.capitalBeanList.get(0).getAddTradeCount() * singlePrice);
        // 非现金资产
        double nonCashAssets = capitalManagement.capitalBeanList.get(0).getNonCashAssets() + (capitalManagement.capitalBeanList.get(0).getAddTradeCount() * singlePrice);
        // 风险率（不变）
        double riskRatio = capitalManagement.capitalBeanList.get(0).getRiskRatio();
        // 交易量
        int tradeCount = capitalManagement.capitalBeanList.get(0).getTradeCount() + capitalManagement.capitalBeanList.get(0).getAddTradeCount();
        // 交易增量（不变）
        int addTradeCount = capitalManagement.capitalBeanList.get(0).getAddTradeCount();
        // 交易单格（不变）
        double tradeprice = capitalManagement.capitalBeanList.get(0).getSinglePrice();

        // 保存最新资金记录
        CapitalBean capitalBean = new CapitalBean(totalCapital, cashCapital, nonCashAssets, riskRatio, tradeCount, addTradeCount, tradeprice);
        capitalManagement.capitalBeanList.add(capitalBean);

        log.info("后置核算 : {}",capitalManagement.capitalBeanList.get(0).toString());
    }
}
