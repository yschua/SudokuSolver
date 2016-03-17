import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class SudokuGrid implements Cloneable
{
	private char[][] values;
	private int unfilled;
	private ArrayList<Pair<Integer, Integer>> unfilledList;

	public SudokuGrid()
	{
		values = new char[9][9];
		unfilledList = new ArrayList<>();

		IntStream.range(0, values.length).forEach(i ->
				IntStream.range(0, values[i].length).forEach(j -> values[i][j] = '0'));

		this.computeUnfilled();
	}

	public SudokuGrid(String initValues)
	{
		values = new char[9][9];
		unfilledList = new ArrayList<>();
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
		IntStream.range(0, values[index - 1].length)
				.forEach(i -> chr[i] = values[i][index - 1]);
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



	public void addToUnfilledList(int i, int j) {
		unfilled++;
		unfilledList.add(new Pair<>(i + 1, j + 1));
	}

	/* 0 represents an unfilled slot on the grid */
	public void computeUnfilled()
	{
		unfilled = 0;
		unfilledList.clear();

		IntStream.range(0, values.length)
				.forEach(i -> IntStream.range(0, values[i].length)
						.filter(j -> values[i][j] == '0')
						.forEach(j -> addToUnfilledList(i, j)));
	}

	public boolean isComplete()
	{
		return unfilled == 0;
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
