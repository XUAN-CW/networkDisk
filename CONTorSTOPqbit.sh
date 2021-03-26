for ((i=1; i<=14400; ))
do
#     找到一个符合要求的文件
    shouldBeUploaded=$(find . -type f -size +1M -size -20G 2>/dev/null|sed 's/\(.*\)/\"\1\"/'| xargs du --exclude="." -k 2>/dev/null| grep -v '!qB$'| sort -n | sed -n 1p | awk 'sub($1,"")'|awk '$1=$1')
    
    availableSpace=$(df --block-size=M | grep /dev/vda1 |awk '{print  $4}'|sed 's/\(.*\)\(.\)/\1/g')
    shouldBeUploadedFileSize=$(ls -l --block-size=M ${shouldBeUploaded} |awk '{print $5}'|sed 's/\(.*\)\(.\)/\1/g')
    reservedSpace=31000
    DownloadFreeSpace=$(expr ${availableSpace} - ${shouldBeUploadedFileSize} - ${reservedSpace})
    echo "shouldBeUploaded=${shouldBeUploaded}"
    echo "shouldBeUploadedFileSize=${shouldBeUploadedFileSize}"
    echo "reservedSpace=${reservedSpace}"
    echo "availableSpace=${availableSpace}"
    echo "DownloadFreeSpace=${DownloadFreeSpace}"
    qbitPID=$(ps -ef |grep qbittorrent-nox | grep -v "grep" |awk '{print $2}')
    if [ ${DownloadFreeSpace} -gt 0 ]
    then
        kill -CONT ${qbitPID}
        echo "CONT--$(date)"
    else
        kill -STOP ${qbitPID}
        echo "STOP--$(date)"
    fi
    echo "sleep 60s"
    echo "========================================"
    sleep 60
done
