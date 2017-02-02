import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Iterator;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class readarchive {

	public static void main(String[] args) throws IOException {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.YEAR, -1);
		long timeinmil = rightNow.getTimeInMillis();
		File f1 = new File("C:\\analyticsprogram\\archive.csv");
		if (!f1.exists() && !f1.isDirectory()) {
			f1.createNewFile();
		}
		CSVWriter writer = new CSVWriter(new FileWriter("C:\\analyticsprogram\\newarchive.csv"));
		CSVReader reader = new CSVReader(new FileReader("C:\\analyticsprogram\\archive.csv"));
		String[] nextLine;
		String[] firstline;
		boolean replacement = false;
		Calendar datetoget = Calendar.getInstance();
		firstline = new String[7];
		firstline = reader.readNext();
		for (int i = 0; i < 366; i++) {
			String filename = getfilename(datetoget);
			File f = new File(filename);
			if (f.exists()) {
				FileReader in = new FileReader(filename);
				LinkedList<String> fileread = new LinkedList<String>();
				BufferedReader br;
				br = new BufferedReader(in);
				String line;
				while ((line = br.readLine()) != null) {
					fileread.add(line);
				}
				br.close();
				Iterator<String> fileiterator = fileread.descendingIterator();
				while (fileiterator.hasNext() && replacement == false) {
					line = fileiterator.next();
					if (line.contains(".mp3") && line.contains("201") && line.contains("404") == false
							&& line.contains(" GET /archives/")) {
						StringBuilder timesb = new StringBuilder();
						for (int start = 0; start < 20; start++) {
							timesb.append(line.charAt(start));
						}

						int start = 47;
						if (line.charAt(start) == 'l') {
							start = start + 9;
						}
						StringBuilder qualitysb = new StringBuilder();
						while (line.charAt(start) != '/') {
							qualitysb.append(line.charAt(start));
							++start;
						}
						++start;
						int end = line.indexOf(".mp3");
						StringBuilder showsb = new StringBuilder();
						for (int i2 = start; i2 < end; i2++) {
							showsb.append(line.charAt(i2));
						}
						start = end + 12;
						StringBuilder ipsb = new StringBuilder();
						while (line.charAt(start) != ' ') {
							ipsb.append(line.charAt(start));
							++start;
						}
						String[] newline;
						newline = new String[7];
						Long filetime = getArchiveTime(timesb.toString());
						newline[0] = Long.toString(filetime);
						newline[1] = qualitysb.toString();
						ServerLocation location = getLocation(ipsb.toString());
						newline[2] = location.countryName;
						newline[3] = location.regionName;
						newline[4] = location.city;
						newline[5] = ipsb.toString();
						newline[6] = showsb.toString();
						if (Arrays.equals(newline, firstline))
							replacement = true;
						else
							writer.writeNext(newline);
					}
				}
			}
			if (replacement)
				break;
			datetoget.add(Calendar.DAY_OF_MONTH, -1);
		}
		writer.writeNext(firstline);
		while ((nextLine = reader.readNext()) != null) {
			if (Long.parseLong(nextLine[0]) >= timeinmil) {
				writer.writeNext(nextLine);
			} else {
				break;
			}
		}
		writer.close();
		reader.close();
		File old = new File("newarchive.csv");
		File current = new File("archive.csv");
		current.delete();
		old.renameTo(current);
	}

	public static long getArchiveTime(String s) {
		StringBuilder day = new StringBuilder();
		day.append(s.charAt(8));
		day.append(s.charAt(9));
		int dayint = Integer.parseInt(day.toString());
		StringBuilder month = new StringBuilder();
		month.append(s.charAt(5));
		month.append(s.charAt(6));
		int monthint = Integer.parseInt(month.toString());
		--monthint;
		StringBuilder year = new StringBuilder();
		year.append(s.charAt(0));
		year.append(s.charAt(1));
		year.append(s.charAt(2));
		year.append(s.charAt(3));
		int yearint = Integer.parseInt(year.toString());
		StringBuilder hour = new StringBuilder();
		hour.append(s.charAt(11));
		hour.append(s.charAt(12));
		int hourint = Integer.parseInt(hour.toString());
		StringBuilder min = new StringBuilder();
		min.append(s.charAt(14));
		min.append(s.charAt(15));
		int minint = Integer.parseInt(min.toString());
		StringBuilder sec = new StringBuilder();
		sec.append(s.charAt(17));
		sec.append(s.charAt(18));
		int secint = Integer.parseInt(sec.toString());
		Calendar creationDate = Calendar.getInstance();
		creationDate.set(Calendar.MILLISECOND, 0);
		creationDate.set(yearint, monthint, dayint, hourint, minint, secint);
		return creationDate.getTimeInMillis();

	}

	public static ServerLocation getLocation(String ipAddress) {
		File file = new File("GeoLiteCity.dat");
		ServerLocation serverLocation = null;

		try {

			serverLocation = new ServerLocation();

			LookupService lookup = new LookupService(file, LookupService.GEOIP_MEMORY_CACHE);
			Location locationServices = lookup.getLocation(ipAddress);
			// if it isn't in the database, stop looking. This will break the
			// program if you continue
			if (locationServices != null) {
				serverLocation.setCountryCode(locationServices.countryCode);
				serverLocation.setCountryName(locationServices.countryName);
				serverLocation.setRegion(locationServices.region);
				serverLocation.setRegionName(
						regionName.regionNameByCode(locationServices.countryCode, locationServices.region));
				serverLocation.setCity(locationServices.city);
				serverLocation.setPostalCode(locationServices.postalCode);
				serverLocation.setLatitude(String.valueOf(locationServices.latitude));
				serverLocation.setLongitude(String.valueOf(locationServices.longitude));
			} else {
				serverLocation.setCountryCode("N/A");
				serverLocation.setCountryName("N/A");
				serverLocation.setRegion("N/A");
				serverLocation.setRegionName("N/A");
				serverLocation.setCity("N/A");
			}
			if (serverLocation.countryCode == null)
				serverLocation.setCountryCode("N/A");
			if (serverLocation.countryName == null)
				serverLocation.setCountryName("N/A");
			if (serverLocation.region == null)
				serverLocation.setRegion("N/A");
			if (serverLocation.regionName == null)
				serverLocation.setRegionName("N/A");
			if (serverLocation.city == null)
				serverLocation.setCity("N/A");

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		return serverLocation;
	}

	public static String getfilename(Calendar date) {
		int day = date.get(Calendar.DAY_OF_MONTH);
		int month = date.get(Calendar.MONTH);
		++month;
		int year = date.get(Calendar.YEAR);
		year = year % 100;
		StringBuilder filename = new StringBuilder();
		filename.append("C:\\inetpub\\logs\\LogFiles\\W3SVC2\\u_ex");
		filename.append(year);
		if (month < 10) {
			filename.append('0');
		}
		filename.append(month);
		if (day < 10) {
			filename.append('0');
		}
		filename.append(day);
		filename.append(".log");
		return filename.toString();
	}
}
