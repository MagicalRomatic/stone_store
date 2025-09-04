<template>
    <div class="forget-container">
        <!-- 左侧区域 -->
        <div class="forget-left">
            <div class="overlay"></div>
            <div class="left-content">
                <h1>找回您的账号 🔑</h1>
                <p class="subtitle">简单几步，轻松重置密码</p>
                <div class="feature-list">
                    <div class="feature-item">
                        <span>📝</span>
                        <p>填写账号信息</p>
                    </div>
                    <div class="feature-item">
                        <span>✉️</span>
                        <p>验证身份信息</p>
                    </div>
                    <div class="feature-item">
                        <span>🔒</span>
                        <p>设置新的密码</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- 右侧重置密码区域 -->
        <div class="forget-right">
            <div class="forget-box">
                <h2>重置密码 🔐</h2>
                <p class="reset-text">请填写以下信息以重置您的密码</p>

                <el-form ref="loginForm" :model="formData" label-width="0" :rules="rules">
                    <el-form-item prop="UserName">
                        <el-input v-model.trim="formData.UserName" placeholder="请输入账号" prefix-icon="el-icon-user">
                        </el-input>
                    </el-form-item>

                    <el-form-item prop="Email">
                        <el-input v-model.trim="formData.Email" placeholder="请输入邮箱" prefix-icon="el-icon-message">
                        </el-input>
                    </el-form-item>

                    <el-form-item prop="PhoneNumber">
                        <el-input v-model.trim="formData.PhoneNumber" placeholder="请输入联系方式" prefix-icon="el-icon-phone">
                        </el-input>
                    </el-form-item>

                    <el-form-item prop="Password">
                        <el-input type="password" v-model.trim="formData.Password" placeholder="请输入新密码"
                            prefix-icon="el-icon-lock">
                        </el-input>
                    </el-form-item>

                    <el-form-item prop="Code">
                        <div class="verify-code">
                            <el-input v-model.trim="formData.Code" placeholder="请输入验证码">
                            </el-input>
                            <div class="code-img">
                                <ValidCode ref="ValidCode"></ValidCode>
                            </div>
                        </div>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" class="submit-btn" @click="ForgetBtn">
                            确认重置
                        </el-button>
                    </el-form-item>
                </el-form>

                <div class="forget-footer">
                    <div class="login-link">
                        <span>已有账号？</span>
                        <RouterLink to="/Login">
                            <span class="link-text">返回登录 👉</span>
                        </RouterLink>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ValidCode from '@/components/Code/canvas.vue'
import store from '@/store';
import { settings } from 'nprogress';
export default {
    components: {
        ValidCode: ValidCode
    },
    data() {
        return {
            formData: {
                UserName: '',
                Password: '',

                Code: ""
            },

            rules: {
                UserName: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                ],
                Password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
                "Email": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                    {
                        validator: (rule, value, callback) => {
                            var reg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
                            if (!value || !reg.test(value)) {
                                callback(new Error('请输入正确邮箱'));
                            }
                            else {
                                callback();
                            }
                        }, trigger: 'blur'
                    },
                ],
                "ImageUrls": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Name": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

                "PhoneNumber": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                    {
                        validator: (rule, value, callback) => {
                            var reg = /^1[34578]\d{9}$/;
                            if (!value || !reg.test(value)) {
                                callback(new Error('请输入正确的手机号'));
                            }
                            else {
                                callback();
                            }
                        }, trigger: 'blur'
                    },
                ],
                Code: [
                    { required: true, message: '请输入验证码', trigger: 'blur' },
                    {
                        validator: (rule, value, callback) => {
                            let identifyCode = this.$refs.ValidCode.getCode();

                            if (value != identifyCode) {
                                callback(new Error('请输入正确的验证码'));
                            } else {
                                callback();
                            }
                        }, trigger: 'blur'
                    }
                ]
            }
        }

    },
    created() {

    },
    methods: {

        ForgetBtn() {
            this.$refs.loginForm.validate(async (valid) => {
                if (valid) {

                    let res = await this.$Post("/User/ForgetPassword", this.formData)
                    console.log(res);
                    if (res.Success) {
                        this.$message.success("修改密码成功!");
                        this.$router.push({
                            path: "/Login"
                        })
                    }
                    else {

                        this.$refs.ValidCode.refreshCode();
                    }
                } else {
                    this.$message.error("验证不通过")
                    this.$refs.ValidCode.refreshCode();
                    return false;
                }
            });
        }
    }
}
</script>

<style scoped lang="scss">
// 忘记密码页面容器
.forget-container {
    width: 100vw;
    height: 100vh;
    display: flex;
    background-color: #f5f5f5;
}

// 左侧区域样式
.forget-left {
    position: relative;
    flex: 1;
    background-image: url('@/assets/login1.png');
    background-size: cover;
    background-position: center;

    .overlay {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: linear-gradient(135deg, rgba(0, 0, 0, 0.4) 0%, rgba(0, 0, 0, 0.6) 100%);
    }

    .left-content {
        position: relative;
        z-index: 1;
        padding: 60px;
        color: white;

        h1 {
            font-size: 42px;
            margin-bottom: 20px;
            animation: fadeInDown 0.8s ease;
        }

        .subtitle {
            font-size: 24px;
            margin-bottom: 60px;
            opacity: 0.9;
            animation: fadeInUp 0.8s ease;
        }

        .feature-list {
            .feature-item {
                display: flex;
                align-items: center;
                margin-bottom: 30px;
                animation: fadeInLeft 0.8s ease;

                span {
                    font-size: 32px;
                    margin-right: 20px;
                }

                p {
                    font-size: 18px;
                    opacity: 0.9;
                }
            }
        }
    }
}

// 右侧重置密码区域样式
.forget-right {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: white;

    .forget-box {
        width: 400px;
        animation: fadeIn 0.8s ease;

        h2 {
            font-size: 28px;
            margin-bottom: 10px;
            color: #333;
        }

        .reset-text {
            color: #666;
            margin-bottom: 40px;
        }
    }
}

// 验证码区域样式
.verify-code {
    display: flex;
    gap: 10px;

    .el-input {
        flex: 1;
    }

    .code-img {
        width: 120px;
    }
}

// 提交按钮样式
.submit-btn {
    width: 100%;
    height: 44px;
    font-size: 16px;
    margin-top: 20px;

    &:hover {
        transform: translateY(-2px);
        transition: all 0.3s ease;
    }
}

// 底部链接样式
.forget-footer {
    margin-top: 20px;
    text-align: center;
    color: #666;

    .link-text {
        color: var(--primary-color);
        margin-left: 5px;
        cursor: pointer;

        &:hover {
            text-decoration: underline;
        }
    }
}

// 动画关键帧
@keyframes fadeIn {
    from {
        opacity: 0;
    }

    to {
        opacity: 1;
    }
}

@keyframes fadeInDown {
    from {
        opacity: 0;
        transform: translateY(-20px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(20px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@keyframes fadeInLeft {
    from {
        opacity: 0;
        transform: translateX(-20px);
    }

    to {
        opacity: 1;
        transform: translateX(0);
    }
}
</style>
