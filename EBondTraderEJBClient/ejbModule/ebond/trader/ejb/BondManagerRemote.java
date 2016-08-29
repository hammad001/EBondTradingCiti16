package ebond.trader.ejb;

import java.util.List;

import javax.ejb.Remote;

import ebond.trader.jpa.Bond;
import ebond.trader.jpa.BookedBond;

@Remote
public interface BondManagerRemote {
	void putBookedBondData(BookedBond bondData);

	List<Bond> getBondData();

	List<Bond> getBondResultSet(String isin, String creditRating, String couponRateFrom, String couponRateTo,
			String maturityDateFrom, String maturityDateTo, String frequency, String currency, String yeildFrom,
			String yeildTo, String lastPriceFrom, String lastPriceTo);
	List<BookedBond> getBlotterBonds(String blotterQ);
}
