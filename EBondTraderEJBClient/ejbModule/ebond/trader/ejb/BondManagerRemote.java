package ebond.trader.ejb;

import java.util.List;

import javax.ejb.Remote;

import ebond.trader.jpa.Bond;
import ebond.trader.jpa.EBond;

@Remote
public interface BondManagerRemote {
	void putBondData(Bond bondData);
	List<Bond> getBondData();
	List<EBond> getBondResultSet(String isin, String creditRating, String couponRateFrom, String couponRateTo,
			String maturityDateFrom, String maturityDateTo, String frequency, String currency, String yeildFrom, String yeildTo, String lastPriceFrom, String lastPriceTo);
	public List<EBond> getTestResult();
}
