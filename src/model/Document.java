/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 * Lớp Document chứa các thuộc tính liên quan đến tài liệu như mã, số bản phát hành, nhà xuất bản, số lượng
 * và các phương thức như get id, get set số bản phát hành, nhà xuất bản và số bản sao
 *
 * @author admin1
 */
public class Document implements Serializable {
	private static final long serialVersionUID = 1L;
	private final int id;
	private int releaseNo;
	private Publisher p;
	private int copies;

	public Document(int id, int releaseNo, Publisher p, int copies) {
		this.id = id;
		this.releaseNo = releaseNo;
		this.p = p;
		this.copies = copies;
	}

	public int getId() {
		return id;
	}

	public int getReleaseNo() {
		return releaseNo;
	}

	public void setReleaseNo(int releaseNo) {
		this.releaseNo = releaseNo;
	}

	public Publisher getP() {
		return p;
	}

	public void setP(Publisher p) {
		this.p = p;
	}

	public int getCopies() {
		return copies;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}
}
