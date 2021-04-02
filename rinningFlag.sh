runningFlag="runningFlag_$(date +%s)"

echo "删除此文件,程序安全停止 ">${runningFlag}
for((i=0;i<1000;i++))
do
    if [ -f ${runningFlag} ];then
        echo "文件存在_${i}"
    else
        echo "文件不存在_${i}"
    fi
    sleep 1
done
