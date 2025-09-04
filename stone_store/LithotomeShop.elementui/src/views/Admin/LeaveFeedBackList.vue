
<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">
                    <el-form-item label="反馈标题" prop="Title">
                        <el-input v-model.trim="searchForm.Title" placeholder="请输入反馈标题" :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="反馈人">
                        <SigleSelect url="/User/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.FeedbackUserId" :where="{ RoleType: 2 }">
                        </SigleSelect>
                    </el-form-item>
                    <el-form-item label="处理人">
                        <SigleSelect url="/User/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.DoWithUserId">
                        </SigleSelect>
                    </el-form-item>

                    <el-form-item label="是否处理">
                        <el-select v-model="searchForm.IsDoWith" :clearable="true" placeholder="请选择是否处理">
                            <el-option key="true" label="是" value="true">
                            </el-option>
                            <el-option key="false" label="否" value="false">
                            </el-option>
                        </el-select>
                    </el-form-item>

                </el-form>
            </div>

        </el-card>


        <el-dialog :title="'处理'" :visible.sync="editorShow" width="50%" :lock-scroll="true" height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody">
                    <el-col :span="24">
                        <el-form-item label="反馈标题" prop="Title">
                            <el-input type="text" v-model.trim="formData.Title" placeholder="请输入反馈标题" :disabled="true"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="反馈人" prop="FeedbackUserId">
                            <SigleSelect url="/User/List" columnName="Name" columnValue="Id" :disabled="true"
                                v-model="formData.FeedbackUserId">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="处理人" prop="DoWithUserId">
                            <SigleSelect url="/User/List" columnName="Name" columnValue="Id" :disabled="true"
                                v-model="formData.DoWithUserId">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="是否处理" prop="IsDoWith">
                            <el-switch v-model="formData.IsDoWith">
                            </el-switch>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="反馈内容" prop="Content">
                            <div v-html="formData.Content"></div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="回复内容" prop="ReplayContent">
                            <QillRichText v-model="formData.ReplayContent"></QillRichText>
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


        <PaginationTable ref="PaginationTableId" url="/LeaveFeedBack/List" :column="dataColum" :where="where">
            <template v-slot:header>

                <el-button type="danger" size="mini" icon="el-icon-delete" @click="ShowBatchDeleteModal()">批 量 删
                    除</el-button>
            </template>
            <template v-slot:Operate="scope">
                <el-button class="margin-top-xs" type="primary" size="mini" v-if="scope.row.IsDoWith != true"
                    @click="ShowEditModal(scope.row.Id)">处理</el-button>
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
    name: "LeaveFeedBackList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId", "ShopId"
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

                },
                {
                    key: "Title",
                    title: "反馈标题",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Content",
                    title: "反馈内容",
                    width: "140px",
                    type: store.getters.ColumnType.RICHTEXT,
                },
                {
                    key: "FeedbackUserId",
                    hidden: true,
                },
                {
                    key: "FeedbackUserDto.Name",
                    title: "反馈人",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "IsDoWith",
                    title: "是否处理",
                    width: "160px",

                    type: store.getters.ColumnType.JUDGMENTTAG,
                },
                {
                    key: "DoWithUserId",
                    hidden: true,
                },
                {
                    key: "DoWithUserDto.Name",
                    title: "处理人",


                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "ReplayContent",
                    title: "回复内容",

                    type: store.getters.ColumnType.RICHTEXT,
                },
                {
                    title: "操作",
                    width: "300px",
                    key: "Operate",
                    type: store.getters.ColumnType.USERDEFINED,
                },
            ],
            editModalFormRules: {
                "Title": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "FeedbackUserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "DoWithUserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "IsDoWith": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Content": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ReplayContent": [
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
        //修改属性
        async UpdateEntityAsync(Id, title, data) {
            let { Data } = await this.$PostSigleUpdate(`/LeaveFeedBack/Get`, `/LeaveFeedBack/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/LeaveFeedBack/Get`, { Id: Id });
            Data.IsDoWith = true;
            Data.DoWithUserId = this.UserId;
            this.formData = Data;

            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/LeaveFeedBack/CreateOrEdit`, this.formData);

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
            await this.$PostDelete(`/LeaveFeedBack/Delete`, { Id: Id });
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
            await this.$PostDelete(`/LeaveFeedBack/BatchDelete`, { Ids: ids });

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
