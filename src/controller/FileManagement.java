/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import model.Author;
import model.Document;
import model.IssuedFor;
import model.Person;
import model.Publisher;

/**
 *
 * @author admin1
 */
public class FileManagement {

	/**
	 * đối tượng filemanagement
	 */
	private static FileManagement object;
	/**
	 * đường dẫn thư mục chứa file dữ liệu
	 */
	private final String filePath = "./library.dat";
	/**
	 * danh sách chứa dữ liệu thư viện, nó bao gồm các danh sách chứa 
	 * tác giả, nhà xuất bản, người dùng,
	 * tài liệu, lượt mượn
	 */
	private ArrayList<Object> libraryData;

	/**
	 * hàm khởi tạo cho thư viện, có chức năng 
	 * khởi tạo dữ liệu thư viện mới từ đầu
	 */
	private FileManagement() {
		this.libraryData = new ArrayList<>();
		this.libraryData.add(new ArrayList<Author>());
		this.libraryData.add(new ArrayList<Publisher>());
		this.libraryData.add(new ArrayList<Person>());
		this.libraryData.add(new ArrayList<Document>());
		this.libraryData.add(new ArrayList<IssuedFor>());
	}

	/**
	 * @return trả về đối tượng của lớp hiện tại
	 */
	public static FileManagement getObject() {
		if (object == null) {
			object = new FileManagement();
		}

		return object;
	}

	/**
	 * khởi tạo dữ liệu thư viện,
	 * đọc dữ liệu thư viện từ file dữ liệu và lưu 
	 * vào libraryData để dùng cho xử lý 
	 * @return  
	 */
	public int initData() {
		try {
			FileInputStream fis = new FileInputStream(this.filePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.libraryData = (ArrayList<Object>) ois.readObject();
			ois.close();
			fis.close();
			return -1;
		} catch (FileNotFoundException e) {
			return this.writeData();
		} catch (ClassNotFoundException e) {
			return 2;
		} catch (IOException e) {
			return 1;
		}
	}

	/**
	 * có chức năng lưu dữ liệu trong arraylist
	 * vào file dữ liệu 
	 */
	int writeData() {
		try {
			FileOutputStream fos = new FileOutputStream(this.filePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.libraryData);
			oos.close();
			fos.close();
			return -1;
		} catch (FileNotFoundException ex) {
			return 0;
		} catch (IOException e) {
			return 1;
		}
	}

	/**
	 * có chức năng trả về dữ liệu chứa tác giả
	 * trong dữ liệu thư viện
	*/
	ArrayList<Author> authorsDat() {
		return (ArrayList<Author>) this.libraryData.get(0);
	}

	/**
	 * có chức năng trả về dữ liệu chứa nhà xuất bản
	 * trong dữ liệu thư viện
	*/	
	ArrayList<Publisher> publishersDat() {
		return (ArrayList<Publisher>) this.libraryData.get(1);
	}

	/**
	 * có chức năng trả về dữ liệu chứa người dùng
	 * trong dữ liệu thư viện
	*/
	ArrayList<Person> usersDat() {
		return (ArrayList<Person>) this.libraryData.get(2);
	}

	/**
	 * có chức năng trả về dữ liệu chứa tài liệu
	 * trong dữ liệu thư viện
	*/
	ArrayList<Document> docsDat() {
		return (ArrayList<Document>) this.libraryData.get(3);
	}

	/**
	 * có chức năng trả về dữ liệu chứa các lượt mượn
	 * trong dữ liệu thư viện
	*/
	ArrayList<IssuedFor> issuedDat() {
		return (ArrayList<IssuedFor>) this.libraryData.get(4);
	}
}
