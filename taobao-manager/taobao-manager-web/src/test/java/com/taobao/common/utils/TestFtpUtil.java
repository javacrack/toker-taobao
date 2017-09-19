package com.taobao.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

public class TestFtpUtil {
	@Test
	public void TestuploadFile() {
		try {
			FileInputStream in = new FileInputStream(new File("C:/Users/Administrator/Pictures/4.jpg"));
			boolean flag = FtpUtil.uploadFile("192.168.199.114", 21, "ftpuser", "ftpuser",
					"/home/ftpuser/www/images/products", "/2017/08/21", "toker.jpg", in);
			assert flag ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
