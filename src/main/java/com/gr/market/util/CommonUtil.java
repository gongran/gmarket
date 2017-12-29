package com.gr.market.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CommonUtil {
	public static String amountFomat(String amount, int precision) {
		String pp = "0";
		for (int i = 0; i < precision - 1; i++) {
			pp += "0";
		}
		DecimalFormat df = new DecimalFormat("######0." + pp);
		return df.format(Double.valueOf(amount));
	}

	public static String multiply(String a, String b) {
		BigDecimal a1 = new BigDecimal(a);
		BigDecimal a2 = new BigDecimal(b);
		return a1.multiply(a2).toString();
	}

}
