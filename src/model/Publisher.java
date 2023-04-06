/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Lớp Publisher bao gồm các thuộc tính liên quan đến nhà xuất bản như mã, tên, và danh sách tài liệu của nhà xuất bản
 * các phương thức như get id, get set name và thêm, xóa tài liệu, toString
 * @author admin1
 */
public class Publisher implements Serializable {

	private static final long serialVersionUID = 1L;
	private final int id;
	private String name;
	private final ArrayList<Document> docs;

	public Publisher(int id, String name) {
		this.id = id;
		this.name = name;
		this.docs = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addDoc(Document d) {
		this.docs.add(d);
	}

	public void removeDoc(Document d) {
		this.docs.remove(d);
	}

	public int getDocsSize() {
		return this.docs.size();
	}

	public Object[] toObject() {
		return new Object[]{
			this.id, this.name, this.docs.size()
		};
	}

	@Override
	public String toString() {
		return this.id + "-" + this.name;
	}
}
