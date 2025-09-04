<template>
    <div v-if="Name">
        <div style="display: flex;align-items: center;font-size: 14px;">
            <span style="font-weight: bolder;margin-right: 10px;">您当前的位置:</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/Front/MyOrderList' }">我的订单</el-breadcrumb-item>
                <el-breadcrumb-item>去支付</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="card margin-top-xs">
            <!-- 订单详情区域 -->
            <div class="order-details">
                <h2>订单详情</h2>
                <div class="detail-item">
                    <span>订单号：</span>
                    <span>{{ Name.OrderNo }}</span>
                </div>
                <div class="detail-item">
                    <span>订单状态：</span>
                    <span>{{ Name.OrderStatusFormat }}</span>
                </div>
                <div class="detail-item">
                    <span>石材信息：</span>
                    <span v-if="Name.OrderDets && Name.OrderDets.length > 0">
                        {{ Name.OrderDets[0].GoodDto.Name }} x {{ Name.OrderDets[0].Qty }}
                    </span>
                </div>
                <div class="detail-item">
                    <span>收货地址：</span>
                    <span>{{ Name.ReceiveAddress }}</span>
                </div>
                <div class="detail-item">
                    <span>收货人：</span>
                    <span>{{ Name.ReceiveName }}</span>
                </div>
                <div class="detail-item">
                    <span>联系电话：</span>
                    <span>{{ Name.ReceivePhone }}</span>
                </div>
                <div class="detail-item">
                    <span>支付金额：</span>
                    <span>¥{{ Name.TotalMoney }}</span>
                </div>
                <div class="detail-item highlight">
                    <span>支付剩余时间：</span>
                    <span>{{ remainingTime }}</span>
                </div>
                <div class="pay-actions">
                    <el-button type="primary" @click="AliPaySubmit(Name.OrderNo)" :disabled="remainingTime === '支付时间已过期'">
                        支付宝支付
                    </el-button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import SelectTab from '@/components/Select/SelectTab.vue';
import { mapGetters } from 'vuex';
export default {
    name: 'Name',
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', 'UserId'
        ])
    },
    components: {
        PaginationBox,
        SelectTab
    },
    data() {
        return {
            Name: null,
            remainingTime: '',
            timer: null
        }
    },
    watch: {
        "$route": async function (to, from) {
            await this.GetNameApi();
        }
    },
    created() {
        this.GetNameApi();
    },
    methods: {
        //得到数据接口
        async GetNameApi() {
            let { Data
            } = await this.$Post("/OrderInfo/Get",
                {
                    Id: this.$route.query.OrderInfoId
                })
            this.Name = Data;
            this.$nextTick(() => {
                this.startTimer();
            })
        },

        calculateRemainingTime() {
            if (!this.Name || !this.Name.CreationTime) return;

            const createTime = new Date(this.Name.CreationTime);
            // 支付时间为创建时间后的15分钟
            const expireTime = new Date(createTime.getTime() + 15 * 60 * 1000);
            const now = new Date();

            if (now >= expireTime) {
                this.remainingTime = '支付时间已过期';
                clearInterval(this.timer);
                return;
            }

            const diff = expireTime - now;
            const minutes = Math.floor(diff / 60000);
            const seconds = Math.floor((diff % 60000) / 1000);
            this.remainingTime = `${minutes}分${seconds}秒`;
        },

        startTimer() {
            this.calculateRemainingTime();
            this.timer = setInterval(this.calculateRemainingTime, 1000);
        },

        async AliPaySubmit(orderNo) {
            let { Data: requestContent } = await this.$Post(`/OrderInfo/AliPay`, {
                PayOrderNo: orderNo,
                CallBack: `http://${location.host}/#/Front/MyOrderList`
            });

            const payDiv = document.getElementById('payDiv');
            if (payDiv) {
                document.body.removeChild(payDiv);
            }

            const div = document.createElement('div');
            div.id = 'payDiv';
            div.innerHTML = requestContent;
            document.body.appendChild(div);
            document.getElementById('payDiv').getElementsByTagName('form')[0].submit();
        },
    },
    beforeDestroy() {
        if (this.timer) {
            clearInterval(this.timer);
        }
    }
}
</script>
<style scoped lang="scss">
/* 订单详情卡片样式 */
.order-details {
    padding: 20px;
}

/* 详情项样式 */
.detail-item {
    margin: 10px 0;
    display: flex;
    gap: 10px;
}

/* 高亮文本样式 */
.highlight {
    color: #f56c6c;
    font-weight: bold;
}

/* 支付按钮区域样式 */
.pay-actions {
    margin-top: 20px;
}
</style>