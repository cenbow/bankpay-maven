package common.plugins.keys;

/**
 * 枚举字段类型接口<br>
 * 实现此接口的枚举类型各个数据类型声明：<BR>
 * 
 * <li>数字型(带小数)： BigDecimal</li> <BR>
 * <li>数字型(不带小数)： Integer、Long</li><BR>
 * <li>字符型： String</li>
 * @author mupeng
 * @see Enum2StringUtils
 */
public interface EnumKeys {

    /**
     * 
     * 功能描述: <br>
     * 长度
     * 
     * @return
     */
    Double getLength();

    /**
     * 
     * 功能描述: <br>
     * 数据类型
     * 
     * @return
     */
    Class<?> getType();
}
