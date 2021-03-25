for((i=1;;i=i+1))
do
    output=`java -jar createZips.jar` # 分割视频文件
    output=`chmod 777 createZips.sh`
    output=`./createZips.sh`
    output=`rm -rf createZips.sh`
    echo splitZipEnd-${i}
    output=`bypy -d --include-regex ".+\.z(\d+|ip)" syncup`
    echo uploadEnd-${i}
    output=`bypy compare > compare.txt`
    echo compareEnd-${i}
    output=`java -jar deleteLocalSameFile.jar`
    echo deleteLocalSameFileEnd-${i}
    output=`rm -rf compare.txt`
    echo rmCompareEnd-${i}
done
exit 0
