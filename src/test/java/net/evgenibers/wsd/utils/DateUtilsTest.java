package net.evgenibers.wsd.utils;

import net.evgenibers.wsd.BasicTest;
import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest extends BasicTest {

	@Test
	public void currentDateAndTime() {
		String s1 = DateUtils.currentDateAndTime();

		Assert.assertNotNull(s1);
		Assert.assertFalse(s1.isEmpty());
	}

	@Test
	public void testCurrentDateAndTime() {
		String s1 = DateUtils.currentDateAndTime(null);
		String s2 = DateUtils.currentDateAndTime("");
		String s3 = DateUtils.currentDateAndTime(" ");
		String s4 = DateUtils.currentDateAndTime("jfosdi hfsd huoisld f");
		String s5 = DateUtils.currentDateAndTime(DateUtils.DEFAULT_DATE_TIME_PATTERN);

		Assert.assertNotNull(s1);
		Assert.assertNotNull(s2);
		Assert.assertNotNull(s3);
		Assert.assertNotNull(s4);
		Assert.assertNotNull(s5);

		Assert.assertFalse(s1.isEmpty());
		Assert.assertTrue(s2.isEmpty());
		Assert.assertFalse(s3.isEmpty());
		Assert.assertFalse(s4.isEmpty());
		Assert.assertFalse(s5.isEmpty());

		Assert.assertEquals(DateUtils.DEFAULT_DATE_TIME_ERROR_STRING, s1);
		Assert.assertNotEquals(DateUtils.DEFAULT_DATE_TIME_ERROR_STRING, s2);
		Assert.assertNotEquals(DateUtils.DEFAULT_DATE_TIME_ERROR_STRING, s3);
		Assert.assertEquals(DateUtils.DEFAULT_DATE_TIME_ERROR_STRING, s4);
		Assert.assertNotEquals(DateUtils.DEFAULT_DATE_TIME_ERROR_STRING, s5);
	}
}