<template>
    <div>
        <el-container style="height: 100vh">

            <el-header>
                <div class="bg-header">
                    <div class="flex align-center">
                        <img :src="require('@/assets/logo.png')" style="width: 50px; height: 50px;">
                        <span style="margin-left:10px">天然石材交易管理系统</span>
                    </div>
                    <div style="display: flex;align-items: center">
                        <el-avatar :size="60" :src="UserInfo.ImageUrls" size="medium">
                            <img :src="require('@/assets/默认头像.png')" />
                        </el-avatar>
                        <div style="margin-left:10px">
                            <el-dropdown>
                                <span class="el-dropdown-link" style="color:white">
                                    {{ UserInfo.Name }}<i class="el-icon-arrow-down el-icon--right"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item>
                                        <div @click="ToPath('/Admin/UserPerson')">个人信息</div>

                                    </el-dropdown-item>
                                    <el-dropdown-item>
                                        <div @click="ToPath('/Admin/PasswordEdit')">修改密码</div>

                                    </el-dropdown-item>
                                    <el-dropdown-item>
                                        <div @click="LoginOut()">退出</div>
                                    </el-dropdown-item>

                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>

                    </div>
                </div>


            </el-header>


            <el-container>
                <el-aside width="200px">

                    <el-menu class="menu-list" :router="true" active-text-color="var(--primary-color)">
                        <el-menu-item index="/Admin/Home">
                            <img class="meun-ico" :src="require('@/assets/控制台.png')">
                            <span>控制台</span>
                        </el-menu-item>

                        <template v-if="['管理员'].indexOf(RoleType) != -1">

                            <el-submenu index="/Admin/UserList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/用户管理.png')">
                                    <span>用户管理</span>
                                </template>
                                <el-menu-item index="/Admin/UserList">
                                    <img class="meun-ico" :src="require('@/assets/用户转换.png')">
                                    <span>用户信息</span>
                                </el-menu-item>

                            </el-submenu>

                            <el-submenu index="/Admin/ArticleTypeList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/资讯.png')">
                                    <span>资讯管理</span>
                                </template>
                                <el-menu-item index="/Admin/ArticleTypeList">
                                    <img class="meun-ico" :src="require('@/assets/资讯分类.png')">
                                    <span>资讯类型</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/ArticleList">
                                    <img class="meun-ico" :src="require('@/assets/资讯.png')">
                                    <span>资讯信息</span>
                                </el-menu-item>
                            </el-submenu>

                            <el-menu-item index="/Admin/LeaveFeedBackList">
                                <img class="meun-ico" :src="require('@/assets/反馈.png')">
                                <span>反馈信息</span>
                            </el-menu-item>
                            <el-menu-item index="/Admin/SysNoticeList">
                                <img class="meun-ico" :src="require('@/assets/通知.png')">
                                <span>系统通知</span>
                            </el-menu-item>
                            <el-menu-item index="/Admin/ShopList">
                                <img class="meun-ico" :src="require('@/assets/店铺.png')">
                                <span>店铺管理</span>
                            </el-menu-item>
                            <el-submenu index="/Admin/GoodTypeList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/石材.png')">
                                    <span>石材管理</span>
                                </template>
                                <el-menu-item index="/Admin/GoodTypeList">
                                    <img class="meun-ico" :src="require('@/assets/分类.png')">
                                    <span>石材分类</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/GoodList">
                                    <img class="meun-ico" :src="require('@/assets/石材 (1).png')">
                                    <span>石材信息</span>
                                </el-menu-item>
                            </el-submenu>

                            <el-submenu index="/Admin/OrderInfoList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/0-订单管理.png')">
                                    <span>订单管理</span>
                                </template>
                                <el-menu-item index="/Admin/OrderInfoList">
                                    <img class="meun-ico" :src="require('@/assets/原始记录.png')">
                                    <span>商家订单</span>
                                </el-menu-item>

                                <el-menu-item index="/Admin/OrderCommentList">
                                    <img class="meun-ico" :src="require('@/assets/评论.png')">
                                    <span>订单评价</span>
                                </el-menu-item>

                            </el-submenu>


                        </template>
                        <template v-if="['商家'].indexOf(RoleType) != -1">

                            <el-menu-item index="/Admin/OrderAnalyse" v-if="ShopDto.AuditStatus == 2">
                                <img class="meun-ico" :src="require('@/assets/分析统计.png')">
                                <span>订单分析</span>
                            </el-menu-item>

                            <el-menu-item index="/Admin/ShopEdit">
                                <img class="meun-ico" :src="require('@/assets/店铺.png')">
                                <span>我的店铺</span>
                            </el-menu-item>
                            <el-menu-item index="/Admin/WeChat">
                                <img class="meun-ico" :src="require('@/assets/聊天消息.png')">
                                <span>客户回话</span>
                            </el-menu-item>


                            <el-submenu index="/Admin/GoodTypeList" v-if="ShopDto.AuditStatus == 2">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/石材.png')">
                                    <span>石材管理</span>
                                </template>
                                <el-menu-item index="/Admin/GoodTypeList">
                                    <img class="meun-ico" :src="require('@/assets/分类.png')">
                                    <span>石材分类</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/GoodList">
                                    <img class="meun-ico" :src="require('@/assets/石材 (1).png')">
                                    <span>石材维护</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/GoodStockList">
                                    <img class="meun-ico" :src="require('@/assets/库存.png')">
                                    <span>库存记录</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/GoodReplenishmentList">
                                    <img class="meun-ico" :src="require('@/assets/预警.png')">
                                    <span>库存预警</span>
                                </el-menu-item>

                            </el-submenu>

                            <el-submenu index="/Admin/OrderInfoList" v-if="ShopDto.AuditStatus == 2">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/0-订单管理.png')">
                                    <span>订单管理</span>
                                </template>
                                <el-menu-item index="/Admin/OrderInfoList">
                                    <img class="meun-ico" :src="require('@/assets/原始记录.png')">
                                    <span>商家订单</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/OrderCommentList">
                                    <img class="meun-ico" :src="require('@/assets/评论.png')">
                                    <span>订单评价</span>
                                </el-menu-item>

                            </el-submenu>

                            <el-submenu index="/Admin/CouponList" v-if="ShopDto.AuditStatus == 2">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/优惠活动-5.png')">
                                    <span>优惠活动</span>
                                </template>
                                <el-menu-item index="/Admin/CouponList">
                                    <img class="meun-ico" :src="require('@/assets/优惠卷.png')">
                                    <span>优惠卷</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/CouponRecordList">
                                    <img class="meun-ico" :src="require('@/assets/0-订单管理.png')">
                                    <span>优惠卷发放记录</span>
                                </el-menu-item>

                            </el-submenu>

                        </template>



                    </el-menu>
                </el-aside>

                <el-main class="admin-main">
                    <el-breadcrumb separator-class="el-icon-arrow-right" class="margin-bottom-xs">
                        <el-breadcrumb-item v-for="(item, index) in breadList" :key="index" :to="item.path">{{
                            item.meta.title
                        }}</el-breadcrumb-item>
                    </el-breadcrumb>

                    <transition>
                        <router-view></router-view>
                    </transition>

                </el-main>

            </el-container>
        </el-container>
    </div>
