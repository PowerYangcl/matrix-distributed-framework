package com.matrix.validate;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
public class CreateInstanceRequest {

    /**
     * az信息
     */
    @Expose
    @NotBlank(message = ErrorCode.MISSING_ARGUMENT)
    @ParamValid(method = "verifyAz", message = ErrorCode.INVALID_ARGUMENT)
    private String az;

    /**
     * 设备类型
     */
    @Expose
    @SerializedName("deviceType")
    @NotBlank(message = ErrorCode.MISSING_ARGUMENT)
    private String device_type;

    /**
     * 镜像类型
     */
    @Expose
    @SerializedName("imageType")
    @NotBlank(message = ErrorCode.MISSING_ARGUMENT)
    @Pattern(regexp = Validation.REGEX_IMAGE_TYPE, message = ErrorCode.INVALID_ARGUMENT)
    private String image_type;

    /**
     * OS类型
     */
    @Expose
    @SerializedName("osTypeId")
    @NotBlank(message = ErrorCode.MISSING_ARGUMENT)
    @Pattern(regexp = Validation.REGEX_ID, message = ErrorCode.INVALID_ARGUMENT)
    private String image_id;

    /**
     * 系统盘raid 类型
     */
    @Expose
    @SerializedName("sysRaidTypeId")
    @NotBlank(message = ErrorCode.MISSING_ARGUMENT)
    @Pattern(regexp = Validation.REGEX_ID, message = ErrorCode.INVALID_ARGUMENT)
    private String sys_raid_type_id;

    /**
     * raid 类型
     */
    @Expose
    @SerializedName("dataRaidTypeId")
    @Pattern(regexp = Validation.REGEX_ID, message = ErrorCode.INVALID_ARGUMENT)
    private String data_raid_type_id;

    /**
     * 子网编号
     */
    @Expose
    @SerializedName("subnetId")
    @Pattern(regexp = Validation.REGEX_ID, message = ErrorCode.INVALID_ARGUMENT)
    private String subnet_id;

    @Expose
    @SerializedName("privateIp")
    @Pattern(regexp = Validation.IP_ADDRESS, message = ErrorCode.INVALID_ARGUMENT)
    private String private_ip;

    /**
     * 是否启用外网，默认yes
     */
    @Expose
    @SerializedName("enableInternet")
    @Pattern(regexp = Validation.REGEX_KEEP_DATA, message = ErrorCode.INVALID_ARGUMENT)
    public String enable_internet = "yes";

    /**
     * 是否启用IPV6
     */
    @Expose
    @SerializedName("enableIpv6")
    @Pattern(regexp = Validation.REGEX_KEEP_DATA, message = ErrorCode.INVALID_ARGUMENT)
    public String enable_ipv6 = "no";

    /**
     * IPV6地址
     */
    @Expose
    @SerializedName("ipv6Address")
    @Pattern(regexp = Validation.REGEX_IPV6, message = ErrorCode.INVALID_ARGUMENT)
    public String ipv6_address;

    /**
     * 网络类型basic/vpc
     */
    @Expose
    @SerializedName("networkType")
    @NotBlank(message = ErrorCode.MISSING_ARGUMENT)
    @Pattern(regexp = Validation.REGEX_NETWORK_TYPE, message = ErrorCode.INVALID_ARGUMENT)
    private String network_type;

    /**
     * 子网cidr
     */
    @Expose
    @SerializedName("cidr")
    @Pattern(regexp = Validation.REGEX_CIDR, message = ErrorCode.INVALID_ARGUMENT)
    private String cidr;

    /**
     * 链路类型bgp
     */
    @Expose
    @SerializedName("lineType")
    @Pattern(regexp = Validation.REGEX_LINE_TYPE, message = ErrorCode.INVALID_ARGUMENT)
    private String line_type;

    /**
     * 外网带宽(M)
     */
    @Expose
    @SerializedName("bandwidth")
    private Integer bandwidth;

    @Expose
    @SerializedName("extraUplinkBandwidth")
    private Integer extra_uplink_bandwidth;

    @Expose
    @SerializedName("internetChargeMode")
    @Pattern(regexp = Validation.REGEX_CHARGE_MODE, message = ErrorCode.INVALID_ARGUMENT)
    private String internet_charge_mode;

