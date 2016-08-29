package ebond.trader.jpa;

import java.io.Serializable;
import java.util.Date;

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
	private int bondId;
	private char buySell;
	private int quantity;
	private Date purchaseDate;
	
	@OneToOne
	@JoinColumn(name="BondId")
	private EBond ebond;
	
	public BookedBond() {
		super();
	}

	public BookedBond(int bondId, char buySell, int quantity, Date purchaseDate) {
		super();
		this.bondId = bondId;
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

	public int getBondId() {
		return bondId;
	}

	public void setBondId(int bondId) {
		this.bondId = bondId;
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
	
	
	
	
   
}
