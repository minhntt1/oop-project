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

	public static Object[] validateCopies(JTextField c) {
		try {
			int stemp = validateFieldEmpty(c);

			if (stemp == -1) {
				int no = Integer.parseInt(c.getText());

				if (no < 0) {
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
