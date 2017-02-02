import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.ListIterator;

import com.opencsv.CSVReader;

public class archivewriter {

	public static void main(String[] args) throws IOException {
		Calendar rightnow = Calendar.getInstance();
		LinkedList<ShowNode> shows = new LinkedList<ShowNode>();
		String[] line;
		int timeinmil = 0;
		int quality = 1;
		int country = 2;
		int region = 3;
		int city = 4;
		int ip = 5;
		int showname = 6;
		String[] ipduplicates;
		int duplicatenumber = 0;
		String[] qualityduplicates;
		String[] showduplicates;
		ipduplicates = new String[100];
		qualityduplicates = new String[100];
		showduplicates = new String[100];
		CSVReader reader = new CSVReader(new FileReader("C:\\analyticsprogram\\archive.csv"));
		while ((line = reader.readNext()) != null) {
			boolean unique = true;
			for (int i = 0; i < 100; i++) {
				if (line[quality].equals(qualityduplicates[i]) && line[ip].equals(ipduplicates[i])
						&& line[showname].equals(showduplicates[i])) {
					unique = false;
					break;
				}
			}
			if (unique) {
				qualityduplicates[duplicatenumber] = line[quality];
				ipduplicates[duplicatenumber] = line[ip];
				showduplicates[duplicatenumber] = line[showname];
				++duplicatenumber;
				duplicatenumber = duplicatenumber % 100;
				ListIterator<ShowNode> showiterator = shows.listIterator();
				boolean added = false;
				int place = 0;
				while (showiterator.hasNext() && added == false) {
					ShowNode shownode = showiterator.next();
					if (shownode.showname.compareTo(line[showname]) == 0) {
						shownode.add(line[country], line[region], line[city], line[quality], line[timeinmil]);
						added = true;
					} else if (shownode.showname.compareTo(line[showname]) > 0) {
						ShowNode newnode = new ShowNode(line[showname], rightnow);
						newnode.add(line[country], line[region], line[city], line[quality], line[timeinmil]);
						shows.add(place, newnode);
						added = true;
					}
					++place;
				}
				if (added == false) {
					ShowNode newnode = new ShowNode(line[showname], rightnow);
					newnode.add(line[country], line[region], line[city], line[quality], line[timeinmil]);
					shows.add(newnode);
				}
			}
		}
		// write pages
		ListIterator<ShowNode> iterator = shows.listIterator();
		reader.close();
		while (iterator.hasNext()) {
			ShowNode node = iterator.next();
			new Thread(node.showname) {
				public void run() {
					try {
						node.createpage();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
		PrintWriter p = new PrintWriter("C:\\inetpub\\archivesite\\analytics\\shows.html");
		//PrintWriter p=new PrintWriter("shows.html");
		p.print("<!doctype html>\n<html>\n<head>\n<meta charset=\"UTF-8\">\n<title>WRUW Archive Statistics</title>\n</head>\n\n<body>\n<p><center> <img src=\"brand.png\" width=\"297\" height=\"77\" alt=\"\"/></center></p>\n<p><center>\n  <h1>Archive Statistics</h1>\n");
		p.println("<p><em>Last Updated: " + rightnow.getTime() + "</em></p>");
		p.print("</center>\n<p>&nbsp;</p>\n<h3>Please look through the list below for your show. If your show is not listed, then it has not been downloaded</h3>\n<ul>\n");
		ListIterator<ShowNode> iterator1 = shows.listIterator();
		while (iterator1.hasNext()) {
			ShowNode node = iterator1.next();
			p.println("  <li><a href=\"" + node.showname + ".html\">" + node.showname + "</a></li>");
		}
		p.print("  </ul>");
		p.println(
				"<p><em><font color=\"#4F4F4F\">Created by Isaac Nicholas <a href=\"mailto:idn2@case.edu\">idn2@case.edu</a></font></em></p>");
		p.println("<p><em><font color=\"#4F4F4F\">Updated " + rightnow.getTime() + "</font></em></p>");
		p.print("<p><em><font color=\"#4F4F4F\">Modified 2016/Apr/24</font></em></p>\n</body>\n</html>");
		p.close();
	}
}
