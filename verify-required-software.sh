#!/usr/bin/env bash
echo "Checking Java 8 is installed "

if type java | grep -q 'java' ; then
    echo Found java executable in PATH
    _java=java


elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    echo Found java executable in JAVA_HOME
    _java="$JAVA_HOME/bin/java"

else
    echo "*********************VALIDATION ERROR - Java is not installed. Please install JAVA 1.8 or upper version******************"
    exit 1
fi

if [ ! -z "$_java" ]; then

    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo version "$version"
    if [ "$version" > "1.8" ]; then
        echo Java version 1.8 or higer is installed...

    else
        echo Java version is less than 1.8
        echo "***********************VALIDATION ERROR...Please install JAVA 1.8 or upper version********************"
        exit 1
    fi
else
    exit 1
fi

echo "Checking MAVEN installed "


if type mvn | grep -q 'mvn' ; then
    echo Found MAVEN  in PATH
    _maven=mvn


elif [[ -n "$MAVEN_HOME" ]] && [[ -x "$MAVEN_HOME/bin/mvn" ]];  then
    echo Found Maven executable in MAVEN_HOME
    _maven="$MAVEN_HOME/bin/mvn"

else
    echo "*********************VALIDATION ERROR - MAVEN is not installed. Please install MAVEN  version******************"
    exit 1
fi

if [ ! -z "_maven" ]; then

    version=$("$_maven" -version 2>&1 )
    echo version "$version"
    if [ "$version" > "3.0" ]; then
        echo Maven version 3.x higher version is installed...

    else
        echo Maven version is less than 3.0
        echo "***********************VALIDATION ERROR...Please install MAVEN 3.x upper version********************"
        exit 1
    fi
else
    exit 1
fi