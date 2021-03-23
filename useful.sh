

# 查找 `/` 目录下 75M 以上文件
find / -type f -size +75M 
# 查找 `/` 目录下 75M 以上文件，不以 `.!qB` 结尾
find / -type f -size +75M | grep -v '!qB$'
