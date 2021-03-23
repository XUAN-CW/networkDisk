tempFile=tempfile${RANDOM}.txt

bypy compare > ${tempFile}

sameFilesAt=`cat ${tempFile} | grep -n 'Same files' | cut -d ':' -f 1`
#echo ${sameFilesAt}
differentFilesAt=`cat ${tempFile} | grep -n 'Different files' | cut -d ':' -f 1`
#echo ${differentFilesAt}


sameFilePathArray=($(cat ${tempFile} | sed -n "${sameFilesAt},${differentFilesAt}p" | grep '^F' | awk '{print $3}'))
for element in $(seq 0 $((${#sameFilePathArray[*]}-1)))
do
    rm -rf ${sameFilePathArray[$element]}
done


rm -rf ${tempFile}