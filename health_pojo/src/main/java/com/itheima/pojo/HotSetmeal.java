package com.itheima.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class HotSetmeal implements Serializable {
    //{name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
    private String name;
    private Integer setmealCount;
    private BigDecimal proportion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSetmealCount() {
        return setmealCount;
    }

    public void setSetmealCount(Integer setmealCount) {
        this.setmealCount = setmealCount;
    }

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }
}
