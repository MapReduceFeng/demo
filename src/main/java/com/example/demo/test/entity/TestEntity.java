package com.example.demo.test.entity;

import java.io.Serializable;
public class TestEntity  implements Serializable {
	private static final long serialVersionUID =  1518072885920094017L;

	private Integer id; // 

	private String userName; // 

	private String userPass; // 

	private String userRole; // 

	public Integer getId() {
		return this.id;}

	public void setId(Integer id) {
		this.id = id;
}

	public String getUserName() {
		return this.userName;}

	public void setUserName(String userName) {
		this.userName = userName;
}

	public String getUserPass() {
		return this.userPass;}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
}

	public String getUserRole() {
		return this.userRole;}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
}

}
