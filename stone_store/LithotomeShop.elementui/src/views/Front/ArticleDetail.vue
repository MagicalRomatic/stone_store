<template>
    <div v-if="ArticleDetail">
        <div style="display: flex;align-items: center;font-size: 14px;">
            <span style="font-weight: bolder;margin-right: 10px;">您当前的位置:</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/Front/ArticleList' }">资讯列表</el-breadcrumb-item>
                <el-breadcrumb-item>{{ ArticleDetail.Title }}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="article-container">
            <!-- 文章头部信息 -->
            <div class="article-header">
                <h1 class="article-title">{{ ArticleDetail.Title }}</h1>
                <div class="article-meta">
                    <span class="meta-item">
                        <i class="fas fa-user"></i>
                        {{ ArticleDetail.CreatorAppUserDto.Name }}
                    </span>
                    <span class="meta-item">
                        <i class="fas fa-calendar"></i>
                        {{ ArticleDetail.CreationTime }}
                    </span>
                    <span class="meta-item">
                        <i class="fas fa-eye"></i>
                        {{ ArticleDetail.ReadCount }} 阅读
                    </span>
                    <span class="meta-item">
                        <i class="fas fa-folder"></i>
                        {{ ArticleDetail.ArticleTypeDto.Name }}
                    </span>
                </div>
            </div>

            <!-- 文章封面图 -->
            <div class="article-cover" v-if="ArticleDetail.Cover">
                <img :src="ArticleDetail.Cover" :alt="ArticleDetail.Title">
            </div>

            <!-- 文章内容 -->
            <div class="article-content" v-html="ArticleDetail.Content"></div>

            <!-- 返回按钮 -->
            <div class="article-footer">
                <button class="back-btn" @click="goBack">
                    <i class="fas fa-arrow-left"></i> 返回上一页
                </button>
            </div>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';



import Pagination from "@/components/Pagination/PaginationBox.vue";

export default {
    name: "ArticleDetail",
    components: {
        Pagination: Pagination,

    },
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {
        return {
            ArticleDetail: null,
            Shop: null,
            where: {},
        }
    },
    watch: {
        '$route': function (to, from) {

            // 在这里可以执行你需要的操作，例如更新数据、发送请求等
            if (to.path == '/Front/ArticleDetail') {
                this.GetArticleApi();

            }
        }
    },

    created() {

        this.GetArticleApi();


    },
    methods: {
        async AddReadCount() {
            await this.$Post("/Article/AddReadCount", { Id: this.$route.query.ArticleId })
        },

        //得到作品信息
        async GetArticleApi() {
            let { Data } = await this.$Post("/Article/Get", { Id: this.$route.query.ArticleId })
            this.ArticleDetail = Data;
            this.$nextTick(() => {
                this.AddReadCount();
            })
        },

        //返回上一个页面
        goBack() {
            this.$router.go(-1)
        },

    }

}
</script>

<style scoped>
/* 文章容器 */
.article-container {

    margin: 20px auto;
    padding: 20px;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

/* 文章标题 */
.article-title {
    font-size: 28px;
    color: #333;
    margin-bottom: 16px;
    text-align: center;
    line-height: 1.4;
}

/* 文章元信息 */
.article-meta {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    gap: 20px;
    color: #666;
    margin-bottom: 24px;
    font-size: 14px;
}

.meta-item {
    display: flex;
    align-items: center;
    gap: 6px;
}

/* 文章封面 */
.article-cover {
    margin-bottom: 24px;
    border-radius: 8px;
    overflow: hidden;
}

.article-cover img {
    width: 100%;
    height: auto;
    object-fit: cover;
}

/* 文章内容 */
.article-content {
    line-height: 1.8;
    color: #444;
    font-size: 16px;
}

.article-content p {
    margin-bottom: 16px;
}

.article-content img {
    max-width: 100%;
    height: auto;
    border-radius: 4px;
    margin: 16px 0;
}

/* 返回按钮 */
.article-footer {
    margin-top: 32px;
    text-align: center;
}

.back-btn {
    padding: 8px 20px;
    background: #4a90e2;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background 0.3s;
}

.back-btn:hover {
    background: #357abd;
}

@media (max-width: 768px) {
    .article-container {
        margin: 10px;
        padding: 15px;
    }

    .article-title {
        font-size: 24px;
    }

    .article-meta {
        gap: 10px;
    }
}
</style>
