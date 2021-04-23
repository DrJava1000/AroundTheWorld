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
	private String problem;
	private String answer;
	private String tip;
	private String WRONG_ANSWER;
	private String correctAnswer;
	
	public Puzzle()
	{
		
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
	 * @return the wRONG_ANSWER
	 */
	public String getWRONG_ANSWER()
	{
		return WRONG_ANSWER;
	}

	/**
	 * @param wRONG_ANSWER the wRONG_ANSWER to set
	 */
	public void setWRONG_ANSWER(String wRONG_ANSWER) 
	{
		WRONG_ANSWER = wRONG_ANSWER;
	}

	/**
	 * @return the correctAnswer
	 */
	public String getCorrectAnswer() 
	{
		return correctAnswer;
	}

	/**
	 * @param correctAnswer the correctAnswer to set
	 */
	public void setCorrectAnswer(String correctAnswer) 
	{
		this.correctAnswer = correctAnswer;
	}
	
}
