package org.debugroom.wedding.domain.service.common;

import java.sql.Timestamp;
import java.util.Calendar;

public class DateUtil {

	public static Timestamp getCurrentDate(){
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}
	
	public static Timestamp getFutureDateFromNow(String days){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(days));
		return new Timestamp(calendar.getTimeInMillis());
	}

}
