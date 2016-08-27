package ebond.trader.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import ebond.trader.ejb.BondManagerLocal;
import ebond.trader.jpa.Bond;

@RequestScoped
@Path("/bond")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class BondResource {

	Context context;
	BondManagerLocal bean;

	public BondResource() {

		try {
			context = new InitialContext();
			bean = (BondManagerLocal) context
					.lookup("java:global/EBondTrader/EBondTraderEJB/BondManager!ebond.trader.ejb.BondManagerLocal");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@POST // from Bond Static Maintenance
	@Produces("application/json")
	@Consumes("application/json")
	public List<Bond> getBsmBondData(BsmSearchQuery bsq) {

		System.out.println("in BondResource BSM POST");

		System.out.println("Entered ISIN: " + bsq.getIsin());
		System.out.println("Entered Currency: " + bsq.getCurrency());

		/*List<String> SearchQueryParams = Arrays.asList("isin", "creditRating", "couponRateFrom", "couponRateTo",
				"maturityDateFrom", "maturityDateTo", "frequency", "currency");
		List<String> SearchQueryValues = Arrays.asList(bsq.getIsin(), bsq.getCreditRating(), bsq.getCouponRateFrom(),
				bsq.getCouponRateTo(), bsq.getMaturityDateFrom(), bsq.getMaturityDateTo(), bsq.getFrequency(),
				bsq.getCurrency());
		System.out.println(SearchQueryParams);
		System.out.println(SearchQueryValues);*/
		
		/*return bean.getBondResultSet(bsq.getIsin(), bsq.getCreditRating(), bsq.getCouponRateFrom(),
				bsq.getCouponRateTo(), bsq.getMaturityDateFrom(), bsq.getMaturityDateTo(), bsq.getFrequency(),
				bsq.getCurrency());*/
		return bean.getBondData();
	}

	@GET // from Blotter
	// @Path("/blotter")
	@Produces("application/json")
	@Consumes("text/plain")
	public List<Bond> getBookedBondData() {
		System.out.println("in Booked Bond GET");
		return bean.getBondData();
	}

	@POST // from Trade Booking Screen
	@Consumes({ "application/json", "text/plain" })
	@Produces({ "application/json" })
	public void acceptOrder(Bond b) {
		System.out.println("in BondResource TBS POST");
		bean.putBondData(b);
		System.out.println("Received bond name:" + b.getBondName());

	}
}
