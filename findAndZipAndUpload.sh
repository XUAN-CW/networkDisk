for ((i=1; i<=14400; ))
do
    ########## 文件搜索解释 ########################################
    # - 在 qbit 目录下找小于 40G 的文件
    # - 单引号双引号转义
    # - 显示文件大小
    # - 从小大排序
    # - 不要未下载完成的 .!qB 文件
    # - 取最上面的一行路径(经上面排序，取到的是最小的文件)
    # - 删除 awk 第一段,也就是文件大小
    # -
    shouldBeUploaded=$(find qbit -type f -size -40G 2>/dev/null|grep -v '!qB$'|grep -v parts|sed 's/\([\x20-\x2E\x3A-\x40\x5B-\x60\x7B-\x7E]\)/\\\1/g'|xargs du --exclude="." -k 2>/dev/null| sort -n |sed -n 1p|sed 's/^[0-9]*\x09//g')
    shouldBeUploadedFileSize=$(find qbit -type f -size -40G 2>/dev/null|grep -v '!qB$'|grep -v parts|sed 's/\([\x20-\x2E\x3A-\x40\x5B-\x60\x7B-\x7E]\)/\\\1/g'|xargs du --exclude="." -k 2>/dev/null| sort -n |sed -n 1p|awk '{print $1}')
    echo "currentFile: ${shouldBeUploaded}"
    echo "shouldBeUploadedFileSize=${shouldBeUploadedFileSize}"
    empty=""
    if [[ ${empty} = ${shouldBeUploaded} ]] ; then
        echo "empty! sleep 60s"
        sleep 60
        continue
    fi
    
    ###############################################################
    echo "shouldBeUploaded:${shouldBeUploaded}"
    # 如果文件没有后缀，这里会返回本身
    dir="${shouldBeUploaded%.*}"
    # 如果文件没有后缀，上面返回本身，这里就会创建文件夹失败
    mkdir "${dir}"
    echo "dir:${dir}"
    shouldBeUploadedInDir="${dir}/${shouldBeUploaded##*/}"
    echo "shouldBeUploadedInDir:${shouldBeUploadedInDir}"
    mv "${shouldBeUploaded}" "${shouldBeUploadedInDir}"
    shouldBeUploaded="${shouldBeUploadedInDir}"
    #################################################################
    
    
    if [ 64 -gt ${shouldBeUploadedFileSize} ]
    then
        echo "less than 64K"
        lessThan64K="${shouldBeUploaded}""_less_than64K"
        mv "${shouldBeUploaded}" "${lessThan64K}"
        bypy -v upload "${lessThan64K}" "${dir}"
        sleep 60
        continue
    fi
    
#     获取文件所在路径
#     设置压缩文件
    zipTmp="${shouldBeUploaded%.*}-$(date +%s).zip"
    zipFile="${shouldBeUploaded%.*}-zipFile.zip"
#    加密压缩
    zip -P Xuan19981224 "${zipTmp}" "${shouldBeUploaded}" -m
#    分卷
    zip -s 18m "${zipTmp}" --out "${zipFile}"
#    删除临时文件
    rm -rf "${zipTmp}"
#    上传 zip 文件，.zip 最后上传 
    bypy -v --include-regex ".+\.z\d+" syncup "${dir}" "${dir}"
    bypy -v --include-regex ".+\.zip" syncup "${dir}" "${dir}"
    echo "$complete! ${i}th file ${shouldBeUploaded} is uploaded! sleep 60s"
    ((i++))
    sleep 60
done


