package cn.www.utils;

import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

public class DateConverter2 implements Converter {

	public Object convert(@SuppressWarnings("rawtypes") Class type, Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof Date) {
			return value;
		}

		if (value instanceof Long) {
			Long longValue = (Long) value;
			return new Date(longValue.longValue());
		}

		try {
			return TimeUtil.controlTime(value.toString());
		} catch (Exception e) {
			throw new ConversionException(e);
		}
	}
}
