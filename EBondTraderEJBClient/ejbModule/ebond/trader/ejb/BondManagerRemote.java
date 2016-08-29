package ebond.trader.ejb;

import java.util.List;

import javax.ejb.Remote;

import ebond.trader.jpa.Bond;
import ebond.trader.jpa.EBond;

@Remote
public interface BondManagerRemote {
	void putBondData(EBond bondData);
	List<EBond> getBondData();
	List<EBond> getBondResultSet(String isin, String creditRating, String couponRateFrom, String couponRateTo,
			String maturityDateFrom, String maturityDateTo, String frequency, String currency);
//	public List<EBond> getResultFromQuery(String param);  // Test Function for queries
}
