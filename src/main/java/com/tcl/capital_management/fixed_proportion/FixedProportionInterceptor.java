package com.tcl.capital_management.fixed_proportion;

import com.tcl.bean.DataBean;
import com.tcl.bean.TradeRecordBean;
import com.tcl.utils.MathUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yangqj on 2017/1/18.
 */
@Component
@Aspect
public class FixedProportionInterceptor {

    @Autowired
    FixedProportionCapitalManagement capitalManagement;

    @Around("@annotation(com.tcl.capital_management.fixed_proportion.annotation.FixedProportionCapitalManagement)")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        DataBean dataBean = null;
        for(Object o : args){
            if(o instanceof DataBean ){
                dataBean = (DataBean)o;
                break;
            }

        }

        // 执行交易
        boolean result = (boolean)joinPoint.proceed();

        // 交易完成后，做 资金管理
        if(result){
            // 获取今日价格
            double close = MathUtil.formatDouble(dataBean.getClose());
            TradeRecordBean capital = capitalManagement.getCapital(close);
        }
        return result;
    }

}
