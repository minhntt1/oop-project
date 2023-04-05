/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author admin1
 */
public class Reader extends Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date registerDate;
	private boolean activeStatus;

	public Reader(Date registerDate, boolean activeStatus, int id, String name, String userName, String password) {
		super(id, name, userName, password);
		this.registerDate = registerDate;
		this.activeStatus = activeStatus;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public boolean isActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	public Object[] toObject(){
		return new Object[]{
			this.getId(), this.getName(), this.getUserName(), new SimpleDateFormat("HH:mm dd/MM/yyyy").format(this.getRegisterDate()), this.isActiveStatus() ? "Có" : "Không"
		};
	}

	@Override
	public String toString() {
		return String.valueOf(this.getId()).concat("-").concat(this.getUserName());
	}
}
