# WRUW-analytics

A program I wrote to read an IIS and Icecast (the streaming service we use) log, scan the IP addresses to find their location, and displays it.

For the IIS logs, it breaks into files (archived shows), and then creates a webpage for each file that is being downloaded.

It breaks the listeners by the hour, 2 hours, day, week, month, and year and then in each one groups by country, then state, then city.
