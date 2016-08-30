package ebond.trader.jpa;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: BookedBond
 *
 */
@Entity

public class BookedBond implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	private int orderId;
	private char buySell;
	private int quantity;
	private Date purchaseDate;
	
	@ManyToOne
	@JoinColumn(name="BondId")
	private EBond ebond;
	
	public BookedBond() {
		super();
	}

	public BookedBond( char buySell, int quantity, Date purchaseDate) {
		super();
		this.buySell = buySell;
		this.quantity = quantity;
		this.purchaseDate = purchaseDate;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public char getBuySell() {
		return buySell;
	}

	public void setBuySell(char buySell) {
		this.buySell = buySell;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public EBond getEbond() {
		return ebond;
	}

	public void setEbond(EBond ebond) {
		this.ebond = ebond;
	}
	
	
	
	
   
}
