package com.example.administrator.batheasy3.bean1;


public class User extends Character {

	private String sex;			//�Ա�
	private String school;		//ѧУ
	private int score;			//���÷�
	private String portraitPath;//ͷ��·��
	private String userState; 	//


	//���췽��
	public User(String phoneNumber, String name, String password, int score, String sex, String school, String portraitPath) {
		super(phoneNumber, name, password);
		this.sex = sex;
		this.score = score;
		this.school = school;
		this.portraitPath = portraitPath;
	}
	
	
	public User(String phoneNumber, String password, String sex, String school) {
		super(phoneNumber);
		this.password = password;
		this.sex 	  = sex;
		this.school   = school;
	}
	
	
	public User(String phoneNumber) {
		super(phoneNumber);
	}


	public String getUserState() {
		return userState;
	}


	public String getSchool() {
		return school;
	}


	public void setSchool(String school) {
		this.school = school;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public int getScore() {
		return score;
	}

	
	public String getPortraitPath() {
		return portraitPath;
	}


}
