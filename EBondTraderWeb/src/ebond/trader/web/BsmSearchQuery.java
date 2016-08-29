package ebond.trader.web;

public class BsmSearchQuery {

	
	private String isin;
	private String creditRating;
	private String couponRateFrom;
	private String couponRateTo;
	private String maturityDateFrom;
	private String maturityDateTo;
	private String frequency;
	private String currency;
	
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public String getCreditRating() {
		return creditRating;
	}
	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}
	public String getCouponRateFrom() {
		return couponRateFrom;
	}
	public void setCouponRateFrom(String couponRateFrom) {
		this.couponRateFrom = couponRateFrom;
	}
	public String getCouponRateTo() {
		return couponRateTo;
	}
	public void setCouponRateTo(String couponRateTo) {
		this.couponRateTo = couponRateTo;
	}
	public String getMaturityDateFrom() {
		return maturityDateFrom;
	}
	public void setMaturityDateFrom(String maturityDateFrom) {
		this.maturityDateFrom = maturityDateFrom;
	}
	public String getMaturityDateTo() {
		return maturityDateTo;
	}
	public void setMaturityDateTo(String maturityDateTo) {
		this.maturityDateTo = maturityDateTo;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
