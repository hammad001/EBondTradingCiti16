package ebond.trader.ejb;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

import ebond.trader.jpa.BookedBond;
import ebond.trader.jpa.EBond;

@Remote
public interface BondManagerRemote {
	//	public List<EBond> getResultFromQuery(String param);  // Test Function for queries
	String putBookedBondData(String buySell, String quantity, String bondId, String accountId);

	List<EBond> getBondData();

	List<EBond> getBondResultSet(String isin, String creditRating, String couponRateFrom, String couponRateTo,
			String maturityDateFrom, String maturityDateTo, String frequency, String currency, String yeildFrom,
			String yeildTo, String lastPriceFrom, String lastPriceTo);
	List<BookedBond> getBlotterBonds(String accountId,String isin);
	List<EBond> populateTBS(String TbsIsinQ);

	// User Account related interface methods
	public HashMap<String, String> registerUser(String name, String userName, String password);
	public HashMap<String, String> loginUser(String userName, String password);
}
