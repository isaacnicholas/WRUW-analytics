import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.ListIterator;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;

public class ShowNode {
	String showname;
	int[] lasthour;
	int[] last2hours;
	int[] last12hours;
	int[] today;
	int[] lastweek;
	int[] lastmonth;
	int[] lastyear;
	LinkedList<CountryNode> lasthourcountry = new LinkedList<CountryNode>();
	LinkedList<CountryNode> last2hourscountry = new LinkedList<CountryNode>();
	LinkedList<CountryNode> last12hourscountry = new LinkedList<CountryNode>();
	LinkedList<CountryNode> todaycountry = new LinkedList<CountryNode>();
	LinkedList<CountryNode> lastweekcountry = new LinkedList<CountryNode>();
	LinkedList<CountryNode> lastmonthcountry = new LinkedList<CountryNode>();
	LinkedList<CountryNode> lastyearcountry = new LinkedList<CountryNode>();
	LinkedList<RegionNode> lasthourregion = new LinkedList<RegionNode>();
	LinkedList<RegionNode> last2hoursregion = new LinkedList<RegionNode>();
	LinkedList<RegionNode> last12hoursregion = new LinkedList<RegionNode>();
	LinkedList<RegionNode> todayregion = new LinkedList<RegionNode>();
	LinkedList<RegionNode> lastweekregion = new LinkedList<RegionNode>();
	LinkedList<RegionNode> lastmonthregion = new LinkedList<RegionNode>();
	LinkedList<RegionNode> lastyearregion = new LinkedList<RegionNode>();
	LinkedList<CityNode> lasthourcity = new LinkedList<CityNode>();
	LinkedList<CityNode> last2hourscity = new LinkedList<CityNode>();
	LinkedList<CityNode> last12hourscity = new LinkedList<CityNode>();
	LinkedList<CityNode> todaycity = new LinkedList<CityNode>();
	LinkedList<CityNode> lastweekcity = new LinkedList<CityNode>();
	LinkedList<CityNode> lastmonthcity = new LinkedList<CityNode>();
	LinkedList<CityNode> lastyearcity = new LinkedList<CityNode>();
	Calendar rightNow;

	public ShowNode(String nameofshow, Calendar time) {
		rightNow = time;
		showname = nameofshow;
		lasthour = new int[4];
		last2hours = new int[4];
		last12hours = new int[4];
		today = new int[4];
		lastweek = new int[4];
		lastmonth = new int[4];
		lastyear = new int[4];
	}

