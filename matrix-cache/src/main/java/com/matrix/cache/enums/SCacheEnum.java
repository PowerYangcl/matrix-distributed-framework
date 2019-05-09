package com.matrix.cache.enums;

/**
 * @descriptions 系统业务功能相关枚举
 * 	service cache enum
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年12月11日 下午4:48:31
 * @version 1.0.1
 */
public enum SCacheEnum {
    MipTest,
    MipArticleListPage, // 文章列表页缓存
    MipTicketCommonRule,// 券规则缓存
    MipMemberTicket,//会员券缓存
    MipScoreCleanRule,//积分清零规则
    MipScoreBaseRule,//积分基础规则
    MipScoreCommonRule,//积分通用规则
    MipMemberScore,//会员积分缓存
    MipMemberWallet,//会员钱包缓存
    MipMemberComments,//会员备注缓存
    MipCardTemplateInfo,//卡模版缓存
    MipCardBatchMakeInfo,//卡批次最后一条缓存
    MipCardTemplateInterests,//卡模版与权益数据缓存
    MipMemberCard,//会员卡缓存
    MipLevelInfo,//等级缓存
    MipLevelDimensionType,//等级升降规则计算维度缓存
    MipLevelDimensionTypeList,//等级升降规则计算维度列表缓存
    MipLevelConfig,//等级体系配置缓存
    MipMemberLevel,//会员等级缓存
    MipInterests,//权益缓存
    MipInterestsType,//权益类型缓存
    MipInterestsTypeList,//权益类型列表缓存
    MipMemberInterests,//会员权益缓存
    MipMemberInfo,//会员信息缓存
    MipMemberCardInfo,//会员信息缓存
    MipRecordMemberCardAssetChangeLog,//会员资产变动记录缓存
    MipRecordMemberLevelChangeLog,//会员身份变化记录缓存
    MipMapMemberGroup,//分组信息缓存（包括关联对象）
    MipMarketingActivities,//营销活动缓存对象
    MipMapMemberGroupList,//会员关系的分组列表缓存
    MonitorMemberStatistics,//埋点系统--会员行为统计列表缓存
    MonitorMemberInfo,//埋点系统--会员基本信息缓存
    MonitorShopConfigOptions//埋点系统--商家 / 店铺配置信息
}
