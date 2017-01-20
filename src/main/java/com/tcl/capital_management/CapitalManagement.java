package com.tcl.capital_management;

import com.tcl.bean.CapitalBean;
import com.tcl.bean.TradeRecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangqj on 2017/1/18.
 */
public abstract class CapitalManagement {
    public List<CapitalBean> capitalBeanList = new ArrayList<CapitalBean>();
    public abstract TradeRecordBean getCapital(double singlePrice);
}
