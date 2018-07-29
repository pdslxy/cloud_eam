package com.enerbos.cloud.eam.microservice.constants;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2015
 * Company       北京翼虎能源科技有限公司
 *
 * @author       sunfeilong
 * @version      1.0
 * @date         2016/1/18 15:13
 * @Description  自定义日期格式转换
 */

public class CustomDateSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object value, JsonGenerator jgen, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(value);
        jgen.writeString(formattedDate);
    }
}
