runningFlag="runningFlag_$(date +%s)"
echo "删除此文件,程序安全停止 ">${runningFlag}

for ((i=1; i<=14400; ))
do
    if [ -f ${runningFlag} ];then
        echo "文件存在,程序继续运行"
    else
        echo "文件不存在,程序安全停止"
        break
    fi
    ########## 文件搜索解释 ########################################
    # - 在 qbit 目录下找小于 40G 的文件
    # - 单引号双引号转义
    # - 显示文件大小
    # - 从小大排序
    # - 不要未下载完成的 .!qB 文件
    # - 取最上面的一行路径(经上面排序，取到的是最小的文件)
    shouldBeUploaded=$(find qbit -type f -size -40G 2>/dev/null|grep -v '!qB$'|grep -v parts|sed 's/\([\x20-\x2E\x3A-\x40\x5B-\x60\x7B-\x7E]\)/\\\1/g'|xargs du --exclude="." -m 2>/dev/null| sort -n |sed -n 1p|sed 's/^[0-9]*\x09//g')
    shouldBeUploadedFileSize=$(find qbit -type f -size -40G 2>/dev/null|grep -v '!qB$'|grep -v parts|sed 's/\([\x20-\x2E\x3A-\x40\x5B-\x60\x7B-\x7E]\)/\\\1/g'|xargs du --exclude="." -m 2>/dev/null| sort -n |sed -n 1p|awk '{print $1}')
    empty=""
    if [[ ${empty} = ${shouldBeUploaded} ]] ; then
        echo "empty! sleep 60s"
        sleep 60
        continue
    fi
    
    allAvailableSpace=$(df --block-size=m | grep /dev/vda1 |awk '{print  $4}'|sed 's/\(.*\)\(.\)/\1/g')
    reservedSpace=1024
    zipAvailableSpace=$(expr ${allAvailableSpace} - ${shouldBeUploadedFileSize} - ${reservedSpace})
    echo "shouldBeUploaded=${shouldBeUploaded}"
    echo "shouldBeUploadedFileSize=${shouldBeUploadedFileSize}"
    echo "reservedSpace=${reservedSpace}"
    echo "allAvailableSpace=${allAvailableSpace}"
    echo "zipAvailableSpace=${zipAvailableSpace}"
    if [ 0 -gt ${zipAvailableSpace} ]
    then
        echo "space insufficient,stop zip! sleep 60s"
        sleep 60
        continue
    fi
    ###############################################################
    echo "获取文件所在路径"
    dir=$(dirname "${shouldBeUploaded}" | sed $'s/[^[:alnum:]\/]/_/g')
    dir="${dir}/"$(echo "${shouldBeUploaded##*/}"|sed $'s/[^[:alnum:]\/]/_/g')
    echo "dir:${dir}"
    mkdir  -p "${dir}"
    mv "${shouldBeUploaded}" "${dir}/"
    ###############################################################
    echo "设置压缩文件"
    zipFileFlag="zipFileFlag"
    zipTmp="${dir}/smallFile_${zipFileFlag}_$(date +%s).zip"
    bigFile="${dir}/bigFile_${zipFileFlag}_$(date +%s).zip"
    echo "加密压缩"
    zip -rP Xuan19981224 "${zipTmp}" "${dir}/" -m
    if [ ${shouldBeUploadedFileSize} -gt 18 ]
    then
        echo "分卷"
        zip -s 18m "${zipTmp}" --out "${bigFile}"
        echo "删除临时文件"
        rm -rf "${zipTmp}"
    fi
    echo "bypy 上传到百度云"
    python3 -m bypy -v syncup "${dir}" "${dir}" | grep -a "${zipFileFlag}" |grep -a "==>" |grep -a OK |  cut -d = -f 1 |tee zipFileDeleted.txt| xargs rm
    echo "删除空文件夹"
    find -type d -empty | sed 's/\(.*\)/\"\1\"/' | tee deleteEmptyFolder.txt | xargs rmdir
#    break
    echo "complete! ${i}th file ${shouldBeUploaded} is uploaded! sleep 60s"
    ((i++))
    sleep 60
done


