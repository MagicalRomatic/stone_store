<template>
    <div class="main-container">
        <div style="display: flex;align-items: center;font-size: 14px;">
            <span style="font-weight: bolder;margin-right: 10px;">您当前的位置:</span>
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>我的反馈</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <el-card class="box-card margin-top-xs">
            <div slot="header" class="clearfix">
                <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">
                    <el-form-item label="反馈标题" prop="Title">
                        <el-input v-model.trim="searchForm.Title" placeholder="请输入反馈标题" :clearable="true"></el-input>
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


        <el-dialog :title="formData.Id ? '修改留言反馈' : '添加留言反馈'" :visible.sync="editorShow" width="50%" :lock-scroll="true"
            height="800px">
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
                        <el-button @click="editorShow = false">取 消</el-button>
                    </el-form-item>
                </el-row>
            </el-form>

        </el-dialog>
        <!-- 培训列表 -->
        <PaginationBox ref="PaginationTableId" :where="where" url="/LeaveFeedBack/List">
            <template v-slot:content="{ data }">
                <!-- 反馈列表容器 -->
                <div class="feedback-list">
                    <div v-for="item in data" :key="item.Id" class="feedback-card">
                        <!-- 卡片头部 -->
                        <div class="card-header">
                            <div class="user-info">
                                <img :src="item.FeedbackUserDto.ImageUrls" class="avatar" />
                                <span class="username">{{ item.FeedbackUserDto.Name }}</span>
                            </div>
                            <div class="status" :class="{ 'handled': item.IsDoWith }">
                                {{ item.IsDoWith ? '已处理' : '待处理' }}
                            </div>
                        </div>
                        <!-- 卡片内容 -->
                        <div class="card-content">
                            <h3 class="title">{{ item.Title }}</h3>
                            <div class="content" v-html="item.Content"></div>
                        </div>
                        <!-- 卡片底部 -->
                        <div class="card-footer">
                            <span class="time">提交时间：{{ item.CreationTime }}</span>
                            <div class="actions">
                                <el-button type="text" size="small" @click="ShowEditModal(item.Id)">
                                    编辑
                                </el-button>
                                <el-button type="text" size="small" @click="ShowDeleteModal(item.Id)">
                                    删除
                                </el-button>
                            </div>
                        </div>
                    </div>
                </div>
            </template>
        </PaginationBox>


    </div>
</template>

<script>
import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import { mapGetters } from 'vuex';
export default {
    name: "LeaveFeedBackList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    components: {
        PaginationBox,
    },
    data() {

        return {
            where: {},
            searchForm: {},//搜索定义的数据对象

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
        this.where.FeedbackUserId = this.UserId;
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

<style scoped>
/* 反馈列表容器 */
.feedback-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
    padding: 20px 0;
}

/* 反馈卡片样式 */
.feedback-card {
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    padding: 16px;
    transition: transform 0.3s;
}

.feedback-card:hover {
    transform: translateY(-5px);
}

/* 卡片头部样式 */
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;
}

.user-info {
    display: flex;
    align-items: center;
    gap: 10px;
}

.avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    object-fit: cover;
}

.username {
    font-weight: 500;
    color: #333;
}

.status {
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 12px;
    background: #f56c6c;
    color: white;
}

.status.handled {
    background: #67c23a;
}

/* 卡片内容样式 */
.card-content {
    margin-bottom: 16px;
}

.title {
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 8px;
    color: #303133;
}

.content {
    font-size: 14px;
    color: #606266;
    line-height: 1.6;
    max-height: 100px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
}

/* 卡片底部样式 */
.card-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-top: 16px;
    border-top: 1px solid #ebeef5;
}

.time {
    font-size: 12px;
    color: #909399;
}

.actions {
    display: flex;
    gap: 10px;
}
</style>
