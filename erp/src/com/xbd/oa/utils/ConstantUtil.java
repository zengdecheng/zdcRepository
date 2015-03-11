package com.xbd.oa.utils;

public class ConstantUtil {
	/**
	 * 订单类型1 模拟报价
	 */
	public static final String OA_ORDER_TYPE_1 = "1";
	/**
	 * 订单类型2 样衣打版
	 */
	public static final String OA_ORDER_TYPE_2 = "2";
	/**
	 * 订单类型3 大货生产
	 */
	public static final String OA_ORDER_TYPE_3 = "3";
	/**
	 * 订单类型4 售后服务
	 */
	public static final String OA_ORDER_TYPE_4 = "4";
	
	/**
	 * 工作流1 模拟报价
	 */
	public static final String WORKFLOW_KEY_PROCESS1 = "oawf1";
	/**
	 * 工作流2 样衣打版
	 */
	public static final String WORKFLOW_KEY_PROCESS2 = "oawf2";
	/**
	 * 工作流3 大货生产
	 */
	public static final String WORKFLOW_KEY_PROCESS3 = "oawf3";
	
	/**
	 * 数据字典表  服装品类 type 
	 */
	public static final String DT_TYPE_CLOTH_CLASS = "1";
	/**
	 * 数据字典表  服装品类 基础品类--再设置工作流时一定要设置基础品类
	 */
	public static final String DT_TYPE_CLOTH_CLASS_BASE = "1";
	
	/**
	 * 定时任务 间隔时间  24小时
	 */
	public static final long NOTIFYSTAFF_TIMER = 24*60*60*1000;
	
	/**
	 * 定时任务 流程流转时提醒下一流程操作人员（订单提前3小时提醒）
	 */
	public static final long NOTIFYSTAFF_BEFORE3HOURS = 3*60*60*1000;
	
	/**
	 * 模板id 流程流转时，提醒客户  尊贵的亲，您的【style_code】订单已经在【step_name】，请耐心等待，如需帮助请致电4000-777-356
	 */
	public static final String TEMPLET_NOTIFY_CUSTOMER = "91002450";
	
	/**
	 * 模板id 流程流转时，提醒操作人 【style_code】款已流转到你这里了，请及时处理，谢谢！ 
	 */
	public static final String TEMPLET_NOFIFY_OPERATOR = "91002451";
	
	/**
	 * 模板id 流程超时时，提醒主管及操作人 款号为{style_code}订单在{step_name}超期了，请{operator}及时处理。
	 */
	public static final String TEMPLET_NOTIFY_OUTTIME = "91002468";
	
	/**
	 * 流程超时提醒状态 未提醒
	 */
	public static final String NOTIFYSTAFF_STATE_TIMEOUT_NO = "1";
	
	/**
	 * 流程超时提醒状态 已提醒
	 */
	public static final String NOTIFYSTAFF_STATE_TIMEOUT = "0";
	
	/**
	 * 流程流转提醒状态 未提醒
	 */
	public static final String NOTIFYSTAFF_STATE_REMIND_NO = "1";
	
	/**
	 * 流程流转提醒状态 已提醒
	 */
	public static final String NOTIFYSTAFF_STATE_REMIND = "0";
	
	/**
	 * 提醒类型 流转提醒
	 */
	public static final String NOTIFY_TYPE_REMIND = "remind";
	
	/**
	 * 提醒类型 超时提醒
	 */
	public static final String NOTIFY_TYPE_TIMEOUT = "timeout";
	
	/**
	 * 打板订单最后步骤
	 */
	public static final String DABAN_LAST_STEP_INX = "6";
	
	/**
	 * 大货订单最后步骤
	 */
	public static final String DAHUO_LAST_STEP_INX = "9";
	
	/**
	 * 观察者每日统计 时间间隔  每晚1:00  24小时
	 */
	public static final long STATISTICS_EVERYDAY_AUTORUN = 24*60*60*1000;
	
	/**
	 * 观察者APP 每周统计 时间间隔 每周一 晚2点
	 */
	public static final long STATISTICS_EVERYWEEK_AUTORUN = 7*24*60*60*1000;
	
	/**
	 * 观察者严重超时时间
	 */
	public static final long STATISTICS_TIMEOUT_SERIOUS = 3*60*60*1000;
	
