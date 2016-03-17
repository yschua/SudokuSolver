import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.*;

public class SudokuSolver
{
	private SudokuGrid grid;
	private SudokuGrid partSoln;

	public SudokuSolver(SudokuGrid grid)
	{
		this.grid = grid;
		partSoln = grid.clone();
	}

	/* Returns true if key is found in array */
	private static boolean contains(char[] array, char key)
	{
		return IntStream.range(0, array.length).anyMatch(i -> array[i] == key);
	}

	/* Returns true if key is a valid value in a position on the grid */
	public static boolean checkValid(SudokuGrid grid, char key, int i, int j)
	{
		int sqIndex;
		if (i >= 1 && i <= 3)
			sqIndex = 1;
		else if (i >= 4 && i <= 6)
			sqIndex = 4;
		else
			sqIndex = 7;
		if (j >= 4 && j <= 6)
			sqIndex ++;
		else if (j >= 7 && j <= 9)
			sqIndex += 2;
		return !contains(grid.getRow(i), key) && !contains(grid.getColumn(j), key) && !contains(grid.getSquare(sqIndex), key);
	}

	/* Returns all possible values for a position given a grid */
	public static ArrayList<Character> getPossibleValues(SudokuGrid grid, int i, int j)
	{
		ArrayList<Character> possibleValues = new ArrayList<>(4);
		IntStream.range(1, 10)
				.mapToObj(k -> Character.forDigit(k, 10))
				.filter(k -> checkValid(grid, k, i, j))
				.forEach(possibleValues::add);

		return possibleValues;
	}

	/* Returns true if grid is solved */
	public static boolean isSolution(SudokuGrid partSoln)
	{
		return IntStream.range(1, 10)
				.allMatch(k ->
						checkUnique(partSoln.getRow(k)) ||
						checkUnique(partSoln.getColumn(k)) ||
						checkUnique(partSoln.getSquare(k)));
	}

	/* Returns true if the values within array are unique (1-9) */
	public static boolean checkUnique(char[] array)
	{
		return !contains(array, '0') && IntStream.range(1, 10)
				.mapToObj(i -> Character.forDigit(i, 10))
				.allMatch(i -> contains(array, i));
	}

	/* Solve the puzzle using backtracking */
	public SudokuGrid solve()
	{
		return solve(this.partSoln, false);
	}

	/* Returns true if value is not valid */
	private static boolean rejectValue(SudokuGrid partSoln, char value, int i, int j)
	{
		return !checkValid(partSoln, value, i, j);
	}

	/* Solve the puzzle using backtracking */
	private SudokuGrid solve(SudokuGrid partSoln, boolean rejectFlag)
	{
		if (rejectFlag) {
			return null;
		}
		if (isSolution(partSoln)) {
			/* debug */ System.out.println("Found solution!"); System.out.println(partSoln);
			return partSoln;
		} else {
			SudokuGrid solution;
			ArrayList<Pair<Integer, Integer>> unfilledList = partSoln.getUnfilledList();
			Pair<Integer, Integer> unfilledPos = unfilledList.remove(0);
			int x = unfilledPos.getX();
			int y = unfilledPos.getY();
			ArrayList<Character> possibleVal = getPossibleValues(grid, x, y);
			//* debug */ System.out.print("Possible values at " + unfilledPos + ": "); printArray(possibleVal);

			for (char val : possibleVal) {
				if (rejectValue(partSoln, val, x, y)) {
					//* debug */ System.out.println("Rejected " + val + " from " + unfilledPos);
					rejectFlag = true;
				}
				partSoln.setValue(val, x, y);
				//* debug */ System.out.println("Set " + val + " to " + unfilledPos);
				if ((solution = solve(partSoln.clone(), rejectFlag)) != null) {
					return solution;
				}
				//* debug */ System.out.println("Removed " + val + " from " + unfilledPos);
				if (rejectFlag) {
					rejectFlag = false;
				}
			}
			return null;
		}

	}

	/* Debug code */
	private static void printArray(ArrayList<Character> array)
	{
		System.out.print("[");
		IntStream.range(0, array.size())
				.forEach(i -> System.out.println(array.get(i) + ","));
		System.out.println("\b]");
	}

}
