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

	/**
	 * đối tượng quản lý nxb/tác giả
	 */
	private static PublisherAuthorManagement object;
	/**
	 * danh sách tài nhà xuất bản cần quản lý
	 */
	private final ArrayList<Publisher> publishers;	//mang chua doi tuong Person
	/**
	 * danh sách tác giả cần quản lý
	 */
	private final ArrayList<Author> authors;

	/**
	 * có chức năng khởi tạo và lưu vào danh sách để quản lý
	 */
	private PublisherAuthorManagement() {
		this.publishers = FileManagement.getObject().publishersDat();
		this.authors = FileManagement.getObject().authorsDat();
	}

	/**
	 * @return trả về đối tượng của lớp hiện tại
	 */
	public static PublisherAuthorManagement getObject() {
		if (object == null) {
			object = new PublisherAuthorManagement();
		}
		return object;
	}

	/**
	 * có chức năng lưu dữ liệu vào file
	 * @return trả về trạng thái ghi ra file dữ liệu 
	 */
	public int updatePAList() {
		return FileManagement.getObject().writeData();
	}

	/**
	 * thêm tác giả vào danh sách
	 * @param name tên tác giả
	 * @return mã trạng thái và đối tượng tác giả sau khi thêm
	 */
	public Object[] addAuthor(String name) {
		int id = this.authors.isEmpty() ? 10000 : this.authors.get(this.authors.size() - 1).getId() + 1;
		Author a = new Author(id, name);

		this.authors.add(a);

		return new Object[]{this.updatePAList(), a};
	}

	/**
	 * xóa tác giả khỏi danh sách
	 * @param id mã tác giả
	 * @return mã trạng thái sau khi xóa tác giả
	 */
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

	/**
	 * tìm kiếm tác giả theo điều kiện 
	 * @param id mã tác giả
	 * @param name tên tác giả
	 * @return danh sách tác giả
	 */
	public ArrayList<Author> findAuthor(int id, String name) {
		return this.authors
				.stream()
				.filter(x
						-> (id == -1 ? true : x.getId() == id)
				&& (name.isEmpty() ? true : x.getName().toLowerCase().contains(name.toLowerCase()))
				)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * thêm nhà xuất bản
	 * @param name tên nhà xuất bản
	 * @return mã trạng thái thêm và đối tượng nhà xuất bản sau khi thêm
	 */
	@SuppressWarnings("empty-statement")
	public Object[] addPublisher(String name) {
		int id = this.publishers.isEmpty() ? 10000 : this.publishers.get(this.publishers.size() - 1).getId() + 1;;

		Publisher p = new Publisher(id, name);

		this.publishers.add(p);

		return new Object[]{this.updatePAList(), p};
	}

	/**
	 * xóa nhà xuất bản khỏi danh sách
	 * @param id mã nhà xuất bản
	 * @return mã trạng thái xóa nhà xuất bản
	 */
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

	/**
	 * tìm nhà xuất bản theo điều kiện đầu vào
	 * @param id mã NXB
	 * @param name tên NXB
	 * @return danh sách các NXB
	 */
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
