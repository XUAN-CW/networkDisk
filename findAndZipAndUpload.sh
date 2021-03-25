for ((i=1; i<=14400; ))
do
    # 找到一个符合要求的文件
    shouldBeUploaded=$(find . -type f -size +90M -size -20G 2>/dev/null|sed 's/\(.*\)/\"\1\"/'| xargs du --exclude="." -k 2>/dev/null| grep -v '!qB$'| sort -n | sed -n 1p | awk 'sub($1,"")'|awk '$1=$1'
)
    echo "currentFile: ${shouldBeUploaded}"
    empty=""
    if [[ ${empty} = ${shouldBeUploaded} ]] ; then
        echo "empty! sleep 60s"
        sleep 60
        continue
    fi
    # 获取文件所在路径
    # 设置压缩文件
    zipTmp="${shouldBeUploaded%.*}-$(date +%s).zip"
    zipFile="${shouldBeUploaded%.*}.zip"
    #加密压缩
    zip -P Xuan19981224 "${zipTmp}" "${shouldBeUploaded}" -m
    #分卷
    zip -s 18m "${zipTmp}" --out "${zipFile}"
    #删除临时文件
    rm -rf "${zipTmp}"
    #上传 zip 文件，.zip 最后上传 
    bypy -v --include-regex ".+\.z\d+" syncup
    bypy -v --include-regex ".+\.zip" syncup
    echo "$complete! ${i}th file ${shouldBeUploaded} is uploaded! sleep 60s"
    ((i++))
    sleep 60
done