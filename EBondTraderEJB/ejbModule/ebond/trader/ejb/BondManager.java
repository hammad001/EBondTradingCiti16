package ebond.trader.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ebond.trader.jpa.Bond;
import ebond.trader.jpa.EBond;

//This is an session bean
@Stateless
public class BondManager implements BondManagerRemote, BondManagerLocal {

	/**
	 * Default constructor.
	 */
	public BondManager() {
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext(unitName = "EBondTraderJPA-PU")
	EntityManager em;

	public void putBondData(Bond bondData) {
		em.persist(bondData);
	}

	public List<Bond> getBondData() {
		TypedQuery<Bond> query = em.createQuery("SELECT b" + " FROM Bond AS b", Bond.class);

		return (List<Bond>) query.getResultList();
	}
	
	
	/*public List<Bond> getBookedBonds() {
		TypedQuery<Bond> query = em.createQuery("SELECT b" + " FROM BookedBond AS b", Bond.class);

		return (List<Bond>) query.getResultList();
	}*/


	public List<EBond> getBondResultSet(String isin, String creditRating, String couponRateFrom, String couponRateTo,
			String maturityDateFrom, String maturityDateTo, String frequency, String currency, String yeildFrom, String yeildTo, String lastPriceFrom, String lastPriceTo) {

		String tempQuery = "SELECT b from EBond AS b ";// trailing spaces added
														// to prevent accidental
														// concat
		String formatMDFrom = new String();
		String formatMDTo = new String();
		
		try {
			Date datef = new SimpleDateFormat("dd-MM-yyyy").parse(maturityDateFrom);
			Date datet = new SimpleDateFormat("dd-MM-yyyy").parse(maturityDateTo);
			formatMDFrom = new SimpleDateFormat("yyyy-MM-dd").format(datef);
			formatMDTo = new SimpleDateFormat("yyyy-MM-dd").format(datet);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int notNullCount = -1;

		// blank query case
		if (isin.length() == 0 && creditRating.length() == 0 && couponRateFrom.length() == 0
				&& couponRateTo.length() == 0 && maturityDateFrom.length() == 0 && maturityDateTo.length() == 0
				&& frequency.length() == 0 && currency.length() == 0 && yeildFrom.length() == 0 && yeildFrom.length() == 0 && lastPriceFrom.length()==0 && lastPriceTo.length()==0) {
			System.out.println("Search is null");

		} else {

			tempQuery = tempQuery + " WHERE ";// if any entry is not null, we
												// need a WHERE clause first
			notNullCount++;// this is a reference to check whether to add AND or
							// not
			// begin with adding
			// ISIN_________________________________________________
			if (isin.length() != 0 && notNullCount == 0) {
				tempQuery = tempQuery + "b.isin='" + isin+"'";
				notNullCount++;// any non null String after this will have an
								// AND clause before it
			}
			// adding creditRating__________________________________
			if (creditRating.length() != 0 && notNullCount == 0) {
				// if isin is null,notNullCount is still 0 we proceed after
				// WHERE
				tempQuery = tempQuery + "b.creditRating='" + creditRating + "'";
				notNullCount++;// any non null String after this will have an
								// AND clause before it

			} else if (creditRating.length() != 0 && notNullCount > 0) {
				// if isin is not null,notNullCount>0 we add an AND clause in
				// between
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.creditRating='" + creditRating+"'";
				notNullCount++;

			}
			// adding couponRateFrom and To__________________________________
			if (couponRateFrom.length() != 0 && couponRateTo.length()!=0 && notNullCount == 0) {
				tempQuery = tempQuery + "b.couponRate BETWEEN " + couponRateFrom + " AND " + couponRateTo;
				notNullCount++;

			} else if (couponRateFrom.length() != 0 && couponRateTo.length()!=0 && notNullCount > 0) {
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.couponRate BETWEEN " + couponRateFrom + " AND " + couponRateTo;
				notNullCount++;

			}
			// adding maturityDateFrom and To__________________________________
			if (maturityDateFrom.length() != 0 && maturityDateTo.length() != 0 && notNullCount == 0) {
				tempQuery = tempQuery + "b.maturityDate BETWEEN '" + formatMDFrom + "' AND '" + formatMDFrom + "'";
				notNullCount++;

			} else if (maturityDateFrom.length() != 0 && maturityDateTo.length() != 0 && notNullCount > 0) {
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.maturityDate BETWEEN '" + formatMDTo + "' AND '" + formatMDTo + "'";
				notNullCount++;

			}

			// adding frequency__________________________________
			if (frequency.length() != 0 && notNullCount == 0) {
				// if isin is null,notNullCount is still 0 we proceed after
				// WHERE
				tempQuery = tempQuery + "b.frequency=" + frequency;
				notNullCount++;// any non null String after this will have an
								// AND clause before it

			} else if (frequency.length() != 0 && notNullCount > 0) {
				// if isin is not null,notNullCount>0 we add an AND clause in
				// between
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.couponFrequency=" + frequency;
				notNullCount++;

			}
			// adding currency__________________________________
			if (currency.length() != 0 && notNullCount == 0) {
				// if isin is null,notNullCount is still 0 we proceed after
				// WHERE
				tempQuery = tempQuery + "b.currency='" + currency+"'";
				notNullCount++;// any non null String after this will have an
								// AND clause before it

			} else if (currency.length() != 0 && notNullCount > 0) {
				// if isin is not null,notNullCount>0 we add an AND clause in
				// between
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.currency='" + currency+"'";
				notNullCount++;
				
			}
			
			if (yeildFrom.length() != 0 && yeildTo.length() != 0 && notNullCount == 0) {
				tempQuery = tempQuery + "b.yeild BETWEEN " + yeildFrom + " AND " + yeildTo;
				notNullCount++;

			} else if (yeildFrom.length() != 0 && yeildTo.length() != 0 && notNullCount > 0) {
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.yeild BETWEEN " + yeildFrom + " AND " + yeildTo;
				notNullCount++;

			}
			
			if (lastPriceFrom.length() != 0 && lastPriceTo.length() != 0 && notNullCount == 0) {
				tempQuery = tempQuery + "b.lastPrice BETWEEN " + lastPriceFrom + " AND " + lastPriceTo;
				notNullCount++;

			} else if (lastPriceFrom.length() != 0 && lastPriceTo.length() != 0 && notNullCount > 0) {
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.lastPrice BETWEEN " + lastPriceFrom + " AND " + lastPriceTo;
				notNullCount++;

			}
		}
		System.out.println("Executed: " + tempQuery);
		TypedQuery<EBond> query = em.createQuery(tempQuery, EBond.class);

		return (List<EBond>) query.getResultList();
	}
	
	public List<EBond> getTestResult(){
		//String tempQuery = "Select eb from EBond AS eb WHERE eb.isin = 'TYE047'";
		String tempQuery = "Select eb from EBond AS eb WHERE eb.lastPrice BETWEEN 200 AND 300";
		//String tempQuery = "Select eb from EBond AS eb WHERE eb.maturityDate BETWEEN '2030-01-01' AND '2040-01-01'";
//		String newdat = new String();
//		
//		try {
//			Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dat);
//			newdat = new SimpleDateFormat("yyyy-MM-dd").format(date);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println(newdat);
//		String tempQuery = "Select eb from EBond AS eb WHERE eb.maturityDate = '" + newdat + "'";
		
		TypedQuery<EBond> query = em.createQuery(tempQuery, EBond.class);

		return (List<EBond>) query.getResultList();
	}

}
