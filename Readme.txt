RecordsValidator

RecordsValidator is a Java based application to Validate the CSV and xml Records. After Validation it will
generate the Report for invalid Records.

Installation
It is a java project and need JDK 1.8

USE:

The application takes the arguments from Command Line.

Example :

Java Main ./test_files/records.csv

** Two sample input files are included for sample run.
./test_files/records.csv
./test_files/records.xml

Currently Application only support xml and csv input file .

Validations : Currently validaton is written using if and else but Predicate can be used in future .


Report : Report will be in the output_files folder with the name of  report.txt


Test :
Test Case is written using Junit .