    @Expose
    @SerializedName("bandwidthPackageId")
    @Pattern(regexp = Validation.REGEX_ID, message = ErrorCode.INVALID_ARGUMENT)
    private String bandwidth_package_id;

    @Expose
    @SerializedName("name")
    @NotBlank(message = ErrorCode.MISSING_ARGUMENT)
    @Pattern(regexp = Validation.REGEX_INSTANCE_NAME, message = ErrorCode.INVALID_ARGUMENT)
    private String name;

    @Expose
    @Pattern(regexp = Validation.REGEX_HOSTNAME, message = ErrorCode.INVALID_ARGUMENT)
    @Length(min = 2, max = 64, message = ErrorCode.OUT_OF_RANGE)
    private String hostname;

    @Expose
    @SerializedName("description")
    @Length(min = 0, max = 256, message = ErrorCode.OUT_OF_RANGE)
    private String description;

    /**
     * 设备root密码
     */
    @Expose
    @SerializedName("password")
    @Pattern(regexp = Validation.REGEX_PASSWORD, message = ErrorCode.INVALID_ARGUMENT)
    private String password;

    @Expose
    @SerializedName("count")
    @NotNull(message = ErrorCode.MISSING_ARGUMENT)
    @Range(min = 1, max = 500, message = ErrorCode.OUT_OF_RANGE)		// v2.27.2 中台机器扩展至500(retail)
    private Integer count;

    @Valid
    @Expose
    @SerializedName("charge")
    @NotNull(message = ErrorCode.MISSING_ARGUMENT)
    private Charge charge;

    @Valid
    @Expose
    @SerializedName("aliasIps")
    private List<AliasIPRequest> alias_ips;

    @Valid
    @Expose
    private List<AliasIPRequest> ipv6AliasIps;

    @Expose
    @SerializedName("userData")
    private String user_data;

    @Expose
    @SerializedName("keypairId")
    @Pattern(regexp = Validation.REGEX_ID, message = ErrorCode.INVALID_ARGUMENT)
    private String keypair_id;
    
    @Expose
    @SerializedName("keypairIdSos")				// v2.27.0 sos带外控制台
    @Pattern(regexp = Validation.REGEX_ID, message = ErrorCode.INVALID_ARGUMENT)
    private String keypair_id_sos;

    @Expose
    @SerializedName("interfaceMode")
    @Pattern(regexp = Validation.REGEX_INTERFACE_MODE, message = ErrorCode.INVALID_ARGUMENT)
    private String interface_mode = InterfaceMode.BOND.toString();

    @Expose
    @SerializedName("extensionSubnetId")
    @Pattern(regexp = Validation.REGEX_ID, message = ErrorCode.INVALID_ARGUMENT)
    private String extension_subnet_id;

    @Expose
    @SerializedName("extensionPrivateIp")
    @Pattern(regexp = Validation.IP_ADDRESS, message = ErrorCode.INVALID_ARGUMENT)
    private String extension_private_ip;

    @Expose
    @SerializedName("extensionEnableInternet")
    @Pattern(regexp = "yes|no", message = ErrorCode.INVALID_ARGUMENT)
    private String extension_enable_internet;

    /**
     * 是否启用IPV6
     */
    @Expose
    @SerializedName("extensionEnableIpv6")
    @Pattern(regexp = Validation.REGEX_KEEP_DATA, message = ErrorCode.INVALID_ARGUMENT)
    public String extension_enable_ipv6 = "no";

    /**
     * IPV6地址
     */
    @Expose
    @SerializedName("extensionIpv6Address")
    @Pattern(regexp = Validation.REGEX_IPV6, message = ErrorCode.INVALID_ARGUMENT)
    public String extension_ipv6_address;

    @Expose
    @SerializedName("extensionLineType")
    @Pattern(regexp = Validation.REGEX_LINE_TYPE, message = ErrorCode.INVALID_ARGUMENT)
    private String extension_line_type;

    @Expose
    @SerializedName("extensionBandwidth")
    private Integer extension_bandwidth;

    @Expose
    @SerializedName("extensionExtraUplinkBandwidth")
    private Integer extension_extra_uplink_bandwidth;

    @Expose
    @SerializedName("extensionInternetChargeMode")
    @Pattern(regexp = Validation.REGEX_CHARGE_MODE, message = ErrorCode.INVALID_ARGUMENT)
    private String extension_internet_charge_mode;

