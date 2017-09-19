package com.taobao.search;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrCloudTest {
	@Test
	public void testAddDocument() throws Exception {
		String zkHost = "192.168.199.132:2181,192.168.199.132:2182,192.168.199.132:2183";
		CloudSolrServer server=new CloudSolrServer(zkHost);
		server.setDefaultCollection("collection2");
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title", "测试商品");
		server.add(document);
		server.commit();
	}
}
