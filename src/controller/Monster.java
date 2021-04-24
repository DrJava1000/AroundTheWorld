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
	private Item weakness;
	private String tip;
	private boolean defeated;
	private String wrongItemChoice;
	private String rightItemChoice;


	
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
	 * @return the weakness
	 */
	public Item getWeakness() 
	{
		return weakness;
	}

	/**
	 * @param weakness the weakness to set
	 */
	public void setWeakness(Item weakness)
	{
		this.weakness = weakness;
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
	 * @return the wrongItemChoice
	 */
	public String getWrongItemChoice()
	{
		return wrongItemChoice;
	}

	/**
	 * @param wrongItemChoice the wrongItemChoice to set
	 */
	public void setWrongItemChoice(String wrongItemChoice)
	{
		this.wrongItemChoice = wrongItemChoice;
	}

	/**
	 * @return the rightItemChoice
	 */
	public String getRightItemChoice()
	{
		return rightItemChoice;
	}

	/**
	 * @param rightItemChoice the rightItemChoice to set
	 */
	public void setRightItemChoice(String rightItemChoice)
	{
		this.rightItemChoice = rightItemChoice;
	}
	
	
}
