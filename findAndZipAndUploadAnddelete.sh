for ((i=1; i<=14400; ))
do
    # 找到一个符合要求的文件
    shouldBeUploaded=$(find . -type f -size +90M -size -20G 2>/dev/null| xargs du --exclude="." -k 2>/dev/null| grep -v '!qB$'| sort -n | sed -n 1p | awk '{print $2}')
    echo "currentFile: ${shouldBeUploaded}"
    empty=""
    if [[ ${empty} = ${shouldBeUploaded} ]] ; then
        echo "empty! sleep 60s"
        sleep 60
        continue
    fi
    # 获取文件所在路径
    dir=$(dirname "${shouldBeUploaded}")
    # 设置压缩文件
    zipTmp=${dir}/zipTmp$(date +%s)${RANDOM}.zip
    zipFile=${dir}/zipFile$(date +%s)${RANDOM}.zip
    #加密压缩
    zip -P Xuan19981224 "${zipTmp}" "${shouldBeUploaded}" -m
    #分卷
    zip -s 18m "${zipTmp}" --out "${zipFile}"
    #删除临时文件
    rm -rf "${zipTmp}"
    ############################################################################################
    #上传 zip 文件，.zip 最后上传 
    bypy -v --include-regex ".+\.z\d+" syncup
    bypy -v --include-regex ".+\.zip" syncup
    ############################################################################################
    
    bypyCompareInfo=bypyCompareInfo$(date +%s)${RANDOM}.txt
    dir=$(echo ${dir} | sed 's/\.\(.*\)/\1/g'|sed 's/\/\(.*\)/\1/g')
    
    bypy compare ${dir} ${dir} > ${bypyCompareInfo}
    
#    cat ${bypyCompareInfo}
    
    sameFilesAt=`cat ${bypyCompareInfo} | grep -n 'Same files' | cut -d ':' -f 1`
#    echo ${sameFilesAt}
    differentFilesAt=`cat ${bypyCompareInfo} | grep -n 'Different files' | cut -d ':' -f 1`
#    echo ${differentFilesAt}
    
    sameFilePathArray=($(cat ${bypyCompareInfo} | sed -n "${sameFilesAt},${differentFilesAt}p" | grep '^F' | awk '{print $3}'))
    for element in $(seq 0 $((${#sameFilePathArray[*]}-1)))
    do
#    echo ${sameFilePathArray[$element]}
        rm -rf ${dir}/${sameFilePathArray[$element]}
    done
    
    rm -rf ${bypyCompareInfo}

    echo "${i}th file ${shouldBeUploaded} is complete! sleep 60s"
    i++
    sleep 60
done