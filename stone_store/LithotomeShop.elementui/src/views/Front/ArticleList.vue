<template>
    <div>
        <div style="display: flex;align-items: center;font-size: 14px;">
            <span style="font-weight: bolder;margin-right: 10px;">您当前的位置:</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>资讯列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class=" ">
            <SelectTab url="/ArticleType/List" v-model="where.ArticleTypeId" BindName="Name" BindValue="Id" title="分类"
                @Select="ChangeArticleType">
            </SelectTab>
            <PaginationBox ref="PaginationBox" :where="where" url="/Article/List">
                <template v-slot:content="{ data }">
                    <div class="article-list">
                        <div v-for="item in data" :key="item.Id" class="article-item" @click="ToDetail(item.Id)">
                            <div class="article-cover">
                                <img :src="item.Cover" :alt="item.Title">

                            </div>
                            <div class="article-info">
                                <h3 class="article-title">{{ item.Title }}</h3>
                                <p class="article-content" v-html="item.Content"></p>
                                <div class="article-meta">
                                    <span>分类：{{ item.ArticleTypeDto.Name }}</span>
                                    <span>阅读量：{{ item.ReadCount }}</span>
                                    <span>发布时间：{{ item.CreationTime }}</span>
                                    <span>作者：{{ item.CreatorAppUserDto.Name || '未知' }}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </template>
            </PaginationBox>
        </div>
    </div>
</template>
<script>
import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import SelectTab from '@/components/Select/SelectTab.vue';
import { mapGetters } from 'vuex';
export default {
    name: 'Article',
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', 'UserId'])
    },
    components: {
        PaginationBox,
        SelectTab
    },
    data() {
        return {
            where: {}
        }
    },
    created() { },
    methods: {
        ChangeArticleType(Id) {
            this.$refs.PaginationBox.Reload(this.where);
        },
        ToDetail(Id) {
            this.$router.push({
                path: '/Front/ArticleDetail',
                query: {
                    ArticleId: Id,
                }
            })
        }
    },
}
</script>
<style scoped>
.article-list {
    width: 100%;
}

.article-item {
    display: flex;
    padding: 15px 0px;
    border-bottom: 1px solid #eee;
    cursor: pointer;
    transition: all 0.3s;
}

.article-item:hover {
    background-color: #f5f7fa;
}

.article-cover {
    position: relative;
    width: 200px;
    height: 140px;
    margin-right: 20px;
}

.article-cover img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 4px;
}

.top-tag {
    position: absolute;
    top: 10px;
    right: 10px;
    background-color: #f56c6c;
    color: white;
    padding: 2px 6px;
    border-radius: 2px;
    font-size: 12px;
}

.article-info {
    flex: 1;
}

.article-title {
    margin: 0 0 10px 0;
    font-size: 18px;
    color: #303133;
}

.article-content {
    margin: 0 0 10px 0;
    color: #606266;
    font-size: 14px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
}

.article-meta {
    color: #909399;
    font-size: 12px;
}

.article-meta span {
    margin-right: 15px;
}
</style>