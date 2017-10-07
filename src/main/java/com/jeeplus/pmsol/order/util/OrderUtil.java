package com.jeeplus.pmsol.order.util;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;

/**
 * Created by tingting on 2017/10/7.
 */
public class OrderUtil {

    // 生成订单号
    public static String createOrderNum(){
        return "O" + StringUtils.upperCase(IdGen.randomBase62(1)) + DateUtils.getDate("MMddHHmmss") + StringUtils.upperCase(IdGen.randomBase62(2));

    }
}
