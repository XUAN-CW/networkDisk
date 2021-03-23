# 找到一个符合要求的文件
shouldBeUploaded=$(find . -type f -size +90M | grep -v '!qB$' | sed -n 1p)
# 获取文件所在路径
dir=$(dirname "${shouldBeUploaded}")
# 设置压缩文件
zipTmp=${dir}/zipTmp$(date +%s)${RANDOM}.zip
zipFile=${dir}/zipFile$(date +%s)${RANDOM}.zip
#加密压缩
zip -P Xuan19981224 "${zipTmp}" "${shouldBeUploaded}"
#分卷
zip -s 18m "${zipTmp}" --out "${zipFile}"
#删除源文件与临时文件
rm -rf "${shouldBeUploaded}" "${zipTmp}"