package ebond.trader.ejb;

import java.util.List;

import javax.ejb.Local;

import ebond.trader.jpa.Bond;


import ebond.trader.jpa.BookedBond;
import ebond.trader.jpa.EBond;

@Local
public interface BondManagerLocal {
	List<EBond> getBondData();
//	public List<EBond> getResultFromQuery(String param);  //Test Function for queries
	String putBookedBondData(String buySell, String quantity, String bondId);

	List<EBond> getBondResultSet(String isin, String creditRating, String couponRateFrom, String couponRateTo,
			String maturityDateFrom, String maturityDateTo, String frequency, String currency, String yeildFrom,
			String yeildTo, String lastPriceFrom, String lastPriceTo);
	List<BookedBond> getBlotterBonds(String blotterQ);
	List<EBond> populateTBS(String TbsIsinQ);
}
