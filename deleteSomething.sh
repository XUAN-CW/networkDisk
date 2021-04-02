for ((i=1; i<=14400; ))
do
    echo $(date)
    #根据日志删除已经上传的文件
    tail -n 1000 FindAndZipAndUpload.out | grep -a zipFile |grep -a "==>" |grep -a OK |  cut -d = -f 1 | sed 's/.\(.*\)../\1/'|sed 's/\([\x20\x22\x23]\)/\\\1/g' | xargs rm
#    tail -n 100 FindAndZipAndUpload.out | grep -a zipFile |grep -a "==>" |grep -a OK |  cut -d = -f 1 | sed 's/.\(.*\)../\1/'
    #清空日志
#    >FindAndZipAndUpload.out
    #删除空文件夹
    find -type d -empty | sed 's/\(.*\)/\"\1\"/' # 这一句提示一下删除了什么
    find -type d -empty | sed 's/\(.*\)/\"\1\"/' | xargs rmdir
    #每分钟清理一次
    echo "sleeping"
    sleep 60
done

