package ebond.trader.ejb;

import java.util.List;

import javax.ejb.Local;

import ebond.trader.jpa.Bond;

@Local
public interface BondManagerLocal {
	void putBondData(Bond bondData);
	List<Bond> getBondData();
}
