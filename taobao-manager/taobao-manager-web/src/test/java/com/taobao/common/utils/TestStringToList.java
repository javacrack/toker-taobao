package com.taobao.common.utils;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestStringToList {
	@Test
	public void TestToList() {
		try {
			String a ="12";
			List<String> list=Arrays.asList(a.split(","));
			for(String s:list) {
				System.out.println(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
