package com.perfect.commons.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author xiaowei
 * @title BigDecimalToDoubleConverter
 * @package com.perfect.core
 * @description
 * @update 2015年10月23日. 下午5:53
 */
@Component
public class BigDecimalToDoubleConverter implements Converter<BigDecimal, Double> {
    @Override
    public Double convert(BigDecimal bigDecimal) {
        return bigDecimal.doubleValue();
    }
}
