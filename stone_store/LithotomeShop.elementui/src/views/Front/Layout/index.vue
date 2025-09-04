<template>
    <div>
        <el-container>
            <el-header style="background-color: white;width: 100%;position: fixed;z-index:1000">
                <el-menu :default-active="active" class="main-container" mode="horizontal" text-color="black"
                    active-text-color="var(--primary-color)">
                    <el-menu-item index="">
                        <img style="height: 50px;object-fit: contain;" :src="require('@/assets/logo.png')">
                    </el-menu-item>

                    <el-menu-item index="/Front/Home" @click="ToPath('/Front/Home')">天然石材交易管理系统</el-menu-item>
                    <el-menu-item index="/Front/ArticleList" @click="ToPath('/Front/ArticleList')">石材资讯</el-menu-item>
                    <el-menu-item index="/Front/SysNoticeList" @click="ToPath('/Front/SysNoticeList')">系统通知</el-menu-item>
                    <el-menu-item index="/Front/LeaveFeedBackAdd" @click="ToPath('/Front/LeaveFeedBackAdd')"
                        v-if="Token">反馈申请</el-menu-item>
                    <el-menu-item style="float: right;" @click="ToPath('/Front/PersonCenter')"
                        v-if="Token && RoleType == '用户'">{{ UserInfo.UserName }}</el-menu-item>
                    <el-menu-item style="float: right;" v-if="Token && RoleType == '用户'">
                        <RouterLink :to="{ path: '/Front/BuyCard' }"> 我的购物车</RouterLink>
                    </el-menu-item>
                    <el-menu-item style="float: right;" v-if="Token && RoleType == '用户'">
                        <RouterLink :to="{ path: '/Front/WeChat' }"> 我的会话</RouterLink>
                    </el-menu-item>
                    <el-menu-item index="Register" style="float: right;" v-if="!Token"
                        @click="ToRegister()">注册</el-menu-item>
                    <el-menu-item index="Login" style="float: right;" v-if="!Token" @click="ToLogin()">登录</el-menu-item>


                </el-menu>

            </el-header>
            <div style="height: 50px;"></div>
            <el-main class="main-container main-box">
                <router-view></router-view>
            </el-main>
            <div style="height: 20px;"></div>
            <Footer></Footer>
        </el-container>


    </div>
</template>

<script>

import { mapGetters } from 'vuex';
import Footer from "./Footer.vue";
export default {
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {
        return {
            active: 'home',

        }
    },
    components: {

        Footer
    },
    created() {

    },

    methods: {
        //前往登录
        ToLogin() {
            this.$router.push({
                path: "/Login"
            })
        },
        //前往注册
        ToRegister() {
            this.$router.push({
                path: "/Register"
            })
        },
        //退出
        async LoginOut() {
            await this.$store.dispatch('Logout')
            this.$router.push({
                path: "/Login"
            })
        },
        //跳转
        async ToPath(url) {
            this.$router.push({
                path: url
            })
        },
        //跳转到用户信息
        async ToUserInfo() {
            this.$router.push({
                path: "/Front/UserPerson"
            })
        },
        //跳转到修改密码
        async ToEditPassword() {
            this.$router.push({
                path: "/Front/PasswordEdit"
            })
        },
        //返回上一个页面
        goBack() {
            this.$router.go(-1)
        }

    },


}
</script>

<style scoped>
.main-container {
    width: var(--content-width);
    margin: 0 auto;
}

.main-box {
    min-height: calc(100vh - 120px);
}

.el-main {
    padding: 20px 0px 0px 0px !important;
    overflow: hidden;
}
</style>
