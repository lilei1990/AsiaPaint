package com.asia.paint.base.network.data

/**
 * 作者 : LiLei
 * 时间 : 2020/10/21.
 * 邮箱 :416587959@qq.com
 * 描述 :
 */
data class VipGoodTask(
        val bonus: List<Bonu>,
        val display_info: String,
        val goods: List<Good>,
        val task: Task
)

data class Bonu(
    val add_time: String,
    val bonus_type: Int,
    val day: Int,
    val desc: String,
    val endtime: String,
    val group: Int,
    val ids: String,
    val image: String,
    val limit: Int,
    val min_money: String,
    val money: String,
    val name: String,
    val num: Int,
    val status: Int,
    val strtime: String,
    val type: Int,
    val type_id: Int,
    val url: String
)

data class Good(
    val default_image: List<String>,
    val goods_id: Int,
    val goods_name: String,
    val number: Int,
    val price: String
)

data class Task(
    val goods_ids: String,
    val id: Int,
    val kind: String,
    val price: String,
    val type_ids: String
)