package com.taobao.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

public class FtpUploadTest {
	
	@Test
	public void TestFtpClient() throws SocketException, IOException {
		FTPClient client =new FTPClient();
		client.connect("192.168.199.114");
		client.login("ftpuser", "ftpuser");
		FileInputStream fis=new FileInputStream(new File("C:/Users/Administrator/Pictures/3.jpg"));
		client.changeWorkingDirectory("~/www/images/products");
		client.setFileType(FTP.BINARY_FILE_TYPE);//默认传的是文本格式，如果不要这段代码传上去的图片就花了
		client.storeFile("helloworld2.jpg", fis);
		
		fis.close();
		client.logout();
	}

}
