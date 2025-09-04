<template>
    <div v-if="Shop != null">
        <div style="display: flex;align-items: center;font-size: 14px;">
            <span style="font-weight: bolder;margin-right: 10px;">您当前的位置:</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/' }">店铺详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="margin-top-xs card">
            <el-page-header @back="goBack()" content="店铺石材">
            </el-page-header>
            <div class="shop margin-top-xs">
                <img :src="Shop.LogoCover" class="shop-logo">
                <div class="shop-name">店铺:<span>{{ Shop.Name }}</span></div>
                <div> <el-button @click="CollectBtn()">{{ CollectId > 0 ? '取 消 收 藏' : '收 藏' }}</el-button></div>
            </div>
        </div>

        <div class="margin-top-xs card">
            <div class="tab">

                <div class="header-title">石材分类</div>
                <el-tabs class="margin-top-lg" v-model="SelectShopGoodTypeId" @tab-click="handleClick">
                    <el-tab-pane label="全部" :name="''"></el-tab-pane>
                    <el-tab-pane v-for="(item, index) in GoodTypeList" :key="index" :label="item.Name"
                        :name="item.Id.toString()"> </el-tab-pane>
                </el-tabs>

                <Pagination url="/Good/List" ref="Pagination" :where="where">
                    <template v-slot:content="{ data }">
                        <GoodListCard :DataList="data"></GoodListCard>
                    </template>

                </Pagination>
            </div>


        </div>

        <div class="margin-top-xs card" v-if="Token">
            <div class="tab">
                <div class="header-title">店铺优惠卷</div>


                <Pagination url="/Coupon/List" ref="CouponPagination"
                    :where="{ ShopId: $route.query.ShopId, GetCanAppoint: true }">
                    <template v-slot:content="{ data }">
                        <div class="coupon-container">
                            <div v-for="(item, index) in data" @click="AppointCoupon(item)" class="coupon-item"
                                :key="index">
                                <div class="coupon-left">
                                    <div class="coupon-amount">
                                        <span class="currency">¥</span>
                                        <span class="number">{{ item.Title }}</span>
                                    </div>
                                    <div class="coupon-condition">满 {{ item.UseMoney }} 可用</div>
                                </div>
                                <div class="coupon-right">
                                    <div class="coupon-desc">优惠券</div>
                                    <div class="coupon-time">{{ item.BeginActiveTime }}~{{ item.EndActiveTime }}</div>
                                    <div class="coupon-btn">立即领取</div>
                                </div>
                                <div class="coupon-circle coupon-circle-left"></div>
                                <div class="coupon-circle coupon-circle-right"></div>
                            </div>
                        </div>
                    </template>

                </Pagination>
            </div>


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
    name: "ShopGood",
    components: {
        Pagination: Pagination,
        GoodListCard
    },
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId", "ShopId"
        ])
    },
    data() {
        return {
            Shop: null,
            GoodTypeList: [],
            SelectShopGoodTypeId: '',
            CollectId: 0,
            where: {},
        }
    },
    created() {
        this.where.ShopId = this.$route.query.ShopId;
        this.where.IsOnSale = true;
        this.GetShopApi();
        this.GetGoodTypeApi();
        this.GetCollectApi();
    },
    methods: {
        //得到店铺信息
        async GetShopApi() {
            let { Data } = await this.$Post("/Shop/Get", { Id: this.$route.query.ShopId })
            this.Shop = Data;
        },
        //得到石材分类
        async GetGoodTypeApi() {
            let { Data: { Items } } = await this.$Post("/GoodType/List", { ShopId: this.$route.query.ShopId })
            this.GoodTypeList = Items;
        },
        //分类选择
        async handleClick(e) {

            this.$refs.Pagination.Reload({ ShopGoodTypeId: e.name });
        },
        //返回上一个页面
        goBack() {
            this.$router.go(-1)
        },
        //查询是否收藏过
        async GetCollectApi() {
            if (this.Token) {
                let { Data } = await this.$Post("/ShopCollect/Get", { Id: 0, ShopId: this.$route.query.ShopId, CollectUserId: this.UserId });
                this.CollectId = Data.Id;
            }
        },
        //领取优惠卷
        async AppointCoupon(item) {

            let { Data } = await this.$Post("/CouponRecord/CreateOrEdit", { CouponId: item.Id, ShopId: this.$route.query.ShopId, GetUserId: this.UserId, BeginActiveTime: item.BeginActiveTime, EndActiveTime: item.EndActiveTime });
            this.$message.success("领取成功!");
            this.$refs.CouponPagination.Reload();
        },
        //收藏按钮
        async CollectBtn() {
            if (this.Token) {
                if (this.CollectId > 0) {
                    //取消
                    await this.$Post("/ShopCollect/Delete", { Id: this.CollectId });
                    this.CollectId = 0;
                }
                else {
                    //创建
                    let { Data } = await this.$Post("/ShopCollect/CreateOrEdit", { ShopId: this.$route.query.ShopId, CollectUserId: this.UserId });
                    this.CollectId = Data.Id;
                }

            }
            else {
                this.$message.error("请先登录再操作");
            }
        },
    }

}
</script>

<style scope>
.shop {

    background-color: #F3F6F8;
    border-bottom: 1px solid #F3F6F8;
    overflow: hidden;
    display: flex;
    align-items: center;
    font-size: 14px;
    color: #11192D;
    border-radius: 20px;
    padding: 10px;
    font-weight: bold;
}

.shop-logo {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 10px;
    object-fit: fill;
}

.shop-name {
    flex: 1;
}

.tab {}

.tab .tit {
    font-weight: bolder;
}

.coupon-container {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    padding: 10px;
}

.coupon-item {
    width: calc((100% - 40px) / 3);
    /* 一行显示3个，减去两个间隔的20px */
    min-width: 280px;
    height: 100px;
    display: flex;
    background: linear-gradient(90deg, #ff5f6d 0%, #ffc371 100%);
    border-radius: 8px;
    position: relative;
    cursor: pointer;
    transition: transform 0.2s;
}

.coupon-item:hover {
    transform: translateY(-3px);
}

.coupon-left {
    width: 40%;
    padding: 15px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    color: white;
    border-right: 2px dashed rgba(255, 255, 255, 0.3);
}

.coupon-amount {
    display: flex;
    align-items: baseline;
}

.currency {
    font-size: 16px;
    margin-right: 2px;
}

.number {
    font-size: 10px;
    font-weight: bold;
}

.coupon-condition {
    font-size: 12px;
    margin-top: 5px;
}

.coupon-right {
    width: 60%;
    padding: 15px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.coupon-desc {
    color: white;
    font-size: 16px;
    font-weight: bold;
}

.coupon-time {
    color: rgba(255, 255, 255, 0.8);
    font-size: 12px;
}

.coupon-btn {
    background: white;
    color: #ff5f6d;
    padding: 4px 12px;
    border-radius: 12px;
    font-size: 12px;
    width: fit-content;
    font-weight: bold;
}

.coupon-circle {
    width: 20px;
    height: 20px;
    background: white;
    border-radius: 50%;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
}

.coupon-circle-left {
    left: -10px;
}

.coupon-circle-right {
    right: -10px;
}
</style>