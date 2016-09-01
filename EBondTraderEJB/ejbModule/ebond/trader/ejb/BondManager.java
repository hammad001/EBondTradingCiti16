package ebond.trader.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ebond.trader.jpa.BookedBond;
import ebond.trader.jpa.EBond;
import ebond.trader.jpa.UserAccount;

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

	public List<EBond> getBondData() {
		TypedQuery<EBond> query = em.createQuery("SELECT b" + " FROM EBond AS b", EBond.class);

		return (List<EBond>) query.getResultList();
	}

	public List<BookedBond> getBlotterBonds(String accountId, String isin) {
		//int accId = Integer.parseInt(accountId);
		String tempQuery = "SELECT b FROM BookedBond AS b WHERE b.userAccount.accountId="+accountId;
		// if blotterQ is not null, add a where clause, else execute as it is
		if (isin.length() != 0) {
			tempQuery = tempQuery + " AND b.ebond.isin='" + isin + "'";// add
																				// quotes
		}
		System.out.println("Executed in Blotter: " + tempQuery);
		TypedQuery<BookedBond> query = em.createQuery(tempQuery, BookedBond.class);

		return (List<BookedBond>) query.getResultList();
	}
	
	public HashMap<String, String> registerUser(String name, String userName, String password){
		UserAccount userAcc = new UserAccount(name,userName,password);
		HashMap<String, String> response = new HashMap<>();
		String tempQuery = "SELECT u FROM UserAccount AS u where u.userName='"+userName+"'";
		TypedQuery<UserAccount> query = em.createQuery(tempQuery,UserAccount.class);
		if(query.getResultList().isEmpty()){
			System.out.println("Registering : "+name+" with username : "+userName);
			em.persist(userAcc);
			if(em.contains(userAcc)){
				response.put("status", "success");
				return response;
			}else{
				response.put("status", "failure");
				response.put("errorMsg", "Database Access Failure");
				return response;
			}
		}else{
			response.put("status", "failure");
			response.put("errorMsg", "Username already exists");
			return response;
			// return "User already exists"    //
		}
		
	}
	
	public HashMap<String, String> loginUser(String userName, String password){	
		HashMap<String, String> response = new HashMap<>();
		
		String tempQuery = "SELECT u FROM UserAccount AS u where u.userName='"+userName+"'";
		TypedQuery<UserAccount> query = em.createQuery(tempQuery,UserAccount.class);
		
		if(!query.getResultList().isEmpty()){
			UserAccount userAccount = query.getSingleResult();
			System.out.println("Database password : "+userAccount.getPassword()+" , Rest Password : "+password);
			
			if(userAccount.getPassword().equals(password)){
				response.put("status", "success");
				response.put("name", userAccount.getName());
				response.put("accountId", String.valueOf(userAccount.getAccountId()));
				return response;		
			}else {
				response.put("status", "failure");
				response.put("errorMsg", "Invalid Password");
				return response;
			}
		}else{
			response.put("status", "failure");
			response.put("errorMsg", "Username doesn't exist");
			return response;
		}
		//UserAccount userAcc = em.find(UserAccount.class, userName);
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

	public List<EBond> getTestResult() {
		// String tempQuery = "Select eb from EBond AS eb WHERE eb.isin =
		// 'TYE047'";
		String tempQuery = "Select eb from EBond AS eb WHERE eb.lastPrice BETWEEN 200 AND 300";
		// String tempQuery = "Select eb from EBond AS eb WHERE eb.maturityDate
		// BETWEEN '2030-01-01' AND '2040-01-01'";
		// String newdat = new String();
		//
		// try {
		// Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dat);
		// newdat = new SimpleDateFormat("yyyy-MM-dd").format(date);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// System.out.println(newdat);
		// String tempQuery = "Select eb from EBond AS eb WHERE eb.maturityDate
		// = '" + newdat + "'";

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

	public String putBookedBondData(String buySell, String quantity, String bondId, String accountId) {

		int quantityInt = Integer.parseInt(quantity);
		int bondIdLookup = Integer.parseInt(bondId);
		char buySellChar = buySell.charAt(0);
		int bookedByLookup = Integer.parseInt(accountId);

		// Changed to get present Date/time from the server instead
		Date presentDate = new Date();
		java.sql.Date formatPdate = new java.sql.Date(presentDate.getTime());
		BookedBond bookedbond = new BookedBond(buySellChar, quantityInt, formatPdate);  // Change as per user details IMPORTANT
		bookedbond.setEbond(em.find(EBond.class, bondIdLookup));
		bookedbond.setUserAccount(em.find(UserAccount.class, bookedByLookup));
		if(em.find(EBond.class, bondIdLookup)!=null && em.find(UserAccount.class, bookedByLookup)!=null)
				em.persist(bookedbond);
		// Check for a case in which Booked bond has exactly same details.
		if (em.contains(bookedbond)) {
			return "Success";
		} else {
			return "Failure";
		}

	}

}
