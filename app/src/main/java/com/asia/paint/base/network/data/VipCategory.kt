package com.asia.paint.base.network.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 作者 : LiLei
 * 时间 : 2020/10/23.
 * 邮箱 :416587959@qq.com
 * 描述 :
 */
@Parcelize
data class VipCategory(
    val add_time: Int,
    val id: Int,
    val image: String,
    val name: String,
    val pid: Int,
    val sort: Int,
    val status: String
) : Parcelable