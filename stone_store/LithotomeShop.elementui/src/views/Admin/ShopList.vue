<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">
                    <el-form-item label="店铺名称" prop="NameLike">
                        <el-input v-model.trim="searchForm.NameLike" placeholder="请输入店铺名称" :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="店铺地址" prop="AddressLike">
                        <el-input v-model.trim="searchForm.AddressLike" placeholder="请输入店铺地址"
                            :clearable="true"></el-input>
                    </el-form-item>

                    <el-form-item label="审核原因" prop="AuditReasonLike">
                        <el-input v-model.trim="searchForm.AuditReasonLike" placeholder="请输入审核原因"
                            :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="店铺保障" prop="EnsureLike">
                        <el-input v-model.trim="searchForm.EnsureLike" placeholder="请输入店铺保障"
                            :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="商家账号">
                        <SigleSelect url="/User/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.UserId" :where="{ RoleType: 3 }">
                        </SigleSelect>
                    </el-form-item>
                    <el-form-item label="审核状态">
                        <SigleSelect url="/Select/ShopAuditStatusEnum" columnName="Name" :clearable="true"
                            columnValue="Code" v-model="searchForm.AuditStatus">
                        </SigleSelect>
                    </el-form-item>
                    <el-form-item label="审核人">
                        <SigleSelect url="/User/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.AuditUserId" :where="{ RoleType: 1 }">
                        </SigleSelect>
                    </el-form-item>
                    <el-form-item label="店铺详情" prop="ContentLike">
                        <el-input v-model.trim="searchForm.ContentLike" placeholder="请输入店铺详情"
                            :clearable="true"></el-input>
                    </el-form-item>
                </el-form>
            </div>

        </el-card>

        <el-dialog title="进行审核" :visible.sync="editorShow" width="50%" :lock-scroll="true" height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody" style="overflow-y: scroll">


                    <el-col :span="24">
                        <el-form-item label="审核状态" prop="AuditStatus">
                            <SigleSelect url="/Select/ShopAuditStatusEnum" :filterValue="[1]" columnName="Name"
                                columnValue="Code" v-model="formData.AuditStatus">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="审核原因" prop="AuditReason">
                            <el-input type="textarea" v-model.trim="formData.AuditReason" placeholder="请输入审核原因"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>


                </el-row>


                <el-row type="flex" justify="end" align="bottom">
                    <el-form-item>
                        <el-button type="primary" plain @click="Audit()">确 定</el-button>
                        <el-button @click="editorShow = false">取 消</el-button>
                    </el-form-item>
                </el-row>
            </el-form>

        </el-dialog>


        <PaginationTable ref="PaginationTableId" url="/Shop/List" :column="dataColum">
            <template v-slot:header>

                <ExportButton :where="searchForm" exportUrl="/Shop/Export"></ExportButton>

            </template>
            <template v-slot:Operate="scope">
                <!-- <el-button type="primary" size="mini" icon="el-icon-edit" @click="ShowEditModal(scope.row.Id)">修
                    改</el-button> -->
                <el-button type="primary" v-if="scope.row.AuditStatus == 1" size="mini"
                    @click="ShowEditModal(scope.row.Id)">进行审核</el-button>
                <el-button type="danger" size="mini" @click="ShowDeleteModal(scope.row.Id)">删 除</el-button>
            </template>
        </PaginationTable>
    </div>
</template>

<script>
import store from '@/store';
import { mapGetters } from 'vuex'
export default {
    name: "ShopList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {

        return {
            searchForm: {},//搜索定义的数据对象
            dataColum: [
                {
                    key: "Id",
                    hidden: true,
                },
                {
                    key: "AuditStatus",
                    hidden: true,
                },
                {
                    key: "Name",
                    title: "店铺名称",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "LogoCover",
                    title: "Logo",
                    type: store.getters.ColumnType.IMAGES,
                },

                {
                    key: "Email",
                    title: "邮箱",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Phone",
                    title: "电话",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Address",
                    title: "店铺地址",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "UserId",
                    hidden: true,
                },
                {
                    key: "UserDto.UserName",
                    title: "店铺账号",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },



                {
                    key: "AuditStatusFormat",
                    title: "审核状态",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "AuditUserId",
                    hidden: true,
                },
                {
                    key: "AuditUserDto.Name",
                    title: "审核人",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "AuditReason",
                    title: "审核原因",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Content",
                    title: "店铺详情",
                    width: "140px",
                    type: store.getters.ColumnType.RICHTEXT,
                },
                {
                    key: "Ensure",
                    title: "店铺保障",
                    width: "150px",
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
                "Name": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "UserName": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "UserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "AuditUserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "AuditStatus": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "LogoCover": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Address": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Email": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Phone": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "AuditReason": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Ensure": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Content": [
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
            let { Data } = await this.$PostSigleUpdate(`/Shop/Get`, `/Shop/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            console.log(this.UserId)
            let { Data } = await this.$Post(`/Shop/Get`, { Id: Id });
            Data.AuditUserId = this.UserId;

            this.formData = Data;

            this.editorShow = true;

        },

        /**
         * 店铺审核
         */
        async Audit() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/Shop/Audit`, this.formData);

                    if (Success) {
                        this.editorShow = false;
                        this.$refs.PaginationTableId.Reload(this.searchForm);
                    }
                }
            })
        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/Shop/ShopRegister`, this.formData);

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

            await this.$PostDelete(`/Shop/Delete`, { Id: Id });
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
            await this.$PostDelete(`/Shop/BatchDelete`, { Ids: ids });

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