</template>

<script>

import { adminRouters } from "@/router/index";
import { mapGetters } from "vuex";
export default {
    name: 'Layout',
    data() {
        return {
            routerMenu: [],
            breadList: [],
        }
    },
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'RoleTypeFormat', 'HasUserInfo', 'ColumnType', "UserId", "ShopId", "ShopDto"
        ])
    },
    watch: {
        $route() {
            this.getBreadcrumb();
        },
    },

    created() {
        this.routerMenu = adminRouters;
        this.getBreadcrumb();
    },
    methods: {
        //跳转
        async ToPath(url) {
            this.$router.push({
                path: url
            })
        },
        async LoginOut() {

            await this.$store.dispatch('Logout')
            window.location.reload('/Login')
        },
        isHome(route) {
            return route.path === "/Admin";
        },
        getBreadcrumb() {
            let matched = this.$route.matched;
            if (!this.isHome(matched[0])) {
                matched = [{ path: "/Admin", meta: { title: "控制台" } }].concat(matched);
            }
            this.breadList = matched;

        }
    }
}
</script>

<style scoped>
/* 自定义菜单样式 */
.custom-menu {
    height: 100%;
    width: 100%;
    background-color: #F5F7FA;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

/* 滚动容器 */
.menu-scroll-container {
    height: 100%;
    overflow-y: auto;
    overflow-x: hidden;
    scrollbar-width: thin;
}

/* 滚动条样式 */
.menu-scroll-container::-webkit-scrollbar {
    width: 6px;
}

.menu-scroll-container::-webkit-scrollbar-thumb {
    background-color: var(--primary-color, #409EFF);
    border-radius: 3px;
}

.menu-scroll-container::-webkit-scrollbar-track {
    background-color: #f0f0f0;
}

/* 菜单项基础样式 */
.menu-item,
.menu-group-title,
.menu-item-sub {
    display: flex;
    align-items: center;
    padding: 0 20px;
    transition: all 0.3s ease;
    cursor: pointer;
    position: relative;
    font-size: 14px;
    color: #303133;
}

/* 一级菜单项 */
.menu-item {
    height: 50px;
    margin: 10px 0;
    border-radius: 6px;
    margin-left: 10px;
    margin-right: 10px;
}

.menu-item i {
    font-size: 18px;
    margin-right: 12px;
    color: #606266;
}

.menu-item:hover {
    background-color: #E6F1FC;
}

.menu-item.active {
    background-color: var(--primary-color, #409EFF);
    color: white;
}

.menu-item.active i {
    color: white;
}

/* 菜单组标题 */
.menu-group {
    margin: 5px 0;
}

.menu-group-title {
    height: 42px;
    padding-left: 20px;
    font-weight: bold;
    display: flex;
    align-items: center;
    justify-content: space-between;
    color: #606266;
}

.menu-group-title i:first-child {
    font-size: 18px;
    margin-right: 12px;
}

.toggle-icon {
    transition: transform 0.3s ease;
}

.toggle-icon.expanded {
    transform: rotate(90deg);
}

.menu-group-title:hover {
    color: var(--primary-color, #409EFF);
}

/* 子菜单 */
.sub-menu {
    overflow: hidden;
    transition: max-height 0.3s ease;
    padding-left: 20px;
}

/* 子菜单项 */
.menu-item-sub {
    height: 40px;
    margin: 5px 0;
    border-radius: 4px;
    margin-right: 10px;
}

.menu-item-sub i {
    font-size: 16px;
    margin-right: 10px;
    color: #909399;
}

.menu-item-sub:hover {
    background-color: #E6F1FC;
}

.menu-item-sub.active {
    background-color: #E6F1FC;
    color: var(--primary-color, #409EFF);
}

.menu-item-sub.active i {
    color: var(--primary-color, #409EFF);
}
</style>

<style>
.el-header,
.el-footer {

    text-align: center;
    line-height: 60px;
    padding: 0px !important;
}

.el-aside {

    color: #333;
    text-align: center;
    line-height: 200px;
}


.menu-list {
    height: calc(100vh - 60px);
}

.el-submenu__title {
    text-align: left
}

.el-menu-item {
    text-align: left;
}

.admin-main {
    height: calc(100vh - 60px) !important;
}

.meun-ico {
    width: 25px;
    height: 25px;
    margin-right: 5px;
}
</style>