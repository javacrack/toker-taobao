package com.taobao.controller.common;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taobao.common.utils.JsonUtils;
import com.taobao.service.PictureService;

@Controller
@RequestMapping("/pic")
public class PictureController {
	private Logger logger=LoggerFactory.getLogger(PictureController.class);
	@Autowired
	private PictureService pictureService;
	
	/**
	 * 
	 * @param uploadFile:这个名字看common.js
	 * 中富文本便结气kindEditor
	 * kingEditorParams : {
	*	//指定上传文件参数名称
	*	filePostName  : "uploadFile",
	*	//指定上传文件请求的url。
	*	uploadJson : '/pic/upload',
	*	//上传类型，分别为image、flash、media、file
	*	dir : "image"
	},
	 * @return
	 * @throws Exception
	 * ResponseBody：
	 * 1.spring把对象转换成json格式,此时ResponseHeader 是application/json调用Response write向body里写值，
	 * 因为response里接受不了java对象
	 * 2.如果返回是string就直接把string写到body里，此时ResponseHeader 是text/plain
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public Map uploda(MultipartFile uploadFile) throws Exception {
//		logger.info(String.format("s%上传图片开始了", PictureController.class.getName()));
		logger.info(String.format("[%s:上传图片开始了]", PictureController.class.getName()));
		//调用service上传图片
		Map pictureResult = pictureService.uploadPicture(uploadFile);
		//返回上传结果
		return pictureResult;
		
	}
	//错误的写法这样会做跳转到string相应的jsp
//	@RequestMapping("/upload")	
//	public String uploda(MultipartFile uploadFile) throws Exception {
//		logger.info(String.format("[%s:上传图片开始了]", PictureController.class.getName()));
//		//调用service上传图片
//		Map pictureResult = pictureService.uploadPicture(uploadFile);
//		//返回上传结果
//		return JsonUtils.objectToJson(pictureResult);
//		
//	}
}
