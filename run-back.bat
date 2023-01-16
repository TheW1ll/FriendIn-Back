cd hsqldb
start cmd /c java -cp hsqldb.jar org.hsqldb.Server
cd ..
start cmd /c gradlew.bat "-Dorg.gradle.java.home=\home\junkyards\.jdks\corretto-17.0.5" "bootRun"
echo Press any key to exit . . .
pause>nul