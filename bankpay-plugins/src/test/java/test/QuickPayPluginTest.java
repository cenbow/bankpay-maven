package test;

import org.junit.Before;
import org.junit.Test;

import common.plugins.client.QuickPayClient;
import common.plugins.dto.QuickPayRequestData;

public class QuickPayPluginTest {

	private QuickPayClient payClient;
	private QuickPayRequestData request;

	@Before
	public void before() {
		payClient = new QuickPayClient("ICBCQuickPayPlugin");
		request = payClient.getRequestData();
	}

	@Test
	public void checkOrder() {
		request.setOrderId("111");
		request.setSignNo("222");
		payClient.checkOrder(request);
	}

}
