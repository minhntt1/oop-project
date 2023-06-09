/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 * Lớp Librarian kế thừa Person bao gồm các thuộc tính của thủ thư như số điện thoại
 * và các phương thức get set số điện thoại
 * 
 * @author admin1
 */
public class Librarian extends Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private String phoneNo;

	public Librarian(String phoneNo, int id, String name, String userName, String password) {
		super(id, name, userName, password);
		this.phoneNo = phoneNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
