package controller;


/**	Class: Monster
 * @author Ashley
 * @version 1
 * Course: ITEC 3860 Spring 2021
 * Written: Apr 23, 2021
 *
 * This class - Creates the object Monster. 
 *
 * Purpose - to be read from the Model
 */


public class Monster 
{
	private int monsterID;
	private String monsterName;
	private String monsterDescription;
	private int rightItemChoice;
	private String tip;
	private boolean defeated;
	private String wrongChoice;
	private String rightChoice;

	
	public Monster()
	{
	}
	
	

	public int getMonsterID() {
		return monsterID;
	}



	public void setMonsterID(int monsterID) {
		this.monsterID = monsterID;
	}
	
	
	public String getMonsterName() {
		return monsterName;
	}



	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}



	public String getMonsterDescription() {
		return monsterDescription;
	}



	public void setMonsterDescription(String monsterDescription) {
		this.monsterDescription = monsterDescription;
	}


	/**
	 * @return the tip
	 */
	public String getTip()
	{
		return tip;
	}

	/**
	 * @param tip the tip to set
	 */
	public void setTip(String tip) 
	{
		this.tip = tip;
	}


	/**
	 * @return the defeated
	 */
	public boolean isDefeated() {
		return defeated;
	}



	/**
	 * @param defeated the defeated to set
	 */
	public void setDefeated(boolean defeated) {
		this.defeated = defeated;
	}



	/**
	 * @return the wrongChoice
	 */
	public String getWrongChoice() {
		return wrongChoice;
	}



	/**
	 * @param i the wrongChoice to set
	 */
	public void setWrongChoice(String i) {
		this.wrongChoice = i;
	}



	/**
	 * @return the rightChoice
	 */
	public String getRightChoice() {
		return rightChoice;
	}



	/**
	 * @param rightChoice the rightChoice to set
	 */
	public void setRightChoice(String rightChoice) {
		this.rightChoice = rightChoice;
	}



	/**
	 * @return the rightItemChoice
	 */
	public int getRightItemChoice() {
		return rightItemChoice;
	}



	/**
	 * @param rightItemChoice the rightItemChoice to set
	 */
	public void setRightItemChoice(int rightItemChoice) {
		this.rightItemChoice = rightItemChoice;
	}
}
