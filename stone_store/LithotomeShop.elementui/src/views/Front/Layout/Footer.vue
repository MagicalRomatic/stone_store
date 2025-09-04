<template>
  <el-footer class="footer">
    <!-- 主要内容区域 -->
    <div class="footer-content">
      <!-- 快速导航区 -->
      <div class="footer-section">
        <div class="nav-group">
          <h3>购物指南</h3>
          <ul>
            <li><a href="#">购物流程</a></li>
            <li><a href="#">会员介绍</a></li>
            <li><a href="#">常见问题</a></li>
          </ul>
        </div>
        <div class="nav-group">
          <h3>配送方式</h3>
          <ul>
            <li><a href="#">配送说明</a></li>
            <li><a href="#">配送范围</a></li>
            <li><a href="#">运费说明</a></li>
          </ul>
        </div>
        <div class="nav-group">
          <h3>支付方式</h3>
          <ul>
            <li><a href="#">在线支付</a></li>
            <li><a href="#">货到付款</a></li>
            <li><a href="#">优惠券说明</a></li>
          </ul>
        </div>
        <div class="nav-group">
          <h3>售后服务</h3>
          <ul>
            <li><a href="#">退换货政策</a></li>
            <li><a href="#">退换货流程</a></li>
            <li><a href="#">维修补件</a></li>
          </ul>
        </div>
      </div>

      <!-- 联系方式区 -->
      <div class="contact-section">
        <div class="contact-info">
          <h3>联系我们</h3>
          <p>客服热线：400-123-4567</p>
          <p>工作时间：09:00-22:00</p>
          <p>电子邮箱：service@example.com</p>
        </div>
        <div class="qr-code">
          <img src="/qrcode.png" alt="微信公众号" />
          <p>扫码关注公众号</p>
        </div>
      </div>
    </div>

    <!-- 底部版权信息 -->
    <div class="copyright">
      <p>版权所有 © 2025 天然石材交易管理系统 All Rights Reserved</p>
      <p>备案号：xxx-xxx号 | 地址：xx省xx市xx区xx路xx号</p>
    </div>
  </el-footer>
</template>

<script>
export default {
  name: 'Footer'
}
</script>

<style scoped>
/* 底部整体样式 */
.footer {
  background-color: #222222;
  padding: 40px 0 20px;
  color: #999;
  height: auto !important;
}

/* 内容区域布局 */
.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

/* 导航分组样式 */
.footer-section {
  display: flex;
  justify-content: space-between;
  margin-bottom: 30px;
}

.nav-group {
  flex: 1;
  margin-right: 20px;
}

.nav-group h3 {
  color: #fff;
  font-size: 16px;
  margin-bottom: 15px;
}

.nav-group ul {
  list-style: none;
  padding: 0;
}

.nav-group ul li {
  margin-bottom: 8px;
}

.nav-group ul li a {
  color: #999;
  text-decoration: none;
  font-size: 14px;
}

.nav-group ul li a:hover {
  color: #fff;
}

/* 联系方式区域样式 */
.contact-section {
  display: flex;
  justify-content: space-between;
  padding: 20px 0;
  border-top: 1px solid #333;
}

.contact-info h3 {
  color: #fff;
  margin-bottom: 15px;
}

.contact-info p {
  margin-bottom: 8px;
  font-size: 14px;
}

.qr-code {
  text-align: center;
}

.qr-code img {
  width: 100px;
  height: 100px;
  margin-bottom: 8px;
}

.qr-code p {
  font-size: 12px;
}

/* 版权信息样式 */
.copyright {
  text-align: center;
  padding-top: 20px;
  border-top: 1px solid #333;
  font-size: 12px;
}

.copyright p {
  margin: 5px 0;
}
</style>
