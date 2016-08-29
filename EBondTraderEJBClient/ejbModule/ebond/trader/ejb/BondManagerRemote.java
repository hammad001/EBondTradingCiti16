package ebond.trader.ejb;

import java.util.List;

import javax.ejb.Remote;

import ebond.trader.jpa.Bond;

@Remote
public interface BondManagerRemote {
	void putBondData(Bond bondData);
	List<Bond> getBondData();
	List<Bond> getBondResultSet(String isin, String creditRating, String couponRateFrom, String couponRateTo,
			String maturityDateFrom, String maturityDateTo, String frequency, String currency,String lastPriceFrom,String lastPriceTo,String yield);
}
