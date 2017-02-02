
public class CountryNode {
	public String country;
	public int count = 1;
	public boolean sorted = false;

	public CountryNode(String thiscountry) {
		country = thiscountry;
	}

	public void setCountry(String s) {
		country = s;
	}

	public void increase() {
		++count;
	}

	public void markSorted() {
		sorted = true;
	}
}
