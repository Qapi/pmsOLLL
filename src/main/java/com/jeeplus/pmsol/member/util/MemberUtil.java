package com.jeeplus.pmsol.member.util;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;

/**
 * Created by tingting on 2017/10/7.
 */
public class MemberUtil {

    // 生成会员号
    public static String createMemberNum(){
        return "M" + StringUtils.upperCase(IdGen.randomBase62(1)) + DateUtils.getDate("MMddHHmm") + StringUtils.upperCase(IdGen.randomBase62(2));
    }

    // 增加积分，累计房晚


    // 增加积分
}
