package ebond.trader.jpa;


import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;

public class EBondTest {

	@Test
	public void test() {
		double epsilon=0.0000000000001;
		EBond ebond=new EBond("12345",Date.valueOf("2010-01-01"),2,100,1.5,'A',Date.valueOf("2050-01-01"),150,170,130,40,1.5,"AA","USD");
		ebond.setBondId(1);
		Assert.assertTrue(ebond.getCreditRating().equals("AA"));
		Assert.assertTrue(ebond.getCurrency().equals("USD"));
		Assert.assertTrue(ebond.getIsin().equals("12345"));
		Assert.assertTrue(ebond.getBondId()==1);
		Assert.assertEquals(ebond.getChangeInPrice(),40,epsilon);
		Assert.assertTrue(ebond.getCouponFrequency()=='A');
		Assert.assertEquals(ebond.getCouponRate(),1.5,epsilon);
		Assert.assertTrue(ebond.getFaceValue()==100);
		Assert.assertEquals(ebond.getHigh(),170,epsilon);
		Assert.assertTrue(ebond.getIssueDate().equals(Date.valueOf("2010-01-01")));
		Assert.assertEquals(ebond.getLastPrice(),150,epsilon);
		Assert.assertEquals(ebond.getLow(),130,epsilon);
		Assert.assertEquals(ebond.getYeild(),1.5,epsilon);
		Assert.assertTrue(ebond.getMaturityDate().equals(Date.valueOf("2050-01-01")));
		Assert.assertTrue(ebond.getSettlementDays()==2);
	}

}
