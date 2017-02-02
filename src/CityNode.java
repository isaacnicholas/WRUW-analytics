
public class CityNode {
	public String country;
	public String region;
	public String city;
	public int count = 1;
	public boolean sorted = false;

	public CityNode(String thiscity, String thisregion, String thiscountry) {
		city = thiscity;
		region = thisregion;
		country = thiscountry;
	}

	public void setCountry(String s) {
		country = s;
	}

	public void setRegion(String s) {
		region = s;
	}

	public void setCity(String s) {
		city = s;
	}

	public void increase() {
		++count;
	}

	public void markSorted() {
		sorted = true;
	}
}
