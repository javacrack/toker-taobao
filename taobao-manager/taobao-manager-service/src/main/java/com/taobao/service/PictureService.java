package com.taobao.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
	Map uploadPicture(MultipartFile multipartFile);
}
