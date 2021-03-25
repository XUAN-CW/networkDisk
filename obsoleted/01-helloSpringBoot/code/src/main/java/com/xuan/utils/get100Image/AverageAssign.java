package com.xuan.utils.get100Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XUAN
 * @date 2021/3/10 - 17:28
 * @references
 * @purpose 将List均分成n份
 * @errors
 */
public class AverageAssign {
    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     *
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remaider = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }


    public static Map<Integer,Integer> arrayAssign(int all, int n) {
        Map<Integer,Integer> integerMap = new HashMap<>();
        int remaider = all % n;  //(先计算出余数)
        int number = all / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            if (remaider > 0) {
                integerMap.put(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                integerMap.put(i * number + offset, (i + 1) * number + offset);
            }
        }
        return integerMap;
    }
}
