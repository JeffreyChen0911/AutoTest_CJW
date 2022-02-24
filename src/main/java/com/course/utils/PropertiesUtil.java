package com.course.utils;

public class PropertiesUtil {
	public static Configuration config = null;

    static {
        try {
            config = new PropertiesConfiguration("configPro/test.properties");
        } catch (Exception e) {
            log.error("获取配置的属性失败：", e);
        }
    }

    /**
     * @Description <返回字符串的属性值，没有对应的key时返回null>
     * @Author zhaiyt
     * @Date 15:40 2018/9/23
     * @Param [key]
     * @return java.lang.String
     **/
    public static String getPropertiesStr(String key) {
        return config.getString(key);
    }

    /**
     * @Description <返回数字型的属性值>
     * @Author zhaiyt
     * @Date 15:40 2018/9/23
     * @Param [key]
     * @return int
     **/
    public static int getPropertiesNum(String key) {
        try {
            return config.getInt(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }

    }

    /**
     * @Description <返回布尔型的属性值>
     * @Author zhaiyt
     * @Date 15:40 2018/9/23
     * @Param [key]
     * @return java.lang.Boolean
     **/
    public static Boolean getPropertiesBoolean(String key) {
        try {
            return config.getBoolean(key);
        } catch (Exception e) {
            log.error("返回布尔型的属性值：", e);
            return false;
        }
    }

    /**
     * @Description <test>
     * @Author zhaiyt
     * @Date 15:41 2018/9/23
     * @Param [args]
     * @return void
     **/
    public static void main(String[] args) {
        String filename = getPropertiesStr("FILENAME");
        System.out.println(filename);
    }
}
