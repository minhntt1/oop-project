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
 *
 * @author admin1
 */
public interface IFaceDocumentManagement {

	public boolean checkAndRemoveIssuedUser(int uid);

	public Object[] addBook(int relNo, int copies, int pid, int[] aid, String bname, int bpages);

	public Object[] addPaper(int relNo, int copies, int pid, Date relDate);

	public int removeDoc(int id);

	public Object[] editBook(int id, int relNo, int pid, int copies, String name, int[] aid, int pages);

	public Object[] editPaper(int id, int relNo, int pid, int copies, Date relDate);

	public Object[] issueDoc(int rid, int did, Date date, int idxPeriod);

	public Object[] returnDoc(int iid);

	public ArrayList<Document> findDoc(int id, int relNo, int pid);

	public ArrayList<Document> findBook(int id, int relNo, int pid, int copies, String name, int[] aid, int pages);

	public ArrayList<Document> findPaper(int id, int relNo, int pid, int copies, Date relDate);

	public ArrayList<IssuedFor> findDocIssued(int rid, String name, String user, Date date, int status, int type, int returned);

	public Object[] getIssuedStatsByUser(int uid);

	public Object[] getIssuedStatsOverall();

	public Object[] getStatsOverall();
}
