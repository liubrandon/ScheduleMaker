CSC490 Spring 2016 Final Project: WPEA Scheduling - README
by Brandon Liu and Geyang Qin

This program is useful for generating a draft schedule to expedite the termly process of scheduling shows done by the WPEA board. The software reads from the previous term's schedule (.xls) and the current term's application data exported from the SurveyMonkey application data, and schedules as many shows as it can into a new .xls file, given the availability of each applicant. After the schedule output is generated, a list of all of the shows unable to be scheduled is printed to the console, which aids the WPEA board in finishing the final scheduling process.

To use the program:
1. Navigate to the unzipped file directory in the command line using "cd"
2. Compile all necessary .java files using the following command (reference to the jxl.jar API): "javac -classpath jxl.jar:. filename.java"
3. Run the program with the following command: "java -classpath jxl.jar:. Write"
	a. The scheduling software will now run, and the new schedule output file will be saved in the directory as "generated_WPEA_schedule.xls"
	b. The list of shows unabled to be fit into schedule with their corresponding times will be printed to the console
4. The schedule can then be saved as a PDF file for ease of access by following the instructions for printing (save as a PDF instead of print) at this site: https://support.office.com/en-us/article/Make-a-worksheet-fit-the-printed-page-34A91EB5-8B4E-4A8A-AB28-B6492012EAAE

Lots of love,
Brandon and Geyang