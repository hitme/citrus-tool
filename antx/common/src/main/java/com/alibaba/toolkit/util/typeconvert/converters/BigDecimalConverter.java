/*
 * Copyright 2010 Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.alibaba.toolkit.util.typeconvert.converters;

import com.alibaba.toolkit.util.typeconvert.ConvertChain;
import com.alibaba.toolkit.util.typeconvert.ConvertFailedException;
import com.alibaba.toolkit.util.typeconvert.Converter;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 将对象转换成<code>BigDecimal</code>.
 *
 * <ul>
 * <li>
 * 如果对象为<code>null</code>, 则抛出带默认值的<code>ConvertFailedException</code>.
 * </li>
 * <li>
 * 如果对象已经是<code>BigDecimal</code>了, 直接返回.
 * </li>
 * <li>
 * 如果对象是<code>BigInteger</code>, 直接转换成<code>BigDecimal</code>.
 * </li>
 * <li>
 * 如果对象是<code>Number</code>类型, 则返回它的<code>BigDecimal</code>值.
 * </li>
 * <li>
 * 如果对象是空字符串, 则抛出带默认值的<code>ConvertFailedException</code>.
 * </li>
 * <li>
 * 如果对象是字符串, 则试着把它转换成<code>BigDecimal</code>.  如果不成功, 则抛出<code>ConvertFailedException</code>.
 * </li>
 * <li>
 * 否则, 把对象传递给下一个<code>Converter</code>处理.
 * </li>
 * </ul>
 *
 *
 * @version $Id: BigDecimalConverter.java,v 1.1 2003/07/03 07:26:36 baobao Exp $
 * @author Michael Zhou
 */
public class BigDecimalConverter implements Converter {
    protected static final BigDecimal DEFAULT_VALUE = new BigDecimal(BigInteger.ZERO);

    public Object convert(Object value, ConvertChain chain) {
        if (value == null) {
            throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
        }

        if (value instanceof BigDecimal) {
            return value;
        }

        if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        }

        if (value instanceof Number) {
            return new BigDecimal(value.toString());
        }

        if (value instanceof String) {
            String strValue = ((String) value).trim();

            try {
                return new BigDecimal(strValue);
            } catch (NumberFormatException e) {
                if (strValue.length() > 0) {
                    throw new ConvertFailedException(e);
                }

                throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
            }
        }

        return chain.convert(value);
    }
}