	public void add(String country, String region, String city, String quality, String time) {
		long filetime = Long.parseLong(time);
		if (rightNow.getTimeInMillis() - filetime < 1000L * 60L * 60L) {
			++lasthour[0];
			// if it has a location to give
			if (country != "N/A") {
				boolean newcountry = true;
				// Is it a new country? if so add it, if not, increment the
				// count by one
				ListIterator<CountryNode> lasthourcountryiterator = lasthourcountry.listIterator();
				while (lasthourcountryiterator.hasNext()) {
					CountryNode node = lasthourcountryiterator.next();
					if (node.country.equals(country)) {
						newcountry = false;
						node.increase();
						break;
					}
				}
				if (newcountry == true) {
					CountryNode newcountrynode = new CountryNode(country);
					lasthourcountry.add(newcountrynode);
				}
			}
			if (region != "N/A") {
				boolean newregion = true;
				// Same thing for region, this is the same as a State
				ListIterator<RegionNode> lasthourregioniterator = lasthourregion.listIterator();
				while (lasthourregioniterator.hasNext()) {
					RegionNode node = lasthourregioniterator.next();
					if (node.country.equals(country) && node.region.equals(region)) {
						newregion = false;
						node.increase();
						break;
					}
				}
				if (newregion == true) {
					RegionNode newregionnode = new RegionNode(region, country);
					lasthourregion.add(newregionnode);
				}
			}
			if (city != "N/A") {
				boolean newcity = true;
				ListIterator<CityNode> lasthourcityiterator = lasthourcity.listIterator();
				while (lasthourcityiterator.hasNext()) {
					CityNode node = lasthourcityiterator.next();
					if (node.country.equals(country) && node.region.equals(region) && node.city.equals(city)) {
						newcity = false;
						node.increase();
						break;
					}
				}
				if (newcity == true) {
					CityNode newcitynode = new CityNode(city, region, country);
					lasthourcity.add(newcitynode);
				}
			}
			if (quality.equals("256")) {
				++lasthour[3];
			}
			if (quality.equals("128")) {
				++lasthour[2];
			}
			if (quality.equals("56")) {
				++lasthour[1];
			}
		}
		if (rightNow.getTimeInMillis() - filetime < 1000L * 60L * 60L * 2L) {
			++last2hours[0];
			// if it has a location to give
			if (country != "N/A") {
				boolean newcountry = true;
				// Is it a new country? if so add it, if not, increment the
				// count by one
				ListIterator<CountryNode> last2hourscountryiterator = last2hourscountry.listIterator();
				while (last2hourscountryiterator.hasNext()) {
					CountryNode node = last2hourscountryiterator.next();
					if (node.country.equals(country)) {
						newcountry = false;
						node.increase();
						break;
					}
				}
				if (newcountry == true) {
					CountryNode newcountrynode = new CountryNode(country);
					last2hourscountry.add(newcountrynode);
				}
			}
			if (region != "N/A") {
				boolean newregion = true;
				// Same thing for region, this is the same as a State
				ListIterator<RegionNode> last2hoursregioniterator = last2hoursregion.listIterator();
				while (last2hoursregioniterator.hasNext()) {
					RegionNode node = last2hoursregioniterator.next();
					if (node.country.equals(country) && node.region.equals(region)) {
						newregion = false;
						node.increase();
						break;
					}
				}
				if (newregion == true) {
					RegionNode newregionnode = new RegionNode(region, country);
					last2hoursregion.add(newregionnode);
				}
			}
			if (city != "N/A") {
				boolean newcity = true;
				ListIterator<CityNode> last2hourscityiterator = last2hourscity.listIterator();
				while (last2hourscityiterator.hasNext()) {
					CityNode node = last2hourscityiterator.next();
					if (node.country.equals(country) && node.region.equals(region) && node.city.equals(city)) {
						newcity = false;
						node.increase();
						break;
					}
				}
				if (newcity == true) {
					CityNode newcitynode = new CityNode(city, region, country);
					last2hourscity.add(newcitynode);
				}
			}
			if (quality.equals("256")) {
				++last2hours[3];
			}
			if (quality.equals("128")) {
				++last2hours[2];
			}
			if (quality.equals("56")) {
				++last2hours[1];
			}
		}
		if (rightNow.getTimeInMillis() - filetime < 1000L * 60L * 60L * 12L) {
			++last12hours[0];
			// if it has a location to give
			if (country != "N/A") {
				boolean newcountry = true;
				// Is it a new country? if so add it, if not, increment the
				// count by one
				ListIterator<CountryNode> last12hourscountryiterator = last12hourscountry.listIterator();
				while (last12hourscountryiterator.hasNext()) {
					CountryNode node = last12hourscountryiterator.next();
					if (node.country.equals(country)) {
						newcountry = false;
						node.increase();
						break;
					}
				}
				if (newcountry == true) {
					CountryNode newcountrynode = new CountryNode(country);
					last12hourscountry.add(newcountrynode);
				}
			}
			if (region != "N/A") {
				boolean newregion = true;
				// Same thing for region, this is the same as a State
				ListIterator<RegionNode> last12hoursregioniterator = last12hoursregion.listIterator();
				while (last12hoursregioniterator.hasNext()) {
					RegionNode node = last12hoursregioniterator.next();
					if (node.country.equals(country) && node.region.equals(region)) {
						newregion = false;
						node.increase();
						break;
					}
				}
				if (newregion == true) {
					RegionNode newregionnode = new RegionNode(region, country);
					last12hoursregion.add(newregionnode);
				}
			}
			if (city != "N/A") {
				boolean newcity = true;
				ListIterator<CityNode> last12hourscityiterator = last12hourscity.listIterator();
				while (last12hourscityiterator.hasNext()) {
					CityNode node = last12hourscityiterator.next();
					if (node.country.equals(country) && node.region.equals(region) && node.city.equals(city)) {
						newcity = false;
						node.increase();
						break;
					}
				}
				if (newcity == true) {
					CityNode newcitynode = new CityNode(city, region, country);
					last12hourscity.add(newcitynode);
				}
			}
			if (quality.equals("256")) {
				++last12hours[3];
			}
			if (quality.equals("128")) {
				++last12hours[2];
			}
			if (quality.equals("56")) {
				++last12hours[1];
			}
		}
		if (rightNow.getTimeInMillis() - filetime < 1000L * 60L * 60L * 24L) {
			++today[0];
			// if it has a location to give
			if (country != "N/A") {
				boolean newcountry = true;
				// Is it a new country? if so add it, if not, increment the
				// count by one
				ListIterator<CountryNode> todaycountryiterator = todaycountry.listIterator();
				while (todaycountryiterator.hasNext()) {
					CountryNode node = todaycountryiterator.next();
					if (node.country.equals(country)) {
						newcountry = false;
						node.increase();
						break;
					}
				}
				if (newcountry == true) {
					CountryNode newcountrynode = new CountryNode(country);
					todaycountry.add(newcountrynode);
				}
			}
			if (region != "N/A") {
				boolean newregion = true;
				// Same thing for region, this is the same as a State
				ListIterator<RegionNode> todayregioniterator = todayregion.listIterator();
				while (todayregioniterator.hasNext()) {
					RegionNode node = todayregioniterator.next();
					if (node.country.equals(country) && node.region.equals(region)) {
						newregion = false;
						node.increase();
						break;
					}
				}
				if (newregion == true) {
					RegionNode newregionnode = new RegionNode(region, country);
					todayregion.add(newregionnode);
				}
			}
			if (city != "N/A") {
				boolean newcity = true;
				ListIterator<CityNode> todaycityiterator = todaycity.listIterator();
				while (todaycityiterator.hasNext()) {
					CityNode node = todaycityiterator.next();
					if (node.country.equals(country) && node.region.equals(region) && node.city.equals(city)) {
						newcity = false;
						node.increase();
						break;
					}
				}
				if (newcity == true) {
					CityNode newcitynode = new CityNode(city, region, country);
					todaycity.add(newcitynode);
				}
			}
			if (quality.equals("256")) {
				++today[3];
			}
			if (quality.equals("128")) {
				++today[2];
			}
			if (quality.equals("56")) {
				++today[1];
			}
		}
		if (rightNow.getTimeInMillis() - filetime < 1000L * 60L * 60L * 12L * 7L) {
			++lastweek[0];
			// if it has a location to give
			if (country != "N/A") {
				boolean newcountry = true;
				// Is it a new country? if so add it, if not, increment the
				// count by one
				ListIterator<CountryNode> lastweekcountryiterator = lastweekcountry.listIterator();
				while (lastweekcountryiterator.hasNext()) {
					CountryNode node = lastweekcountryiterator.next();
					if (node.country.equals(country)) {
						newcountry = false;
						node.increase();
						break;
					}
				}
				if (newcountry == true) {
					CountryNode newcountrynode = new CountryNode(country);
					lastweekcountry.add(newcountrynode);
				}
			}
			if (region != "N/A") {
				boolean newregion = true;
				// Same thing for region, this is the same as a State
				ListIterator<RegionNode> lastweekregioniterator = lastweekregion.listIterator();
				while (lastweekregioniterator.hasNext()) {
					RegionNode node = lastweekregioniterator.next();
					if (node.country.equals(country) && node.region.equals(region)) {
						newregion = false;
						node.increase();
						break;
					}
				}
				if (newregion == true) {
					RegionNode newregionnode = new RegionNode(region, country);
					lastweekregion.add(newregionnode);
				}
			}
			if (city != "N/A") {
				boolean newcity = true;
				ListIterator<CityNode> lastweekcityiterator = lastweekcity.listIterator();
				while (lastweekcityiterator.hasNext()) {
					CityNode node = lastweekcityiterator.next();
					if (node.country.equals(country) && node.region.equals(region) && node.city.equals(city)) {
						newcity = false;
						node.increase();
						break;
					}
				}
				if (newcity == true) {
					CityNode newcitynode = new CityNode(city, region, country);
					lastweekcity.add(newcitynode);
				}
			}
			if (quality.equals("256")) {
				++lastweek[3];
			}
			if (quality.equals("128")) {
				++lastweek[2];
			}
			if (quality.equals("56")) {
				++lastweek[1];
			}
		}
		if (rightNow.getTimeInMillis() - filetime < 1000L * 60L * 60L * 12L * 31L) {
			++lastmonth[0];
			// if it has a location to give
			if (country != "N/A") {
				boolean newcountry = true;
				// Is it a new country? if so add it, if not, increment the
				// count by one
				ListIterator<CountryNode> lastmonthcountryiterator = lastmonthcountry.listIterator();
				while (lastmonthcountryiterator.hasNext()) {
					CountryNode node = lastmonthcountryiterator.next();
					if (node.country.equals(country)) {
						newcountry = false;
						node.increase();
						break;
					}
				}
				if (newcountry == true) {
					CountryNode newcountrynode = new CountryNode(country);
					lastmonthcountry.add(newcountrynode);
				}
			}
			if (region != "N/A") {
				boolean newregion = true;
				// Same thing for region, this is the same as a State
				ListIterator<RegionNode> lastmonthregioniterator = lastmonthregion.listIterator();
				while (lastmonthregioniterator.hasNext()) {
					RegionNode node = lastmonthregioniterator.next();
					if (node.country.equals(country) && node.region.equals(region)) {
						newregion = false;
						node.increase();
						break;
					}
				}
				if (newregion == true) {
					RegionNode newregionnode = new RegionNode(region, country);
					lastmonthregion.add(newregionnode);
				}
			}
			if (city != "N/A") {
				boolean newcity = true;
				ListIterator<CityNode> lastmonthcityiterator = lastmonthcity.listIterator();
				while (lastmonthcityiterator.hasNext()) {
					CityNode node = lastmonthcityiterator.next();
					if (node.country.equals(country) && node.region.equals(region) && node.city.equals(city)) {
						newcity = false;
						node.increase();
						break;
					}
				}
				if (newcity == true) {
					CityNode newcitynode = new CityNode(city, region, country);
					lastmonthcity.add(newcitynode);
				}
			}
			if (quality.equals("256")) {
				++lastmonth[3];
			}
			if (quality.equals("128")) {
				++lastmonth[2];
			}
			if (quality.equals("56")) {
				++lastmonth[1];
			}
		}
		if (rightNow.getTimeInMillis() - filetime < 1000L * 60L * 60L * 12L * 365L) {
			++lastyear[0];
			// if it has a location to give
			if (country != "N/A") {
				boolean newcountry = true;
				// Is it a new country? if so add it, if not, increment the
				// count by one
				ListIterator<CountryNode> lastyearcountryiterator = lastyearcountry.listIterator();
				while (lastyearcountryiterator.hasNext()) {
					CountryNode node = lastyearcountryiterator.next();
					if (node.country.equals(country)) {
						newcountry = false;
						node.increase();
						break;
					}
				}
				if (newcountry == true) {
					CountryNode newcountrynode = new CountryNode(country);
					lastyearcountry.add(newcountrynode);
				}
			}
			if (region != "N/A") {
				boolean newregion = true;
				// Same thing for region, this is the same as a State
				ListIterator<RegionNode> lastyearregioniterator = lastyearregion.listIterator();
				while (lastyearregioniterator.hasNext()) {
					RegionNode node = lastyearregioniterator.next();
					if (node.country.equals(country) && node.region.equals(region)) {
						newregion = false;
						node.increase();
						break;
					}
				}
				if (newregion == true) {
					RegionNode newregionnode = new RegionNode(region, country);
					lastyearregion.add(newregionnode);
				}
			}
			if (city != "N/A") {
				boolean newcity = true;
				ListIterator<CityNode> lastyearcityiterator = lastyearcity.listIterator();
				while (lastyearcityiterator.hasNext()) {
					CityNode node = lastyearcityiterator.next();
					if (node.country.equals(country) && node.region.equals(region) && node.city.equals(city)) {
						newcity = false;
						node.increase();
						break;
					}
				}
				if (newcity == true) {
					CityNode newcitynode = new CityNode(city, region, country);
					lastyearcity.add(newcitynode);
				}
			}
			if (quality.equals("256")) {
				++lastyear[3];
			}
			if (quality.equals("128")) {
				++lastyear[2];
			}
			if (quality.equals("56")) {
				++lastyear[1];
			}
		}
	}

