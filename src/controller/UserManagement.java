/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import model.Librarian;
import model.Person;
import model.Reader;

/**
 *
 * @author admin1
 */
public class UserManagement {

	private static UserManagement object;
	private final ArrayList<Person> users;	//mang chua doi tuong Person

	public UserManagement() {
		this.users = FileManagement.getObject().usersDat();
	}

	public static UserManagement getObject() {
		if (object == null) {
			object = new UserManagement();
		}

		return object;
	}

	public int updateUserList() {
		return FileManagement.getObject().writeData();
	}

	public Object[] checkUser(String userName, String pass) {	//kiem tra xem co person nao voi user va pass tuong ung ko, neu co thi tra ve
		Person p = this.users
				.stream()
				.filter(x -> x.getUserName().equals(userName) && x.getPassword().equals(pass))
				.findAny()
				.orElse(null);

		if (p != null && p instanceof Reader && ((Reader) p).getPassword().isEmpty()) {
			return new Object[]{7};
		} else if (p != null && (p instanceof Reader && ((Reader) p).isActiveStatus() || p instanceof Librarian)) {
			return new Object[]{-1, p};
		} else if (p != null && p instanceof Reader && !((Reader) p).isActiveStatus()) {
			return new Object[]{4};
		} else {
			return new Object[]{3};
		}
	}

	public boolean checkUserExisted(String user, int id) {
		return this.users.stream().anyMatch(x -> x.getUserName().equals(user) && x.getId()!=id);
	}

	public Object[] registerUser(int type, String userName, String pass, String name, String phoneNo, boolean readerStatus, Date readerDate) {
		int id = this.users.isEmpty() ? 1000 : this.users.get(this.users.size() - 1).getId() + 1; //id tu tang
		Person p;

		if (!this.checkUserExisted(userName, id)) {
			if (type == 0) //neu la tai khoan nguoi dung
			{
				p = new Reader(readerDate, readerStatus, id, name, userName, pass);
			} else //tai khoan thu thu
			{
				p = new Librarian(phoneNo, id, name, userName, pass);
			}

			this.users.add(p);

			return new Object[]{this.updateUserList(), p};
		} else {
			return new Object[]{6};
		}
	}

	public Object[] editReader(int rid, String name, String user, Date date, boolean status) {
		Reader p = (Reader) this.users
				.stream()
				.filter(x -> x instanceof Reader && x.getId() == rid)
				.findFirst()
				.orElse(null);

		if (p == null) {
			return new Object[]{5};
		}

		if (this.checkUserExisted(user, rid)) {
			return new Object[]{6};
		}

		p.setName(name);
		p.setUserName(user);
		p.setRegisterDate(date);
		p.setActiveStatus(status);

		return new Object[]{this.updateUserList(), p};
	}

	public Object[] editLibrarian(int lid, String name, String user, String phone) {
		Librarian p = (Librarian) this.users
				.stream()
				.filter(x -> x instanceof Librarian && x.getId() == lid)
				.findFirst()
				.orElse(null);

		if (p == null) {
			return new Object[]{5};
		}

		if (this.checkUserExisted(user, lid)) {
			return new Object[]{6};
		}

		p.setName(name);
		p.setUserName(user);
		p.setPhoneNo(phone);

		return new Object[]{this.updateUserList(), p};
	}

	public int removeUser(int id) {
		Person p = this.users
				.stream()
				.filter(x -> x.getId() == id)
				.findFirst()
				.orElse(null);

		if (p == null) {
			return 5;
		}

		if (p instanceof Reader && DocumentManagement.getObject().checkAndRemoveIssuedUser(id)) {
			return 8;
		}

		this.users.remove(p);

		return this.updateUserList();
	}

	public ArrayList<Person> findReader(int id, String name, String user, Date date, int status) {
		return this.users
				.stream()
				.filter((Person x)
						-> x instanceof Reader
				&& (id == -1 ? true : x.getId() == id)
				&& (name.isEmpty() ? true : x.getName().toLowerCase().contains(name.toLowerCase()))
				&& (user.isEmpty() ? true : x.getUserName().toLowerCase().contains(user.toLowerCase()))
				&& (date == null ? true : ((Reader) x).getRegisterDate().equals(date))
				&& (status == 2 ? true : (status == 1 ? ((Reader) x).isActiveStatus() : !((Reader) x).isActiveStatus()))
				).collect(Collectors.toCollection(ArrayList::new));
	}

	public ArrayList<Person> findLibrarian(int id, String name, String user, String phone) {
		return this.users
				.stream()
				.filter(x
						-> x instanceof Librarian
				&& (id == -1 ? true : x.getId() == id)
				&& (name.isEmpty() ? true : x.getName().toLowerCase().contains(name.toLowerCase()))
				&& (user.isEmpty() ? true : x.getUserName().toLowerCase().contains(user.toLowerCase()))
				&& (phone.isEmpty() ? true : ((Librarian) x).getPhoneNo().contains(phone))
				).collect(Collectors.toCollection(ArrayList::new));
	}

	public int editUserPass(int rid, String pass) {
		Person p = this.users
				.stream()
				.filter(x -> x.getId() == rid)
				.findFirst()
				.orElse(null);

		if (p == null) {
			return 5;
		}

		p.setPassword(pass);

		return this.updateUserList();
	}

	public Long[] getStatsReader() {
		long totalReaders = this.users.stream()
				.filter(x -> x instanceof Reader)
				.count();

		long totalActive = this.users.stream()
				.filter(x -> x instanceof Reader && ((Reader) x).isActiveStatus())
				.count();

		long totalInactive = totalReaders - totalActive;

		return new Long[]{totalReaders, totalActive, totalInactive};
	}
}
