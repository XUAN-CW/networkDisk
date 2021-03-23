bypyCompareInfo=bypyCompareInfo$(date +%s)${RANDOM}.txt

bypy compare > ${bypyCompareInfo}

sameFilesAt=`cat ${bypyCompareInfo} | grep -n 'Same files' | cut -d ':' -f 1`
#echo ${sameFilesAt}
differentFilesAt=`cat ${bypyCompareInfo} | grep -n 'Different files' | cut -d ':' -f 1`
#echo ${differentFilesAt}


sameFilePathArray=($(cat ${bypyCompareInfo} | sed -n "${sameFilesAt},${differentFilesAt}p" | grep '^F' | awk '{print $3}'))
for element in $(seq 0 $((${#sameFilePathArray[*]}-1)))
do
    rm -rf ${sameFilePathArray[$element]}
done


rm -rf ${bypyCompareInfo}