package ebond.trader.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: EBond
 *
 */
@Entity
@Table(name="EBOND")
public class EBond implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public EBond() {
		super();
	}
	
	@Id
	private int bondId;
	
	private String isin;
	private Date issueDate;
	private Date settlementDays;
	private int faceValue;
	private double couponRate;
	private char couponFrequency;
	private Date maturityDate;
	private double lastPrice;
	private double high;
	private double low;
	private double changeInPrice;
	private double yeild;
	private String creditRating;
	private String currency;
	

	
	public EBond(String isin, Date issueDate, Date settlementDays, int faceValue, double couponRate,
			char couponFrequency, Date maturityDate, double lastPrice, double high, double low, double changeInPrice,
			double yeild, String creditRating, String currency) {
		super();
		this.isin = isin;
		this.issueDate = issueDate;
		this.settlementDays = settlementDays;
		this.faceValue = faceValue;
		this.couponRate = couponRate;
		this.couponFrequency = couponFrequency;
		this.maturityDate = maturityDate;
		this.lastPrice = lastPrice;
		this.high = high;
		this.low = low;
		this.changeInPrice = changeInPrice;
		this.yeild = yeild;
		this.creditRating = creditRating;
		this.currency = currency;
	}

	public int getBondId() {
		return bondId;
	}

	public void setBondId(int bondId) {
		this.bondId = bondId;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getSettlementDays() {
		return settlementDays;
	}

	public void setSettlementDays(Date settlementDays) {
		this.settlementDays = settlementDays;
	}

	public int getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(int faceValue) {
		this.faceValue = faceValue;
	}

	public double getCouponRate() {
		return couponRate;
	}

	public void setCouponRate(double couponRate) {
		this.couponRate = couponRate;
	}

	public char getCouponFrequency() {
		return couponFrequency;
	}

	public void setCouponFrequency(char couponFrequency) {
		this.couponFrequency = couponFrequency;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getChangeInPrice() {
		return changeInPrice;
	}

	public void setChangeInPrice(double changeInPrice) {
		this.changeInPrice = changeInPrice;
	}

	public double getYeild() {
		return yeild;
	}

	public void setYeild(double yeild) {
		this.yeild = yeild;
	}

	public String getCreditRating() {
		return creditRating;
	}

	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
   
}
