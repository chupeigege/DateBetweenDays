package vip.aquan.datebetweendays.util;

import com.google.common.collect.Maps;
import vip.aquan.datebetweendays.pojo.Price;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: 两个日期之间存在空数据，补充完整时间
 * @date: 2020/11/18 22:08
 * @Description:
 */
public class DateBetweenDays {

    public static void main(String[] args) throws ParseException {
        Date dateStart = new SimpleDateFormat("yyyy-MM-dd").parse("2020-10-18");
        Date dateEnd = new SimpleDateFormat("yyyy-MM-dd").parse("2020-10-25");
        List<String> betweenDays = DateBetweenDays.getBetweenDays(dateStart, dateEnd, "yyyy-MM-dd");
        //假设这是数据库查出来的
        List<Price> dbList = new ArrayList<>();
        dbList.add(new Price("2020-10-19", 2000));
        dbList.add(new Price("2020-10-21", 3000));
        dbList.add(new Price("2020-10-22", 5000));
        Map<String, Price> dbMap = Maps.uniqueIndex(dbList, Price::getMydate);
        System.out.println("未填充前数据:" + dbList);
        List<Price> resultList = new ArrayList<>();
        betweenDays.forEach(b -> {
            if (dbMap.containsKey(b)) {
                resultList.add(dbMap.get(b));
            } else {
                resultList.add(new Price(b, 0));
            }
        });
        System.out.println("填充后数据：");
        resultList.forEach(System.out::println);
    }


    /**
     * 获取两个日期之间的所有日期(字符串格式, 按天计算，包含开始位置和结束位置)
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getBetweenDays(Date startDate, Date endDate, String pattern) {
        if (null == pattern || "".equals(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        // 保存两个时间之间的日期，TreeSet保证有序且不重复
        Set<String> result = new TreeSet<>();

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        // 如果格式化后的开始时间等于格式化后的结束时间，则不重复添加结束时间
        if (sdf.format(startDate).equals(sdf.format(endDate))) {
            result.add(sdf.format(startDate));
            return new ArrayList<>(result);
        }

        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startDate);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(endDate);

        // 不允许传入的开始时间大于结束时间
        if (!tempStart.before(tempEnd)) {
            throw new IllegalArgumentException("不允许开始时间大于结束时间");
        }

        while (tempStart.before(tempEnd)) {
            result.add(sdf.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        result.add(sdf.format(tempEnd.getTime()));
        return new ArrayList<>(result);
    }
}
