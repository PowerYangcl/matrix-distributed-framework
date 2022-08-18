package com.matrix.validate;


/**
 * @description: 入参通用基础验证规则，适用于绝大部分业务场景。
 * 		各自业务模块如果出现不满足的情况可以继承此类来扩展自身业务验证。
 * 		此处结合：@Pattern(regexp=Validation.****, message="*****") 来一起使用，javax.validation.constraints.Pattern。
 * 
 * @author Yangcl
 * @date 2022-3-20 21:05:03
 * @home https://github.com/PowerYangcl
 * @path matrix-core / com.matrix.validate.Validation.java
 * @version 1.6.0.8-validation
 */
public interface Validation {

	// @Pattern(regexp=Validation.MATRIX_PERMISSIONS_NAV_TYPE, message="101010062")   只能验证String 字段，不能验证Integer字段
	String MATRIX_PERMISSIONS_NAV_TYPE = "0|1|2|3|4|5";
	
	String GW_ACTIVE = "dev|test|pre|master|prod";		// spring cloud gateway生产环境
	String GW_REQUEST_TYPE = "get|post|put|delete";		// spring cloud gateway 请求方式
	String GW_ROUTE_TYPE = "project|url";		// spring cloud gateway 路由类型
	String GW_PREDICATES = "Path|After|Before|Between|Cookie|Header|Host|Method|Query|RemoteAddr|Weight|XForwardedRemoteAddr";		// spring cloud gateway 断言规则
	String GW_STATISTICAL_DIMENSION = "month|day|hour|minute|second";
	
	String REGEX_NUMBER = "^[0-9]*$";
	
	String REGEX_EMAIL = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";
	String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d\\*\\(\\)`~!@#\\$%&_\\-+=\\|\\{\\}\\[\\]:\";\\'<>,.\\?\\/\\）])[a-zA-Z\\d\\*\\(\\)`~!@#\\$%&_\\-+=\\|\\{\\}\\[\\]:\";\\'<>,.\\?\\/\\）]{8,30}$";
	String REGEX_UUID = "^[a-zA-Z0-9-_]{12,40}$";
	String GATEWAY_TYPE = "small|medium|large|xlarge";
	String REGEX_IPV4 = "(10|11|172|192)\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
	String REGEX_CIDR = REGEX_IPV4 + "/(\\d{1,3})";
	String REGEX_IPV6 = "^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$";
	String REGEX_IPV6_CIDR = "^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$|^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*\\/(1[01][0-9]|12[0-8]|[0-9]{1,2})$";
	String REGEX_NAME = "^[-0-9a-zA-Z_\u4e00-\u9fa5]{1,32}$";
	String REGEX_PROTOCOL = "TCP|UDP|HTTP|HTTPS";
	
	
}
