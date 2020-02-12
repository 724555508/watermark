/**   
 * Copyright © 2019 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * 功能描述：
 * @Package: com.Yang.util 
 * @author: Administrator   
 * @date: 2019年4月16日 下午1:56:27 
 */
package com.Yang.commom.utils;

import com.Yang.util.IsNull;

/**   
* Copyright: Copyright (c) 2019 LanRu-Caifu
* 
* @ClassName: IsNullValue.java
* @Description: 该类的功能描述
*
* @version: v1.0.0
* @author: Administrator
* @date: 2019年4月16日 下午1:56:27 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2019年4月16日     Administrator           v1.0.0               修改原因
*/
public class Is {

	public static boolean Null(Object obejct) {
		return IsNull.isNullOrEmpty(obejct);

	}
	
	
}
