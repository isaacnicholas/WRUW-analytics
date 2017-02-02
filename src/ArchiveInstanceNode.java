
public class ArchiveInstanceNode {
	public String ip;
	public String name;
	public String quality;

	public ArchiveInstanceNode(String newname, String newip, String newquality) {
		name = newname;
		ip = newip;
		quality = newquality;
	}

	public boolean compare(String newname, String newip, String newquality) {
		if (newname.equals(name) && newip.equals(ip) && newquality.equals(quality))
			return true;
		else
			return false;
	}
}
