for ((i=1; i<=14400; ))
do
    echo $(date)
    #根据日志删除已经上传的文件
    tail -n 3000 FindAndZipAndUpload.out | grep -a zipFile |grep -a "==>" |grep -a OK |awk '{print $2}' | xargs rm -rf
    #清空日志
#    >FindAndZipAndUpload.out
    #删除空文件夹
    find -type d -empty | xargs rm -rf
    #每分钟清理一次
    echo "sleeping"
    sleep 60
done

