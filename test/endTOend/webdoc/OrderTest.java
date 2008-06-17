package webdoc;

import junit.framework.TestCase;

public class OrderTest extends TestCase {
public static final int SOME_ID = 1;
public static final String SOME_NAME1 = "boo";

public void test_order_save() {
	Order orderToBeFound = new Order(SOME_ID, SOME_NAME1);
	orderToBeFound.save();
	assertNotNull(orderToBeFound.load(SOME_ID));
}
}
