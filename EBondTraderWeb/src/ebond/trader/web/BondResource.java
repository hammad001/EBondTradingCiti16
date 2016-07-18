package ebond.trader.web;

import java.io.StringReader;
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
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import ebond.trader.ejb.BondManagerLocal;
import ebond.trader.jpa.BookedBond;
import ebond.trader.jpa.EBond;

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
	@Path("/bsm")
	public List<EBond> getBsmBondData(String bsq) {

		System.out.println("in BondResource BSM POST");
		System.out.println("BSM Received JSON: " + bsq);

		JsonReader jsonReaderBSM = Json.createReader(new StringReader(bsq));
		JsonObject bsqJson = jsonReaderBSM.readObject();

		return bean.getBondResultSet(bsqJson.getString("isin"), bsqJson.getString("creditRating"),
				bsqJson.getString("couponRateFrom"), bsqJson.getString("couponRateTo"),
				bsqJson.getString("maturityDateFrom"), bsqJson.getString("maturityDateTo"),
				bsqJson.getString("frequency"), bsqJson.getString("currency"), bsqJson.getString("yeildFrom"), bsqJson.getString("yeildTo"), bsqJson.getString("lastPriceFrom"), bsqJson.getString("lastPriceTo"));
	}

	@GET // from Blotter
	@Path("/blotter")
	@Produces("application/json")
	@Consumes("text/plain")
	public List<BookedBond> getBookedBondData(@QueryParam("isin") @DefaultValue("") String blotterQ) {
						
		System.out.println("in Blotter GET");
		if(blotterQ.length()!=0){
			System.out.println("Blotter Search Query ISIN: "+blotterQ);
		}
		
		return bean.getBlotterBonds(blotterQ);
		
	}


	@POST // from Trade Booking Screen
	@Path("/tbs")
	@Consumes({ "text/plain" })
	@Produces({ "application/json" })
	public HashMap<String, String> acceptBooking(String bookingParam) {
		JsonReader jsonReaderBookingParam = Json.createReader(new StringReader(bookingParam));
		JsonObject bookingParamJson = jsonReaderBookingParam.readObject();
		
		System.out.println("in BondResource TBS POST");
		String response = bean.putBookedBondData(bookingParamJson.getString("buySell"),
				bookingParamJson.getString("quantity"), bookingParamJson.getString("bondId"));
		
		// Getting success response as a string, Passing to a key value pair
		HashMap<String, String> responseMap = new HashMap<>();
		responseMap.put("status", response);
		return responseMap;
	}
	
	
	@GET // from Trade Booking Screen
	@Path("/tbs")
	@Produces({ "application/json" })
	public List<EBond> populateBooking(@QueryParam("isin") @DefaultValue("") String TbsIsinQ) {
		//fetches data as an entity bean BookedBond, so it needs no Json String input (Auto Parsed)
		System.out.println("in BondResource TBS GET, Looking for: "+TbsIsinQ );
		return bean.populateTBS(TbsIsinQ);
		
	}

	

}
