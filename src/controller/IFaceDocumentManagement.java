/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.Date;
import model.Document;
import model.IssuedFor;

/**
 * Interface IFaceDocumentManagement chứa các phương thức cần thiết cho DocumentManagment để quản lý tài liệu
 *
 * @author admin1
 */
public interface IFaceDocumentManagement {
	
	/**
	 * có chức năng kiểm tra và xóa độc giả 
	 * nếu như có mã độc giả trong danh sách mượn và 
	 * độc giả có sách chưa trả thì trả về true
	 * ngược lại tìm các lượt mượn liên quan đến độc giả này và xóa nó
	 * sau đó trả về false
	 * @param uid
	 * @return trả về true nếu như tồn tại độc giả và không có sách nào chưa trả, ngược lại trả về false
	 */
	public boolean checkAndRemoveIssuedUser(int uid);

	/**
	 * có chức năng thêm sách vào danh sách tài liệu
	 * đầu tiên kiểm tra xem có nhà xuất bản, tác giả với mã tương ứng không,
	 * nếu không có thì trả về mã trạng thái không có,
	 * nếu có thì thêm sách và liên kết với NXB, tác giả đã cho
	 * @param relNo số bản phát hành
	 * @param copies số bản sao
	 * @param pid mã nhà xuất bản
	 * @param aid danh sách mã tác giả
	 * @param bname tên sách
	 * @param bpages số trang sách
	 * @return trả về một danh sách object: đối tượng thứ nhất là mã trạng thái,
	 * đối tượng thứ 2 là object Book nếu như nó được thêm thành công
	 */
	public Object[] addBook(int relNo, int copies, int pid, int[] aid, String bname, int bpages);
	
	/**
	 * có chức năng thêm báo vào danh sách tài liệu
	 * đầu tiên kiểm tra xem có nhà xuất bản với mã tương ứng không,
	 * nếu không có thì trả về mã trạng thái không có,
	 * nếu có thì thêm báo và liên kết với NXB đã cho
	 * @param relNo số bản phát hành
	 * @param copies số bản sao
	 * @param pid mã nhà xuất bản
	 * @param relDate ngày phát hành
	 * @return trả về mảng object: đối tượng thứ 1 là mã trạng thái,
	 * đối tượng thứ 2 là object Paper nếu thêm báo thành công
	 */
	public Object[] addPaper(int relNo, int copies, int pid, Date relDate);
	
	/**
	 * có chức năng xóa tài liệu khỏi danh sách,
	 * đầu tiên kiểm tra xem có tài liệu với mã cần xóa không, nếu không có trả về mã không tìm thấy
	 * nếu có thì kiểm tra xem tài liệu có trong danh sách lượt mượn mà chưa trả không, nếu có thì trả về mã không thể xóa tài liệu do vẫn có lượt mượn,
	 * nếu không thì xóa tài liệu khỏi danh sách tài liệu của NXB và tác giả (nếu như là sách)
	 * và đồng thời xóa tất cả lượt mượn liên quan đến tài liệu này
	 * @param id mã tài liệu cần thêm
	 * @return trả về mã trạng thái xóa tài liệu
	 */
	public int removeDoc(int id);

	/**
	 * có chức năng sửa thông tin sách
	 * đầu tiên kiểm tra mã sách có tồn tại không, nếu không thì trả về mã lỗi
	 * nếu có thì lấy danh sách nhà xuất bản, tác giả tương ứng, nếu không có thì trả về mã lỗi
	 * nếu có thì xóa sách khỏi danh sách sách của NXB, TG của nó hiện tại
	 * sau đó sửa thông tin theo tham số đầu vào
	 * sau đó thêm vào danh sách nhà xuất bản, mà sau khi sửa
	 * cuối cùng trả về mã trạng thái và đối tượng sách vừa sửa
	 * @param id mã sách
	 * @param relNo số bản phát hành
	 * @param pid mã nhà xuất bản
	 * @param copies số bản sao
	 * @param name tên tài liệu
	 * @param aid mảng các mã tác giả
	 * @param pages số trang 
	 * @return trả về mảng Object, đối tượng thứ nhất là mã trạng thái,
	 * đối tượng thứ hai là object Book sau khi vừa sửa
	 */
	public Object[] editBook(int id, int relNo, int pid, int copies, String name, int[] aid, int pages);

	/**
	 * có chức năng sửa thông tin báo,
	 * đầu tiên kiểm tra xem có mã bài báo trong danh sách không, nếu không có thì trả về mã lỗi,
	 * nếu có thì kiểm tra xem có mã nxb, tg tương ứng không 
	 * nếu có thì xóa báo khỏi nxb hiện tại của nó, sửa thông tin cần sửa của bài báo,
	 * sau đó thêm bài báo vào danh sách báo của NXB cần thêm
	 * cuối cùng trả về mã trạng thái và đối tượng báo vừa sửa
	 * @param id mã báo
	 * @param relNo số bản phát hành
	 * @param pid mã nhà xuất bản
	 * @param copies số bản sao
	 * @param relDate ngày xuất bản
	 * @return trả về mảng object, đối tượng thứ nhất là mã trạng thái,
	 * đối tượng thứ hai là object paper sau khi vừa sửa
	 */
	public Object[] editPaper(int id, int relNo, int pid, int copies, Date relDate);

