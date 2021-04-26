/**	Class: Puzzle
 * @author Ashley
 * @version 1
 * Course: ITEC 3860 Spring 2021
 * Written: Apr 23, 2021
 *
 * This class - creates the Object Puzzle.
 *
 * Purpose - to be read from the Model.
 */
package controller;


public class Puzzle 
{
	private int puzzleID;
	private String problem;
	private String answer;
	private String tip;
	private boolean isSolved;
	private int roomID; 
	public static final String WRONG_ANSWER = "The answer to the question was incorrect, all of a sudden you feel your life drain a bit. "
			+ "You lost 5 health points.";
	
	/**
	 * @return the puzzleID
	 */
	public int getPuzzleID() 
	{
		return puzzleID;
	}

	/**
	 * @param puzzleID the puzzleID to set
	 */
	public void setPuzzleID(int puzzleID) 
	{
		this.puzzleID = puzzleID;
	}

	/**
	 * @return the isSolved
	 */
	public boolean isSolved() 
	{
		return isSolved;
	}

	/**
	 * @param isSolved the isSolved to set
	 */
	public void setSolved(boolean isSolved) 
	{
		this.isSolved = isSolved;
	}
	
	/** Method: storeSolved
	  * 
	  * This method stores the fact that the current Player has solved this puzzle. 
	  */
	public void storeVisited(Player player)
	{
		GameController.getMap().storeSolved(player, this);
	}

	/**
	 * @return the problem
	 */
	public String getProblem() 
	{
		return problem;
	}

	/**
	 * @param problem the problem to set
	 */
	public void setProblem(String problem) 
	{
		this.problem = problem;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer()
	{
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) 
	{
		this.answer = answer;
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
	 * @return the correctAnswer
	 */
	public String getCorrectAnswer() 
	{
		return answer;
	}

	/**
	 * @param correctAnswer the correctAnswer to set
	 */
	public void setCorrectAnswer(String ans) 
	{
		answer = ans;
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	
}
