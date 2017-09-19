package com.taobao.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taobao.common.utils.FtpUtil;
import com.taobao.common.utils.IDUtils;
import com.taobao.service.PictureService;
@Service
public class PictureServiceImpl implements PictureService {
	@Value("${FTP.HOST}")
	private String FTP_HOST;
	@Value("${FTP.PORT}")
	private Integer FTP_PORT; // 写Integer这样spring可以转换
	@Value("${FTP.USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP.PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP.BASH.PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;

	@Override
	public Map uploadPicture(MultipartFile multipartFile) {
		Map resultMap=new HashMap();
		// 去源文件名的类型，生成新的文件名
		String oldName = multipartFile.getOriginalFilename();
		// 生成新文件名
		// UUID.randomUUID()
		String newName = IDUtils.genImageName();
		newName = newName + oldName.substring(oldName.lastIndexOf("."));
		String filepath=new DateTime().toString("yyyy/MM/dd");
		try {
			boolean result = FtpUtil.uploadFile(FTP_HOST, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
					filepath, newName, multipartFile.getInputStream());
			/**
			 * 参照kindeditor的api返回值
			 * http://kindeditor.net/docs/upload.html
			 */			
			if(!result) {
				resultMap.put("error", 1);
				resultMap.put("message","文件上传失败");
			}else {
				resultMap.put("error", 0);
				resultMap.put("url", IMAGE_BASE_URL+filepath+"/"+newName);
			}
		} catch (IOException e) {
			resultMap.put("error", 1);
			resultMap.put("message","文件上传发生异常");
		}
		return resultMap;
	}

}
