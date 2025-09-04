<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">
                    <el-form-item label="名称" prop="NameLike">
                        <el-input v-model.trim="searchForm.NameLike" placeholder="请输入名称" :clearable="true"></el-input>
                    </el-form-item>

                    <el-form-item label="所属店铺" v-if="RoleType == '管理员'">
                        <SigleSelect url="/Shop/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.ShopId">
                        </SigleSelect>
                    </el-form-item>
                    <el-form-item label="所属商家" v-if="RoleType == '管理员'">
                        <SigleSelect url="/User/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.UserId" :where="{ RoleType: 3 }">
                        </SigleSelect>
                    </el-form-item>

                    <el-form-item label="分类">
                        <SigleSelect url="/GoodType/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.GoodTypeId" :where="{ IsShop: false }">
                        </SigleSelect>
                    </el-form-item>

                </el-form>
            </div>

        </el-card>


        <el-dialog :title="formData.Id ? '修改石材' : '添加石材'" :visible.sync="editorShow" width="50%" :lock-scroll="true"
            height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody" style="  height: 600px;overflow-y: scroll">
                    <el-col :span="24">
                        <el-form-item label="名称" prop="Name">
                            <el-input type="text" v-model.trim="formData.Name" placeholder="请输入名称"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>


                    <el-col :span="24">
                        <el-form-item label="分类" prop="GoodTypeId">
                            <SigleSelect url="/GoodType/List" columnName="Name" columnValue="Id"
                                v-model="formData.GoodTypeId" :where="{ IsShop: false }">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" v-if="ShopId">
                        <el-form-item label="店铺分类" prop="ShopGoodTypeId">
                            <SigleSelect url="/GoodType/List" columnName="Name" columnValue="Id" :where="{ ShopId: ShopId }"
                                v-model="formData.ShopGoodTypeId">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="封面" prop="Cover">
                            <UploadImages :limit="1" v-model="formData.Cover"></UploadImages>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="主图" prop="ImageUrls">
                            <UploadImages :limit="7" v-model="formData.ImageUrls"></UploadImages>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="单位" prop="Unit">
                            <el-input type="text" v-model.trim="formData.Unit" placeholder="请输入单位"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="价格" prop="Price">
                            <el-input type="number" v-model.trim="formData.Price" placeholder="请输入价格"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="库存下限" prop="ReplenishmentLimit">
                            <el-input type="number" v-model.trim="formData.ReplenishmentLimit" placeholder="请输入库存下限"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="详情内容" prop="Content">
                            <QillRichText v-model="formData.Content"></QillRichText>
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


        <PaginationTable ref="PaginationTableId" url="/Good/List" :column="dataColum" :where="where">
            <template v-slot:header>
                <el-button type="primary" size="mini" plain icon="el-icon-edit" @click="ShowEditModal()"
                    v-if="RoleType == '商家'">新 增</el-button>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="ShowBatchDeleteModal()"
                    v-if="RoleType == '商家'">批 量 删
                    除</el-button>
                <ExportButton :where="{ ...where, ...searchForm }" exportUrl="/Good/Export" v-if="RoleType == '商家'">
                </ExportButton>

            </template>
            <template v-slot:Operate="scope">
                <el-button type="primary" size="mini" @click="ShowEditModal(scope.row.Id)" v-if="RoleType == '商家'">修
                    改</el-button>
                <el-button style="background-color: hotpink;color:white" size="mini"
                    @click="ToGoodProp(scope.row.Id)">石材属性</el-button>
                <el-button style="background-color: rgb(62, 228, 84);color:white" size="mini"
                    v-if="scope.row.IsOnSale != true"
                    @click="UpdateEntityAsync(scope.row.Id, '你确定要上架吗？', { IsOnSale: true })">上架</el-button>
                <el-button style="background-color: rgb(163, 57, 101);color:white" size="mini"
                    v-if="scope.row.IsOnSale == true"
                    @click="UpdateEntityAsync(scope.row.Id, '你确定要下架吗？', { IsOnSale: false })">下架</el-button>

                <el-button type="danger" size="mini" @click="ShowDeleteModal(scope.row.Id)" v-if="RoleType == '商家'">删
                    除</el-button>
            </template>
        </PaginationTable>
    </div>
</template>

<script>
import store from '@/store';
import { mapGetters } from 'vuex';
export default {
    name: "GoodList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId", "ShopId"
        ])
    },
    data() {

        let where = {};
        if (store.getters.RoleType == "商家") {
            where.ShopId = store.getters.ShopId;
        }
        return {
            where: where,
            searchForm: {},//搜索定义的数据对象
            dataColum: [
                {
                    key: "Id",
                    hidden: true,

                },

                {
                    key: "Name",
                    title: "名称",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "GoodTypeDto.Name",
                    title: "石材分类名称",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Cover",
                    title: "封面",
                    type: store.getters.ColumnType.IMAGES,
                },

                {
                    key: "Price",
                    title: "价格",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Stock",
                    title: "库存",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "ReplenishmentLimit",
                    title: "库存下限",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },

                {
                    key: "Content",
                    title: "详情内容",
                    width: "140px",
                    type: store.getters.ColumnType.RICHTEXT,
                },
                {
                    key: "ShopId",
                    hidden: true,
                },
                {
                    key: "ShopDto.Name",
                    title: "店铺名称",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "IsOnSale",
                    title: "是否上架",
                    width: "150px",
                    type: store.getters.ColumnType.JUDGMENTTAG,
                },
                {
                    key: "UserId",
                    hidden: true,
                },
                // {
                //     key: "UserDto.Name",
                //     title: "商家名称",
                //     width: "150px",
                //     type: store.getters.ColumnType.SHORTTEXT,
                // },
                {
                    key: "Unit",
                    title: "单位",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },


                {
                    key: "GoodTypeId",
                    hidden: true,
                },
                {
                    key: "ImageUrls",
                    title: "主图",
                    type: store.getters.ColumnType.IMAGES,
                    width: "550px",
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
                "ShopId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "UserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

                "GoodTypeId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

                "Cover": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ImageUrls": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Unit": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Price": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ReplenishmentLimit": [
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
        //前往石材属性
        async ToGoodProp(Id) {
            this.$router.push({
                path: "/Admin/GoodPropList",
                query: {
                    GoodId: Id
                }
            })
        },
        //修改属性
        async UpdateEntityAsync(Id, title, data) {
            let { Data } = await this.$PostSigleUpdate(`/Good/Get`, `/Good/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/Good/Get`, { Id: Id });
            Data.IsEdit = Id > 0;
            if (Data.IsEdit == false) {
                Data.AuditStatus = 0;
                Data.Stock = 0;
                Data.ReplenishmentLimit = 0;
                Data.ShopId = this.ShopId;
                Data.UserId = this.UserId;
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
                    var { Success } = await this.$Post(`/Good/CreateOrEdit`, this.formData);

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
            await this.$PostDelete(`/Good/Delete`, { Id: Id });
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
            await this.$PostDelete(`/Good/BatchDelete`, { Ids: ids });

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