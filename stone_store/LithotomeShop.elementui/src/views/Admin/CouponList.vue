<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">
                    <el-form-item label="名称" prop="Title">
                        <el-input v-model.trim="searchForm.Title" placeholder="请输入名称" :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="有效时间">
                        <el-date-picker v-model="searchForm.ActiveTimeRange" type="datetimerange"
                            start-placeholder="开始有效时间" end-placeholder="结束有效时间" :default-time="['00:00:00']"
                            value-format="yyyy-MM-dd HH:mm:ss">
                        </el-date-picker>
                    </el-form-item>
                </el-form>
            </div>

        </el-card>


        <el-dialog :title="formData.Id ? '修改优惠卷' : '添加优惠卷'" :visible.sync="editorShow" width="50%" :lock-scroll="true"
            height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody">
                    <el-col :span="24">
                        <el-form-item label="名称" prop="Title">
                            <el-input type="text" v-model.trim="formData.Title" placeholder="请输入名称"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="有效时间" prop="ActiveTimeRange">
                            <el-date-picker v-model="formData.ActiveTimeRange" align="right" type="datetimerange"
                                range-separator="-" start-placeholder="开始有效时间" end-placeholder="结束有效时间" placeholder="选择"
                                value-format="yyyy-MM-dd HH:mm:ss">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="发放数量" prop="Qty">
                            <el-input type="number" v-model.trim="formData.Qty" placeholder="请输入发放数量"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="使用限制" prop="UseMoney">
                            <el-input type="number" v-model.trim="formData.UseMoney" placeholder="请输入使用限制"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="面值" prop="Money">
                            <el-input type="number" v-model.trim="formData.Money" placeholder="请输入面值"
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


        <PaginationTable ref="PaginationTableId" url="/Coupon/List" :column="dataColum" :where="{ ShopId: ShopId }">
            <template v-slot:header>
                <el-button type="primary" size="mini" plain icon="el-icon-edit" @click="ShowEditModal()">新 增</el-button>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="ShowBatchDeleteModal()">批 量 删
                    除</el-button>
            </template>
            <template v-slot:Operate="scope">
                <!-- <el-button class="margin-top-xs" type="primary" size="mini" @click="ShowEditModal(scope.row.Id)">修
                    改</el-button> -->
                <el-button class="margin-top-xs" type="danger" size="mini" @click="ShowDeleteModal(scope.row.Id)">删
                    除</el-button>
            </template>
        </PaginationTable>
    </div>
</template>

<script>
import store from '@/store';
import { mapGetters } from 'vuex'
export default {
    name: "CouponList",
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
                    title: "名称",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "UseMoney",
                    title: "使用限制",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Money",
                    title: "面值",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "ActiveTime",
                    title: "有效时间",
                    width: "350px",
                    type: store.getters.ColumnType.SHORTTEXT,
                    template: function (item, index) {
                        return `${item.BeginActiveTime}至${item.EndActiveTime}`
                    }
                },
                {
                    key: "Qty",
                    title: "总数量",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "GetQty",
                    title: "领取数量",

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
                "Title": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ActiveTimeRange": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

                "UserIds": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "UseMoney": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Money": [
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
            let { Data } = await this.$PostSigleUpdate(`/Coupon/Get`, `/Coupon/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/Coupon/Get`, { Id: Id });
            if (!Id) {
                Data.ShopId = this.ShopId;
                Data.GetQty = 0;
            }
            if (Data.BeginActiveTime && Data.EndActiveTime) {
                Data.ActiveTimeRange = [Data.BeginActiveTime, Data.EndActiveTime];
            }
            this.formData = Data;

            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    this.formData.BeginActiveTime = this.formData.ActiveTimeRange[0];
                    this.formData.EndActiveTime = this.formData.ActiveTimeRange[1];
                    var { Success } = await this.$Post(`/Coupon/CreateOrEdit`, this.formData);

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
            await this.$PostDelete(`/Coupon/Delete`, { Id: Id });
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
            await this.$PostDelete(`/Coupon/BatchDelete`, { Ids: ids });

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