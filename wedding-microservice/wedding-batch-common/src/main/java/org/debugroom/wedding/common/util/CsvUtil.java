package org.debugroom.wedding.common.util;

import java.sql.Timestamp;
import java.util.Date;

public class CsvUtil {

	private static final String SEPARATOR = ", ";
	private static final String SINGLE_QUOTE = "'";
	
	public static final String addSingleQuoteAndWhiteSpace(Object object){
		return addSeparator(addSingleQuote(object));
	}

	public static final String addSeparator(boolean args){
		return new StringBuilder().append(args).append(SEPARATOR).toString();
	}
	
	public static final String addSeparator(Object object){
		return new StringBuilder().append(object).append(SEPARATOR).toString();
	}

	public static final String addSingleQuote(Object object){
		if(null == object){
			return new StringBuilder().append(object).toString();
		}
		if(object instanceof String){
			return new StringBuilder(SINGLE_QUOTE).append(object).append(SINGLE_QUOTE).toString();
		}
		if(object instanceof Integer){
			return new StringBuilder().append(object).toString();
		}
		if(object instanceof Long){
			return new StringBuilder().append(object).toString();
		}
		if(object instanceof Timestamp){
			return new StringBuilder(SINGLE_QUOTE).append(object).append(SINGLE_QUOTE).toString();
		}
		if(object instanceof Date){
			return new StringBuilder(SINGLE_QUOTE).append(object).append(SINGLE_QUOTE).toString();
		}
		return new StringBuilder(SINGLE_QUOTE).append(object).append(SINGLE_QUOTE).toString();
	}
}
