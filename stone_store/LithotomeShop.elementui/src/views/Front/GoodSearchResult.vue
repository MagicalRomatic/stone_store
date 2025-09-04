<template>
    <div class="page-container ">
        <div style="display: flex;align-items: center;font-size: 14px;">
            <span style="font-weight: bolder;margin-right: 10px;">您当前的位置:</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/' }">搜索结果</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="margin-top-xs card">
            <el-page-header @back="goBack()" content="石材搜索">
            </el-page-header>

        </div>
        <div class="margin-top-xs">
            <GoodSearch :KeyWord="KeyWord" @Search="SearchCallBack"></GoodSearch>
        </div>
        <div class="margin-top-xs card">
            <div class="tab">
                <h3>搜索的结果如下</h3>

                <Pagination url="/Good/List" ref="Pagination" :where="{ KeyWord: KeyWord,IsOnSale:true}">
                    <template v-slot:content="{ data }">
                        <GoodListCard :DataList="data"></GoodListCard>
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
import store from '@/store';
import { mapGetters } from 'vuex';
import Pagination from "@/components/Pagination/PaginationBox.vue"
import GoodListCard from "./GoodListCard.vue"
import GoodSearch from "./GoodSearch.vue"

export default {
    name: "Home",
    components: {
        Pagination,
        GoodSearch,
        GoodListCard
    },
    data() {
        return {
            KeyWord: "",

        }
    },
    created() {
        this.KeyWord = this.$route.query.KeyWord;
    },
    methods: {
        //返回上一个页面
        goBack() {
            this.$router.go(-1)
        },
        SearchCallBack(e) {
            this.$refs.Pagination.Reload({ KeyWord: e });
        },

    }

}
</script>

<style scope></style>