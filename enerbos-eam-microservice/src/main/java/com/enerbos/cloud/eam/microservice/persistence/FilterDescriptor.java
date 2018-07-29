package com.enerbos.cloud.eam.microservice.persistence;

public class FilterDescriptor {

    //等于一个值
    public final static String FilterOperator_EQ = "eq";
    //不等于一个值
    public final static String FilterOperator_NEQ = "neq";
    //大于一个值
    public final static String FilterOperator_GT = "gt";
    //大于等于
    public final static String FilterOperator_GTE = "gte";
    //小于一个值
    public final static String FilterOperator_LT = "lt";
    //小于等于
    public final static String FilterOperator_LTE = "lte";
    //小于另一个字段
    public final static String FilterOperator_LT_A = "lta";
    //大于另一个字段
    public final static String FilterOperator_GT_A = "gta";
    //包含一个值
    public final static String FilterOperator_CONTAINS = "contains";
    //在这一天
    public final static String FilterOperator_TIME_IN = "time_in";
    //在此之前
    public final static String FilterOperator_TIME_BEFORE = "time_before";
    //在此之后
    public final static String FilterOperator_TIME_AFTER = "time_after";

    private String field;
    private Object value;
    private String operator;

    public FilterDescriptor() {
    }

    public FilterDescriptor(String field, Object value, String operator) {
        this.field = field;
        this.value = value;
        this.operator = operator;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
