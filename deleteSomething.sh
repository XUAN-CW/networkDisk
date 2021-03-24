for ((i=1; i<=14400; ))
do
    echo $(date)
    #根据日志删除已经上传的文件
    cat nohup.out | grep zipFile |grep OK |awk '{print $1}' | xargs rm -rf
    #删除空文件夹
    find -type d -empty | xargs rm -rf
    #十分钟清理一次
    sleep 600
done
