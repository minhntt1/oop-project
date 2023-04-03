/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author admin1
 */
public class Book extends Document implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Author> authors;
	private int pages;

	public Book(String name, ArrayList<Author> authors, int pages, int id, int releaseNo, Publisher p, int copies) {
		super(id, releaseNo, p, copies);
		this.name = name;
		this.authors = authors;
		this.pages = pages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
	public Object[] toObject(){
		return new Object[]{
			this.getId(), this.getReleaseNo(), this.getP().getId()+"-"+this.getP().getName(), this.getName(), this.authors.toArray(), this.pages, this.getCopies()
		};
	}

	@Override
	public String toString() {
		return String.valueOf(this.getId()).concat("-").concat(this.name);
	}
}
