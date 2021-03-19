bypy -d --include-regex ".+\.z(\d+|ip)" syncup
bypy compare > compare.txt
java -jar deleteLocalSameFile.jar
rm -rf compare.txt
