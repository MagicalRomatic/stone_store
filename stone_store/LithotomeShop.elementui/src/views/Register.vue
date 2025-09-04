<template>
    <div class="register-container">
        <!-- 左侧区域 -->
        <div class="register-left">
            <div class="overlay"></div>
            <div class="left-content">
                <div class="logo-area">
                    <img src="@/assets/logo.png" alt="系统Logo" class="logo-image" />
                    <h1>天然石材交易管理平台</h1>
                </div>
                <p class="slogan">臻品石材 · 智慧交易 · 全球连接</p>

                <!-- 特色功能介绍 -->
                <div class="feature-cards">
                    <div class="feature-card">
                        <i class="el-icon-user"></i>
                        <div class="feature-text">
                            <h3>会员专享优惠</h3>
                            <p>注册会员即享精选石材折扣与专属服务</p>
                        </div>
                    </div>
                    <div class="feature-card">
                        <i class="el-icon-position"></i>
                        <div class="feature-text">
                            <h3>个性化购物推荐</h3>
                            <p>智能分析您的偏好，推荐最适合的石材产品</p>
                        </div>
                    </div>
                    <div class="feature-card">
                        <i class="el-icon-bell"></i>
                        <div class="feature-text">
                            <h3>精彩活动不错过</h3>
                            <p>新品发布、限时优惠，会员第一时间获知</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 右侧注册区域 -->
        <div class="register-right">
            <div class="register-box">
                <h2>账号注册</h2>
                <p class="welcome-text">填写以下信息，立即加入全球顶级天然石材交易平台</p>

                <!-- 注册表单 -->
                <el-form ref="loginForm" :model="formData" label-width="0" :rules="rules">
                    <el-form-item prop="UserName">
                        <el-input v-model.trim="formData.UserName" placeholder="请输入账号" prefix-icon="el-icon-user">
                        </el-input>
                    </el-form-item>

                    <el-form-item prop="Password">
                        <el-input type="password" v-model.trim="formData.Password" placeholder="请输入密码"
                            prefix-icon="el-icon-lock">
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

                    <el-form-item prop="Name">
                        <el-input v-model.trim="formData.Name" placeholder="请输入姓名" prefix-icon="el-icon-user">
                        </el-input>
                    </el-form-item>

                    <el-form-item prop="RoleType">
                        <el-radio-group v-model="formData.RoleType">
                            <el-radio v-for="item in roleOptions" v-if="item.Code != '1'" :key="item.Code"
                                :label="item.Code">
                                {{ item.Label }}
                            </el-radio>
                        </el-radio-group>
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
                        <el-button type="primary" class="register-btn" @click="RegisterBtn">
                            立即注册
                        </el-button>
                    </el-form-item>
                </el-form>

                <!-- 底部链接区域 -->
                <div class="register-options">
                    <div class="login-link">
                        <span>已有账号？</span>
                        <RouterLink to="/Login">
                            <span class="action-link">立即登录</span>
                        </RouterLink>
                    </div>
                    <RouterLink to="/ForgetPassword">
                        <span class="forget-link">忘记密码</span>
                    </RouterLink>
                </div>

                <div class="footer-divider"></div>

                <div class="register-footer">
                    <p class="copyright">© 2025 天然石材交易管理平台</p>
                    <p class="slogan-text">专业矿山直供 · 品质保障</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ValidCode from '@/components/Code/canvas.vue';
