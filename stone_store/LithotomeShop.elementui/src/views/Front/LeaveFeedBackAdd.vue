<template>
    <div>
        <div style="display: flex;align-items: center;font-size: 14px;">
            <span style="font-weight: bolder;margin-right: 10px;">您当前的位置:</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/Front/MyLeaveFeedBackList' }">我的反馈</el-breadcrumb-item>
                <el-breadcrumb-item>反馈申请</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="card margin-top-xs">
            <div class="feedback-tips">
                <div class="tips-container">
                    <i class="tips-icon el-icon-info"></i>
                    <div class="tips-content">
                        <h4>温馨提示</h4>
                        <p>请详细描述您遇到的问题，我们会尽快处理您的反馈。</p>
                        <p>反馈内容越详细，我们就能更好地为您解决问题。</p>
                        <p>如有必要，您可以：</p>
                        <ul>
                            <li>添加具体的问题发生时间</li>
                            <li>描述问题发生的具体步骤</li>
                            <li>上传相关的截图或文件</li>
                        </ul>
                    </div>
                </div>
            </div>

            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody">
                    <el-col :span="24">
                        <el-form-item label="反馈标题" prop="Title">
                            <el-input type="text" v-model.trim="formData.Title" placeholder="请输入反馈标题"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="反馈内容" prop="Content">
                            <QillRichText v-model="formData.Content"></QillRichText>
                        </el-form-item>
                    </el-col>

                </el-row>


                <el-row type="flex" justify="end" align="bottom">
                    <el-form-item>
                        <el-button type="primary" plain @click="CreateOrEditForm()">确 定</el-button>

                    </el-form-item>
                </el-row>
            </el-form>
        </div>
    </div>
</template>
<script>
import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import SelectTab from '@/components/Select/SelectTab.vue';
import { mapGetters } from 'vuex';
export default {
    name: 'LeaveFeedBack',
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', 'UserId'
        ])
    },
    components: {
        PaginationBox,
        SelectTab
    },
    data() {
        return {

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
        }
    },
    watch: {
        "$route": async function (to, from) {

        }
    },
    created() {
        this.ShowEditModal(null);
    },
    methods: {
        /**
          * 点击新增或者编辑的时候会触发
           */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/LeaveFeedBack/Get`, { Id: Id });
            if (!Id) {
                Data.FeedbackUserId = this.UserId;
                Data.IsDoWith = false;
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
                    var { Success } = await this.$Post(`/LeaveFeedBack/CreateOrEdit`, this.formData);

                    if (Success) {
                        this.editorShow = false;
                        this.$router.push('/Front/MyLeaveFeedBackList');
                    }
                }
            })
        },
    },
}
</script>
<style lang="scss" scoped>
/* 提示框容器 */
.tips-container {
    display: flex;
    align-items: flex-start;
    padding: 16px;
    background-color: #f4f4f5;
    border-radius: 4px;
    margin-bottom: 20px;
}

/* 提示图标样式 */
.tips-icon {
    font-size: 20px;
    color: #909399;
    margin-right: 12px;
    margin-top: 2px;
}

/* 提示内容区域 */
.tips-content {
    flex: 1;

    h4 {
        margin: 0 0 8px 0;
        font-size: 16px;
        color: #303133;
    }

    p {
        margin: 0 0 6px 0;
        color: #606266;
        line-height: 1.6;
    }

    ul {
        margin: 8px 0 0 20px;
        padding: 0;
        color: #606266;

        li {
            margin-bottom: 4px;
        }
    }
}
</style>