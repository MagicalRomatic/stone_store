<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">
                    <el-form-item label="评论" prop="Comment">
                        <el-input v-model.trim="searchForm.Comment" placeholder="请输入评论" :clearable="true"></el-input>
                    </el-form-item>

                    <el-form-item label="石材">
                        <SigleSelect url="/Good/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.GoodId" :where="{ ShopId: ShopId }">
                        </SigleSelect>
                    </el-form-item>

                </el-form>
            </div>

        </el-card>


        <el-dialog :title="formData.Id ? '修改订单评价' : '添加订单评价'" :visible.sync="editorShow" width="50%" :lock-scroll="true"
            height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody" style="max-height: 600px;overflow-y: scroll">

                    <el-col :span="24">
                        <el-form-item label="回复图片" prop="ReplyImageUrls">
                            <UploadImages :limit="1" v-model="formData.ReplyImageUrls"></UploadImages>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="回复评论" prop="ReplyContent">
                            <el-input type="textarea" :rows="2" v-model.trim="formData.ReplyContent" placeholder="请输入回复评论"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>

                </el-row>


                <el-row type="flex" justify="end" align="bottom">
                    <el-form-item>
                        <el-button type="primary" plain @click="CreateOrEditForm()">确 定</el-button>
                        <el-button @click="editorShow = false">取 消</el-button>
                    </el-form-item>
                </el-row>
            </el-form>

        </el-dialog>


        <PaginationTable ref="PaginationTableId" url="/OrderComment/List" :column="dataColum" :where="where">
            <template v-slot:header>

                <el-button type="danger" size="mini" icon="el-icon-delete" @click="ShowBatchDeleteModal()">批 量 删
                    除</el-button>
            </template>
            <template v-slot:Operate="scope">
                <el-button class="margin-top-xs" type="primary" size="mini" @click="ShowEditModal(scope.row.Id)">{{
                    scope.row.ReplyContent ? '修改' : '评价' }}</el-button>
                <el-button class="margin-top-xs" type="danger" size="mini" @click="ShowDeleteModal(scope.row.Id)">删
                    除</el-button>
            </template>
        </PaginationTable>
    </div>
</template>
    
<script>
import store from '@/store';
import { mapGetters } from 'vuex';
export default {
    name: "OrderCommentList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId", 'ShopId'
        ])
    },
    data() {

        return {
            where: {},
            searchForm: {},//搜索定义的数据对象
            dataColum: [
                {
                    key: "Id",
                    hidden: true,

                }, {
                    key: "OrderDto.OrderNo",
                    title: "订单编号",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "CommentScore",
                    title: "评分",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Comment",
                    title: "评论",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "ShopId",
                    hidden: true,
                },
                {
                    key: "ShopDto.Name",
                    title: "店铺名称",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "GoodId",
                    hidden: true,
                },
                {
                    key: "GoodDto.Name",
                    title: "石材名称",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "ImageUrls",
                    title: "评价图片",
                    type: store.getters.ColumnType.IMAGES,
                },
                {
                    key: "CommentUserId",
                    hidden: true,
                },
                {
                    key: "CommentUserDto.Name",
                    title: "评论人",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "OrderId",
                    hidden: true,
                },

                {
                    key: "ReplyImageUrls",
                    title: "回复图片",
                    type: store.getters.ColumnType.IMAGES,
                },
                {
                    key: "ReplyContent",
                    title: "回复内容",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    title: "操作",
                    width: "300px",
                    key: "Operate",
                    type: store.getters.ColumnType.USERDEFINED,
                },
            ],
            editModalFormRules: {
                "ShopId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "GoodId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "CommentUserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "OrderId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ImageUrls": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Comment": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "CommentScore": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ReplyImageUrls": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ReplyContent": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
            },
            formData: {

            },//保存或者修改定义的数据对象
            editorShow: false,

        };
    },
    created() {
        if (this.RoleType == '商家') {
            this.where.ShopId = this.ShopId;
        }
    },
    methods: {
        //修改属性
        async UpdateEntityAsync(Id, title, data) {
            let { Data } = await this.$PostSigleUpdate(`/OrderComment/Get`, `/OrderComment/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/OrderComment/Get`, { Id: Id });
            this.formData = Data;

            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/OrderComment/CreateOrEdit`, this.formData);

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
            await this.$PostDelete(`/OrderComment/Delete`, { Id: Id });
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
            await this.$PostDelete(`/OrderComment/BatchDelete`, { Ids: ids });

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
    