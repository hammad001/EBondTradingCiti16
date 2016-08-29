package ebond.trader.web;

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
	
	public BondResource(){
		
		try {
			context = new InitialContext();
			bean = (BondManagerLocal) context.lookup("java:global/EBondTrader/EBondTraderEJB/BondManager!ebond.trader.ejb.BondManagerLocal");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GET
	@Produces("application/json")
	public List<Bond> getBondData() {
		//System.out.println("ENTEREd get method");
		return bean.getBondData();
	}
	
	@POST
	@Consumes({"application/json","text/plain"})	
	@Produces({"application/json"})
	public void acceptOrder(Bond b){
		bean.putBondData(b);
		System.out.println("Received bond name.");
		
	}
}
