package ebond.trader.jpa;


import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;

public class BookedBondTest {

	@Test
	public void test() {
		
		double epsilon=0.0000000000001;
		EBond ebond=new EBond("12345",Date.valueOf("2010-01-01"),2,100,1.5,'A',Date.valueOf("2050-01-01"),150,170,130,40,1.5,"AA","USD");
		ebond.setBondId(1);
		
		BookedBond ob=new BookedBond('B',22,Date.valueOf("2016-09-01"));
		ob.setOrderId(1);
		ob.setEbond(ebond);
		
		Assert.assertTrue(ob.getOrderId()==1);
		Assert.assertTrue(ob.getBuySell()=='B');
		Assert.assertTrue(ob.getPurchaseDate().equals(Date.valueOf("2016-09-01")));
		Assert.assertTrue(ob.getQuantity()==22);
		
		Assert.assertTrue(ob.getEbond().getCreditRating().equals("AA"));
		Assert.assertTrue(ob.getEbond().getCurrency().equals("USD"));
		Assert.assertTrue(ob.getEbond().getIsin().equals("12345"));
		Assert.assertTrue(ob.getEbond().getBondId()==1);
		Assert.assertEquals(ob.getEbond().getChangeInPrice(),40,epsilon);
		Assert.assertTrue(ob.getEbond().getCouponFrequency()=='A');
		Assert.assertEquals(ob.getEbond().getCouponRate(),1.5,epsilon);
		Assert.assertTrue(ob.getEbond().getFaceValue()==100);
		Assert.assertEquals(ob.getEbond().getHigh(),170,epsilon);
		Assert.assertTrue(ob.getEbond().getIssueDate().equals(Date.valueOf("2010-01-01")));
		Assert.assertEquals(ob.getEbond().getLastPrice(),150,epsilon);
		Assert.assertEquals(ob.getEbond().getLow(),130,epsilon);
		Assert.assertEquals(ob.getEbond().getYeild(),1.5,epsilon);
		Assert.assertTrue(ob.getEbond().getMaturityDate().equals(Date.valueOf("2050-01-01")));
		Assert.assertTrue(ob.getEbond().getSettlementDays()==2);
	}

}
