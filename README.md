# LogFileAnalyzer
A LogFile analyzer for more or less standard Ruby on Rails Unicorn logs using java language

The GUI Application is in File UnicornAnalyzer
The tests class contains the file reader and analyzer.

About the application :

When choosing Add LogFile you can add a file which will be read and then printed in the right Pane. Therefor, all analyzes are down and you just have to select in which order you want to print them on the Sort By menu.
The button Show Report will then print the report in the left pane.
You can save the report with the save report fonction (in file menu). 
NB: the report save will have no specific order and is on JSON format.

It is also possible to add an old report in JSON format. The logfiles analyzed will be add to this report that you can print and save as before.

There is also a parameters menu that allows you to add a time delimitation to the report. The logFile added after the time definition will only take care of requests between the two time laps.
