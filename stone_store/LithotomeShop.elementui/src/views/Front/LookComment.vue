<template>
    <div v-if="OrderComment">
        <div style="display: flex;align-items: center;font-size: 14px;">
            <span style="font-weight: bolder;margin-right: 10px;">您当前的位置:</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/Front/MyOrderList' }">评价列表</el-breadcrumb-item>
                <el-breadcrumb-item>评价详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="card margin-top-xs">
            <div class="good-info">
                <img :src="OrderComment.GoodDto.Cover" class="good-img">
                <div class="good-detail">
                    <h3>{{ OrderComment.GoodDto.Name }}</h3>
                    <div class="price">￥{{ OrderComment.GoodDto.Price }}</div>
                </div>
            </div>
            <div class="comment-section">
                <div class="user-info">
                    <img :src="OrderComment.CommentUserDto.ImageUrls ? OrderComment.CommentUserDto.ImageUrls : require('@/assets/emptyheadimg.jpg')"
                        class="avatar">
                    <span class="username">{{ OrderComment.CommentUserDto.Name }}</span>
                    <div class="score">
                        <el-rate v-model="OrderComment.CommentScore" disabled show-score></el-rate>
                    </div>
                    <span class="time">{{ OrderComment.CreationTime }}</span>
                </div>
                <div class="comment-content">
                    <p>{{ OrderComment.Comment }}</p>
                    <div class="image-list" v-if="OrderComment.ImageUrls">
                        <img v-for="(url, index) in OrderComment.ImageUrls.split(',')" :key="index" :src="url"
                            @click="previewImage(url)">
                    </div>
                </div>
                <div class="reply-section" v-if="OrderComment.ReplyContent">
                    <div class="reply-title">
                        <i class="el-icon-s-custom"></i>
                        <span>商家回复</span>
                    </div>
                    <p>{{ OrderComment.ReplyContent }}</p>
                    <div class="image-list" v-if="OrderComment.ReplyImageUrls">
                        <img v-for="(url, index) in OrderComment.ReplyImageUrls.split(',')" :key="index" :src="url"
                            @click="previewImage(url)">
                    </div>
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
    name: 'OrderComment',
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
            OrderComment: null,
        }
    },
    watch: {
        "$route": async function (to, from) {
            await this.GeOrderCommentApi();
        }
    },
    created() {
        this.GetOrderCommentApi();
    },
    methods: {
        //得到数据接口
        async GetOrderCommentApi() {
            let { Data
            } = await this.$Post("/OrderComment/Get",
                {
                    Id: 0,
                    OrderInfoId: this.$route.query.OrderInfoId
                })
            this.OrderComment = Data;
            this.$nextTick(() => { })
        },
    },
}
</script>
<style scoped lang="scss">
.card {
    background: #fff;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

/* 石材信息样式 */
.good-info {
    display: flex;
    padding: 20px;
    border-bottom: 1px solid #eee;

    .good-img {
        width: 120px;
        height: 120px;
        object-fit: cover;
        border-radius: 8px;
    }

    .good-detail {
        margin-left: 20px;

        h3 {
            margin: 0 0 10px 0;
            font-size: 18px;
        }

        .price {
            color: #ff6b6b;
            font-size: 20px;
            font-weight: bold;
        }
    }
}

/* 评价区域样式 */
.comment-section {
    padding: 20px;

    .user-info {
        display: flex;
        align-items: center;
        margin-bottom: 20px;

        .avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-right: 10px;
        }

        .username {
            font-weight: bold;
            margin-right: 20px;
        }

        .score {
            margin-right: 20px;
        }

        .time {
            color: #999;
            font-size: 14px;
        }
    }

    .comment-content {
        line-height: 1.6;

        p {
            margin-bottom: 15px;
            text-align: justify;
        }
    }
}

/* 图片列表样式 */
.image-list {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    margin-top: 15px;

    img {
        width: 100px;
        height: 100px;
        object-fit: cover;
        border-radius: 4px;
        cursor: pointer;
        transition: transform 0.3s;

        &:hover {
            transform: scale(1.05);
        }
    }
}

/* 商家回复样式 */
.reply-section {
    margin-top: 20px;
    background: #f8f9fa;
    padding: 15px;
    border-radius: 4px;

    .reply-title {
        display: flex;
        align-items: center;
        color: #409EFF;
        margin-bottom: 10px;

        i {
            margin-right: 5px;
        }
    }

    p {
        color: #666;
        margin: 10px 0;
    }
}
</style>