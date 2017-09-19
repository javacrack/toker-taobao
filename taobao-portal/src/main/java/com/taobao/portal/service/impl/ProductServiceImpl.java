package com.taobao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.common.utils.HttpClientUtil;
import com.taobao.common.utils.JsonUtils;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemDesc;
import com.taobao.pojo.TbItemParamItem;
import com.taobao.portal.Dto.ProductPortalItem;
import com.taobao.portal.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService{

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITME_INFO_URL}")
	private String ITME_INFO_URL;
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;

	@Override
	public ProductPortalItem getItemById(Long itemId) {
		
		try {
			//调用rest的服务查询商品基本信息
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITME_INFO_URL + itemId);
			if (!StringUtils.isBlank(json)) {
				TaoBaoResult taoBaoResult = TaoBaoResult.formatToPojo(json, ProductPortalItem.class);
				if (taoBaoResult.getStatus() == 200) {
					ProductPortalItem item = (ProductPortalItem) taoBaoResult.getData();
					return item;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


	/**
	 * 	取商品描述
	 * <p>Title: getItemDescById</p>
	 * <p>Description: </p>
	 * @param itemId
	 * @return
	 * @see com.taotao.portal.service.ItemService#getItemDescById(java.lang.Long)
	 */
	@Override
	public String getItemDescById(Long itemId) {
		try {
			//查询商品描述
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
			//转换成java对象
			TaoBaoResult taoBaoResult = TaoBaoResult.formatToPojo(json, TbItemDesc.class);
			if (taoBaoResult.getStatus() == 200) {
				TbItemDesc itemDesc = (TbItemDesc) taoBaoResult.getData();
				//取商品描述信息
				String result = itemDesc.getItemDesc();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据商品id查询规格参数
	 * <p>Title: getItemParam</p>
	 * <p>Description: </p>
	 * @param itemId
	 * @return
	 * @see com.taotao.portal.service.ItemService#getItemParam(java.lang.Long)
	 */
	@Override
	public String getItemParam(Long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
			//把json转换成java对象
			TaoBaoResult taoBaoResult = TaoBaoResult.formatToPojo(json, TbItemParamItem.class);
			if (taoBaoResult.getStatus() == 200) {
				TbItemParamItem itemParamItem = (TbItemParamItem) taoBaoResult.getData();
				String paramData = itemParamItem.getParamData();
				//生成htmlt
				// 把规格参数json数据转换成java对象
				List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
				StringBuffer sb = new StringBuffer();
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
				sb.append("    <tbody>\n");
				for(Map m1:jsonList) {
					sb.append("        <tr>\n");
					sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
					sb.append("        </tr>\n");
					List<Map> list2 = (List<Map>) m1.get("params");
					for(Map m2:list2) {
						sb.append("        <tr>\n");
						sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
						sb.append("            <td>"+m2.get("v")+"</td>\n");
						sb.append("        </tr>\n");
					}
				}
				sb.append("    </tbody>\n");
				sb.append("</table>");
				//返回html片段
				return sb.toString();
			}
				 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}

}
