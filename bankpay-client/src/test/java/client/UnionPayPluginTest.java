package client;

import org.junit.Before;
import org.junit.Test;

import data.dto.UnionPayRequestData;

public class UnionPayPluginTest {

	private UnionPayRequestData request;
	private UnionPayClient client;

	@Before
	public void setUp() {
		client = new UnionPayClient("ICBCUnionPayPlugin");
		request = client.getRequestData();
	}

	@Test
	public void checkOrder() {
		request.setOrderId("111");
		client.checkOrder(request);
	}

}
