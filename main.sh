chmod -R 777 .
nohup ./findAndZipAndUpload.sh > FindAndZipAndUpload.out 2>&1 &
nohup ./deleteSomething.sh > DeleteSomething.out 2>&1 &
nohup ./contOrStopQbit.sh > ContOrStopQbit.out 2>&1 &

nohup ./showInfo.sh > ShowInfo.html 2>&1 &