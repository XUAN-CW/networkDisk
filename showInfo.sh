
for((i=0;i<100;))
do
    df -h >index.txt && ls -Rhal >> index.txt
    sleep 10
done
