package com.gr.market;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gr.market.entity.CoinPrice;

public class MyTest {
	public static void main(String args[]) {
		Map<String, Object> map01 = new HashMap<>();
		Map<String, Object> map02 = new HashMap<>();
		Map<String, Object> map03 = new HashMap<>();
		map01.put("name", "aaa");
		map01.put("cny_trade", "12");
		map02.put("name", "bbb");
		map02.put("cny_trade", "11");
		map03.put("name", "ccc");
		map03.put("cny_trade", "15");

		
		List<Map<String, Object>> list = Arrays.asList(map01, map02, map03);
		list.sort((Map<String, Object> m1, Map<String, Object> m2) -> String.valueOf(m1.get("cny_trade"))
				.compareTo(String.valueOf(m2.get("cny_trade"))));
		for (Map<String, Object> map : list) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				System.out.println(entry.getValue());
			}
		}
	}

}