	public long getTime(String s) {
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
		creationDate.set(yearint, monthint, dayint, hourint, minint, secint);
		return creationDate.getTimeInMillis();

	}

	public void createpage() throws FileNotFoundException {
		// Sort the Linked Lists by number of hits, decreasing
		for (int i = 0; i < lasthourcountry.size(); i++) {
			ListIterator<CountryNode> iterator = lasthourcountry.listIterator();
			int largest = lasthourcountry.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < lasthourcountry.size() - i; i2++) {
				CountryNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			CountryNode node = lasthourcountry.get(place);
			lasthourcountry.remove(place);
			lasthourcountry.addLast(node);
		}
		for (int i = 0; i < last2hourscountry.size(); i++) {
			ListIterator<CountryNode> iterator = last2hourscountry.listIterator();
			int largest = last2hourscountry.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < last2hourscountry.size() - i; i2++) {
				CountryNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			CountryNode node = last2hourscountry.get(place);
			last2hourscountry.remove(place);
			last2hourscountry.addLast(node);
		}
		for (int i = 0; i < last12hourscountry.size(); i++) {
			ListIterator<CountryNode> iterator = last12hourscountry.listIterator();
			int largest = last12hourscountry.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < last12hourscountry.size() - i; i2++) {
				CountryNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			CountryNode node = last12hourscountry.get(place);
			last12hourscountry.remove(place);
			last12hourscountry.addLast(node);
		}
		for (int i = 0; i < todaycountry.size(); i++) {
			ListIterator<CountryNode> iterator = todaycountry.listIterator();
			int largest = todaycountry.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < todaycountry.size() - i; i2++) {
				CountryNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			CountryNode node = todaycountry.get(place);
			todaycountry.remove(place);
			todaycountry.addLast(node);
		}
		for (int i = 0; i < lastweekcountry.size(); i++) {
			ListIterator<CountryNode> iterator = lastweekcountry.listIterator();
			int largest = lastweekcountry.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < lastweekcountry.size() - i; i2++) {
				CountryNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			CountryNode node = lastweekcountry.get(place);
			lastweekcountry.remove(place);
			lastweekcountry.addLast(node);
		}
		for (int i = 0; i < lastmonthcountry.size(); i++) {
			ListIterator<CountryNode> iterator = lastmonthcountry.listIterator();
			int largest = lastmonthcountry.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < lastmonthcountry.size() - i; i2++) {
				CountryNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			CountryNode node = lastmonthcountry.get(place);
			lastmonthcountry.remove(place);
			lastmonthcountry.addLast(node);
		}
		for (int i = 0; i < lastyearcountry.size(); i++) {
			ListIterator<CountryNode> iterator = lastyearcountry.listIterator();
			int largest = lastyearcountry.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < lastyearcountry.size() - i; i2++) {
				CountryNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			CountryNode node = lastyearcountry.get(place);
			lastyearcountry.remove(place);
			lastyearcountry.addLast(node);
		}
		for (int i = 0; i < lasthourregion.size(); i++) {
			ListIterator<RegionNode> iterator = lasthourregion.listIterator();
			int largest = lasthourregion.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < lasthourregion.size() - i; i2++) {
				RegionNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			RegionNode node = lasthourregion.get(place);
			lasthourregion.remove(place);
			lasthourregion.addLast(node);
		}
		for (int i = 0; i < last2hoursregion.size(); i++) {
			ListIterator<RegionNode> iterator = last2hoursregion.listIterator();
			int largest = last2hoursregion.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < last2hoursregion.size() - i; i2++) {
				RegionNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			RegionNode node = last2hoursregion.get(place);
			last2hoursregion.remove(place);
			last2hoursregion.addLast(node);
		}
		for (int i = 0; i < last12hoursregion.size(); i++) {
			ListIterator<RegionNode> iterator = last12hoursregion.listIterator();
			int largest = last12hoursregion.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < last12hoursregion.size() - i; i2++) {
				RegionNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			RegionNode node = last12hoursregion.get(place);
			last12hoursregion.remove(place);
			last12hoursregion.addLast(node);
		}
		for (int i = 0; i < todayregion.size(); i++) {
			ListIterator<RegionNode> iterator = todayregion.listIterator();
			int largest = todayregion.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < todayregion.size() - i; i2++) {
				RegionNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			RegionNode node = todayregion.get(place);
			todayregion.remove(place);
			todayregion.addLast(node);
		}
		for (int i = 0; i < lastweekregion.size(); i++) {
			ListIterator<RegionNode> iterator = lastweekregion.listIterator();
			int largest = lastweekregion.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < lastweekregion.size() - i; i2++) {
				RegionNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			RegionNode node = lastweekregion.get(place);
			lastweekregion.remove(place);
			lastweekregion.addLast(node);
		}
		for (int i = 0; i < lastmonthregion.size(); i++) {
			ListIterator<RegionNode> iterator = lastmonthregion.listIterator();
			int largest = lastmonthregion.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < lastmonthregion.size() - i; i2++) {
				RegionNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			RegionNode node = lastmonthregion.get(place);
			lastmonthregion.remove(place);
			lastmonthregion.addLast(node);
		}
		for (int i = 0; i < lastyearregion.size(); i++) {
			ListIterator<RegionNode> iterator = lastyearregion.listIterator();
			int largest = lastyearregion.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < lastyearregion.size() - i; i2++) {
				RegionNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			RegionNode node = lastyearregion.get(place);
			lastyearregion.remove(place);
			lastyearregion.addLast(node);
		}
		for (int i = 0; i < lasthourcity.size(); i++) {
			ListIterator<CityNode> iterator = lasthourcity.listIterator();
			int largest = lasthourcity.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < lasthourcity.size() - i; i2++) {
				CityNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			CityNode node = lasthourcity.get(place);
			lasthourcity.remove(place);
			lasthourcity.addLast(node);
		}
		for (int i = 0; i < last2hourscity.size(); i++) {
			ListIterator<CityNode> iterator = last2hourscity.listIterator();
			int largest = last2hourscity.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < last2hourscity.size() - i; i2++) {
				CityNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			CityNode node = last2hourscity.get(place);
			last2hourscity.remove(place);
			last2hourscity.addLast(node);
		}
		for (int i = 0; i < last12hourscity.size(); i++) {
			ListIterator<CityNode> iterator = last12hourscity.listIterator();
			int largest = last12hourscity.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < last12hourscity.size() - i; i2++) {
				CityNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			CityNode node = last12hourscity.get(place);
			last12hourscity.remove(place);
			last12hourscity.addLast(node);
		}
		for (int i = 0; i < todaycity.size(); i++) {
			ListIterator<CityNode> iterator = todaycity.listIterator();
			int largest = todaycity.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < todaycity.size() - i; i2++) {
				CityNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			CityNode node = todaycity.get(place);
			todaycity.remove(place);
			todaycity.addLast(node);
		}
		for (int i = 0; i < lastweekcity.size(); i++) {
			ListIterator<CityNode> iterator = lastweekcity.listIterator();
			int largest = lastweekcity.getFirst().count;
			int place = 0;
			for (int i2 = i; i2 < lastweekcity.size(); i2++) {
				CityNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			CityNode node = lastweekcity.get(place);
			lastweekcity.remove(place);
			lastweekcity.addLast(node);
		}
		for (int i = 0; i < lastmonthcity.size(); i++) {
			ListIterator<CityNode> iterator = lastmonthcity.listIterator();
			int largest = lastmonthcity.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < lastmonthcity.size() - i; i2++) {
				CityNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			CityNode node = lastmonthcity.get(place);
			lastmonthcity.remove(place);
			lastmonthcity.addLast(node);
		}
		for (int i = 0; i < lastyearcity.size(); i++) {
			ListIterator<CityNode> iterator = lastyearcity.listIterator();
			int largest = lastyearcity.getFirst().count;
			int place = 0;
			for (int i2 = 0; i2 < lastyearcity.size() - i; i2++) {
				CityNode node = iterator.next();
				if (node.sorted == false) {
					if (node.count > largest) {
						largest = node.count;
						place = i2;
					}
				}
			}
			CityNode node = lastyearcity.get(place);
			lastyearcity.remove(place);
			lastyearcity.addLast(node);
		}
		StringBuilder name = new StringBuilder();
		name.append("C:\\inetpub\\archivesite\\analytics\\");
		name.append(showname);
		name.append(".html");
		PrintWriter p = new PrintWriter(name.toString());
		p.print("<!doctype html>\n<html>\n<head>\n<meta charset=\"UTF-8\">\n");
		p.println("<title>" + showname + " Archive Statistics</title>");
		p.print("<meta http-equiv=\"refresh\" content=\"30\">\n</head>\n\n<body>\n<a name=\"top\"><p><center> <img src=\"brand.png\" width=\"297\" height=\"77\" alt=\"\"/></center></p></a> \n<p><center>\n");
		p.println("<h1>" + showname + " Statistics</h1>");
		p.println("<p><em>Last Updated: " + rightNow.getTime() + "</em></p>");
		p.print("</center>\n<p>&nbsp;</p>\n<h2>Jump To:<br>\n</h2>\n<ul>\n");
		p.println("  <li><a href=\""+showname+".html#lasthour\">Last Hour</a></li>");
		p.println("  <li><a href=\""+showname+".html#last2hours\">Last 2 Hours</a></li>");
		p.println("  <li><a href=\""+showname+".html#last12hour\">Last 12 Hours</a></li>");
		p.println("  <li><a href=\""+showname+".html#today\">Last 24 Hours</a></li>");
		p.println("  <li><a href=\""+showname+".html#lastweek\">Last Week (7 Days)</a></li>");
		p.println("  <li><a href=\""+showname+".html#lastmonth\">Last Month (31 Days)</a></li>");
		p.println("  <li><a href=\""+showname+".html#lastyear\">Last Year (365 Days)</a></li>\n</ul>");
		p.print("<h3><a name=\"lasthour\">Last Hour</a></h3>\n");
		p.println("<p>" + lasthour[0] + " listeners</p>");
		p.print("<p>Quality</p>\n<table width=\"10%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>56k</td>\n");
		p.println("      <td>" + lasthour[1] + "</td>");
		p.print("    </tr>\n    <tr>\n      <td>128k</td>\n");
		p.println("      <td>" + lasthour[2] + "</td>");
		p.print("    </tr>\n    <tr>\n      <td>256k</td>\n");
		p.println("      <td>" + lasthour[3] + "</td>");
		p.print("    </tr>\n  </tbody>\n</table>\n<p>Countries</p>\n<table width=\"20%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<CountryNode> lasthourcountryiterator = lasthourcountry.listIterator();
		while (lasthourcountryiterator.hasNext()) {
			CountryNode node = lasthourcountryiterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.country + "&nbsp</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n<p>Regions (States)</p>\n<table width=\"30%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>Region</td>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<RegionNode> lasthourregioniterator = lasthourregion.listIterator();
		while (lasthourregioniterator.hasNext()) {
			RegionNode node = lasthourregioniterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.region + "</td>");
			p.println("      <td>" + node.country + "</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n<p>Cities</p>\n<table width=\"40%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>City</td>\n      <td>Region</td>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<CityNode> lasthourcityiterator = lasthourcity.listIterator();
		while (lasthourcityiterator.hasNext()) {
			CityNode node = lasthourcityiterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.city + "</td>");
			p.println("      <td>" + node.region + "</td>");
			p.println("      <td>" + node.country + "</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n");
		p.println("<p><a href=\""+showname+".html#top\"><em>Go To Top</em></a></p>");
		p.print("<h3><a name=\"last2hours\">Last 2 Hours</a></h3>\n");
		p.println("<p>" + last2hours[0] + " listeners</p>");
		p.print("<p>Quality</p>\n<table width=\"10%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>56k</td>\n");
		p.println("      <td>" + last2hours[1] + "</td>");
		p.print("    </tr>\n    <tr>\n      <td>128k</td>\n");
		p.println("      <td>" + last2hours[2] + "</td>");
		p.print("    </tr>\n    <tr>\n      <td>256k</td>\n");
		p.println("      <td>" + last2hours[3] + "</td>");
		p.print("    </tr>\n  </tbody>\n</table>\n<p>Countries</p>\n<table width=\"20%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<CountryNode> last2hourscountryiterator = last2hourscountry.listIterator();
		while (last2hourscountryiterator.hasNext()) {
			CountryNode node = last2hourscountryiterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.country + "&nbsp</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n<p>Regions (States)</p>\n<table width=\"30%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>Region</td>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<RegionNode> last2hoursregioniterator = last2hoursregion.listIterator();
		while (last2hoursregioniterator.hasNext()) {
			RegionNode node = last2hoursregioniterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.region + "</td>");
			p.println("      <td>" + node.country + "</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n<p>Cities</p>\n<table width=\"40%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>City</td>\n      <td>Region</td>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<CityNode> last2hourscityiterator = last2hourscity.listIterator();
		while (last2hourscityiterator.hasNext()) {
			CityNode node = last2hourscityiterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.city + "</td>");
			p.println("      <td>" + node.region + "</td>");
			p.println("      <td>" + node.country + "</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n");
		p.println("<p><a href=\""+showname+".html#top\"><em>Go To Top</em></a></p>");
		p.print("<h3><a name=\"last12hours\">Last 12 Hours</a></h3>\n");
		p.println("<p>" + last12hours[0] + " listeners</p>");
		p.print("<p>Quality</p>\n<table width=\"10%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>56k</td>\n");
		p.println("      <td>" + last12hours[1] + "</td>");
		p.print("    </tr>\n    <tr>\n      <td>128k</td>\n");
		p.println("      <td>" + last12hours[2] + "</td>");
		p.print("    </tr>\n    <tr>\n      <td>256k</td>\n");
		p.println("      <td>" + last12hours[3] + "</td>");
		p.print("    </tr>\n  </tbody>\n</table>\n<p>Countries</p>\n<table width=\"20%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<CountryNode> last12hourscountryiterator = last12hourscountry.listIterator();
		while (last12hourscountryiterator.hasNext()) {
			CountryNode node = last12hourscountryiterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.country + "&nbsp</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n<p>Regions (States)</p>\n<table width=\"30%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>Region</td>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<RegionNode> last12hoursregioniterator = last12hoursregion.listIterator();
		while (last12hoursregioniterator.hasNext()) {
			RegionNode node = last12hoursregioniterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.region + "</td>");
			p.println("      <td>" + node.country + "</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n<p>Cities</p>\n<table width=\"40%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>City</td>\n      <td>Region</td>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<CityNode> last12hourscityiterator = last12hourscity.listIterator();
		while (last12hourscityiterator.hasNext()) {
			CityNode node = last12hourscityiterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.city + "</td>");
			p.println("      <td>" + node.region + "</td>");
			p.println("      <td>" + node.country + "</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n");
		p.println("<p><a href=\""+showname+".html#top\"><em>Go To Top</em></a></p>");
		p.print("<h3><a name=\"today\">Last 24 Hours</a></h3>\n");
		p.println("<p>" + today[0] + " listeners</p>");
		p.print("<p>Quality</p>\n<table width=\"10%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>56k</td>\n");
		p.println("      <td>" + today[1] + "</td>");
		p.print("    </tr>\n    <tr>\n      <td>128k</td>\n");
		p.println("      <td>" + today[2] + "</td>");
		p.print("    </tr>\n    <tr>\n      <td>256k</td>\n");
		p.println("      <td>" + today[3] + "</td>");
		p.print("    </tr>\n  </tbody>\n</table>\n<p>Countries</p>\n<table width=\"20%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<CountryNode> todaycountryiterator = todaycountry.listIterator();
		while (todaycountryiterator.hasNext()) {
			CountryNode node = todaycountryiterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.country + "&nbsp</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n<p>Regions (States)</p>\n<table width=\"30%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>Region</td>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<RegionNode> todayregioniterator = todayregion.listIterator();
		while (todayregioniterator.hasNext()) {
			RegionNode node = todayregioniterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.region + "</td>");
			p.println("      <td>" + node.country + "</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n<p>Cities</p>\n<table width=\"40%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>City</td>\n      <td>Region</td>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<CityNode> todaycityiterator = todaycity.listIterator();
		while (todaycityiterator.hasNext()) {
			CityNode node = todaycityiterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.city + "</td>");
			p.println("      <td>" + node.region + "</td>");
			p.println("      <td>" + node.country + "</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n");
		p.println("<p><a href=\""+showname+".html#top\"><em>Go To Top</em></a></p>");
		p.print("<h3><a name=\"lastweek\">Last Week</a></h3>\n");
		p.println("<p>" + lastweek[0] + " listeners</p>");
		p.print("<p>Quality</p>\n<table width=\"10%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>56k</td>\n");
		p.println("      <td>" + lastweek[1] + "</td>");
		p.print("    </tr>\n    <tr>\n      <td>128k</td>\n");
		p.println("      <td>" + lastweek[2] + "</td>");
		p.print("    </tr>\n    <tr>\n      <td>256k</td>\n");
		p.println("      <td>" + lastweek[3] + "</td>");
		p.print("    </tr>\n  </tbody>\n</table>\n<p>Countries</p>\n<table width=\"20%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<CountryNode> lastweekcountryiterator = lastweekcountry.listIterator();
		while (lastweekcountryiterator.hasNext()) {
			CountryNode node = lastweekcountryiterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.country + "&nbsp</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n<p>Regions (States)</p>\n<table width=\"30%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>Region</td>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<RegionNode> lastweekregioniterator = lastweekregion.listIterator();
		while (lastweekregioniterator.hasNext()) {
			RegionNode node = lastweekregioniterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.region + "</td>");
			p.println("      <td>" + node.country + "</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n<p>Cities</p>\n<table width=\"40%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>City</td>\n      <td>Region</td>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<CityNode> lastweekcityiterator = lastweekcity.listIterator();
		while (lastweekcityiterator.hasNext()) {
			CityNode node = lastweekcityiterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.city + "</td>");
			p.println("      <td>" + node.region + "</td>");
			p.println("      <td>" + node.country + "</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n");
		p.println("<p><a href=\""+showname+".html#top\"><em>Go To Top</em></a></p>");
		p.print("<h3><a name=\"lastmonth\">Last Month</a></h3>\n");
		p.println("<p>" + lastmonth[0] + " listeners</p>");
		p.print("<p>Quality</p>\n<table width=\"10%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>56k</td>\n");
		p.println("      <td>" + lastmonth[1] + "</td>");
		p.print("    </tr>\n    <tr>\n      <td>128k</td>\n");
		p.println("      <td>" + lastmonth[2] + "</td>");
		p.print("    </tr>\n    <tr>\n      <td>256k</td>\n");
		p.println("      <td>" + lastmonth[3] + "</td>");
		p.print("    </tr>\n  </tbody>\n</table>\n<p>Countries</p>\n<table width=\"20%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<CountryNode> lastmonthcountryiterator = lastmonthcountry.listIterator();
		while (lastmonthcountryiterator.hasNext()) {
			CountryNode node = lastmonthcountryiterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.country + "&nbsp</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n<p>Regions (States)</p>\n<table width=\"30%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>Region</td>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<RegionNode> lastmonthregioniterator = lastmonthregion.listIterator();
		while (lastmonthregioniterator.hasNext()) {
			RegionNode node = lastmonthregioniterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.region + "</td>");
			p.println("      <td>" + node.country + "</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n<p>Cities</p>\n<table width=\"40%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>City</td>\n      <td>Region</td>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<CityNode> lastmonthcityiterator = lastmonthcity.listIterator();
		while (lastmonthcityiterator.hasNext()) {
			CityNode node = lastmonthcityiterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.city + "</td>");
			p.println("      <td>" + node.region + "</td>");
			p.println("      <td>" + node.country + "</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n");
		p.println("<p><a href=\""+showname+".html#top\"><em>Go To Top</em></a></p>");
		p.print("<h3><a name=\"lastyear\">Last Year</a></h3>\n");
		p.println("<p>" + lastyear[0] + " listeners</p>");
		p.print("<p>Quality</p>\n<table width=\"10%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>56k</td>\n");
		p.println("      <td>" + lastyear[1] + "</td>");
		p.print("    </tr>\n    <tr>\n      <td>128k</td>\n");
		p.println("      <td>" + lastyear[2] + "</td>");
		p.print("    </tr>\n    <tr>\n      <td>256k</td>\n");
		p.println("      <td>" + lastyear[3] + "</td>");
		p.print("    </tr>\n  </tbody>\n</table>\n<p>Countries</p>\n<table width=\"20%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<CountryNode> lastyearcountryiterator = lastyearcountry.listIterator();
		while (lastyearcountryiterator.hasNext()) {
			CountryNode node = lastyearcountryiterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.country + "&nbsp</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n<p>Regions (States)</p>\n<table width=\"30%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>Region</td>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<RegionNode> lastyearregioniterator = lastyearregion.listIterator();
		while (lastyearregioniterator.hasNext()) {
			RegionNode node = lastyearregioniterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.region + "</td>");
			p.println("      <td>" + node.country + "</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n<p>Cities</p>\n<table width=\"40%\" border=\"1\">\n  <tbody>\n    <tr>\n      <td>City</td>\n      <td>Region</td>\n      <td>Country</td>\n      <td>Hits</td>\n    </tr>\n");
		ListIterator<CityNode> lastyearcityiterator = lastyearcity.listIterator();
		while (lastyearcityiterator.hasNext()) {
			CityNode node = lastyearcityiterator.next();
			p.println("    <tr>");
			p.println("      <td>" + node.city + "</td>");
			p.println("      <td>" + node.region + "</td>");
			p.println("      <td>" + node.country + "</td>");
			p.println("      <td>" + node.count + "</td>");
			p.println("    </tr>");
		}
		p.print("  </tbody>\n</table>\n");
		p.println("<p><a href=\""+showname+".html#top\"><em>Go To Top</em></a></p>");
		p.println(
				"<p><em><font color=\"#4F4F4F\">Created by Isaac Nicholas <a href=\"mailto:idn2@case.edu\">idn2@case.edu</a></font></em></p>");
		p.println("<p><em><font color=\"#4F4F4F\">Updated " + rightNow.getTime() + "</font></em></p>");
		p.print("<p><em><font color=\"#4F4F4F\">Modified 2016/Apr/24</font></em></p>\n</body>\n</html>");
		p.close();
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
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		return serverLocation;
	}
}
