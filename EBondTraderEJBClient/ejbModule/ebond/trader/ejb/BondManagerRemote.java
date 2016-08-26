package ebond.trader.ejb;

import java.util.List;

import javax.ejb.Remote;

import ebond.trader.jpa.Bond;

@Remote
public interface BondManagerRemote {
	void putBondData(Bond bondData);
	List<Bond> getBondData();
}
