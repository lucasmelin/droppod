package droppod.models;

public class UserModel {
	private int id;
	private String name;
	private String email;
	private int active_status;
	private int account_type;
	
	public UserModel() {
		id = -1;
		name = null;
		email = null;
		active_status = -1;
		account_type = -1;
	}
	
	public UserModel(int id, String name, String email, int active_status, int account_type) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.active_status = active_status;
		this.account_type = account_type;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getActiveStatus() {
		return active_status;
	}
	
	public void setActiveStatus(int active_status) {
		this.active_status = active_status;
	}
	
	public int getAccountType() {
		return account_type;
	}
	
	public void setAccountType(int account_type) {
		this.account_type = account_type;
	}
	
}