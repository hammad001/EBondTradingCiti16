package ebond.trader.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: UserAccount
 *
 */
@Entity
@Table(name="USER")
public class UserAccount implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public UserAccount(){
		super();
	}
	
	@Id
	private int accountId;
	
	private String name;
	private String userName;
	private String password;
	
	public UserAccount(String name, String userName, String password){
		super();
		this.name=name;
		this.userName=userName;
		this.password=password;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
