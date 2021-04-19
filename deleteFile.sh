#!/bin/bash

isSameFile=false
#逐行读取
bypy compare by . | while read line
do
    # 找到相同文件开始的位置
    if [ "$line" = "==== Same files ===" ];then
        isSameFile=true
        continue
    fi
    
    # 找到相同文件结束的位置
    if [ "$line" = "==== Different files ===" ];then
        break
    fi
    
    # 处于相同文件的地方
    if [ "$isSameFile" = "true" ];then
        deletedFile=$(echo ${line} | sed  's/^....//g')
        echo "删除了 ""${deletedFile}"
        rm -rf "${deletedFile}"
    fi
done
