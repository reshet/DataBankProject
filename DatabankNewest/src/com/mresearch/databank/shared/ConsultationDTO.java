package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class ConsultationDTO extends ConsultationDTO_Light{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private String answer = "";
	
	//4405882301020404
	private HashMap<String,String> filling;
//	private ArrayList<String> key_words,authors;
//	private Date date,accept_date,decline_date;
	private String json_desctiptor;
	private Date date_ans;
	private int isPublished;
	public String getJson_desctiptor() {
		return json_desctiptor;
	}


	public void setJson_desctiptor(String json_desctiptor) {
		this.json_desctiptor = json_desctiptor;
	}
	public ConsultationDTO(){}
	public ConsultationDTO(long id,String question, String answer,Date date,Date date_ans,int isPublished,HashMap<String,String> fill)
	{
		super(id,question,date);
		this.setanswer(answer);
		this.date_ans = date_ans;
		this.filling = fill;
		this.isPublished = isPublished;
	}
	
	
	
	
public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public Date getDate_ans() {
		return date_ans;
	}


	public void setDate_ans(Date date_ans) {
		this.date_ans = date_ans;
	}


	public int getIsPublished() {
		return isPublished;
	}


	public void setIsPublished(int isPublished) {
		this.isPublished = isPublished;
	}


	//	public Date getDate() {
//		return date;
//	}
//	public void setDate(Date date) {
//		this.date = date;
//	}
//	public Date getDecline_date() {
//		return decline_date;
//	}
//	public void setDecline_date(Date decline_date) {
//		this.decline_date = decline_date;
//	}
//	public Date getAccept_date() {
//		return accept_date;
//	}
//	public void setAccept_date(Date accept_date) {
//		this.accept_date = accept_date;
//	}
//	public ArrayList<String> getKey_words() {
//		return key_words;
//	}
//	public void setKey_words(ArrayList<String> key_words) {
//		this.key_words = key_words;
//	}
//	public ArrayList<String> getAuthors() {
//		return authors;
//	}
//	public void setAuthors(ArrayList<String> authors) {
//		this.authors = authors;
//	}
	public HashMap<String,String> getFilling() {
		return filling;
	}
	public void setFilling(HashMap<String,String> filling) {
		this.filling = filling;
	}


	public String getanswer() {
		return answer;
	}


	public void setanswer(String answer) {
		this.answer = answer;
	}
}
