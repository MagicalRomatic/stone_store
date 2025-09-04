<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <div class="header-title"> 店铺信息维护</div>

            </div>
            <div class="tb-body">

                <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                    label-width="140px" size="mini">
                    <el-row :gutter="10" class="EditFromBody">
                        <el-col :span="24">
                            <el-form-item label="店铺名称" prop="Name">
                                <el-input type="text" v-model.trim="formData.Name" placeholder="请输入店铺名称"
                                    :clearable="true"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="Logo" prop="LogoCover">
                                <UploadImages :limit="1" v-model="formData.LogoCover"></UploadImages>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="联系电话" prop="Phone">
                                <el-input type="text" v-model.trim="formData.Phone" placeholder="请输入联系电话"
                                    :clearable="true"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="邮箱" prop="Email">
                                <el-input type="text" v-model.trim="formData.Email" placeholder="请输入邮箱"
                                    :clearable="true"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="店铺地址" prop="Address">
                                <el-input type="text" v-model.trim="formData.Address" placeholder="请输入店铺地址"
                                    :clearable="true"></el-input>
                            </el-form-item>
                        </el-col>


                        <el-col :span="24">
                            <el-form-item label="店铺保障" prop="Ensure">
                                <el-input type="text" v-model.trim="formData.Ensure" placeholder="请输入店铺保障"
                                    :clearable="true"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="审核状态" prop="AuditStatus">
                                <SigleSelect url="/Select/ShopAuditStatusEnum" columnName="Name" :disabled="true"
                                    columnValue="Code" v-model="formData.AuditStatus">
                                </SigleSelect>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24" v-if="formData.AuditStatus != 1">
                            <el-form-item label="审核原因" prop="AuditReason">
                                <el-input type="textarea" v-model.trim="formData.AuditReason" :disabled="true"
                                    placeholder="" :clearable="true"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="店铺详情" prop="Content">
                                <QillRichText v-model="formData.Content"></QillRichText>
                            </el-form-item>
                        </el-col>
                    </el-row>


                    <el-row type="flex" justify="end" align="bottom">
                        <el-form-item>
                            <el-button type="primary" v-if="formData.AuditStatus == 1 || formData.AuditStatus == 2" plain
                                @click="CreateOrEditForm()">确
                                定</el-button>
                            <el-button type="primary" v-if="formData.AuditStatus == 3" plain
                                @click="CreateOrEditForm()">再次提交</el-button>
                        </el-form-item>
                    </el-row>
                </el-form>



            </div>

        </el-card>





    </div>
</template>

<script>
import { mapGetters } from 'vuex';
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

            editModalFormRules: {
                "Name": [
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
        this.ShowEditModal();
    },
    methods: {

        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/Shop/Get`, { Id: 0, UserId: this.UserId });
            if (Data.Id == null) {
                Data.UserId = this.UserId;
                Data.AuditStatus = 1;
                Data.Email = this.UserInfo.Email;
                Data.Phone = this.UserInfo.PhoneNumber;
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
                    if (this.formData.AuditStatus == 3) {
                        this.formData.AuditStatus = 1;
                        this.formData.AuditReason = "";
                        this.formData.AuditUserId = null;
                    }
                    var { Success } = await this.$Post(`/Shop/CreateOrEdit`, this.formData);

                    if (Success) {
                        this.$message.success("恭喜你,操作成功");
                    }
                }
            })
        },


    },
};
</script>