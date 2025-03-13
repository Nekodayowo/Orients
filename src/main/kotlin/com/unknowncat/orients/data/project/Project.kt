package com.unknowncat.orients.data.project

import com.unknowncat.orients.data.converter.StringListConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "project")
data class Project(
    @Id val id: Long,

    @Column val name: String,

    @Column val description: String,

    @Column val createUserId: Long,

    @Column var activeUserId: Long, //接手用户id

    @Column var projectState: Int, //项目状态 0-待接手 1-进行中 2-已完成 3-审核中 4-已删除 5-审核未通过
    /*
    typeId 项目分类id

    关于tid分区
    一个6位的十六进制数代表一个完整的分区
    每两位算一个子分区
    例如：#1415AB
    如果  14 -> 计算机科学
         15 -> 算法与数据结构
         AB -> 数据结构
    那么 #1415AB 就代表 计算机科学 -> 算法与数据结构 -> 数据结构

    分类标签在数据库中的结构
    tid有属性(code, name, parentTid)
    name -> 名称
    code -> 编号
    parentTid -> 父分区

    例如 算法与数据结构: Tid(15, "算法与数据结构", 14)
     */
    @Column val typeId: String, //项目分类

    @Column val createdTime: Long = System.currentTimeMillis(),

    @Column var latestUpdateTime: Long = System.currentTimeMillis(),

    @Column val publishLocation: String,

    @Convert(converter = StringListConverter::class)
    @Column val difficult: ArrayList<String> = arrayListOf(), //前端Lv7,后端Lv8

    @Convert(converter = StringListConverter::class)
    @Column val attaches: ArrayList<String> = arrayListOf(), //URL:http://.....jpg/,DESC:文字附件

    @Column var releaseUrl: String = "",

    @Column var notPassReason : String = "",
    )
