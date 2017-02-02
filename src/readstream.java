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

public class readstream {

	public static void main(String[] args) throws IOException {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.YEAR, -1);
		long timeinmil = rightNow.getTimeInMillis();
		File f = new File("C:\\analyticsprogram\\stream.csv");
		if (!f.exists() && !f.isDirectory()) {
			f.createNewFile();
		}
		CSVWriter writer = new CSVWriter(new FileWriter("C:\\analyticsprogram\\newstream.csv"));
		CSVReader reader = new CSVReader(new FileReader("C:\\analyticsprogram\\stream.csv"));
		String[] nextLine;
		String[] firstline;
		firstline = new String[6];
		firstline = reader.readNext();
		FileReader in = new FileReader("C:\\Program Files (x86)\\Icecast\\log\\access.log");
		//FileReader in=new FileReader("access.log");
		LinkedList<String> fileread = new LinkedList<String>();
		BufferedReader br;
		br = new BufferedReader(in);
		String line;
		while ((line = br.readLine()) != null) {
			fileread.add(line);
		}
		br.close();
		Iterator<String> fileiterator = fileread.descendingIterator();
		boolean replacement = false;
		while (fileiterator.hasNext() && replacement == false) {
			line = (String) fileiterator.next();
			// is it a valid line? Will it connect to a valid stream
			if (line.contains("GET /stream256.mp3 HTTP/1.1") || line.contains("GET /stream128.mp3 HTTP/1.1")
					|| line.contains("GET /stream56.mp3 HTTP/1.1")) {
				// Build IP Address
				StringBuilder IP = new StringBuilder();
				int i = 0;
				while (line.charAt(i) != ' ') {
					IP.append(line.charAt(i));
					++i;
				}
				// Get the access time
				StringBuilder time = new StringBuilder();
				i = 0;
				while (line.charAt(i) != '[') {
					++i;
				}
				++i;
				while (line.charAt(i) != ' ') {
					time.append(line.charAt(i));
					++i;
				}
				ServerLocation location = getLocation(IP.toString());
				long filetime = getStreamTime(time.toString());
				String[] newline;
				newline = new String[6];
				newline[0] = Long.toString(filetime);
				if (line.contains("GET /stream256.mp3 HTTP/1.1")) {
					newline[1] = "56";
				}
				if (line.contains("GET /stream128.mp3 HTTP/1.1")) {
					newline[1] = "128";
				}
				if (line.contains("GET /stream56.mp3 HTTP/1.1")) {
					newline[1] = "256";
				}
				newline[2] = location.countryName;
				newline[3] = location.regionName;
				newline[4] = location.city;
				newline[5] = IP.toString();
				if (Arrays.equals(newline, firstline)) {
					replacement = true;
				} else {
					writer.writeNext(newline);
				}
			}
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
		//File old = new File("newstream.csv");
		//File current = new File("stream.csv");
		//current.delete();
		//old.renameTo(current);*/
	}

	public static long getStreamTime(String s) {
		StringBuilder day = new StringBuilder();
		day.append(s.charAt(0));
		day.append(s.charAt(1));
		int dayint = Integer.parseInt(day.toString());
		StringBuilder month = new StringBuilder();
		month.append(s.charAt(3));
		month.append(s.charAt(4));
		month.append(s.charAt(5));
		String monthstring = month.toString();
		int monthint = -1;
		if (monthstring.equals("Jan")) {
			monthint = 0;
		} else if (monthstring.equals("Feb")) {
			monthint = 1;
		} else if (monthstring.equals("Mar")) {
			monthint = 2;
		} else if (monthstring.equals("Apr")) {
			monthint = 3;
		} else if (monthstring.equals("May")) {
			monthint = 4;
		} else if (monthstring.equals("Jun")) {
			monthint = 5;
		} else if (monthstring.equals("Jul")) {
			monthint = 6;
		} else if (monthstring.equals("Aug")) {
			monthint = 7;
		} else if (monthstring.equals("Sep")) {
			monthint = 8;
		} else if (monthstring.equals("Oct")) {
			monthint = 9;
		} else if (monthstring.equals("Nov")) {
			monthint = 10;
		} else if (monthstring.equals("Dec")) {
			monthint = 11;
		}
		StringBuilder year = new StringBuilder();
		year.append(s.charAt(7));
		year.append(s.charAt(8));
		year.append(s.charAt(9));
		year.append(s.charAt(10));
		int yearint = Integer.parseInt(year.toString());
		StringBuilder hour = new StringBuilder();
		hour.append(s.charAt(12));
		hour.append(s.charAt(13));
		int hourint = Integer.parseInt(hour.toString());
		StringBuilder min = new StringBuilder();
		min.append(s.charAt(15));
		min.append(s.charAt(16));
		int minint = Integer.parseInt(min.toString());
		StringBuilder sec = new StringBuilder();
		sec.append(s.charAt(18));
		sec.append(s.charAt(19));
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
}