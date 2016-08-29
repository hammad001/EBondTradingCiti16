package ebond.trader.web;

import java.io.StringReader;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

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
	@Consumes("text/plain")
	@Produces("application/json")
	public List<Bond> getBsmBondData(String bsq) {

		System.out.println("in BondResource BSM POST");
		// System.out.println("Entered JSON: " + bsq);

		// imported javax.json for these classes
		JsonReader jsonReader = Json.createReader(new StringReader(bsq));
		JsonObject bsqJson = jsonReader.readObject();
		System.out.println("Entered ISIN: " + bsqJson.getString("isin"));

		return bean.getBondResultSet(bsqJson.getString("isin"), bsqJson.getString("creditRating"),
				bsqJson.getString("couponRateFrom"), bsqJson.getString("couponRateTo"),
				bsqJson.getString("maturityDateFrom"), bsqJson.getString("maturityDateTo"),
				bsqJson.getString("frequency"), bsqJson.getString("currency"));
		// return bean.getBondData();
	}

	@GET // from Blotter
	// @Path("/blotter")
	@Produces("application/json")
	@Consumes("text/plain")
	public List<Bond> getBookedBondData() {
		// fetches data from BookedBondBeanList, so it needs no Json input
		System.out.println("in Booked Bond GET");
		return bean.getBondData();
		//return bean.getBookedBonds();
	}

	@POST // from Trade Booking Screen
	@Path("/TBS")
	@Consumes({ "application/json", "text/plain" })
	@Produces({ "application/json" })
	public void acceptOrder(Bond b) {
		//fetches data as an entity bean BookedBond, so it needs no Json String input (Auto Parsed)
		System.out.println("in BondResource TBS POST");
		bean.putBondData(b);
		System.out.println("Received bond name:" + b.getBondName());

	}

}
