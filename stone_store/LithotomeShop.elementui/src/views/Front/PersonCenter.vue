<template>
    <div class="page-container ">

        <div class="card">
            <el-page-header @back="goBack()" content="个人中心">
            </el-page-header>
        </div>

        <div class="card margin-top-xs">
            <div class="userInfo">
                <div class="section-left">
                    <img :src="UserInfo.ImageUrls ? UserInfo.ImageUrls : require('@/assets/emptyheadimg.jpg')">
                </div>
                <div class="section-right">
                    <div class="name"> {{ UserInfo.Name }}</div>
                    <div class="my"> 我的账号</div>
                    <div class="my"> {{ UserInfo.UserName }}</div>
                </div>
            </div>
            <!-- <div class="order-summary">
                <div class="item">
                    <div class="tit">0</div>
                    <div class="tit-desc">待付款</div>
                </div>
                <div class="item">
                    <div class="tit">0</div>
                    <div class="tit-desc">待发货</div>
                </div>
                <div class="item">
                    <div class="tit">0</div>
                    <div class="tit-desc">待收货</div>
                </div>
                <div class="item">
                    <div class="tit">0</div>
                    <div class="tit-desc">待评价</div>
                </div>
                <div class="item">
                    <div class="tit">0</div>
                    <div class="tit-desc">退款/售后</div>
                </div>
            </div> -->
        </div>

        <div class="margin-top-xs menu-content card">
            <ul class="menu-list-box">
                <li @click="ToUrl('/Front/UserPerson')">
                    <img :src="require('@/assets/个人信息.png')" class="menu-icon">
                    <div class="tit">个人信息</div>
                </li>
                <li @click="ToUrl('/Front/BuyCard')">
                    <img :src="require('@/assets/购物车购物.png')" class="menu-icon">
                    <div class="tit">我的购物车</div>
                </li>
                <li @click="ToUrl('/Front/MyOrderList')">
                    <img :src="require('@/assets/0-订单管理.png')" class="menu-icon">
                    <div class="tit">我的订单</div>
                </li>
                <li @click="ToUrl('/Front/MyUserAddress')">
                    <img :src="require('@/assets/收货地址.png')" class="menu-icon">

                    <div class="tit">收货地址</div>
                </li>
                <li @click="ToUrl('/Front/GoodCollect')">
                    <img :src="require('@/assets/石材.png')" class="menu-icon">

                    <div class="tit">收藏的石材</div>
                </li>
                <li @click="ToUrl('/Front/ShopCollect')">
                    <img :src="require('@/assets/收藏.png')" class="menu-icon">

                    <div class="tit">收藏的店铺</div>
                </li>

                <li @click="ToUrl('/Front/PasswordEdit')">
                    <img :src="require('@/assets/修改密码.png')" class="menu-icon">

                    <div class="tit">修改密码</div>
                </li>
                <li @click="ToUrl('/Front/MyLeaveFeedBackList')">
                    <img :src="require('@/assets/反馈.png')" class="menu-icon">
                    <div class="tit">我的反馈</div>
                </li>
                <li @click="LogOut()">
                    <img :src="require('@/assets/退出.png')" class="menu-icon">

                    <div class="tit">退出</div>
                </li>
            </ul>

        </div>

        <div class="card margin-top-xs">
            <div class="header-title">猜你喜欢</div>
            <Pagination url="/Good/GetRecommendList" :where="{}">
                <template v-slot:content="{ data }">
                    <GoodListCard :DataList="data"></GoodListCard>
                </template>
            </Pagination>
        </div>

    </div>
</template>

<script>
import Pagination from "@/components/Pagination/PaginationBox.vue";
import { mapGetters } from 'vuex';
import GoodListCard from "./GoodListCard.vue";
export default {
    name: "GoodCollectList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    components: {
        Pagination,
        GoodListCard
    },
    data() {

        return {


        };
    },
    created() {

    },
    methods: {

        //返回上一个页面
        goBack() {
            this.$router.go(-1);
        },
        ToUrl(url) {
            this.$router.push({
                path: url
            })
        },

        async LogOut() {
            await this.$store.dispatch('Logout')
            this.$router.push(`/`);
        },



    },
};
</script>
<style scoped>
.userInfo {
    display: flex;
}

.userInfo .section-left {}

.userInfo .section-left img {
    border-radius: 50%;
    width: 100px;
    height: 100px;
}

.userInfo .section-right {
    margin-left: 20px;
}

.userInfo .section-right .name {
    font-weight: bolder;
    font-size: 20px;
}


.userInfo .section-right .my {
    margin-top: 5px;
    color: #7c889c;
    font-size: 14px;
}

.order-summary {
    margin-top: 15px;
    display: flex;
    justify-content: space-between;
}

.order-summary .item {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.order-summary .item .tit {
    font-weight: bolder;

}

.order-summary .item .tit-desc {
    margin-top: 10px;
}

.menu-content {
    display: flex;
    flex-wrap: nowrap;
}



.menu-content .menu-list-box {
    display: flex;
    flex-wrap: wrap;
    width: 100%;


}

.menu-content .menu-list-box li {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    border: 1px solid transparent;
    padding: 30px;
    border-radius: 10px;
    width: 25%;


}

.menu-content .menu-list-box i {
    font-size: 35px;
}

.menu-content .menu-list-box li:hover {
    border: 1px solid var(--primary-color);
}

.menu-content .menu-list-box .tit {
    margin-top: 10px;
}

.menu-icon {
    width: 50px;
    height: 50px;
}
</style>