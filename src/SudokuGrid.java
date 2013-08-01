import java.util.Arrays;
import java.util.ArrayList;

public class SudokuGrid implements Cloneable
{
	private char[][] values;
	private int unfilled;
	private ArrayList<Pair<Integer, Integer>> unfilledList;

	public SudokuGrid()
	{
		values = new char[9][9];
		unfilledList = new ArrayList<Pair<Integer, Integer>>();
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				values[i][j] = '0';
			}
		}
		this.computeUnfilled();
	}

	public SudokuGrid(String initValues)
	{
		values = new char[9][9];
		unfilledList = new ArrayList<Pair<Integer, Integer>>();
		initValues = initValues.replaceAll("\\s+", "");
		for (int i = 0, strCtr = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++, strCtr++) {
				values[i][j] = initValues.charAt(strCtr);
			}
		}
		this.computeUnfilled();
	}

	public char[] getRow(int index)
	{
		return values[index - 1];
	}

	public char[] getColumn(int index)
	{
		char[] chr = new char[9];
		for (int i = 0; i < values[index - 1].length; i++) {
			chr[i] = values[i][index - 1];
		}
		return chr;
	}

	public char[] getSquare(int index)
	{
		int iIndex, jIndex;
		char[] chr = new char[9];
		if (index < 4) {
			iIndex = 0;
		} else if (index < 7) {
			iIndex = 3;
		} else {
			iIndex = 6;
		}
		if (index == 1 || index == 4 || index == 7) {
			jIndex = 0;
		} else if (index == 2 || index == 5 || index == 8) {
			jIndex = 3;
		} else {
			jIndex = 6;
		}
		for (int i = iIndex, k = 0; i < iIndex + 3; i++) {
			for (int j = jIndex; j < jIndex + 3; j++, k++) {
				chr[k] = values[i][j];
			}
		}
		return chr;
	}

	public char getValue(int i, int j)
	{
		return values[i - 1][j - 1];
	}

	public int getUnfilled()
	{
		return unfilled;
	}

	public ArrayList<Pair<Integer, Integer>> getUnfilledList()
	{
		return unfilledList;
	}

	public boolean setValue(char value, int i, int j)
	{
		if (values[i - 1][j - 1] == '0' && value != '0')
			unfilled--;
		else if (values[i - 1][j - 1] != '0' && value == '0')
			unfilled++;
		values[i - 1][j - 1] = value;
		return true;
	}

	/* 0 represents an unfilled slot on the grid */
	public void computeUnfilled()
	{
		unfilled = 0;
		unfilledList.clear();
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				if (values[i][j] == '0') {
					unfilled++;
					unfilledList.add(new Pair<Integer, Integer>(i + 1, j + 1));
				}
			}
		}
	}

	public boolean isComplete()
	{
		if (unfilled == 0)
			return true;
		return false;
	}

	public SudokuGrid clone()
	{
		SudokuGrid clone = new SudokuGrid();
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				clone.values[i][j] = this.values[i][j];
			}
		}
		clone.computeUnfilled();
		return clone;
	}

	public String toString()
	{
		String str = "";
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				str += values[i][j] + " ";
			}
			str += "\n";
		}
		return str;
	}
}
