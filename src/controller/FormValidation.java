/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author admin1
 */
public class FormValidation {

	/**
	 * có chức năng kiểm tra dữ liệu
	 * nhập vào jtextfield rỗng hay không
	 * @param f form dữ liệu vào
	 * @return mã trạng thái kiểm tra
	 */
	public static int validateFieldEmpty(JTextField f) {
		try {
			if (f.getText().isEmpty()) {
				throw new Exception("Không được để trống");
			}

			return -1;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * kiểm tra mã id có hợp lệ hay không
	 * (kiểm tra thỏa mãn có phải kiểu số và lớn hơn 0 hay không)
	 * @param id form văn bản chứa id 
	 * @return 
	 */
	public static Object[] validateId(JTextField id) {
		try {
			int stemp = validateFieldEmpty(id);

			if (stemp == -1) {
				int ID = Integer.parseInt(String.valueOf(id.getText()));

				if (ID < 0) {
					throw new Exception();
				}

				return new Object[]{stemp, ID};
			}

			return new Object[]{stemp};
		} catch (NumberFormatException e) {
			return new Object[]{9};
		} catch (Exception e) {
			return new Object[]{12};
		}
	}

	/**
	 * kiểm tra form tên đăng nhập vào hợp lệ hay không
	 * (phải thỏa mãn chì chứa các chữ cái và chữ số)
	 * @param usr form chứa tên người dùng
	 * @return trả về mảng chứa mã trạng thái và tên đăng nhập sau khi đã kiểm tra
	 */
	public static Object[] validateUserName(JTextField usr) {
		try {
			int stemp = validateFieldEmpty(usr);

			if (stemp == -1) {
				String re = "^[a-zA-Z0-9]+$";

				if (!usr.getText().matches(re)) {
					throw new Exception("Phải chứa các chữ cái hoặc các số");
				}

				return new Object[]{stemp, usr.getText()};
			}

			return new Object[]{stemp};
		} catch (Exception e) {
			return new Object[]{1};
		}
	}

	/**
	 * kiểm tra mật khẩu có hợp lệ hay không
	 * (phải có độ dài là 8)
	 * @param pw form chứa mật khẩu
	 * @return mã trạng thái kiểm tra và mật khẩu
	 */
	public static Object[] validatePassword(JPasswordField pw) {
		try {
			if (String.valueOf(pw.getPassword()).length() < 8) {
				throw new Exception();
			}

			return new Object[]{-1, String.valueOf(pw.getPassword())};
		} catch (Exception e) {
			return new Object[]{2};
		}
	}

	/**
	 * kiểm tra tên có hợp lệ hay không 
	 * (kiểm tra chỉ chứa chữ cái in hoa, in thường và chữ số, dấu cách)
	 * @param n form tên
	 * @return mảng object gồm mã trạng thái và tên sau khi kiểm tra
	 */
	public static Object[] validateName(JTextField n) {
		try {
			int stemp = validateFieldEmpty(n);

			if (stemp == -1) {
				if (!n.getText().matches("^[a-zA-Z0-9\\s]+$")) {
					throw new Exception();
				}

				return new Object[]{stemp, n.getText()};
			}

			return new Object[]{stemp};
		} catch (Exception e) {
			return new Object[]{3};
		}
	}

	/**
	 * kiểm tra số điện thoại có hợp lệ hay không
	 * (phải là chữ số và có độ dài 10)
	 * @param p form số điện thoại
	 * @return mã trạng thái kiểm tra và số điện thoại sau khi kiểm tra
	 */
	public static Object[] validatePhone(JTextField p) {
		try {
			int stemp = validateFieldEmpty(p);

			if (stemp == -1) {
				if (!p.getText().matches("^\\d{10}$")) {
					throw new Exception("Số điện thoại có độ dài 10 chỉ được chứa chữ số");
				}

				return new Object[]{stemp, p.getText()};
			}

			return new Object[]{stemp};
		} catch (Exception e) {
			return new Object[]{4};
		}
	}

	/**
	 * kiểm tra số trang có hợp lệ hay không (phải là kiểu số và >= 0)
	 * @param pn form số trang
	 * @return mã trạng thái và số trang sau khi kiểm tra
	 */
	public static Object[] validatePageNo(JTextField pn) {
		try {
			int stemp = validateFieldEmpty(pn);

			if (stemp == -1) {
				int d = Integer.parseInt(pn.getText());

				if (d < 0) {
					throw new Exception();
				}

				return new Object[]{-1, d};
			}

			return new Object[]{stemp};
		} catch (NumberFormatException e) {
			return new Object[]{5};
		} catch (Exception e) {
			return new Object[]{12};
		}
	}

	/**
	 * kiểm tra số lượng tài liệu có hợp lệ hay không (phải là kiều số và lớn hơn 0)
	 * @param c form copies
	 * @return mã trạng thái và số lượng tài liệu sau khi kiểm tra
	 */
	public static Object[] validateCopies(JTextField c) {
		try {
			int stemp = validateFieldEmpty(c);

			if (stemp == -1) {
				int no = Integer.parseInt(c.getText());

				if (no <= 0) {
					throw new Exception();
				}

				return new Object[]{stemp, no};
			}

			return new Object[]{stemp};
		} catch (NumberFormatException e) {
			return new Object[]{10};
		} catch (Exception e) {
			return new Object[]{12};
		}
	}

	/**
	 * kiểm tra số bản phát hành có hợp lệ không 
	 * (số bản phát hành phải là kiểu số và có 4 chữ số)
	 * @param rn form số bản phát hành
	 * @return mã trạng thái kiểm tra và giá trị số bản phát hành sau kiểm tra
	 */
	public static Object[] validateRelNo(JTextField rn) {
		try {
			int stemp = validateFieldEmpty(rn);

			if (stemp == -1) {
				int no = Integer.parseInt(rn.getText());

				if (no < 1000 || no > 9999) {
					throw new Exception("Số phát hành là số có 4 chữ số");
				}

				return new Object[]{stemp, no};
			}

			return new Object[]{stemp};
		} catch (NumberFormatException e) {
			return new Object[]{7};
		} catch (Exception e) {
			return new Object[]{6};
		}
	}

	/**
	 * kiểm tra ngày có đúng định dạng dd/MM/yyyy hay không
	 * @param d jtextfield chứa ngày
	 * @return mã trạng thái và ngày sau khi được kiểm tra
	 */
	public static Object[] validateDateNoTime(JTextField d) {
		try {
			int stemp = validateFieldEmpty(d);

			if (stemp == -1) {
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(d.getText());
				return new Object[]{stemp, date};
			}

			return new Object[]{stemp};
		} catch (ParseException e) {
			return new Object[]{11};
		}
	}

	/**
	 * kiểm tra ngày có đúng định dạng HH:mm dd/MM/yyyy hay không
	 * @param d jtextfield chứa ngày
	 * @return mã trạng thái và ngày sau khi được kiểm tra
	 */	
	public static Object[] validateDate(JTextField d) {
		try {
			int stemp = validateFieldEmpty(d);

			if (stemp == -1) {
				Date date = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(d.getText());
				return new Object[]{stemp, date};
			}

			return new Object[]{stemp};
		} catch (ParseException e) {
			return new Object[]{8};
		}
	}
}
