/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Lớp Author chứa các thuộc tính của tác giả như mã tác giả, tên, danh sách tài liệu
 * và các phương thức như lấy mã tài liệu, get set tên, thêm, xóa sách, toString
 * @author admin1
 */
public class Author implements Serializable {

	private static final long serialVersionUID = 1L;
	private final int id;
	private final ArrayList<Book> books;
	private String name;

	public Author(int id, String name) {
		this.id = id;
		this.books = new ArrayList<>();
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void addBook(Book b) {
		this.books.add(b);
	}

	public void removeBook(Book b) {
		this.books.remove(b);
	}

	public int getBooksSize() {
		return this.books.size();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object[] toObject() {
		return new Object[]{
			this.id, this.name, this.books.size()
		};
	}

	@Override
	public String toString() {
		return this.id + "-" + this.name;
	}
}
