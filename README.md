############## INSTRUCTIONS TO EXECUTE PROGRAM #####################################
Project Description - 
The program is written using Java 8 with Spring 5.x framework. Pls. check pom.xml for jar dependencies. 

Software Required :

    JAVA 1.8+ 
    MAVEN 3.x + 
    
The following CSV files were copied in under resources folder 
    plans.csv
    zips.csv
    slcsp.csv
    
Execute the following scripts to compile, build, install and start the project

    1. verify-required-software.sh => verfies the dependencies software and alerts to  install before compile the project.
    2. slcsp-build.sh     => builds the current project using maven commands
    3. slcsp-compile.sh   => compiles the current project using maven commands
    4. slcsp-install.sh   => install all dependencies using maven commands
    5. slcsp-start.sh     => does all build, compile, install dependencies and start the program. 
    
OUTPUT - Displays output on console in csv format  

SAMPLE CONSOLE OUTPUT 
************************Calculating rate for Second Lowest Cost Silver plan ***************************
zipcode,rate
64148,192.84
67118,157.74
40813,
18229,181.39
51012,199.48
79168,190.66
54923,175.42
67651,175.41
49448,164.67
************** Program Execution COMPLETED ************************

