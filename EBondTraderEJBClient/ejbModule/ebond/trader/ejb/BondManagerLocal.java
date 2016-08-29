package ebond.trader.ejb;

import java.util.List;

import javax.ejb.Local;

import ebond.trader.jpa.Bond;
import ebond.trader.jpa.BookedBond;
import ebond.trader.jpa.EBond;

@Local
public interface BondManagerLocal {
	void putBookedBondData(BookedBond bondData);

	List<EBond> getBondData();

	List<EBond> getBondResultSet(String isin, String creditRating, String couponRateFrom, String couponRateTo,
			String maturityDateFrom, String maturityDateTo, String frequency, String currency, String yeildFrom,
			String yeildTo, String lastPriceFrom, String lastPriceTo);
	List<BookedBond> getBlotterBonds(String blotterQ);
	EBond populateTBS(String TbsIsinQ);
}
