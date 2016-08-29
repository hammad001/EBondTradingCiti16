package ebond.trader.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ebond.trader.jpa.Bond;

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

	/*
	 * public List<Bond> getBookedBonds() { TypedQuery<Bond> query =
	 * em.createQuery("SELECT b" + " FROM BookedBond AS b", Bond.class);
	 * 
	 * return (List<Bond>) query.getResultList(); }
	 */

	public List<Bond> getBondResultSet(String isin, String creditRating, String couponRateFrom, String couponRateTo,
<<<<<<< HEAD
			String maturityDateFrom, String maturityDateTo, String frequency, String currency, String lastPriceFrom,
			String lastPriceTo, String yield) {

		String tempQuery = "SELECT b from eBond AS b ";// trailing spaces added
=======
			String maturityDateFrom, String maturityDateTo, String frequency, String currency, String yeildFrom, String yeildTo, String lastPriceFrom, String lastPriceTo) {

		String tempQuery = "SELECT b from EBond AS b ";// trailing spaces added
>>>>>>> refs/remotes/origin/Hammad
														// to prevent accidental
														// concat
		int notNullCount = -1;

		// blank query case
		if (isin.length() == 0 && creditRating.length() == 0 && couponRateFrom.length() == 0
				&& couponRateTo.length() == 0 && maturityDateFrom.length() == 0 && maturityDateTo.length() == 0
<<<<<<< HEAD
				&& frequency.length() == 0 && currency.length() == 0 && lastPriceFrom.length() == 0
				&& lastPriceTo.length() == 0 && yield.length() == 0) {
=======
				&& frequency.length() == 0 && currency.length() == 0 && yeildFrom.length() == 0 && yeildFrom.length() == 0 && lastPriceFrom.length()==0 && lastPriceTo.length()==0) {
>>>>>>> refs/remotes/origin/Hammad
			System.out.println("Search is null");

		} else {

			tempQuery = tempQuery + " WHERE ";// if any entry is not null, we
												// need a WHERE clause first
			notNullCount++;// this is a reference to check whether to add AND or
							// not
			// begin with adding
			// ISIN_________________________________________________
			if (isin.length() != 0 && notNullCount == 0) {
				tempQuery = tempQuery + "b.isin=" + isin;
				notNullCount++;// any non null String after this will have an
								// AND clause before it
			}
			// adding creditRating__________________________________
			if (creditRating.length() != 0 && notNullCount == 0) {
				// if isin is null,notNullCount is still 0 we proceed after
				// WHERE
				tempQuery = tempQuery + "b.creditRating=" + creditRating;
				notNullCount++;// any non null String after this will have an
								// AND clause before it

			} else if (creditRating.length() != 0 && notNullCount > 0) {
				// if isin is not null,notNullCount>0 we add an AND clause in
				// between
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.creditRating=" + creditRating;
				notNullCount++;

			}
			// adding couponRateFrom and To__________________________________
			if (couponRateFrom.length() != 0 && couponRateTo.length() != 0 && notNullCount == 0) {
				tempQuery = tempQuery + "b.couponRate BETWEEN " + couponRateFrom + " AND " + couponRateTo;
				notNullCount++;

			} else if (couponRateFrom.length() != 0 && couponRateTo.length() != 0 && notNullCount > 0) {
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.couponRate BETWEEN " + couponRateFrom + " AND " + couponRateTo;
				notNullCount++;

			}
			// adding maturityDateFrom and To__________________________________
			if (maturityDateFrom.length() != 0 && maturityDateTo.length() != 0 && notNullCount == 0) {
				tempQuery = tempQuery + "b.maturityDate BETWEEN " + maturityDateFrom + " AND " + maturityDateTo;
				notNullCount++;

			} else if (maturityDateFrom.length() != 0 && maturityDateTo.length() != 0 && notNullCount > 0) {
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.maturityDate BETWEEN " + maturityDateFrom + " AND " + maturityDateTo;
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
				tempQuery = tempQuery + "b.currency=" + currency;
				notNullCount++;// any non null String after this will have an
								// AND clause before it

			} else if (currency.length() != 0 && notNullCount > 0) {
				// if isin is not null,notNullCount>0 we add an AND clause in
				// between
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.currency=" + currency;
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
			// adding lastPriceFrom and To__________________________________
			if (lastPriceFrom.length() != 0 && lastPriceTo.length() != 0 && notNullCount == 0) {
				tempQuery = tempQuery + "b.lastPrice BETWEEN " + lastPriceFrom + " AND " + lastPriceTo;
				notNullCount++;

			} else if (lastPriceFrom.length() != 0 && lastPriceTo.length() != 0 && notNullCount > 0) {
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.lastPrice BETWEEN " + lastPriceFrom + " AND " + lastPriceTo;
				notNullCount++;

			}
			// adding yield__________________________________
			if (yield.length() != 0 && notNullCount == 0) {
				tempQuery = tempQuery + "b.yield=" + yield;
				notNullCount++;// any non null String after this will have an
								// AND clause before it

			} else if (yield.length() != 0 && notNullCount > 0) {
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.yield=" + yield;
				notNullCount++;

			}

		}
		System.out.println("Executed: " + tempQuery);
		TypedQuery<Bond> query = em.createQuery(tempQuery, Bond.class);

		return (List<Bond>) query.getResultList();
	}


}
