<template>
    <div>
        <div style="display: flex;align-items: center;font-size: 14px;">
            <span style="font-weight: bolder;margin-right: 10px;">您当前的位置:</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/Front/PersonCenter' }">个人中心</el-breadcrumb-item>
                <el-breadcrumb-item>收藏的石材</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
          <div class=" margin-top-xs">



            <PaginationBox ref="GoodPagination" url="/GoodCollect/List" :where="{ CollectUserId: UserId }">
                <template v-slot:content="{ data }">
                    <div class="good-collect-list">
                        <div v-for="item in data" :key="item.Id" class="good-collect-item">
                            <div class="good-card">
                                <div class="image-wrapper1">
                                    <img :src="item.GoodDto.Cover" :alt="item.GoodDto.Name">
                                    <div class="image-overlay">
                                        <el-button type="primary" size="small" @click="LookBtn(item)">
                                            查看详情
                                        </el-button>
                                    </div>
                                </div>
                                <div class="content">
                                    <h3 class="title">{{ item.GoodDto.Name }}</h3>
                                    <div class="price-box">
                                        <span class="price-prefix">￥</span>
                                        <span class="price">{{ item.GoodDto.Price }}</span>
                                        <span class="unit">/{{ item.GoodDto.Unit }}</span>
                                    </div>
                                    <div class="stock-info">
                                        <i class="el-icon-goods"></i>
                                        库存: {{ item.GoodDto.Stock }} {{ item.GoodDto.Unit }}
                                    </div>
                                    <div class="action-buttons">
                                        <el-button type="danger" size="small" @click="ShowDeleteModal(item.Id)">
                                            <i class="el-icon-star-off"></i> 取消收藏
                                        </el-button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </template>
            </PaginationBox>



            <div class="card margin-top-xs">
                <div class="header-title">根据您的记录推荐</div>
                <PaginationBox ref="GoodRecommendPagination" url="/Good/GetRecommendList" :where="{}">
                    <template v-slot:content="{ data }">
                        <GoodListCard :DataList="data"></GoodListCard>
                    </template>
                </PaginationBox>
            </div>
        </div>
    </div>
</template>

<script>
import PaginationBox from "@/components/Pagination/PaginationBox.vue";
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
        PaginationBox: PaginationBox,
        GoodListCard
    },
    data() {

        return {
            searchForm: {},//搜索定义的数据对??

            editModalFormRules: {
                "GoodId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "CollectUserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
            },
            formData: {

            },//保存或者修改定义的数据对象
            editorShow: false,

        };
    },
    created() {

    },
    methods: {
        LookBtn(item) {
            this.$router.push({
                path: '/Front/Good',
                query: {
                    GoodId: item.GoodId,

                }
            })
        },
        //返回上一个页面
        goBack() {
            this.$router.go(-1);
        },
        //修改属性
        async UpdateEntityAsync(Id, title, data) {
            let { Data } = await this.$PostSigleUpdate(`/GoodCollect/Get`, `/GoodCollect/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/GoodCollect/Get`, { Id: Id });
            this.formData = Data;

            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/GoodCollect/CreateOrEdit`, this.formData);

                    if (Success) {
                        this.editorShow = false;
                        this.$refs.PaginationTableId.Reload(this.searchForm);
                    }
                }
            })
        },
        /**
         * 单个删除的时候会触发
         */
        async ShowDeleteModal(Id) {
            await this.$PostDelete(`/GoodCollect/Delete`, { Id: Id });
            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 批量删除的时候会触发
         */
        async ShowBatchDeleteModal() {
            var ids = this.$refs.PaginationTableId.GetSelectionRow().map(x => x.Id);
            if (ids.length == 0) {
                this.$message.error("你选择需要删除的记录");
                return;
            }
            await this.$PostDelete(`/GoodCollect/BatchDelete`, { Ids: ids });

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击表格搜索按钮会触???
         */
        async SearchClick() {
            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击清空表单会触发
         */
        async ResetClick() {
            this.searchForm = {};
            this.$refs.PaginationTableId.Reload(this.searchForm);
        },




    },
};
</script>

<style lang="scss" scoped>
.good-collect-list {
    width: 100%;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 24px;
    padding: 20px;
}

.good-collect-item {
    transition: all 0.3s ease;

    &:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
    }
}

.good-card {
    background: #fff;
    border-radius: 12px;
    overflow: hidden;
    height: 100%;
    display: flex;
    flex-direction: column;

    .image-wrapper1 {
        position: relative;
        width: 100%;
        padding-top: 100%; // 1:1 aspect ratio
        overflow: hidden;

        img {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.5s ease;
        }

        .image-overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.3);
            display: flex;
            align-items: center;
            justify-content: center;
            opacity: 0;
            transition: opacity 0.3s ease;
        }

        &:hover {
            img {
                transform: scale(1.1);
            }

            .image-overlay {
                opacity: 1;
            }
        }
    }

    .content {
        padding: 16px;
        flex: 1;
        display: flex;
        flex-direction: column;

        .title {
            font-size: 16px;
            font-weight: 600;
            margin: 0 0 12px;
            color: #333;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            line-height: 1.4;
        }

        .price-box {
            margin: 12px 0;
            color: #ff4d4f;

            .price-prefix {
                font-size: 14px;
            }

            .price {
                font-size: 24px;
                font-weight: bold;
            }

            .unit {
                color: #999;
                font-size: 13px;
                margin-left: 4px;
            }
        }

        .stock-info {
            color: #666;
            font-size: 14px;
            margin-bottom: 16px;
            display: flex;
            align-items: center;
            gap: 6px;

            i {
                color: #409EFF;
            }
        }

        .action-buttons {
            margin-top: auto;
            display: flex;
            justify-content: flex-end;
        }
    }
}
</style>