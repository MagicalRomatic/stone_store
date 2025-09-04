<template>
    <div>
        <div style="display: flex;align-items: center;font-size: 14px;">
            <span style="font-weight: bolder;margin-right: 10px;">您当前的位置:</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/Front/PersonCenter' }">个人中心</el-breadcrumb-item>
                <el-breadcrumb-item>收藏的店铺</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class=" margin-top-xs">



            <PaginationBox ref="GoodPagination" url="/ShopCollect/List" :where="{ CollectUserId: UserId }">
                <template v-slot:content="{ data }">
                    <div class="shop-list">
                        <el-row :gutter="20">
                            <el-col :span="8" v-for="item in data" :key="item.Id">
                                <el-card class="shop-card" shadow="hover">
                                    <div class="shop-header">
                                        <el-avatar :size="60" :src="item.ShopDto.LogoCover"></el-avatar>
                                        <div class="shop-info">
                                            <h3>{{ item.ShopDto.Name }}</h3>
                                            <p class="audit-status">
                                                <el-tag size="small" type="success">{{ item.ShopDto.AuditStatusFormat
                                                }}</el-tag>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="shop-content">
                                        <p><i class="el-icon-location"></i> {{ item.ShopDto.Address }}</p>
                                        <p><i class="el-icon-phone"></i> {{ item.ShopDto.Phone }}</p>
                                        <p><i class="el-icon-message"></i> {{ item.ShopDto.Email }}</p>
                                        <p class="ensure"><i class="el-icon-success"></i> {{ item.ShopDto.Ensure }}</p>
                                    </div>

                                    <div class="shop-footer">
                                        <el-button type="primary" size="small" @click="LookBtn(item)">
                                            查看详情
                                        </el-button>
                                        <el-button type="danger" size="small" @click="ShowDeleteModal(item.Id)">
                                            取消收藏
                                        </el-button>
                                    </div>
                                </el-card>
                            </el-col>
                        </el-row>
                    </div>
                </template>
            </PaginationBox>


        </div>
    </div>
</template>

<script>
import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import { mapGetters } from 'vuex';
export default {
    name: "GoodCollectList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    components: {
        PaginationBox: PaginationBox,
    },
    data() {

        return {
            searchForm: {},//搜索定义的数据对象

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
                path: '/Front/ShopGood',
                query: {
                    ShopId: item.ShopId,

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
         * 点击表格搜索按钮会触发
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

<style scoped>
.shop-list {
    width: 100%;
    padding: 20px 0;
}

.shop-card {
    margin-bottom: 20px;
    transition: all 0.3s;
}

.shop-card:hover {
    transform: translateY(-5px);
}

.shop-header {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
}

.shop-info {
    margin-left: 15px;
}

.shop-info h3 {
    margin: 0 0 5px 0;
    font-size: 18px;
}

.audit-status {
    margin: 0;
}

.shop-content {
    padding: 10px 0;
}

.shop-content p {
    margin: 8px 0;
    color: #666;
    font-size: 14px;
}

.shop-content i {
    margin-right: 8px;
    color: #409EFF;
}

.shop-content .ensure {
    color: #67C23A;
    font-weight: bold;
}

.shop-footer {
    margin-top: 15px;
    display: flex;
    justify-content: flex-end;
    gap: 10px;
}
</style>