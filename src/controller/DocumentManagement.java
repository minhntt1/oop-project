/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import model.Author;
import model.Book;
import model.Document;
import model.IssuedFor;
import model.Paper;
import model.Person;
import model.Publisher;
import model.Reader;

/**
 *
 * @author admin1
 */
public class DocumentManagement implements IFaceDocumentManagement {

	private static DocumentManagement object;
	private final int[] listPeriods = {
		1, 2, 7, 14, 30, 60, 360
	};
	private final ArrayList<Document> docs;
	private final ArrayList<IssuedFor> ib;

	private DocumentManagement() {
		this.docs = FileManagement.getObject().docsDat();
		this.ib = FileManagement.getObject().issuedDat();
	}

	public static DocumentManagement getObject() {
		if (object == null) {
			object = new DocumentManagement();
		}

		return object;
	}

	public int updateList() {
		return FileManagement.getObject().writeData();
	}

	@Override
	public ArrayList<Document> findDoc(int id, int relNo, int pid) {
		return this.docs.stream()
				.filter(x
						-> (id == -1 ? true : x.getId() == id)
				&& (relNo == -1 ? true : x.getReleaseNo() == relNo)
				&& (pid == -1 ? true : x.getP().getId() == pid)
				)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public Object[] addBook(int relNo, int copies, int pid, int[] aid, String bname, int bpages) {
		int id = this.docs.isEmpty() ? 1000 : this.docs.get(this.docs.size() - 1).getId() + 1;

		ArrayList<Publisher> targetp = PublisherAuthorManagement.getObject().findPublisher(pid, "");
		if (targetp.isEmpty()) {
			return new Object[]{8};
		}

		Publisher p = targetp.get(0);

		ArrayList<Author> a = new ArrayList<>();
		for (int i = 0; i < aid.length; ++i) {
			ArrayList<Author> targeta = PublisherAuthorManagement.getObject().findAuthor(aid[i], "");

			if (!targeta.isEmpty()) {
				Author found = targeta.get(0);
				a.add(found);
			}
		}

		Book b = new Book(bname, a, bpages, id, relNo, p, copies);
		this.docs.add(b);

		//them sach vao arraylist cua publisher p
		p.addDoc(b);

		//them sach vao arraylist cua cac authors
		for (Author a1 : a) {
			a1.addBook(b);
		}

		return new Object[]{this.updateList(), b};
	}

	@Override
	public Object[] addPaper(int relNo, int copies, int pid, Date relDate) {
		int id = this.docs.isEmpty() ? 1000 : this.docs.get(this.docs.size() - 1).getId() + 1;

		ArrayList<Publisher> targetp = PublisherAuthorManagement.getObject().findPublisher(pid, "");
		if (targetp.isEmpty()) {
			return new Object[]{8};
		}

		Publisher p = targetp.get(0);
		Paper pp = new Paper(relDate, id, relNo, p, copies);

		this.docs.add(pp);

		p.addDoc(pp);

		return new Object[]{this.updateList(), pp};
	}

	@Override
	public int removeDoc(int id) {
		Document d = this.docs.stream()
				.filter(x -> x.getId() == id)
				.findFirst()
				.orElse(null);

		if (d == null) {
			return 3;
		}

		if (this.ib.stream().anyMatch(x -> x.getDocument().getId() == d.getId() && !x.isReturned())) {
			return 6;
		}

		//xoa di lien ket tu nxb den cuon sach
		d.getP().removeDoc(d);

		//neu nhu tai lieu la book thi phai xoa them ca cac tac gia dang ket noi voi no
		if (d instanceof Book) {
			ArrayList<Author> al = ((Book) d).getAuthors();
			for (Author a : al) {
				a.removeBook((Book) d);
			}
		}

		//tim cac luot muon lien quan den tai lieu do va xoa no
		ArrayList<IssuedFor> toDelete = this.ib.stream().filter(x -> x.getDocument().getId() == d.getId()).collect(Collectors.toCollection(ArrayList::new));

		//xoa ca cac luot muon lien quan den tai lieu
		toDelete.forEach(v -> {
			this.ib.remove(v);
		});

		this.docs.remove(d);
		return this.updateList();
	}

	@Override
	public Object[] editBook(int id, int relNo, int pid, int copies, String name, int[] aid, int pages) {
		Book b = (Book) this.docs.stream()
				.filter(x -> x instanceof Book && x.getId() == id)
				.findFirst()
				.orElse(null);

		if (b == null) {
			return new Object[]{3};
		}

		ArrayList<Publisher> targetp = PublisherAuthorManagement.getObject().findPublisher(pid, "");
		if (targetp.isEmpty()) {
			return new Object[]{8};
		}
		Publisher p = targetp.get(0);

		ArrayList<Author> a = new ArrayList<>();
		for (int i = 0; i < aid.length; ++i) {
			ArrayList<Author> targeta = PublisherAuthorManagement.getObject().findAuthor(aid[i], "");

			if (!targeta.isEmpty()) {
				Author found = targeta.get(0);
				a.add(found);
			}
		}

		//xoa quyen sach hien tai voi nxb dang noi den no
		b.getP().removeDoc(b);
		//xoa toan bo quyen sach hien tai khoi cac tac gia dang noi voi no
		ArrayList<Author> al = b.getAuthors();
		for (Author a1 : al) {
			a1.removeBook(b);
		}

		b.setName(name);
		b.setAuthors(a);
		b.setP(p);
		b.setCopies(copies);
		b.setPages(pages);
		b.setReleaseNo(relNo);

		//them sach vao arraylist cua publisher p
		p.addDoc(b);
		//them sach vao arraylist cua cac authors
		for (Author a1 : a) {
			a1.addBook(b);
		}

		return new Object[]{this.updateList(), b};
	}

	@Override
	public Object[] editPaper(int id, int relNo, int pid, int copies, Date relDate) {
		Paper pp = (Paper) this.docs.stream()
				.filter(x -> x instanceof Paper && x.getId() == id)
				.findFirst()
				.orElse(null);

		if (pp == null) {
			return new Object[]{3};
		}

		ArrayList<Publisher> targetp = PublisherAuthorManagement.getObject().findPublisher(pid, "");
		if (targetp.isEmpty()) {
			return new Object[]{8};
		}
		Publisher p = targetp.get(0);

		//xoa khoi ket noi nxb hien tai vs bao
		pp.getP().removeDoc(pp);

		pp.setP(p);
		pp.setCopies(copies);
		pp.setReleaseDate(relDate);
		pp.setReleaseNo(relNo);

		//them bai bao vao vao arraylist cua publisher p
		p.addDoc(pp);

		return new Object[]{this.updateList(), pp};
	}

	@Override
	public Object[] issueDoc(int rid, int did, Date date, int idxPeriod) {
		int iid = this.ib.isEmpty() ? 100 : this.ib.get(this.ib.size() - 1).getId() + 1;

		Document d = this.docs.stream()
				.filter(x -> x.getId() == did)
				.findFirst()
				.orElse(null);

		//neu ko co doc vs ma id tuong ung
		if (d == null) {
			return new Object[]{4};
		}

		//kiem tra xem so luong tai lieu con lai con hay ko
		if (d.getCopies() <= 0) {
			return new Object[]{5};
		}

		ArrayList<Person> targetR = UserManagement.getObject().findReader(rid, "", "", null, 2);
		if (targetR.isEmpty()) {
			return new Object[]{9};
		}

		Reader r = (Reader) targetR.get(0);

		//neu con thi giam di 1
		d.setCopies(d.getCopies() - 1);

		IssuedFor isf = new IssuedFor(iid, r, d, date, listPeriods[idxPeriod], false);

		this.ib.add(isf);

		return new Object[]{this.updateList(), isf};
	}

	@Override
	public boolean checkAndRemoveIssuedUser(int uid) {
		//kiem tra neu co nguoi dung chua tra tuong ung, neu co thi return true
		if (this.ib.stream().anyMatch(x -> x.getReader().getId() == uid && !x.isReturned())) {
			return true;
		}

		//neu ko tim luot muon lien quan den nguoi nay va xoa no
		//tim cac luot muon lien quan den uid va xoa
		ArrayList<IssuedFor> toDelete = this.ib.stream().filter(x -> x.getReader().getId() == uid).collect(Collectors.toCollection(ArrayList::new));

		//xoa ca cac luot muon lien quan den tai lieu
		toDelete.forEach(v -> {
			this.ib.remove(v);
		});

		return false;
	}

	@Override
	public Object[] returnDoc(int iid) {
		IssuedFor o = this.ib.stream()
				.filter(x -> x.getId() == iid)
				.findFirst()
				.orElse(null);

		//kiem tra ma muon co ton tai hay ko
		if (o == null) {
			return new Object[]{4};
		}

		if (o.isReturned()) {
			return new Object[]{7};
		}

		//tang so luong tai lieu len do da tra lai
		Document d = this.docs.stream().filter(x -> x.getId() == o.getDocument().getId()).findAny().orElse(null);
		d.setCopies(d.getCopies() + 1);

		//thay vi xoa khoi danh sach da muon thi set ma muon la da tra tai lieu
		o.setReturned(true);

		return new Object[]{this.updateList(), o};
	}

	@Override
	public ArrayList<Document> findBook(int id, int relNo, int pid, int copies, String name, int[] aid, int pages) {
		return this.docs.stream()
				.filter(x -> (x instanceof Book)
				&& (id == -1 ? true : x.getId() == id)
				&& (relNo == -1 ? true : x.getReleaseNo() == relNo)
				&& (pid == -1 ? true : x.getP().getId() == pid)
				&& (copies == -1 ? true : ((Book) x).getCopies() == copies)
				&& (name.isEmpty() ? true : ((Book) x).getName().toLowerCase().contains(name.toLowerCase()))
				&& (aid == null ? true : Arrays.stream(aid)
								.anyMatch(x1 -> ((Book) x).getAuthors().stream()
								.anyMatch(x2 -> x2.getId() == x1)))
				&& (pages == -1 ? true : ((Book) x).getPages() == pages)
				)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public ArrayList<Document> findPaper(int id, int relNo, int pid, int copies, Date relDate) {
		return this.docs.stream()
				.filter(x -> (x instanceof Paper)
				&& (id == -1 ? true : x.getId() == id)
				&& (relNo == -1 ? true : x.getReleaseNo() == relNo)
				&& (pid == -1 ? true : x.getP().getId() == pid)
				&& (copies == -1 ? true : x.getCopies() == copies)
				&& (relDate == null ? true : ((Paper) x).getReleaseDate().equals(relDate))
				)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public Object[] getIssuedStatsByUser(int uid) {
		long totalIssued = this.ib.stream().filter(x
				-> (uid == -1 ? true : x.getReader().getId() == uid)
		).count();

		long exceeded = this.ib.stream().filter(x
				-> (uid == -1 ? true : x.getReader().getId() == uid)
				&& ((String) x.toObject()[6]).equals("Có")
		).count();

		long remain = totalIssued - exceeded;

		long totalBook = this.ib.stream().filter(x
				-> (uid == -1 ? true : x.getReader().getId() == uid)
				&& (x.getDocument() instanceof Book)
		).count();

		long totalPaper = totalIssued - totalBook;

		long totalReturned = this.ib.stream().filter(x
				-> (uid == -1 ? true : x.getReader().getId() == uid)
				&& (x.isReturned())
		).count();

		long totalNotReturned = this.ib.stream().filter(x
				-> (uid == -1 ? true : x.getReader().getId() == uid)
				&& (!x.isReturned())
		).count();

		return new Object[]{totalIssued, exceeded, remain, totalBook, totalPaper, totalReturned, totalNotReturned};
	}

	@Override
	public Object[] getIssuedStatsOverall() {
		//nhom so theo tai lieu va so cai da tra, chua tra
		Map<Document, Map<Boolean, Long>> m1 = this.ib.stream()
				.collect(Collectors.groupingBy(x -> x.getDocument(),
						Collectors.groupingBy(y -> y.isReturned(), Collectors.counting())));

		//nhom theo doc gia va cac tai lieu khac nhau da muon
		Map<Reader, Map<Document, Long>> m3 = this.ib
				.stream()
				.collect(Collectors.groupingBy(x -> x.getReader(),
						Collectors.groupingBy(y -> y.getDocument(), Collectors.counting())
				));

		//nhom theo doc gia va tong thoi gian da muon tai lieu
		Map<Reader, Map<Boolean, Integer>> m4 = this.ib.stream()
				.collect(Collectors.groupingBy(x -> x.getReader(),
						Collectors.groupingBy(y -> y.isReturned(), Collectors.summingInt(z -> z.getPeriod()))));

		return new Object[]{m1, m3, m4};
	}

	@Override
	public Object[] getStatsOverall() {
		long totalDocs = this.docs.size();

		long totalBooks = this.docs.stream().filter(x -> x instanceof Book).count();

		long totalPaper = this.docs.stream().filter(x -> x instanceof Paper).count();

		ArrayList<String[]> statsDocs = new ArrayList<>();

		this.docs.forEach(v -> {
			if (v != null) {
				Map<Document, Long> m = this.ib.stream()
						.filter(x -> x.getDocument().equals(v) && !x.isReturned())
						.collect(Collectors.groupingBy(y -> y.getDocument(), Collectors.counting()));

				Long value = m.get(v);

				statsDocs.add(new String[]{
					v instanceof Book ? ((Book) v).toString() : ((Paper) v).toString(),
					String.valueOf(v.getCopies()),
					String.valueOf(value == null ? 0 : value)
				});
			}
		});

		return new Object[]{totalDocs, totalBooks, totalPaper, statsDocs};
	}

	@Override
	public ArrayList<IssuedFor> findDocIssued(int rid, String name, String user, Date date, int status, int type, int returned) {
		return this.ib.stream()
				.filter(x -> (rid == -1 ? true : x.getReader().getId() == rid)
				&& (name.isEmpty() ? true : x.getReader().getName().toLowerCase().contains(name.toLowerCase()))
				&& (user.isEmpty() ? true : x.getReader().getUserName().toLowerCase().contains(user.toLowerCase()))
				&& (date == null ? true : x.getDate().equals(date))
				&& (status == 2 ? true : (status == 0 ? x.getReader().isActiveStatus() : !x.getReader().isActiveStatus()))
				&& (type == 2 ? true : (type == 0 ? x.getDocument() instanceof Book : x.getDocument() instanceof Paper))
				&& (returned == 2 ? true : (returned == 1 ? x.isReturned() : !x.isReturned()))
				)
				.collect(Collectors.toCollection(ArrayList::new));
	}
}
