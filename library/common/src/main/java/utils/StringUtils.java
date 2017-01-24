package utils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    private final static Pattern IMG_URL = Pattern.compile(".*?(gif|jpeg|png|jpg|bmp|JPG|JPEG)");

    private final static Pattern URL = Pattern
            .compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)");

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

//    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
//        @Override
//        protected SimpleDateFormat initialValue() {
//            return new SimpleDateFormat("yyyy-MM-dd");
//        }
//    };

    private static final int HOUR = 60 * 60 * 1000;
    private static final int MIN = 60 * 1000;
    private static final int SEC = 1000;

    public static String getSubSepuenceIndexLast(String str) {
        String strResutl = "";
        if (!StringUtils.isEmpty(str)) {
            strResutl = str.subSequence(0, str.length() - 1).toString();
        }
        return strResutl;
    }

    /**
     * @return
     */
    public static String setAlreardyCount(int chooseCount) {
        return chooseCount == 0 ? "" : "已选 " + chooseCount + " 个";
    }

    public static String getEndsWith(String strUrl) {
        if (strUrl.lastIndexOf(".") != -1) {
            return strUrl.substring(strUrl.lastIndexOf("."), strUrl.length()); //后缀
        }
        return "";
    }

    //
//    //**
//      将列表中重复的用户移除，重复指的是userid相同
//     @param userList
//      @return
//     //*
    /*public static   <T extends BaseMol>  ArrayList removeDuplicteMol(List<T> userList){
        Set<T> s=new TreeSet(new Comparator<T>(){
            @Override
            public int compare(T o1, T o2) {
                return o1.getUserUuid().compareTo(o2.getUserUuid());
            }
        });
        s.addAll(userList);
        return new ArrayList(s);
    }
    */


    /**
     * 获取h5给的链接
     */
    public static String getH5returnURL(String url) {
        if (StringUtils.isEmpty(url)) {
            return "";
        }
        int indexOf = url.lastIndexOf("=");
        return url.substring(indexOf + 1, url.length());//包头不包尾
    }

    /**
     * @return
     */
    public static String ifNullToEmpty(String params) {
        return StringUtils.isEmpty(params) ? "" : params;
    }

    /**
     * 把duration数值转换为字符串格式，ec.01:02:03， 02:03
     */
    public static String formatDuration(int duration) {
        int hour = duration / HOUR;

        int min = duration % HOUR / MIN;

        int sec = duration % MIN / SEC;

        if (hour == 0) {
            // 没有小时数，02:03
            return String.format("%02d:%02d", min, sec);
        } else {
            // 有小时数，01:02:03
            return String.format("%02d:%02d:%02d", hour, min, sec);
        }
    }

    /**
     * 格式化当前系统时间为 01:01:01
     */
    public static String formatSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 传递一个 集合** ,返回  "," 好的 字符串
     *
     * @return
     */
    public static String splitArrayListToComma(List<String> selectList) {
        if (null == selectList) {
            return "";
        }
        String[] usersUuids = new String[selectList.size()];
        for (int i = 0; i < selectList.size(); i++) {
            usersUuids[i] = selectList.get(i);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < usersUuids.length; i++) { // 把数组 给 用; 给分割下 ;
            if (i == usersUuids.length - 1) {
                sb.append(usersUuids[i]);
            } else {
                sb.append(usersUuids[i] + ",");
            }
        }
        return sb.toString();
    }

    /**
     * 传递一个 集合** ,返回  "," 好的 字符串
     *
     * @return
     */
    public static String splitArrayListToComma(String[] strings) {
        if (null == strings) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strings.length; i++) { // 把数组 给 用; 给分割下 ;
            if (i == strings.length - 1) {
                sb.append(strings[i]);
            } else {
                sb.append(strings[i] + ",");
            }
        }
        return sb.toString();
    }

    /**
     * 传递一个 集合** ,返回  "," 好的 字符串
     *
     * @param usersUuids
     * @return
     */

    /**
     * 传递一个 集合** ,返回  ";" 好的 字符串
     *
     * @return
     */
    public static String splitArrayList(List<String> selectList) {
        if (null == selectList) {
            return "";
        }
        String[] usersUuids = new String[selectList.size()];

        for (int i = 0; i < selectList.size(); i++) {
            usersUuids[i] = selectList.get(i);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < usersUuids.length; i++) { // 把数组 给 用; 给分割下 ;
            if (i == usersUuids.length - 1) {
                sb.append(usersUuids[i]);
            } else {
                sb.append(usersUuids[i] + ";");
            }
        }
        return sb.toString();
    }

    public static String messageGroupText(List<String> selectList) {
        if (null == selectList) {
            return "";
        }
        String finalStr = "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < selectList.size(); i++) { // 把数组 给 用; 给分割下 ;
            sb.append(selectList.get(i) + "、");
            if (sb.length() >= 10) {
                finalStr = sb.toString();
                break;
            }
        }
        String strs = "";
        try {
            strs = sb.subSequence(0, sb.length() - 1).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(finalStr)) {
            if (sb.length() == 11) {
                return strs + "等";
            }
            strs = strs.substring(0, strs.lastIndexOf("、")) + "等";
        }
        return strs;
    }

    /**
     * 传递一个 集合** ,返回  "、" 好的 字符串
     *
     * @return
     */
    public static String splitArrayListToCaesura(List<String> selectList) {
      /*  if (null == selectList) {
            return "";
        }
		String[] usersUuids=new String[selectList.size()];
		for (int i = 0; i < selectList.size(); i++) {
			usersUuids[i]=selectList.get(i);
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < usersUuids.length; i++) { // 把数组 给 用; 给分割下 ;
			sb.append(usersUuids[i] + "、");
			if (i==1) {
				//第二个
				if (sb.length() <= 11) {
					return sb.append("等").toString();
				}
			}
		}		
		String strs="";
		try {
			strs = sb.subSequence(0, sb.length()-1).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (strs.length() >= 15) {
			strs = strs.substring(0, 15) + "等";
		}
		return strs;*/
        if (null == selectList) {
            return "";
        }
        String finalStr = "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < selectList.size(); i++) { // 把数组 给 用; 给分割下 ;
            sb.append(selectList.get(i) + "、");
            if (sb.length() >= 10) {
                finalStr = sb.toString();
                break;
            }
        }
        String strs = "";
        try {
            strs = sb.subSequence(0, sb.length() - 1).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(finalStr)) {
            if (sb.length() == 11) {
                return strs + "等";
            }
            strs = strs.substring(0, strs.lastIndexOf("、")) + "等";
        }
        return strs;
    }

    /**
     * 选择通讯录后将返回的数据生成一个符合命名规则的群级名称
     *
     * @param employees
     * @return
     */

    /**

    /**
     * 判断一个url是否为图片url
     *
     * @param url
     * @return
     */
    public static boolean isImgUrl(String url) {
        if (url == null || url.trim().length() == 0)
            return false;
        return IMG_URL.matcher(url).matches();
    }

    /**
     * sdcard文件
     **/
    public static final String RES_SDCARD = "sdcard://";
    /**
     * 网络文件
     **/
    public static final String RES_HTTP = "http://";
    /**
     * 网络文件
     **/
    public static final String RES_HTTPS = "https://";

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return true：为空; false：不为空
     */
    public static boolean isEmpty(String str) {
        boolean result = false;
        if (str == null || str.trim().length() == 0) {
            result = true;
        }
        return result;
    }

    /**
     * 原文件   和  新文件名字 加后缀,改名
     *
     * @return
     */
    public static String renameTo(String url, String newName) {
        String fileStr = url.substring(url.lastIndexOf("/") + 1);// /后面的文字
        return url.replace(fileStr, newName);
    }

    /**
     * 判断给定字符串是否空白串。
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串;  为空返回true
     *
     * @param input
     * @return true：为空 ; false：不为空     ' ' 也返回true;
     */
    public static boolean isStrongEmpty(String input) {
        if (input == null || "".equals(input) || input.trim().length() == 0)
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /***
     * 不等于0.0
     *
     * @param input
     * @return
     */
    public static boolean isTwoStrongEmpty(String input) {
        if (input == null || "".equals(input) || input.trim().length() == 0 || "0.0".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串最大长度验证
     *
     * @param str
     * @param length 不能超过的最大值 比如  20;
     * @return true：通过；false：不通过
     */
    public static boolean maxLength(String str, int length) {
        if (isEmpty(str)) {
            return false;
        } else return str.trim().length() <= length;
    }

    /**
     * 字符串最小长度验证
     *
     * @param str
     * @param length
     * @return true：通过；false：不通过
     */
    public static boolean minLength(String str, int length) {
        if (isEmpty(str)) {
            return false;
        } else return str.trim().length() >= length;
    }

    public static boolean isPassword(String pass) {
        return minLength(pass, 6) && maxLength(pass, 20);
    }

    /**
     * 判断字符串是否是合法的手机号 TODO
     *
     * @param str
     * @return true：合法; false：不合法
     */
    public static boolean isPhone(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,2,3,4,5-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断2个非空字符串是否相等
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean twoStringisEquals(String str1, String str2) {
        if (!isEmpty(str1) || !isEmpty(str2)) {
            return isEmpty(str1) == isEmpty(str2);
        }
        return str1.trim() == str2.trim();
    }

    /**
     * 是否为网络文件
     *
     * @param url
     * @return
     */
    public static boolean isNetworkFile(String url) {
        if (StringUtils.isEmpty(url)) {
            return false;
        }
        return url.startsWith(RES_HTTP) || url.startsWith(RES_HTTPS);
    }

    /**
     * 把大图URL地址转换为200的缩略图地址（和服务器约定好的）
     *
     * @param destUrl
     * @return
     */
    public static String imageUrlConvert200ImageUrl(String destUrl) {
        int fileFormat = destUrl.lastIndexOf(".");
        String fileUrl = destUrl + "_200." + destUrl.substring(fileFormat + 1, destUrl.length());
        return fileUrl;
    }


    /**
     * 判断email格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 以空格截取，返回list.get(0),省，list.get(1)市
     */
    public static List<String> getCity(String cityId) {
        List<String> city = new ArrayList<String>();
        int trim = cityId.indexOf(" ");
        String provice = cityId.substring(0, trim);
        String provice_ = cityId.substring(trim + 1);
        city.add(provice);
        city.add(provice_);
        return city;
    }
////	 ----------------------------------
//	 /**
//     * 将字符串转位日期类型
//     *
//     * @param sdate
//     * @return
//     */
//    public static Date toDate(String sdate) {
//        return toDate(sdate, dateFormater.get());
//    }
//
//    public static Date toDate(String sdate, SimpleDateFormat dateFormater) {
//        try {
//            return dateFormater.parse(sdate);
//        } catch (ParseException e) {
//            return null;
//        }
//    }
//
//    public static String getDateString(Date date) {
//        return dateFormater.get().format(date);
//    }

    /**
     * IM时间格式
     *
     * @param sdate
     * @return
     */

    /**
     * 阅读人数时间格式
     *
     * @param sdate
     * @return
     */

    /**
     * 动态显示时间格式
     *
     * @param sdate
     * @return
     */

    /**
     * 动态详情时间格式
     *
     * @param sdate
     * @return
     */

//    /**
//     *  显示的样子不一样
//     * @param sdate
//     * @return
//     */
//    public static String friendly_time2(String sdate) {
//        String res = "";
//        if (isEmpty(sdate))
//            return "";
//
//        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
//        String currentData = StringUtils.getDataTime("MM-dd");
//        int currentDay = toInt(currentData.substring(3));
//        int currentMoth = toInt(currentData.substring(0, 2));
//
//        int sMoth = toInt(sdate.substring(5, 7));
//        int sDay = toInt(sdate.substring(8, 10));
//        int sYear = toInt(sdate.substring(0, 4));
//        Date dt = new Date(sYear, sMoth - 1, sDay - 1);
//
//        if (sDay == currentDay && sMoth == currentMoth) {
//            res = "今天 / " + weekDays[getWeekOfDate(new Date())];
//        } else if (sDay == currentDay + 1 && sMoth == currentMoth) {
//            res = "昨天 / " + weekDays[(getWeekOfDate(new Date()) + 6) % 7];
//        } else {
//            if (sMoth < 10) {
//                res = "0";
//            }
//            res += sMoth + "/";
//            if (sDay < 10) {
//                res += "0";
//            }
//            res += sDay + " / " + weekDays[getWeekOfDate(dt)];
//        }
//
//        return res;
//    }


//    /**
//     * 字符串转布尔值
//     *
//     * @param b
//     * @return 转换异常返回 false
//     */
//    public static boolean toBool(String b) {
//        try {
//            return Boolean.parseBoolean(b);
//        } catch (Exception e) {
//        }
//        return false;
//    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null || "".equals(obj))
            return 0;
        return toInt(obj.toString(), 0);
    }
//
//    /**
//     * 返回当前系统时间
//     */
//    public static String getDataTime(String format) {
//        SimpleDateFormat df = new SimpleDateFormat(format);
//        return df.format(new Date());
//    }

    /**
     * 智能格式化 , 不同的显示样子
     */

//
//    /**
//     * @return 判断一个时间是不是上午
//     */
//    public static boolean isMorning(long when) {
//        android.text.format.Time time = new android.text.format.Time();
//        time.set(when);
//
//        int hour = time.hour;
//        return (hour >= 0) && (hour < 12);
//    }
//
//    /**
//     * @return 判断一个时间是不是今天
//     */
//    public static boolean isToday(long when) {
//        android.text.format.Time time = new android.text.format.Time();
//        time.set(when);
//
//        int thenYear = time.year;
//        int thenMonth = time.month;
//        int thenMonthDay = time.monthDay;
//
//        time.set(System.currentTimeMillis());
//        return (thenYear == time.year)
//                && (thenMonth == time.month)
//                && (thenMonthDay == time.monthDay);
//    }

//    /**
//     * @return 判断一个时间是不是昨天
//     */
//    public static boolean isYesterday(long when) {
//        android.text.format.Time time = new android.text.format.Time();
//        time.set(when);
//
//        int thenYear = time.year;
//        int thenMonth = time.month;
//        int thenMonthDay = time.monthDay;
//
//        time.set(System.currentTimeMillis());
//        return (thenYear == time.year)
//                && (thenMonth == time.month)
//                && (time.monthDay - thenMonthDay == 1);
//    }

//    /**
//     * @return 判断一个时间是不是今年
//     */
//    public static boolean isCurrentYear(long when) {
//        android.text.format.Time time = new android.text.format.Time();
//        time.set(when);
//
//        int thenYear = time.year;
//
//        time.set(System.currentTimeMillis());
//        return (thenYear == time.year);
//    }

//    /**
//     * 获取当前日期是星期几<br>
//     *
//     * @param dt
//     * @return 当前日期是星期几
//     */
//    public static int getWeekOfDate(Date dt) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(dt);
//        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
//        if (w < 0)
//            w = 0;
//        return w;
//    }

//    /**
//     * 判断给定字符串时间是否为今日
//     *
//     * @param sdate
//     * @return boolean
//     */
//    public static boolean isToday(String sdate) {
//        boolean b = false;
//        Date time = toDate(sdate);
//        Date today = new Date();
//        if (time != null) {
//            String nowDate = dateFormater2.get().format(today);
//            String timeDate = dateFormater2.get().format(time);
//            if (nowDate.equals(timeDate)) {
//                b = true;
//            }
//        }
//        return b;
//    }
//
//    /**
//     * 返回long类型的今天的日期
//     *
//     * @return
//     */
//    public static long getToday() {
//    	Calendar cal = Calendar.getInstance();
//    	String curDate = dateFormater2.get().format(cal.getTime());
//    	curDate = curDate.replace("-", "");
//    	return Long.parseLong(curDate);
//    }
//    /**
//     * 返回String类型的今天的日期
//     *
//     * @return
//     */
//    public static String getStringToday() {
//    	Calendar cal = Calendar.getInstance();
//    	String curDate = dateFormater2.get().format(cal.getTime());
//    	curDate = curDate.replace("-", ".");
//    	return curDate;
//    }
//    public static String getStringToday_() {
//    	Calendar cal = Calendar.getInstance();
//    	String curDate = dateFormater2.get().format(cal.getTime());
//    	return curDate;
//    }
//
//    /**
//     * 返回String类型的_的日期
//     * @param date 传递一个 date
//     * @return
//     */
//    public static String getStringFormatToday(Date date) {
//        String curDate = dateFormater2.get().format(date);
//        curDate = curDate.replace("-", ".");
//        return curDate;
//    }
//
//
//
//    public static String getCurTimeStr() {
//        Calendar cal = Calendar.getInstance();
//        String curDate = dateFormater.get().format(cal.getTime());
//        return curDate;
//    }
//
//    /***
//     * 计算两个时间差，返回的是的秒s
//     *
//     * @author 火蚁 2015-2-9 下午4:50:06
//     *
//     * @return long
//     * @param dete1
//     * @param date2
//     * @return
//     */
//    public static long calDateDifferent(String dete1, String date2) {
//
//        long diff = 0;
//
//        Date d1 = null;
//        Date d2 = null;
//
//        try {
//            d1 = dateFormater.get().parse(dete1);
//            d2 = dateFormater.get().parse(date2);
//
//            // 毫秒ms
//            diff = d2.getTime() - d1.getTime();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return diff / 1000;
//    }
//
//    /**
//     * 获取当前时间为每年第几周
//     *
//     * @return
//     */
//    public static int getWeekOfYear() {
//        return getWeekOfYear(new Date());
//    }

//    /**
//     * 获取当前时间为每年第几周
//     *
//     * @param date
//     * @return
//     */
//    public static int getWeekOfYear(Date date) {
//        Calendar c = Calendar.getInstance();
//        c.setFirstDayOfWeek(Calendar.MONDAY);
//        c.setTime(date);
//        int week = c.get(Calendar.WEEK_OF_YEAR) - 1;
//        week = week == 0 ? 52 : week;
//        return week > 0 ? week : 1;
//    }
//
//    public static int[] getCurrentDate() {
//        int[] dateBundle = new int[3];
//        String[] temp = getDataTime("yyyy-MM-dd").split("-");
//
//        for (int i = 0; i < 3; i++) {
//            try {
//                dateBundle[i] = Integer.parseInt(temp[i]);
//            } catch (Exception e) {
//                dateBundle[i] = 0;
//            }
//        }
//        return dateBundle;
//    }


    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }


    public static String getString(String s) {
        return s == null ? "" : s;
    }

    /**
     * @return
     */


    public static String checkValue(String value) {
        return StringUtils.isEmpty(value) ? "" : value;
    }
}
