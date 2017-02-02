
public class RegionNode {
	public String country;
	public String region;
	public int count = 1;
	public boolean sorted = false;

	public RegionNode(String thisregion, String thiscountry) {
		region = thisregion;
		country = thiscountry;
	}

	public void setCountry(String s) {
		country = s;
	}

	public void setRegion(String s) {
		region = s;
	}

	public void increase() {
		++count;
	}

	public void markSorted() {
		sorted = true;
	}
}