    @Expose
    @SerializedName("extensionBandwidthPackageId")
    @Pattern(regexp = Validation.REGEX_ID, message = ErrorCode.INVALID_ARGUMENT)
    private String extension_bandwidth_package_id;

    @Valid
    @Expose
    @SerializedName("extensionAliasIps")
    private List<AliasIPRequest> extension_alias_ips;

    @Valid
    @Expose
    private List<AliasIPRequest> extensionIpv6AliasIps;

    @Expose
    @SerializedName("resourceTags")
    private List<ResourceTag> user_tags;

    @Expose
    @SerializedName("systemDiskPartitions")
    private List<Partition> system_partitions;

    @Expose
    @SerializedName("dataDiskPartitions")
    private List<Partition> data_partitions;

    public BCreateInstanceRequest buildBCreateInstanceRequest() {
        BCreateInstanceRequest param = new BCreateInstanceRequest();
        param.setAz(az);
        param.setSys_raid_type_id(sys_raid_type_id);
        param.setData_raid_type_id(data_raid_type_id);
        param.setDescription(description);
        param.setDevice_type(device_type);
        param.setImage_type(image_type);
        param.setImage_id(image_id);
        param.setSubnet_id(subnet_id);
        param.setPrivate_ip(private_ip);
        param.setNetwork_type(network_type);
        param.setCidr(cidr);
        param.setLine_type(line_type);
        param.setSystem_partitions(system_partitions);
        param.setData_partitions(data_partitions);
        if (StringUtils.isNotBlank(bandwidth_package_id)) {
            param.setBandwidth(1);
            param.setExtra_uplink_bandwidth(0);
        } else {
            param.setBandwidth(bandwidth);
            param.setExtra_uplink_bandwidth(extra_uplink_bandwidth);
        }
        param.setInternet_charge_mode(internet_charge_mode);
        param.setBandwidth_package_id(bandwidth_package_id);
        param.setName(name);
        
        String keypair_id_ = null; // v2.27.0 创建实例时，如果选择【关联SSH密钥】则SOS密钥和登录密钥相同，详情页可以修改成独立的SOS密钥
        if(StringUtils.isNotBlank(keypair_id)) {
        	keypair_id_ = keypair_id;
        }
        if(StringUtils.isNotBlank(keypair_id_sos)) {
        	keypair_id_ = keypair_id_sos;
        	param.setIsFromKeypairSos(true);
        }
        param.setKeypair_id(keypair_id_);
        param.setKeypair_id_sos(keypair_id_);
        if (StringUtils.isNotEmpty(password)) {
            param.setPassword(password);
        } else if (StringUtils.isEmpty(password) && StringUtils.isBlank(keypair_id_)) {
            param.setRandom_password(RandomPassword.makeRandomPwd(8));
        }
        param.setCount(count);
        param.setCharge_duration(charge.getCharge_duration());
        param.setCharge_mode(charge.getCharge_mode());
        param.setCharge_unit(charge.getCharge_unit());
        param.setAuto_renew(charge.getAuto_renew());
        param.setEnable_internet(enable_internet);
        param.setEnable_ipv6(enable_ipv6);
        param.setIpv6_address(ipv6_address);
        param.setHostname(hostname);
        if (StringUtils.isNotEmpty(user_data)) {
            param.setUser_data(user_data);
        }
        param.setAlias_ips(alias_ips);
        param.setIpv6AliasIps(ipv6AliasIps);
        param.setInterface_mode(interface_mode);
        if (InterfaceMode.DUAL.equals(InterfaceMode.parse(interface_mode))) {
            param.setExtension_subnet_id(extension_subnet_id);
            param.setExtension_private_ip(extension_private_ip);
            param.setExtension_enable_internet(extension_enable_internet);
            param.setExtension_line_type(extension_line_type);
            param.setExtension_enable_ipv6(extension_enable_ipv6);
            param.setExtension_ipv6_address(extension_ipv6_address);
            if (StringUtils.isNotBlank(extension_bandwidth_package_id)) {
                param.setExtension_bandwidth(1);
                param.setExtension_extra_uplink_bandwidth(0);
            } else {
                param.setExtension_bandwidth(extension_bandwidth);
                param.setExtension_extra_uplink_bandwidth(extension_extra_uplink_bandwidth);
            }
            param.setExtension_internet_charge_mode(extension_internet_charge_mode);
            param.setExtension_bandwidth_package_id(extension_bandwidth_package_id);
            param.setExtension_alias_ips(extension_alias_ips);
            param.setExtensionIpv6AliasIps(extensionIpv6AliasIps);
        }
        param.setUser_tags(user_tags);
        return param;
    }

