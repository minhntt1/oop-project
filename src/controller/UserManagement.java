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
 * Lớp usermanagement cung cấp các chức năng quản lý liên quan đến người dùng
 *
 * @author admin1
 */
public class UserManagement {

	/**
	 * đối tượng của lớp UserManagement
	 */
	private static UserManagement object;
	/**
	 * danh sách chứa mảng người dùng
	 */
	private final ArrayList<Person> users;	//mang chua doi tuong Person

	/**
	 * khởi tạo danh sách mảng người dùng
	 */
	public UserManagement() {
		this.users = FileManagement.getObject().usersDat();
	}
	
	/**
	 * @return trả về đối tượng của lớp hiện tại
	 */
	public static UserManagement getObject() {
		if (object == null) {
			object = new UserManagement();
		}

		return object;
	}

	/**
	 * có chức năng cập nhật danh sách và lưu vào file
	 * @return trả về trạng thái ghi ra file dữ liệu
	 */
	public int updateUserList() {
		return FileManagement.getObject().writeData();
	}

	/**
	 * có chức năng kiểm tra đăng nhập người dùng hợp lệ
	 * đầu tiên kiểm tra xem có tài khoản và mật khẩu tương ứng không,
	 * nếu có thì kiểm tra xem mật khẩu đã khởi tạo hay chưa,
	 * nếu đã khởi tạo mật khẩu kiểm tra xem tài khoản có hoạt động hay không,
	 * nếu tài khoản hoạt động thì trả về trạng thái thành công và đối tượng người dùng
	 * 
	 * @param userName tên đăng nhập
	 * @param pass mật khẩu
	 * @return trả về object chứa mã trạng thái và đối tượng người dùng nếu tồn tại
	 */
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

	/**
	 * kiểm tra có tồn tại tên người dùng không
	 * @param user tên người dùng
	 * @param id mã người dùng
	 * @return trả về có hoặc không
	 */
	public boolean checkUserExisted(String user, int id) {
		return this.users.stream().anyMatch(x -> x.getUserName().equals(user) && x.getId()!=id);
	}

	/**
	 * có chức năng đăng ký tài khoản người dùng,
	 * trước tiên kiểm tra xem có tên đăng nhập tương ứng tồn tại chưa,
	 * nếu chưa thì kiểm tra loại tài khoản tương ứng để đăng ký,
	 * đăng ký thành công thì trả về mã trạng thái và đối tượng người dùng đăng ký thành công
	 * @param type kiểu tài khoản, 0 nếu là tài khoản độc giả, 1 nếu tài khoản thủ thư
	 * @param readerStatus trạng thái tài khoản độc giả có bị khóa không
	 * @param pass mật khẩu người dùng
	 * @param userName tên đăng nhập người dùng
	 * @param name tên người dùng
	 * @param phoneNo số điện thoại thủ thư
	 * @param readerDate ngày đăng ký tài khoản người dùng
	 * @return trả về mảng đối tượng bao gồm mã trạng thái đăng ký và đối tượng người dùng sau khi đăng ký
	 */
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

	/**
	 * có chức năng sửa tài khoản độc giả,
	 * đầu tiên tìm tài khoản độc giả với mã tương ứng,
	 * nếu tồn tại thì kiểm tra tên người dùng đã tồn tại chưa, 
	 * nếu chưa thì sửa các tham số theo đầu vào
	 * cuối cùng trả về mã và đối tượng độc giả sau khi đã sửa
	 * @param rid mã độc giả 
	 * @param name tên độc giả
	 * @param date ngày đăng ký 
	 * @param user tên người dùng 
	 * @param status trạng thái người dùng
	 * @return trả về mã trạng thái và đối tượng người dùng đã sửa thành công
	 */
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

	/**
	 * có chức năng sửa tài thủ thư,
	 * đầu tiên tìm tài khoản thủ thư với mã tương ứng,
	 * nếu tồn tại thì kiểm tra tên người dùng đã tồn tại chưa, 
	 * nếu chưa thì sửa các tham số theo đầu vào
	 * cuối cùng trả về mã và đối tượng thủ thư sau khi đã sửa
	 * @param lid mã thủ thư
	 * @param name tên thủ thư 
	 * @param user tên đăng nhập thủ thư
	 * @param phone số điện thoại thủ thư
	 * @return trả về mã trạng thái và đối tượng thủ thư
	 */
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

	/**
	 * có chức năng xóa tài khoản người dùng,
	 * kiểm tra xem có id tồn tại không, nếu tồn tại thì 
	 * kiểm tra tiếp xem người độc giả có trong danh sách mượn và có tài liệu nào chưa trả hay không,
	 * nếu không thì tiến hành xóa người dùng khỏi danh sách và xóa các lượt mượn liên quan đến người dùng
	 * trả về mã trạng thái thao tác sửa
	 * 
	 * @param id
	 * @return 
	 */
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

	/**
	 * có chức năng tìm tài khỏan thủ thư theo tham số đầu vào
	 * @param id mã thủ thư
	 * @param name tên thủ thư
	 * @param user tên đăng nhập thủ thư
	 * @param phone số điện thoại thủ thư
	 * @return danh sách thủ thư thỏa mãn tham số đã cho
	 */
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

	/**
	 * có chức năng sửa mật khẩu tài khoản người dùng
	 * nếu có tài khoản người dùng với ID tương ứng 
	 * thì cập nhật mật khẩu 
	 * @param rid
	 * @param pass
	 * @return trả về mã trạng thái cập nhật
	 */
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

	/**
	 * có chức năng thống kê tài khoản độc giả
	 * @return trả về mảng đối tượng bao gồm tổng tài khoản độc giả, tổng tài khoản hoạt động, tổng tài khoản không hoạt động
	 */
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