	/**
	 * có chức năng thực hiện mượn tài liệu và lưu lượt mượn vào danh sách mượn,
	 * đầu tiên tìm xem có tài liệu với mã tương ứng không,
	 * nếu có thì kiểm tra xem số lượng của tài liệu đó còn hay không, 
	 * nếu còn thì kiểm tra xem độc giả mượn có mã hay không,
	 * nếu có thì thực hiện giảm số lượng tài liệu đi 1, sau đó thêm vào lượt mượn
	 * sau đó trả về mã trạng thái và đối tượng lượt mượn nếu thành công
	 * @param rid mã người mượn
	 * @param did mã tài liệu
	 * @param date ngày mượn 
	 * @param idxPeriod mã của thời gian mượn trong mảng thời gian mượn
	 * @return trả về mảng object có hai đối tượng: đối tượng thứ 1 là mã trạng thái,
	 * đối tượng thứ 2 là object lượt mượn nếu mượn thành công
	 */
	public Object[] issueDoc(int rid, int did, Date date, int idxPeriod);

	/**
	 * có chức năng trả tài liệu 
	 * đầu tiên tìm xem có mã mượn tương ứng không,
	 * nếu có thì kiểm tra tiếp xem mã mượn này đã trả hay chưa, 
	 * nếu chưa thì thực hiện trả bằng cách đặt trạng thái đã trả là true, sau đó tăng số lượng tài liệu lên,
	 * cuối cùng trả về trạng thái và đối tượng lượt mượn
	 * @param iid mã lượt mượn
	 * @return trả về mảng object: đối tượng thứ 1 là mã trạng thái,
	 * đối tượng thứ 2 là mã lượt mượn sau khi đã đánh dấu là đã trả
	 */
	public Object[] returnDoc(int iid);

	/**
	 * có chức năng tìm tài liệu
	 * @param id mã tài liệu
	 * @param relNo số bản phát hành
	 * @param pid mã nhà xuất bản
	 * @return trả về danh sách các tài liệu thỏa mãn điều kiện tìm
	 */
	public ArrayList<Document> findDoc(int id, int relNo, int pid);

	/**
	 * có chức năng tìm sách 
	 * @param id mã sách
	 * @param relNo số bản phát hành
	 * @param pid mã nhà xuất bản
	 * @param copies số bản sao 
	 * @param name tên sách
	 * @param aid mảng chứa mã tác giả
	 * @param pages số trang sách
	 * @return trả về danh sách chứa các sách thỏa mãn điều kiện
	 */
	public ArrayList<Document> findBook(int id, int relNo, int pid, int copies, String name, int[] aid, int pages);

	/**
	 * có chức năng tìm báo
	 * @param id mã bài báo
	 * @param relNo số bản phát hành
	 * @param pid mã nhà xuất bản 
	 * @param copies số bản sao 
	 * @param relDate ngày phát hành
	 * @return trả về danh sách các bài báo thỏa mãn điều kiện
	 */
	public ArrayList<Document> findPaper(int id, int relNo, int pid, int copies, Date relDate);

	/**
	 * có chức năng tìm lượt mượn đã mượn
	 * @param rid
	 * @param name
	 * @param user
	 * @param date
	 * @param status
	 * @param type
	 * @param returned
	 * @return trả về danh sách các lượt mượn thỏa mãn điều kiện
	 */
	public ArrayList<IssuedFor> findDocIssued(int rid, String name, String user, Date date, int status, int type, int returned);

	/**
	 * có chức năng thống kê tài liệu mượn theo mã người dùng
	 * @param uid mã độc gỉa
	 * @return trả về mảng object gồm 5 đối tượng: 
	 * tổng tài liệu mượn,
	 * số tài liệu quá hạn trả,
	 * số tài liệu chưa quá hạn,
	 * tổng số sách,
	 * tổng số báo,
	 * tổng số đã trả,
	 * tổng số chưa trả
	 */
	public Object[] getIssuedStatsByUser(int uid);

	/**
	 * có chức năng thống kê tài liệu mượn tổng quan
	 * @return trả về mảng Object gồm 3 đối tượng:
	 * map thống kê tài liệu và số lượng đã trả, chưa trả
	 * map thống kê độc giả và các tài liệu khác nhau đã mượn
	 * map thống kê độc giả và tổng thời gian mượn tài liệu
	 */
	public Object[] getIssuedStatsOverall();

	/**
	 * có chức năng thống kê tài liệu tổng quan
	 * @return trả về mảng object gồm 4 đối tượng:
	 * tổng số tài liệu,
	 * tổng số sách,
	 * tổng số báo,
	 * map thống kê theo tài liệu, số lượng còn lại và số lượng mượn chưa trả
	 */
	public Object[] getStatsOverall();
}