    public void validate() {
        if (NetworkType.VPC.toString().equalsIgnoreCase(network_type) && StringUtils.isEmpty(subnet_id)) {
            throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                    "subnetId can not be null", HttpStatus.BAD_REQUEST,
                    "subnetId");
        }
        if (StringUtils.isNotEmpty(private_ip) && !NetworkType.VPC.toString().equalsIgnoreCase(network_type)) {
            throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                    "basic can not support private_ip", HttpStatus.BAD_REQUEST,
                    "privateIp");
        }
        if (StringUtils.isNotEmpty(private_ip) && count != 1) {
            throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                    "private_ip can support only one", HttpStatus.BAD_REQUEST,
                    "count");
        }
        if ("no".equalsIgnoreCase(enable_ipv6) && StringUtils.isNotEmpty(ipv6_address)) {
            throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                    "ipv6_address is invalid", HttpStatus.BAD_REQUEST, "ipv6Address");
        }
        if (StringUtils.isNotEmpty(ipv6_address) && count != 1) {
            throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                    "private_ip can support only one", HttpStatus.BAD_REQUEST,
                    "count");
        }
        if ("yes".equalsIgnoreCase(enable_internet)) {
            if (NetworkType.RETAIL.toString().equalsIgnoreCase(network_type)) {
                throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                        "enableInternet can not be null", HttpStatus.BAD_REQUEST,
                        "enableInternet");
            }
            if (!NetworkType.RETAIL.toString().equalsIgnoreCase(network_type)
                    && StringUtils.isBlank(line_type)) {
                throw new RestfulException(ErrorCode.MISSING_ARGUMENT,
                        "line_type can not be null", HttpStatus.BAD_REQUEST,
                        "lineType");
            }
            if (bandwidth == null && StringUtils.isBlank(bandwidth_package_id)) {
                throw new RestfulException(ErrorCode.MISSING_ARGUMENT,
                        "bandwidth can not be null", HttpStatus.BAD_REQUEST,
                        "bandwidth");
            }
            if (!Objects.equals(internet_charge_mode, ChargeMode.CONFIG.getName())
                    && StringUtils.isNotBlank(bandwidth_package_id)) {
                throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                        "bandwidthPackageId should be null", HttpStatus.BAD_REQUEST,
                        "bandwidthPackageId");
            }
        }
        if (StringUtils.isNoneBlank(cidr)) {
            com.jcloud.cps.api.common.network.Cidr4.validate(cidr);
        }
        if (!StringUtils.isEmpty(user_data)) {
            if (!Base64Util.isBase64(user_data)) {
                throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                        "user_data is not base64 specification",
                        HttpStatus.BAD_REQUEST, "userData");
            }
            byte[] decode_bytes = Base64.decodeBase64(user_data);
            String decode_text = new String(decode_bytes);
            if (!RegexUtil.isMatch(decode_text, Consts.START_BASH)
                    && !RegexUtil.isMatch(decode_text, Consts.START_PYTHON)
                    && !RegexUtil.isMatch(decode_text, Consts.START_WINDOWS)) {
                throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                        "user_data has to startsWith bash or python or windows specification",
                        HttpStatus.BAD_REQUEST, "userData");
            }
            if (decode_bytes.length > Consts.USER_DATA_SIZE) {
                throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                        "user_data is oversize", HttpStatus.BAD_REQUEST, "userData");
            }
        }
        checkAliapIps(alias_ips,"aliasIps");
        checkAliapIps(ipv6AliasIps,"ipv6AliasIps");
        if (InterfaceMode.DUAL.equals(InterfaceMode.parse(interface_mode))) {
            if (!NetworkType.RETAIL.toString().equalsIgnoreCase(network_type)
                    && StringUtils.isBlank(extension_subnet_id)) {
                throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                        "extension_subnet_id can not be null", HttpStatus.BAD_REQUEST,
                        "extensionSubnetId");
            }
            if ("no".equalsIgnoreCase(extension_enable_ipv6) && StringUtils.isNotEmpty(extension_ipv6_address)) {
                throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                        "extension_ipv6_address is invalid", HttpStatus.BAD_REQUEST, "extensionIpv6Address");
            }
            if ("yes".equals(extension_enable_internet)) {
                if (NetworkType.RETAIL.toString().equalsIgnoreCase(network_type)) {
                    throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                            "line_type can not be null", HttpStatus.BAD_REQUEST,
                            "extensionEnableInternet");
                }
                if (StringUtils.isBlank(extension_line_type)) {
                    throw new RestfulException(ErrorCode.MISSING_ARGUMENT,
                            "extension_line_type can not be null", HttpStatus.BAD_REQUEST,
                            "extensionLineType");
                }
                if (extension_bandwidth == null && StringUtils.isBlank(extension_bandwidth_package_id)) {
                    throw new RestfulException(ErrorCode.MISSING_ARGUMENT,
                            "extension_bandwidth can not be null", HttpStatus.BAD_REQUEST,
                            "extensionBandwidth");
                }
                if (!Objects.equals(extension_internet_charge_mode, ChargeMode.CONFIG.getName())
                        && StringUtils.isNotBlank(extension_bandwidth_package_id)) {
                    throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                            "bandwidthPackageId should be null", HttpStatus.BAD_REQUEST,
                            "extensionBandwidthPackageId");
                }
            }
            checkAliapIps(extension_alias_ips,"extension_alias_ips");
            checkAliapIps(extensionIpv6AliasIps,"extensionIpv6AliasIps");
        }
        if (user_tags != null && user_tags.size() > 10) {
            throw new RestfulException(ErrorCode.QUOTA_EXCEEDED,
                    "resource tag exceeded", HttpStatus.BAD_REQUEST, "resourcetag");
        }
        if (!CollectionUtils.isEmpty(user_tags)) {
            for (ResourceTag tag : user_tags) {
                if (StringUtils.isEmpty(tag.getKey()) || StringUtils.isEmpty(tag.getValue())) {
                    throw new RestfulException(ErrorCode.MISSING_ARGUMENT,
                            "resourceTags can not be null", HttpStatus.BAD_REQUEST,
                            "resourceTags");
                }
            }
        }
        if (NetworkType.RETAIL.toString().equalsIgnoreCase(network_type)) {
            if (StringUtils.isNotEmpty(subnet_id)) {
                throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                        "subnetId is invalid", HttpStatus.BAD_REQUEST, "subnetId");
            }
            if (StringUtils.isNotEmpty(extension_subnet_id)) {
                throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                        "extensionSubnetId is invalid", HttpStatus.BAD_REQUEST, "extensionSubnetId");
            }
            if (StringUtils.isNotEmpty(private_ip)) {
                throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                        "privateIp is invalid", HttpStatus.BAD_REQUEST, "privateIp");
            }
            if (StringUtils.isNotEmpty(extension_private_ip)) {
                throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                        "extensionPrivateIp is invalid", HttpStatus.BAD_REQUEST, "extensionPrivateIp");
            }
            if (StringUtils.isEmpty(line_type)) {
                this.line_type = LineTypeEnum.BGP.toString();
            }
        }
        
        if (!NetworkType.RETAIL.toString().equalsIgnoreCase(network_type)) {		// 2.27.2  非零售中台的服务器创建数量不得超过30
        	if(this.count > 30) {
        		throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                        "count is invalid", HttpStatus.BAD_REQUEST, "count");
        	}
        }
        
        charge.validate();
    }

    private void checkAliapIps(List<AliasIPRequest> aliasIps,String message) {
        if (!CollectionUtils.isEmpty(aliasIps)) {
            if (count > 1) {
                throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                        message+" can support only one", HttpStatus.BAD_REQUEST,
                        "count");
            }
            if (aliasIps.size() > 50) {
                throw new RestfulException(ErrorCode.QUOTA_EXCEEDED,
                        message+" exceeded", HttpStatus.TOO_MANY_REQUESTS);
            }
            Set<String> alias_ip_set = new HashSet<>();
            for (AliasIPRequest alias_ip : aliasIps) {
                alias_ip_set.add(alias_ip.getCidr());
            }
            if (alias_ip_set.size() != aliasIps.size()) {
                throw new RestfulException(ErrorCode.INVALID_ARGUMENT,
                        message+" is repeat", HttpStatus.BAD_REQUEST, message);
            }
        }
    }
}
