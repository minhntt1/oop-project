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

	private static FileManagement object;
	private final String filePath = "./library.dat";
	private ArrayList<Object> libraryData;

	private FileManagement() {
		this.libraryData = new ArrayList<>();
		this.libraryData.add(new ArrayList<Author>());
		this.libraryData.add(new ArrayList<Publisher>());
		this.libraryData.add(new ArrayList<Person>());
		this.libraryData.add(new ArrayList<Document>());
		this.libraryData.add(new ArrayList<IssuedFor>());
	}

	public static FileManagement getObject() {
		if (object == null) {
			object = new FileManagement();
		}

		return object;
	}

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

	ArrayList<Author> authorsDat() {
		return (ArrayList<Author>) this.libraryData.get(0);
	}

	ArrayList<Publisher> publishersDat() {
		return (ArrayList<Publisher>) this.libraryData.get(1);
	}

	ArrayList<Person> usersDat() {
		return (ArrayList<Person>) this.libraryData.get(2);
	}

	ArrayList<Document> docsDat() {
		return (ArrayList<Document>) this.libraryData.get(3);
	}

	ArrayList<IssuedFor> issuedDat() {
		return (ArrayList<IssuedFor>) this.libraryData.get(4);
	}
}
