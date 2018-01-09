# CED2AR data file to DDI 2.5 Generator

This project contains java classes that will allow you to read several versions of **Stata** *or* **SPSS** data sets and generate out DDI 2.5 xml files.

This maven project generates out *two* .jar files.  One is used by developers and the other by end users.  The jar files are:
* **ced2arddigenerator.jar** (*Developers*) The *normal jar* file that you can include in other projects.  This jar depends on: [ced2ar-stata-reader](https://github.com/ncrncornell/ced2ar-stata-reader) and [ced2arspssreader](https://github.com/ncrncornell/ced2arspssreader).  (This is the maven project artifact.)  

* **ced2arddigenerator-jar-with-dependencies.jar** (*End Users*) The *runnable jar* file you can use on a command line prompt.


### Build

*For Developers:* 
1. Clone the github repository to your machine.
2. Go to the root directory of the cloned repository.
3. Use maven 2 to build the project. On the command line, enter the following command

   ```mvn clean install```  


### Usage 
*For Developers:* 


The best way to use this code is to include the jar file in an existing project, such as [ced2ardata2ddi](https://github.com/ncrncornell/ced2ardata2ddi) 
The following code is in: ced2ardata2ddi's DataFileRestController.java file
```
  if (file.getOriginalFilename().toLowerCase().endsWith(".dta")) {
    StataCsvGenerator gen = new StataCsvGenerator();
    variablesCSV = gen.generateVariablesCsv(fileLocation,summaryStats, recordLimit);
  } else if (file.getOriginalFilename().toLowerCase().endsWith(".sav")) {
    SpssCsvGenerator gen = new SpssCsvGenerator();
    variablesCSV = gen.generateVariablesCsv(fileLocation,summaryStats, recordLimit);
  }
```

*For End Users:* 
1. Download ced2arddigenerator-jar-with-dependencies.jar
2. See Run Instructions in next section.


### Run Instructions
Run from a terminal:

`java -jar ced2arddigenerator-jar-with-dependencies.jar  -f <filename>  [ -s <sumstats>  | -l <obsLimit> ]`

usage: Options are as follows...
```
 -f <arg>   (required) data file name and extension.
 -l <arg>   (optional) limit number of observations to process.   Default: Process all observations
 -s <arg>   (optional) generate summary statistics.  Values: TRUE|FALSE   Default: TRUE
```

**Example**

`java -jar ced2arddigenerator-jar-with-dependencies.jar  -f dataset.dta -s TRUE -l 1000`


This run example generates the following files:
* One DDI xml file.  dataset.dta.xml
* Two csv files:
  * dataset.dta.vars.csv
  * dataset.dta_var_values.csv
* One log file.  ced2arstatareader.log

---
Version: 1.2.0 1/9/18 Required: JDK 8.0
