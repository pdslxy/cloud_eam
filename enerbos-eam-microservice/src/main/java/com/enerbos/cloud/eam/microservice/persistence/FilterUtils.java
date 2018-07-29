package com.enerbos.cloud.eam.microservice.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterUtils {

    public static List<FilterDescriptor> filterConverter(String filter) throws IOException, InvocationTargetException, IllegalAccessException {
        List<FilterDescriptor> filterList = new ArrayList<>();
        if(StringUtils.isNotEmpty(filter)){
            ObjectMapper objectMapper = new ObjectMapper();
            List<HashMap<String, Object>> list = objectMapper.readValue("[" + filter + "]", List.class);
            for (int i = 0; i < list.size(); i++) {
                Map tmp = list.get(i);
                FilterDescriptor filterDescriptor = new FilterDescriptor();
//                BeanUtils.populate(filterDescriptor, tmp);
                filterList.add(filterDescriptor);
            }
        }
        return filterList;
    }
}
