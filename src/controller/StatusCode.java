/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 * Lớp Status code chứa các mã lỗi trả về của các module controller
 * @author admin1
 */
public class StatusCode {

	/**
	 * lưu mã lỗi cho module quản lý file
	 */
	public static final String[] fileCode = {
		"Không tìm thấy File",
		"IOException",
		"Classnotfound"
	};
	/**
	 * lưu mã lỗi cho module kiểm tra form dữ liệu
	 */
	public static final String[] formCode = new String[]{
		"Không được để trống",
		"Tên đăng nhập chứa các chữ cái hoặc các số, không có ký tự đặc biệt",
		"Pass phải có độ dài 8",
		"Tên chỉ chứa chữ cái, số và dấu cách",
		"Số điện thoại có độ dài 10 chỉ được chứa chữ số",
		"Số trang phải là kiểu số",
		"Phải là số có 4 chữ số",
		"Số bản phát hành phải là kiểu số",
		"Phải nhập đúng ngày định dạng HH:mm:ss dd/mm/yyyy",
		"Mã phải là kiểu số",
		"Số lượng phải là kiểu số",
		"Phải nhập đúng định dạng dd/mm/yyyy",
		"Phải nhập số >= 0"
	};
	/**
	 * lưu mã lỗi cho module quản lý người dùng
	 */
	public static final String[] umCode = new String[]{
		fileCode[0],
		fileCode[1],
		fileCode[2],
		"Thông tin người dùng không hợp lệ",
		"Tài khoản độc giả không hoạt động",
		"Không tìm thấy mã người dùng",
		"Tên đăng nhập đã tồn tại",
		"Mật khẩu chưa được khởi tạo. Liên hệ thủ thư",
		"Không thể xóa độc giả do vẫn đang mượn tài liệu chưa trả"
	};

	/**
	 * lưu mã lỗi trả về cho module quản lý nhà xuất bản và tác giả
	 */
	public static final String[] pamCode = new String[]{
		fileCode[0],
		fileCode[1],
		fileCode[2],
		"Không tìm thấy mã NXB/Tác giả tương ứng",
		"Không thể xóa NXB/Tác giả do vẫn còn tài liệu"
	};

	/**
	 * lưu mã lỗi trả về cho module quản lý tài liệu
	 */
	public static final String[] dmCode = new String[]{
		fileCode[0],
		fileCode[1],
		fileCode[2],
		"Không tìm thấy mã tài liệu",
		"Không tìm thấy mã mượn tài liệu",
		"Tài liệu còn lại không đủ để mượn",
		"Không thể xóa tài liệu do đang có người mượn chưa trả",
		"Không thể trả tài liệu do nó đã được trả",
		pamCode[3],
		umCode[5]
	};
}
