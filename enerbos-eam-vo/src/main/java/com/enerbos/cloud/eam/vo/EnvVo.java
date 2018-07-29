package com.enerbos.cloud.eam.vo;


import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/11/10.
 */

public class EnvVo implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int count;
    

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "EnvVo{" +
                "count=" + count +
                '}';
    }
}
