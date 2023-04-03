/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.stream.Collectors;
import model.Author;
import model.Publisher;

/**
 *
 * @author admin1
 */
public class PublisherAuthorManagement {

	private static PublisherAuthorManagement object;
	private final ArrayList<Publisher> publishers;	//mang chua doi tuong Person
	private final ArrayList<Author> authors;

	private PublisherAuthorManagement() {
		this.publishers = FileManagement.getObject().publishersDat();
		this.authors = FileManagement.getObject().authorsDat();
	}

	public static PublisherAuthorManagement getObject() {
		if (object == null) {
			object = new PublisherAuthorManagement();
		}
		return object;
	}

	public int updatePAList() {
		return FileManagement.getObject().writeData();
	}

	public Object[] addAuthor(String name) {
		int id = this.authors.isEmpty() ? 10000 : this.authors.get(this.authors.size() - 1).getId() + 1;
		Author a = new Author(id, name);

		this.authors.add(a);

		return new Object[]{this.updatePAList(), a};
	}

	public int removeAuthor(int id) {
		Author a = this.authors.stream()
				.filter(x -> x.getId() == id)
				.findFirst()
				.orElse(null);

		if (a == null) {
			return 3;
		}

		if (a.getBooksSize() > 0) {
			return 4;
		}

		this.authors.remove(a);

		return this.updatePAList();
	}

	public ArrayList<Author> findAuthor(int id, String name) {
		return this.authors
				.stream()
				.filter(x
						-> (id == -1 ? true : x.getId() == id)
				&& (name.isEmpty() ? true : x.getName().toLowerCase().contains(name.toLowerCase()))
				)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@SuppressWarnings("empty-statement")
	public Object[] addPublisher(String name) {
		int id = this.publishers.isEmpty() ? 10000 : this.publishers.get(this.publishers.size() - 1).getId() + 1;;

		Publisher p = new Publisher(id, name);

		this.publishers.add(p);

		return new Object[]{this.updatePAList(), p};
	}

	public int removePublisher(int id) {
		Publisher p = this.publishers.stream()
				.filter(x -> x.getId() == id)
				.findFirst()
				.orElse(null);

		if (p == null) {
			return 3;
		}

		if (p.getDocsSize() > 0) {
			return 4;
		}

		this.publishers.remove(p);

		return this.updatePAList();
	}

	public ArrayList<Publisher> findPublisher(int id, String name) {
		ArrayList<Publisher> res = this.publishers.stream()
				.filter(x
						-> (id == -1 ? true : x.getId() == id)
				&& (name.isEmpty() ? true : x.getName().toLowerCase().contains(name.toLowerCase()))
				)
				.collect(Collectors.toCollection(ArrayList::new));

		return res;
	}
}
