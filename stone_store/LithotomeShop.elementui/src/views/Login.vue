<template>
    <div class="login-container">
        <!-- 左侧区域 -->
        <div class="login-left">
            <div class="overlay"></div>
            <div class="left-content">
                <div class="logo-area">
                    <img src="@/assets/logo.png" alt="系统Logo" class="logo-image" />
                    <h1>天然石材交易管理平台</h1>
                </div>
                <p class="slogan">臻品石材 · 智慧交易 · 全球连接</p>
                <div class="feature-cards">
                    <div class="feature-card">
                        <i class="el-icon-shopping-cart-full"></i>
                        <div class="feature-text">
                            <h3>精选石材资源</h3>
                            <p>汇聚全球稀缺石材，提供原产地直采服务</p>
                        </div>
                    </div>
                    <div class="feature-card">
                        <i class="el-icon-data-analysis"></i>
                        <div class="feature-text">
                            <h3>行业数据分析</h3>
                            <p>石材价格实时追踪，市场趋势智能预测</p>
                        </div>
                    </div>
                    <div class="feature-card">
                        <i class="el-icon-user"></i>
                        <div class="feature-text">
                            <h3>一站式采购方案</h3>
                            <p>从选石到物流，全程专业顾问跟进服务</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 右侧登录区域 -->
        <div class="login-right">
            <div class="login-box">
                <h2>欢迎访问</h2>
                <p class="welcome-text">全球顶级天然石材交易平台，为您开启石材之旅</p>

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

                    <el-form-item prop="RoleType">
                        <el-radio-group v-model="formData.RoleType">
                            <el-radio v-for="item in roleOptions" :key="item.Code" :label="item.Code">
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
                        <el-button type="primary" class="login-btn" @click="LoginBtn">
                            登录账号
                        </el-button>
                    </el-form-item>
                </el-form>

                <div class="login-options">
                    <div class="register-link">
                        <span>首次使用？</span>
                        <RouterLink to="/Register">
                            <span class="action-link">注册账号</span>
                        </RouterLink>
                    </div>
                    <RouterLink to="/ForgetPassword">
                        <span class="forget-link">找回密码</span>
                    </RouterLink>
                </div>

                <div class="footer-divider"></div>

                <div class="login-footer">
                    <p class="copyright">© 2025 天然石材交易管理平台</p>
                    <p class="slogan-text">专业矿山直供 · 品质保障</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ValidCode from '@/components/Code/canvas.vue';
import store from '@/store';
export default {
    components: {
        ValidCode: ValidCode
    },
    data() {
        return {
            formData: {
                UserName: '',
                Password: '',
                RoleType: "",
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
                RoleType: [
                    { required: true, message: '请选择角色', trigger: 'blur' },
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

        },
        LoginBtn() {
            this.$refs.loginForm.validate(async (valid) => {
                if (valid) {

                    let res = await store.dispatch("Login", this.formData);
                    if (res.Success) {
                        this.$message.success("登录成功!");
                        this.$router.push({
                            path: "/Admin"
                        })

                    }

                } else {
                    this.$message.error("登录验证不通过")

                    return false;
                }
            });
        }
    }
}
</script>

<style scoped lang="scss">
// 登录页面容器
.login-container {
    width: 100vw;
    height: 100vh;
    display: flex;
    background-color: #f5f5f5;
}

// 左侧区域样式
.login-left {
    position: relative;
    flex: 1;
    background-image: url('@/assets/login1.png'); // 这里替换成您的背景图
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

// 右侧登录区域样式
.login-right {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: white;

    .login-box {
        width: 400px;
        animation: fadeIn 0.8s ease;

        h2 {
            font-size: 28px;
            margin-bottom: 10px;
            color: #333;
        }

        .welcome-text {
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

// 登录按钮样式
.login-btn {
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
.login-options {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 24px;
    padding: 0 5px;
}

.register-link {
    display: flex;
    align-items: center;
    color: #606266;
    font-size: 14px;

    span {
        margin-right: 4px;
    }
}

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

/* 底部分割线 */
.footer-divider {
    margin: 24px 0;
    height: 1px;
    background: linear-gradient(to right, transparent, rgba(0, 0, 0, 0.1), transparent);
}

/* 页脚版权信息 */
.login-footer {
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