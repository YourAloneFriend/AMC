set CLASSPATH=%CLASSPATH%;..\..\JChart.jar
md bin
javac -d bin src/*.java
cd bin
java -cp ./;..\..\..\JChart.jar RadarChartDemo
cd..
pause..