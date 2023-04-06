/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Lớp Issued For chứa các thuộc tính liên quan đến lượt mượn như mã mượn, độc giả mượn, tài liệu mượn, ngày mượn, thời hạn mượn, trạng thái trả của lượt mượn
 * và các phương thức như get id, reader, document, date, period, get set trạng thái đã trả
 * 
 * @author admin1
 */
public class IssuedFor implements Serializable {
	private static final long serialVersionUID = 1L;
	private final int id;
	private final Reader reader;
	private final Document document;
	private final Date date;
	private final int period;
	private boolean returned;

	public IssuedFor(int id, Reader reader, Document document, Date date, int period, boolean returned) {
		this.id = id;
		this.reader = reader;
		this.document = document;
		this.date = date;
		this.period = period;
		this.returned = returned;
	}

	public int getId() {
		return id;
	}

	public Reader getReader() {
		return reader;
	}

	public Document getDocument() {
		return document;
	}
	
	public Date getDate(){
		return date;
	}

	public int getPeriod() {
		return period;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}
	
	public Object[] toObject(){
		Calendar c = Calendar.getInstance();
		c.setTime(this.getDate());
		c.add(Calendar.DATE, this.getPeriod());
		
		return new Object[]{
			this.getId(),
			String.valueOf(this.reader.getId()).concat("-").concat(this.reader.getUserName()), 
			this.document.toString(),
			this.document instanceof Book ? "Sách" : "Báo", 
			new SimpleDateFormat("HH:mm dd/MM/yyyy").format(this.getDate()), 
			String.valueOf(this.getPeriod()).concat(" ngày"), 
			c.getTime().before(new Date()) && !this.isReturned() ? "Có" : "Không",
			this.isReturned() ? "Đã trả" : "Chưa trả"
		};
	}
}
