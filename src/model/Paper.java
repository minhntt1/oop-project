/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Lớp Paper kế thừa Document gồm các thuộc tính của báo như ngày phát hành
 * và phương thức get set ngày phát hành
 * 
 * @author admin1
 */
public class Paper extends Document implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date releaseDate;

	public Paper(Date releaseDate, int id, int releaseNo, Publisher p, int copies) {
		super(id, releaseNo, p, copies);
		this.releaseDate = releaseDate;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public Object[] toObject(){
		return new Object[]{
			this.getId(), this.getReleaseNo(), this.getP().getId()+"-"+this.getP().getName(), new SimpleDateFormat("dd/MM/yyyy").format(releaseDate), this.getCopies()
		};
	}

	@Override
	public String toString() {
		return String.valueOf(this.getId()).concat("-").concat(new SimpleDateFormat("dd/MM/yyyy").format(releaseDate));
	}
}
