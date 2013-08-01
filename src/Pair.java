public class Pair<T, U>
{
	public final T x;
	public final U y;

	public Pair(T x, U y)
	{
		this.x = x;
		this.y = y;
	}

	public T getX()
	{
		return x;
	}

	public U getY()
	{
		return y;
	}

	public String toString()
	{
		return "(" + x + ", " + y +")";
	}
}
