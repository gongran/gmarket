package com.gr.market.util;

import java.text.SimpleDateFormat;

public class DateUtil {
	/**
	 * 时间戳转日期
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String dateToString(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time = new Long(dateStr) * 1000;
		String d = format.format(time);
		return d;
	}

}
