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
import javax.ws.rs.PathParam;
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

		// imported javax.json for these classes
		JsonReader jsonReaderBSM = Json.createReader(new StringReader(bsq));
		JsonObject bsqJson = jsonReaderBSM.readObject();
		//System.out.println("BSM Search Query ISIN: " + bsqJson.getString("isin"));

		return bean.getBondResultSet(bsqJson.getString("isin"), bsqJson.getString("creditRating"),
				bsqJson.getString("couponRateFrom"), bsqJson.getString("couponRateTo"),
				bsqJson.getString("maturityDateFrom"), bsqJson.getString("maturityDateTo"),
				bsqJson.getString("frequency"), bsqJson.getString("currency"), bsqJson.getString("yeildFrom"), bsqJson.getString("yeildTo"), bsqJson.getString("lastPriceFrom"), bsqJson.getString("lastPriceTo"));
		// return bean.getBondData();
	}

	@GET // from Blotter
	@Path("/blotter/{accountId}")
	//@Consumes("text/plain")//DO NOT ADD Consumes Annotation to a GET 
	@Produces("application/json")
	@Consumes("text/plain")
	public List<BookedBond> getBookedBondData(@PathParam("accountId") String accountId, 
											  @QueryParam("isin") @DefaultValue("") String isin) {
		// fetches data from BookedBondBeanList, so it needs no Json input
						
		System.out.println("in Blotter GET "+accountId+", isin = "+isin);
		if(isin.length()!=0){
			System.out.println("Blotter Search Query ISIN: "+isin);
		}
		
		return bean.getBlotterBonds(accountId, isin);
	}


	@POST // from Trade Booking Screen
	@Path("/tbs")
	@Consumes({ "text/plain" })
	@Produces({ "application/json" })
	public HashMap<String, String> acceptBooking(String bookingParam) {
		//fetches data as an entity bean BookedBond, so it needs no Json String input (Auto Parsed)
		JsonReader jsonReaderBookingParam = Json.createReader(new StringReader(bookingParam));
		JsonObject bookingParamJson = jsonReaderBookingParam.readObject();
		
		System.out.println("in BondResource TBS POST");
		String response = bean.putBookedBondData(bookingParamJson.getString("buySell"),
				bookingParamJson.getString("quantity"), bookingParamJson.getString("bondId"),bookingParamJson.getString("accountId"));
		
		// Getting success response as a string, Passing to a key value pair
		HashMap<String, String> responseMap = new HashMap<>();
		responseMap.put("status", response);
		return responseMap;
	}
	
	// A test function to test individual queries
//	@GET
//	@Path("/test")
//	@Produces("application/json")
//	public List<EBond> testFunction(){
//		return bean.getResultFromQuery();
//	}
	
//	@POST
//	@Path("/test")
//	@Consumes("text/plain")
//	@Produces("application/json")
//	public List<EBond> testFunction(String bsq){
//		// imported javax.json for these classes
//		JsonReader jsonReader = Json.createReader(new StringReader(bsq));
//		JsonObject bsqJson = jsonReader.readObject();
//		System.out.println("Entered ISIN: " + bsqJson.getString("isin"));
//		return bean.getResultFromQuery(bsqJson.getString("isin"));
//		return bean.getResultFromQuery();
//	}
	
	@GET // from Trade Booking Screen
	@Path("/tbs")
	//@Consumes({ "text/plain" })//DO NOT ADD @Consumes Annotation to GET
	@Produces({ "application/json" })
	public List<EBond> populateBooking(@QueryParam("isin") @DefaultValue("") String TbsIsinQ) {
		//fetches data as an entity bean BookedBond, so it needs no Json String input (Auto Parsed)
		System.out.println("in BondResource TBS GET, Looking for: "+TbsIsinQ );
		return bean.populateTBS(TbsIsinQ);
		//System.out.println("Received bond name:" + b.getBondName());
		
	}

	@POST
	@Path("/register")
	@Consumes("text/plain")
	@Produces("application/json")
	public HashMap<String, String> userRegister(String registerParam){
		
		JsonReader jsonReaderRegisterParam = Json.createReader(new StringReader(registerParam));
		JsonObject registerParamJson = jsonReaderRegisterParam.readObject();
		
		// checking the rest method
		System.out.println("Entered in register method with param : "+registerParam);
		return bean.registerUser(registerParamJson.getString("name"), registerParamJson.getString("userName"), registerParamJson.getString("password"));
	}
	
	@POST
	@Path("/login")
	@Consumes("text/plain")
	@Produces("application/json")
	public HashMap<String, String> userLogin(String loginParam){
		JsonReader jsonReaderLoginParam = Json.createReader(new StringReader(loginParam));
		JsonObject loginParamJson = jsonReaderLoginParam.readObject();
		
		// checking the rest method
		System.out.println("Entered in login method with param :"+loginParam);
		return bean.loginUser(loginParamJson.getString("userName"), loginParamJson.getString("password"));		
	}
	
	
	/*public void acceptJsonBooking(String booking) {
		//fetches data as an entity bean BookedBond, so it needs no Json String input (Auto Parsed)
		System.out.println("in BondResource TBS POST");
		System.out.println("BSM Received JSON: " + booking);

		// imported javax.json for these classes
		JsonReader jsonReaderBSM = Json.createReader(new StringReader(booking));
		JsonObject bsqJson = jsonReaderBSM.readObject();
		System.out.println("BSM Search Query ISIN: " + bsqJson.getString("isin"));

		return bean.placeBooking(bsqJson.getString("orderId"), bsqJson.getString("bondId"),
				bsqJson.getString("buySell"), bsqJson.getString("quantity"),
				bsqJson.getString("isin"), bsqJson.getString("issuedBy"),
				bsqJson.getString("couponRate"), bsqJson.getString("couponFrequency"), bsqJson.getString("maturityDate"), bsqJson.getString("yeildTo"), bsqJson.getString("lastPriceFrom"), bsqJson.getString("lastPriceTo"));
		// return bean.getBondData();

		
		
		//bean.putBookedBondData(b);
		//System.out.println("Received bond name:" + b.getBondName());

	}*/
	
}
