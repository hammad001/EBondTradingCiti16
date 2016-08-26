package ebond.trader.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ebond.trader.jpa.Bond;


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
    
    public void putBondData(Bond bondData){
    	em.persist(bondData);
    }
    
    public List<Bond> getBondData(){
		TypedQuery<Bond> query = em.createQuery("SELECT b"+
				" FROM Bond AS b",Bond.class);

		return (List<Bond>)query.getResultList();
    }

}
