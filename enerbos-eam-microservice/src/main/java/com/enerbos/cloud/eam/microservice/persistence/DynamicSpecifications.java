package com.enerbos.cloud.eam.microservice.persistence;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.ListAttribute;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by 莫鹏 on 2015/12/15.
 */
public class DynamicSpecifications {
    private final static DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static <T> Specification<T> bySearchFilter(final Collection<FilterDescriptor> filters, final Class<T> entityClazz) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (!(filters == null) && !filters.isEmpty()) {
                    List<Predicate> predicates = Lists.newArrayList();

                    for (FilterDescriptor filter : filters) {
                        String operator = filter.getOperator();
                        String field = filter.getField();
                        Object value = filter.getValue();

                        String[] names = StringUtils.split(field, ".");
                        Path expression = root.get(names[0]);
                        for (int i = 1; i < names.length; i++) {
                            expression = expression.get(names[i]);
                        }
                        switch (operator) {
                            case FilterDescriptor.FilterOperator_EQ:
                                predicates.add(builder.equal(expression, value));
                                break;
                            case FilterDescriptor.FilterOperator_NEQ:
                                predicates.add(builder.notEqual(expression, value));
                                break;
                            case FilterDescriptor.FilterOperator_GT:
                                predicates.add(builder.gt(expression, (Number) value));
                                break;
                            case FilterDescriptor.FilterOperator_LT:
                                predicates.add(builder.lt(expression, (Number) value));
                                break;
                            case FilterDescriptor.FilterOperator_GTE:
                                predicates.add(builder.ge(expression, (Number) value));
                                break;
                            case FilterDescriptor.FilterOperator_LTE:
                                predicates.add(builder.le(expression, (Number) value));
                                break;
                            case FilterDescriptor.FilterOperator_GT_A:
                                Path expression_GT_A = root.get(String.valueOf(value));
                                predicates.add(builder.gt(expression, expression_GT_A));
                                break;
                            case FilterDescriptor.FilterOperator_LT_A:
                                Path expression_LT_A = root.get(String.valueOf(value));
                                predicates.add(builder.lt(expression, expression_LT_A));
                                break;
                            case FilterDescriptor.FilterOperator_CONTAINS:
                                predicates.add(builder.like(expression, "%" + value + "%"));
                                break;
                            case FilterDescriptor.FilterOperator_TIME_IN:
                                try {
                                    DateTime dateTime = new DateTime(simpleDateFormat.parse(value + ""));
                                    //时间为yyyy-MM-dd 00:00:00则查询00:00——24:00这个时间段
                                    if (dateTime.getHourOfDay() == 0 && dateTime.getMinuteOfHour() == 0 && dateTime.getSecondOfMinute() == 0) {
                                        predicates.add(builder.greaterThanOrEqualTo(expression, dateTime.toDate()));
                                        predicates.add(builder.lessThan(expression, dateTime.plusDays(1).toDate()));
                                    } else {
                                        predicates.add(builder.equal(expression, value));
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case FilterDescriptor.FilterOperator_TIME_BEFORE:
                                try {
                                    predicates.add(builder.lessThan(expression, simpleDateFormat.parse(value + "")));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case FilterDescriptor.FilterOperator_TIME_AFTER:
                                try {
                                    predicates.add(builder.greaterThan(expression, simpleDateFormat.parse(value + "")));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                break;
                            default:
                                break;
                        }
                    }

                    // 将所有条件用 and 联合起来
                    if (!predicates.isEmpty()) {
                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }

                return builder.conjunction();
            }
        };
    }

    public static <T> Specification<T> byAssignedEntityAndSearchFilter(final ListAttribute<T, ?> listAttribute, final Collection<FilterDescriptor> filters, final Class<?> entityClazz, final Class<?> assignedEntityClazz) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

                String assignedEntityName = assignedEntityClazz.getName().substring(assignedEntityClazz.getName().lastIndexOf(".") + 1);
                Join<T, ?> assignedJoin = root.join(listAttribute);

                if (!(filters == null) && !filters.isEmpty()) {

                    List<Predicate> predicates = Lists.newArrayList();
                    for (FilterDescriptor filter : filters) {

                        String operator = filter.getOperator();
                        String field = filter.getField();
                        Object value = filter.getValue();


                        try {
                            Path expression;
                            Class<?> type;

                            if (field.indexOf(assignedEntityName) >= 0) {
                                String tmpField = field.substring(field.indexOf(".") + 1);
                                expression = assignedJoin.get(tmpField);
                                type = new PropertyDescriptor(tmpField, assignedEntityClazz).getPropertyType();
                            } else {
                                expression = root.get(field);
                                type = new PropertyDescriptor(field, entityClazz).getPropertyType();
                            }

                            if (type == double.class || type == Double.class) {
                                value = Double.parseDouble(value.toString());
                            } else if (type == float.class || type == Float.class) {
                                value = Float.parseFloat(value.toString());
                            } else if (type == long.class || type == Long.class) {
                                value = Long.parseLong(value.toString());
                            } else if (type == int.class || type == Integer.class) {
                                value = Integer.parseInt(value.toString());
                            } else if (type == short.class || type == Short.class) {
                                value = Short.parseShort(value.toString());
                            } else if (type == boolean.class || type == Boolean.class) {
                                value = Boolean.parseBoolean(value.toString());
                            } else if (type == Date.class) {
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                String input = value.toString();
                                value = df.parse(input);
                            }

                            switch (operator) {
                                case "eq":
                                    predicates.add(builder.equal(expression, value));
                                    break;
                                case "neq":
                                    predicates.add(builder.notEqual(expression, value));
                                    break;
                                case "gt":
                                    predicates.add(builder.gt(expression, (Number) value));
                                    break;
                                case "lt":
                                    predicates.add(builder.lt(expression, (Number) value));
                                    break;
                                case "contains":
                                    predicates.add(builder.like(expression, (String) value));
                                    break;
                            }

                        } catch (IntrospectionException e) {
                        } catch (NumberFormatException nfe) {
                        } catch (ParseException e) {
                            System.out.println(e);
                        }


                    }

                    // 将所有条件用 and 联合起来
                    if (!predicates.isEmpty()) {
                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }

                return builder.conjunction();
            }
        };
    }
}
