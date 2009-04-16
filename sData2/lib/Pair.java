package lib;

public class Pair<F,S> {
	
	private F first;
	private S second;
	
	public Pair(F f, S s) {
		first = f;	
		second = s;
	}
	
	public F getFirst() {
		return first;
	}
	
	public S getSecond() {
		return second;
	}
	
	public void setFirst(F f) {
		first = f;
	}
	
	public void setSecond(S s) {
		second = s;
	}
	
	public String toString() {
		return "<" + first + ", " + second + ">";
	}
}