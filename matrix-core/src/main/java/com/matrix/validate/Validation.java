package com.matrix.validate;

public interface Validation {

    String REGEX_NETWORK_TYPE = "basic|vpc|retail|custom";
    String REGEX_LINE_TYPE = "bgp|ct|un|cm|dynamic_bgp";
    String REGEX_IMAGE_TYPE = "standard";
    String REGEX_CHARGE_MODE = "prepaid_by_duration|postpaid_by_duration";
    String REGEX_CHARGE_UNIT = "month|year";
    String REGEX_KEEP_DATA = "yes|no";
    String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d\\*\\(\\)`~!@#\\$%&_\\-+=\\|\\{\\}\\[\\]:\";\\'<>,.\\?\\/\\）])[a-zA-Z\\d\\*\\(\\)`~!@#\\$%&_\\-+=\\|\\{\\}\\[\\]:\";\\'<>,.\\?\\/\\）]{8,30}$";
    String IP_ADDRESS = "(10|11|172|192)\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
    String REGEX_CIDR = IP_ADDRESS + "/(\\d{1,3})";
    String REGEX_IPV6_CIDR = "^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$|^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*\\/(1[01][0-9]|12[0-8]|[0-9]{1,2})$";
    String CIDR = REGEX_CIDR+"|"+REGEX_IPV6_CIDR;
    String REGEX_IPV6 = "^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$";
    String IP_CIDR = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})/(\\d{1,3})";
    String REGEX_RETAIN_IP = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(0|255)";
    String REGEX_ID = "^[a-zA-Z0-9-_]{12,40}$";
    String REGEX_RAID_VOLUME_TYPE = "system|data";
    String REGEX_HOSTNAME = "(^((?!(^-)|(-$)|(--+)|(^\\\\.)|(\\\\.$)|(\\\\.\\\\.+))[0-9A-Za-z-.]){2,64}$)|(^\\s*$)";
    String REGEX_NAME = "^[-0-9a-zA-Z_\u4e00-\u9fa5]{1,32}$";
    String REGEX_INSTANCE_NAME = "^[a-zA-Z\u4e00-\u9fa5]([0-9a-zA-Z\u4e00-\u9fa5-_.:{}]{1,127})$";	// v2.27.4 retail实例去除{}验证，因为{{index}}
    String EIP_STATUS = "associate|disassociate";
    String REGEX_LOAD_BALANCER_TYPE = "public";
    String REGEX_IP_ADDRESS_TYPE = "ipv4";
    String REGEX_LOAD_BALANCER_STATUS = "active|inactive";
    String REGEX_PROTOCOL = "TCP|UDP|HTTP|HTTPS";
    String REGEX_ALGORITHM = "wrr|wlc|conhash";
    String REGEX_SWITCH = "on|off";
    String REGEX_COOKIE_TYPE = "insert|rewrite|prefix";
    String REGEX_MAIL_TYPE = "error|notice";
    String REGEX_EMAIL = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";
    String REGEX_NIC_MODE = "bond|normal";
    String REGEX_INTERFACE_NAME = "bond0|eth0|eth1";
    String REGEX_AVAILABLE_PRIVATE_IP_CHANNEL = "eip|vmsg";
    String REGEX_ACTION = "create|update|delete";
    String NAT_GATEWAY_TYPE = "small|medium|large|xlarge";
    String MODIFY_INSTANCE_STATUS_TYPE="running|stopped";
}