export default {
    components: {
        ValidCode: ValidCode
    },
    data() {
        return {
            formData: {
                UserName: '',
                Password: '',
                RoleType: "2",
                Code: ""
            },
            roleOptions: [],
            rules: {
                UserName: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                ],
                Password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
                "Email": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
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
        this.GetRoleTypeApi();
    },
    methods: {
        async GetRoleTypeApi() {
            let { Data: { Items } } = await this.$Post("/Select/RoleType");

            this.roleOptions = Items
            console.log(this.roleOptions)
        },
        RegisterBtn() {
            this.$refs.loginForm.validate(async (valid) => {
                if (valid) {

                    let res = await this.$Post("/User/Register", this.formData)
                    console.log(res);
                    if (res.Success) {
                        this.$message.success("注册成功!");
                        this.$router.push({
                            path: "/Login"
                        })
                    }
                    else {

                        this.$refs.ValidCode.refreshCode();
                    }
                } else {
                    this.$message.error("注册验证不通过")
                    this.$refs.ValidCode.refreshCode();
                    return false;
                }
            });
        }
    }
}
</script>

<style scoped lang="scss">
// 注册页面容器
.register-container {
    width: 100vw;
    height: 100vh;
    display: flex;
    background-color: #f5f5f5;
}

// 左侧区域样式
.register-left {
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

        // Logo区域样式
        .logo-area {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
            animation: fadeInDown 0.8s ease;

            .logo-image {
                width: 50px;
                height: 50px;
                margin-right: 15px;
            }

            h1 {
                font-size: 28px;
                margin: 0;
            }
        }

        // 标语样式
        .slogan {
            font-size: 22px;
            margin-bottom: 50px;
            opacity: 0.9;
            animation: fadeInUp 0.8s ease;
        }

        // 特色功能卡片区
        .feature-cards {
            .feature-card {
                display: flex;
                align-items: flex-start;
                margin-bottom: 25px;
                animation: fadeInLeft 0.8s ease;
                background: rgba(255, 255, 255, 0.1);
                padding: 15px;
                border-radius: 8px;
                backdrop-filter: blur(5px);

                i {
                    font-size: 24px;
                    margin-right: 15px;
                    margin-top: 3px;
                }

                .feature-text {
                    h3 {
                        font-size: 18px;
                        margin: 0 0 5px 0;
                    }

                    p {
                        font-size: 14px;
                        margin: 0;
                        opacity: 0.9;
                        line-height: 1.5;
                    }
                }

                &:hover {
                    background: rgba(255, 255, 255, 0.2);
                    transform: translateX(5px);
                    transition: all 0.3s ease;
                }
            }
        }
    }
}

// 右侧注册区域样式
.register-right {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: white;
    padding: 40px 0;
    overflow-y: auto;

    .register-box {
        width: 400px;
        animation: fadeIn 0.8s ease;

        h2 {
            font-size: 28px;
            margin-bottom: 10px;
            color: #333;
        }

        .welcome-text {
            color: #666;
            margin-bottom: 30px;
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

// 注册按钮样式
.register-btn {
    width: 100%;
    height: 44px;
    font-size: 16px;
    margin-top: 20px;

    &:hover {
        transform: translateY(-2px);
        transition: all 0.3s ease;
    }
}

// 登录选项区样式
.register-options {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 24px;
    padding: 0 5px;
}

.login-link {
    display: flex;
    align-items: center;
    color: #606266;
    font-size: 14px;

    span {
        margin-right: 4px;
    }
}

// 链接按钮样式
.action-link {
    color: #409EFF;
    font-weight: 500;
    position: relative;
    transition: all 0.3s;

    &:hover {
        color: #66b1ff;
    }

    &::after {
        content: '';
        position: absolute;
        bottom: -2px;
        left: 0;
        width: 0;
        height: 1px;
        background-color: #409EFF;
        transition: width 0.3s;
    }

    &:hover::after {
        width: 100%;
    }
}

// 忘记密码链接样式
.forget-link {
    color: #909399;
    font-size: 14px;
    transition: all 0.3s;
    position: relative;

    &:hover {
        color: #409EFF;
    }

    &::after {
        content: '';
        position: absolute;
        bottom: -2px;
        left: 0;
        width: 0;
        height: 1px;
        background-color: #409EFF;
        transition: width 0.3s;
    }

    &:hover::after {
        width: 100%;
    }
}

// 底部分割线
.footer-divider {
    margin: 24px 0;
    height: 1px;
    background: linear-gradient(to right, transparent, rgba(0, 0, 0, 0.1), transparent);
}

// 注册页面底部
.register-footer {
    text-align: center;
}

.copyright {
    font-size: 12px;
    color: #909399;
    margin-bottom: 6px;
}

.slogan-text {
    font-size: 13px;
    font-weight: 500;
    color: #606266;
    letter-spacing: 0.5px;
    background: linear-gradient(45deg, #409EFF, #53a8ff);
    -webkit-background-clip: text;
    background-clip: text;
    color: transparent;
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