	/**
	 * 删除已存在的打版清单SQL
	 */
	public static final String DEL_DABAN_INVENTORY_SQL = 
			"delete from oa_da_ban_info where oa_material_list = {0}";
	/**
	 * 打版清单SQL
	 */
	public static final String DABAN_INVENTORY_SQL = 
			"insert into oa_da_ban_info(unit_num,weight,component,delivery_time,shear_price,unit,goods_price,goods_unit,buyer_loss,paper_tube,deviation,position,it_memo,pur_memo,buffon,oa_material_list)" 
			+" select da.unit_num,da.weight,da.component,da.delivery_time,da.shear_price,da.unit,da.goods_price,da.goods_unit,da.buyer_loss,da.paper_tube,da.deviation,da.position,da.it_memo,da.pur_memo,da.buffon,{0}" 
			+" from oa_da_ban_info da"
			+" where da.oa_material_list = {1}";
	
	/**
	 * 删除已存在的大货清单SQL
	 */
	public static final String DEL_DAHUO_INVENTORY_SQL = 
			"delete from oa_da_huo_info where oa_material_list = {0}";
	/**
	 * 大货清单SQL
	 */
	public static final String DAHUO_INVENTORY_SQL = 
			"insert into oa_da_huo_info(unit_num,need_num,org,num,price,total_price,test_price,freight,total,buyer_loss,paper_tube,deviation,position,it_memo,pur_memo,buffon,oa_material_list)"
			+" select da.unit_num,da.need_num,da.org,da.num,da.price,da.total_price,da.test_price,da.freight,da.total,da.buyer_loss,da.paper_tube,da.deviation,da.position,da.it_memo,da.pur_memo,da.buffon,{0}"
			+" from oa_da_huo_info da"
			+" where da.oa_material_list = {1}";
	/**
	 * 打版清单SQL,只复制布封、单件用量、位置说明、技术备注
	 */
	public static final String DABAN_INVENTORY_PART_SQL = 
			"insert into oa_da_ban_info(unit_num,position,it_memo,pur_memo,buffon,oa_material_list)" 
			+" select da.unit_num,da.position,da.it_memo,da.pur_memo,da.buffon,{0}" 
			+" from oa_da_huo_info da"
			+" where da.oa_material_list = {1}";
	/**
	 * 大货清单SQL,,只复制布封、单件用量、位置说明、技术备注
	 */
	public static final String DAHUO_INVENTORY_PART_SQL = 
			"insert into oa_da_huo_info(unit_num,position,it_memo,pur_memo,buffon,oa_material_list)"
			+" select da.unit_num,da.position,da.it_memo,da.pur_memo,da.buffon,{0}"
			+" from oa_da_ban_info da"
			+" where da.oa_material_list = {1}";
	/**
	 * 删除已存在的尺寸表
	 */
	public static final String DEL_TECH_CLOTHES_SIZE_SQL = 
			"delete from oa_clothes_size where oa_order_id = {0}";
	/**
	 * 技术尺寸表
	 */
	public static final String TECH_CLOTHES_SIZE_SQL = 
			"insert into oa_clothes_size(type,unit,sample_size,oa_order_id)"
			+" SELECT type,unit,sample_size,{0} from oa_clothes_size where oa_order_id = {1}";
	/**
	 * 删除已存在的尺寸详情表
	 */
	public static final String DEL_TECH_CLOTHES_DETAILS_SIZE_SQL = 
			"delete from oa_clothes_size_detail where oa_order = {0}";
	/**
	 * 技术尺寸详情表
	 */
	public static final String TECH_CLOTHES_DETAILS_SIZE_SQL = 
			"insert into oa_clothes_size_detail(position,cloth_size,sample_page_size,tolerance,oa_order)"
			+" SELECT position,{0},sample_page_size,tolerance,{1} from oa_clothes_size_detail where oa_order = {2}";
	
	/**
	 * 删除已存在的技术说明
	 */
	public static final String DEL_TECH_PROCESS_EXPLAIN_SQL = 
			"delete from oa_process_explain where oa_order_id = {0}";
	/**
	 * 技术加工说明
	 */
	public static final String TECH_PROCESS_EXPLAIN_SQL = 
			"insert into oa_process_explain(special_art,cut_art,sewing,tail_button,sewing_pic,measure_pic,tail_ironing,tail_card,tail_packaging,oa_order_id)"
			+" SELECT special_art,cut_art,sewing,tail_button,sewing_pic,measure_pic,tail_ironing,tail_card,tail_packaging,{0} from oa_process_explain where oa_order_id = {1}";
	
}