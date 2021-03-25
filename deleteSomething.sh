for ((i=1; i<=14400; ))
do
    echo $(date)
    #根据日志删除已经上传的文件
    cat FindAndZipAndUpload.log | grep "==>" |grep OK |awk '{print $2}' | xargs rm -rf
    #清空日志
    >FindAndZipAndUpload.log
    #删除空文件夹
    find -type d -empty | xargs rm -rf
    #每分钟清理一次
    echo "sleeping"
    sleep 600
done
