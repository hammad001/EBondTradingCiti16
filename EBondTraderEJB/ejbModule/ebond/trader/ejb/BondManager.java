package ebond.trader.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ebond.trader.jpa.BookedBond;
import ebond.trader.jpa.EBond;

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

	public List<BookedBond> getBlotterBonds(String blotterQ) {

		String tempQuery = "SELECT b FROM BookedBond AS b ";
		// if blotterQ is not null, add a where clause, else execute as it is
		if (blotterQ.length() != 0) {
			tempQuery = tempQuery + " WHERE b.ebond.isin='" + blotterQ + "'";// add
																				// quotes
		}
		System.out.println("Executed in Blotter: " + tempQuery);
		TypedQuery<BookedBond> query = em.createQuery(tempQuery, BookedBond.class);

		return (List<BookedBond>) query.getResultList();
	}

	public List<EBond> getBondResultSet(String isin, String creditRating, String couponRateFrom, String couponRateTo,

			String maturityDateFrom, String maturityDateTo, String frequency, String currency, String yeildFrom,
			String yeildTo, String lastPriceFrom, String lastPriceTo) {

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
				&& frequency.length() == 0 && currency.length() == 0 && yeildFrom.length() == 0
				&& yeildFrom.length() == 0 && lastPriceFrom.length() == 0 && lastPriceTo.length() == 0) {
			System.out.println("Search is null");

		} else {

			tempQuery = tempQuery + " WHERE ";// if any entry is not null, we
												// need a WHERE clause first
			notNullCount++;// this is a reference to check whether to add AND or
							// not
			// begin with adding
			// ISIN_________________________________________________
			if (isin.length() != 0 && notNullCount == 0) {
				tempQuery = tempQuery + "b.isin='" + isin + "'";
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
				tempQuery = tempQuery + "b.creditRating='" + creditRating + "'";
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
				tempQuery = tempQuery + "b.maturityDate BETWEEN '" + formatMDFrom + "' AND '" + formatMDTo + "'";
				notNullCount++;

			} else if (maturityDateFrom.length() != 0 && maturityDateTo.length() != 0 && notNullCount > 0) {
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.maturityDate BETWEEN '" + formatMDFrom + "' AND '" + formatMDTo + "'";
				notNullCount++;

			}

			// adding frequency__________________________________
			if (frequency.length() != 0 && notNullCount == 0) {
				// if isin is null,notNullCount is still 0 we proceed after
				// WHERE
				tempQuery = tempQuery + "b.frequency='" + frequency + "'";
				notNullCount++;// any non null String after this will have an
								// AND clause before it

			} else if (frequency.length() != 0 && notNullCount > 0) {
				// if isin is not null,notNullCount>0 we add an AND clause in
				// between
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.couponFrequency='" + frequency + "'";
				notNullCount++;

			}
			// adding currency__________________________________
			if (currency.length() != 0 && notNullCount == 0) {
				// if isin is null,notNullCount is still 0 we proceed after
				// WHERE
				tempQuery = tempQuery + "b.currency='" + currency + "'";
				notNullCount++;// any non null String after this will have an
								// AND clause before it

			} else if (currency.length() != 0 && notNullCount > 0) {
				// if isin is not null,notNullCount>0 we add an AND clause in
				// between
				tempQuery = tempQuery + " AND ";// safety spaces added
				tempQuery = tempQuery + "b.currency='" + currency + "'";
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

		}
		System.out.println("Executed: " + tempQuery);
		TypedQuery<EBond> query = em.createQuery(tempQuery, EBond.class);

		return (List<EBond>) query.getResultList();

	}



	public List<EBond> populateTBS(String TbsIsinQ) {

		String tempQuery = "SELECT b FROM EBond AS b ";
		// if blotterQ is not null, add a where clause, else execute as it is
		if (TbsIsinQ.length() != 0) {
			tempQuery = tempQuery + " WHERE b.isin='" + TbsIsinQ + "'";// add
																		// quotes
		}
		System.out.println("Populated in TBS: " + tempQuery);
		TypedQuery<EBond> query = em.createQuery(tempQuery, EBond.class);

		return query.getResultList();// java.util.ArrayList
													// cannot be cast to
													// ebond.trader.jpa.EBond

	}

	public String putBookedBondData(String buySell, String quantity, String bondId) {

		int quantityInt = Integer.parseInt(quantity);
		int bondIdLookup = Integer.parseInt(bondId);
		char buySellChar = buySell.charAt(0);

		// Changed to get present Date/time from the server instead
		Date presentDate = new Date();
		java.sql.Date formatPdate = new java.sql.Date(presentDate.getTime());
		BookedBond bookedbond = new BookedBond(buySellChar, quantityInt, formatPdate);
		bookedbond.setEbond(em.find(EBond.class, bondIdLookup));
		em.persist(bookedbond);
		// Check for a case in which Booked bond has exactly same details.
		if (em.contains(bookedbond)) {
			return "Success";
		} else {
			return "Failure";
		}

	}

}
