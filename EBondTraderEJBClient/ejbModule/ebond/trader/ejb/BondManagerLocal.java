package ebond.trader.ejb;

import java.util.List;

import javax.ejb.Local;

import ebond.trader.jpa.Bond;
import ebond.trader.jpa.EBond;

@Local
public interface BondManagerLocal {
	void putBondData(Bond bondData);
	List<Bond> getBondData();
	List<EBond> getBondResultSet(String isin, String creditRating, String couponRateFrom, String couponRateTo,
			String maturityDateFrom, String maturityDateTo, String frequency, String currency, String yeildFrom, String yeildTo, String lastPriceFrom, String lastPriceTo);
	public List<EBond> getTestResult();
}
