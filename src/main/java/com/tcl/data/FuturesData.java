package com.tcl.data;

import com.tcl.bean.DataBean;
import com.tcl.utils.PoiUtil;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangqj on 2016/12/1.
 */
public class FuturesData {

    public static void main(String[] args) throws Exception {
        ArrayList<DataBean> data4excel = getData4excel();
        System.out.println("总数据量：>>>>>>>>>>>> "  + data4excel.size());
    }

    public static ArrayList<DataBean> getData4excel() throws Exception {
        List<List<String>> lists = PoiUtil.readExcel(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\获取数据.xls")), 2, true);
        ArrayList<DataBean> dataBeens = new ArrayList<>();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM/dd/yy");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        for(List<String> list: lists){
            DataBean dataBean = new DataBean();
            dataBean.setTime(simpleDateFormat2.format(simpleDateFormat1.parse(list.get(0))));
            dataBean.setOpen(Double.parseDouble(list.get(1)));
            dataBean.setHigh(Double.parseDouble(list.get(2)));
            dataBean.setClose(Double.parseDouble(list.get(3)));
            dataBean.setLow(Double.parseDouble(list.get(4)));
            dataBean.setAmount(Integer.parseInt(list.get(5)));
            dataBeens.add(dataBean);
        }
        return dataBeens;
    }

}
