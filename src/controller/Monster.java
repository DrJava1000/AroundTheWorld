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
	private Item weakness;
	private String tip;
	private String defeated;
	private String wrongItemChoice;
	private String rightItemChoice;
	
	Monster()
	{
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
	public String getDefeated() 
	{
		return defeated;
	}

	/**
	 * @param defeated the defeated to set
	 */
	public void setDefeated(String defeated) 
	{
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
