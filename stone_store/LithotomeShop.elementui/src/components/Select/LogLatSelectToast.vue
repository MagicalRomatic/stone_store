<template>
    <!-- 添加弹窗包装 -->
    <el-dialog title="选择位置" :visible.sync="visible" :close-on-click-modal="false" width="80%" class="location-select-dialog"
        @close="handleClose">
        <div class="address-select">
            <!-- 左侧搜索面板 -->
            <div class="search-panel">
                <div class="panel-header">
                    <h2>地址选择</h2>
                    <p class="subtitle">请搜索或在地图上选择位置</p>
                </div>

                <!-- 搜索框 -->
                <div class="search-box">
                    <el-input v-model="searchForm.address" placeholder="搜索地址..." prefix-icon="el-icon-search" clearable
                        @keyup.enter.native="searchByKeyword">
                        <el-button slot="append" @click="searchByKeyword">搜索</el-button>
                    </el-input>
                </div>

                <!-- 搜索结果列表 -->
                <div class="search-results" v-if="addressList.length">
                    <div v-for="(item, index) in addressList" :key="index" class="result-item"
                        :class="{ active: isSelected(item) }" @click="selectSearchResult(item)">
                        <div class="item-content">
                            <i class="el-icon-location-information"></i>
                            <div class="item-details">
                                <div class="item-name">{{ item.title }}</div>
                                <div class="item-address">{{ item.address }}</div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 当前选中的地址信息 -->
                <div class="selected-address" v-if="selectAddress.lng">
                    <div class="info-card">
                        <div class="card-header">
                            <i class="el-icon-location success"></i>
                            <span>已选位置</span>
                        </div>
                        <div class="card-content">

                            <div class="address-detail">
                                <p>详细地址：{{ selectAddress.fullAddress }}</p>
                                <p class="address-components">
                                    <span>省份：{{ selectAddress.province }}</span>
                                    <span>城市：{{ selectAddress.city }}</span>
                                    <span>区县：{{ selectAddress.district }}</span>
                                    <span v-if="selectAddress.street">街道：{{ selectAddress.street }}</span>
                                    <span v-if="selectAddress.streetNumber">门牌号：{{ selectAddress.streetNumber }}</span>
                                </p>
                            </div>
                            <div class="coordinates">
                                经度: {{ selectAddress.lng }}<br>
                                纬度: {{ selectAddress.lat }}
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 右侧地图 -->
            <div class="map-container">
                <el-skeleton v-if="map == null" :rows="12" animated />
                <div id="containerMap"></div>
            </div>
        </div>
        <!-- 添加底部按钮 -->
        <span slot="footer" class="dialog-footer">
            <el-button @click="handleClose">取 消</el-button>
            <el-button type="primary" @click="Save">确 定</el-button>
        </span>
    </el-dialog>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name: "LogLatSelectToast",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId", "HotelId",
        ])
    },

    data() {
        return {
            searchForm: { address: "" },
            addressList: [],
            map: null,
            search: null,
            markers: [],
            currentMarker: null,
            currentInfoWindow: null,
            selectAddress: {
                lat: "",
                lng: "",
                name: "",
                province: "",
                city: "",
                district: "",
                street: "",
                streetNumber: "",
                fullAddress: ""
            },
            visible: false,
            callback: null,
        };

    },

    created() {

    },



    methods: {

        OpenToast(Latitude, Longitude, Address, callback) {
            this.selectAddress.lat = Latitude;
            this.selectAddress.lng = Longitude;
            this.selectAddress.name = Address;
            this.visible = true;
            this.callback = callback;
            this.$nextTick(() => {
                this.InitMap();
            })
        },

        InitMap() {

            const center = this.selectAddress.lat && this.selectAddress.lng
                ? new TMap.LatLng(this.selectAddress.lat, this.selectAddress.lng)
                : new TMap.LatLng(42.317556, 118.944677);

            this.map = new TMap.Map('containerMap', {
                zoom: this.selectAddress.lat ? 15 : 6,
                center: center,
                showControl: true,
                mapStyleId: 'style1' // 可以选择适合的地图样式
            });


            this.initializeMarker(center);
            this.bindMapEvents();
        },

        initializeMarker(position) {
            if (this.selectAddress.lat && this.selectAddress.lng) {
                this.currentMarker = new TMap.MultiMarker({
                    map: this.map,
                    geometries: [{
                        id: 'selected',
                        position: position,
                    }]
                });

                this.showInfoWindow(position, this.selectAddress.name);
            }
        },

        showInfoWindow(position, content) {
            if (this.currentInfoWindow) {
                this.currentInfoWindow.close();
            }

            this.currentInfoWindow = new TMap.InfoWindow({
                map: this.map,
                position: position,
                content: `
                    <div class="custom-info-window">
                        <h3>${content || '已选位置'}</h3>
                        <p>${position.lat.toFixed(6)}, ${position.lng.toFixed(6)}</p>
                    </div>
                `,
                offset: { x: 0, y: -50 }
            });
            this.currentInfoWindow.open();
        },

        bindMapEvents() {
            this.map.on("click", (evt) => {
                this.updateSelection({
                    lat: evt.latLng.lat,
                    lng: evt.latLng.lng,
                    name: evt.poi ? evt.poi.name : '未知地点'
                });
            });
        },

        async updateSelection(location) {
            // 创建地址解析器实例
            const geocoder = new TMap.service.Geocoder();

            try {
                // 解析坐标获取详细地址
                const result = await geocoder.getAddress({
                    location: new TMap.LatLng(location.lat, location.lng)
                });

                if (result.status === 0 && result.result) {
                    const addressInfo = result.result.address_component;
                    this.selectAddress = {
                        lat: location.lat,
                        lng: location.lng,
                        name: location.name,
                        province: addressInfo.province || '',
                        city: addressInfo.city || '',
                        district: addressInfo.district || '',
                        street: addressInfo.street || '',
                        streetNumber: addressInfo.street_number || '',
                        fullAddress: result.result.address || ''
                    };
                }
            } catch (error) {
                console.error('获取详细地址失败:', error);
                this.selectAddress = {
                    ...location,
                    province: '',
                    city: '',
                    district: '',
                    street: '',
                    streetNumber: '',
                    fullAddress: ''
                };
            }

            const position = new TMap.LatLng(location.lat, location.lng);

            if (this.currentMarker) {
                this.currentMarker.setGeometries([{
                    id: 'selected',
                    position: position,
                }]);
            } else {
                this.currentMarker = new TMap.MultiMarker({
                    map: this.map,
                    geometries: [{
                        id: 'selected',
                        position: position,
                    }]
                });
            }

            this.showInfoWindow(position, location.name);
        },

        async searchByKeyword() {
            if (!this.searchForm.address.trim()) {
                return;
            }

            try {
                const result = await this.search.searchRectangle({
                    keyword: this.searchForm.address,
                    bounds: this.map.getBounds(),
                });

                this.addressList = result.data;

                // 清除之前的搜索标记
                this.markers.setGeometries([]);

                // 添加新的搜索结果标记
                const geometries = result.data.map((item, index) => ({
                    id: String(index),
                    position: item.location,
                }));

                this.markers.updateGeometries(geometries);
            } catch (error) {
                this.$message.error('搜索失败，请重试');
            }
        },

        selectSearchResult(item) {
            this.updateSelection({
                lat: item.location.lat,
                lng: item.location.lng,
                name: item.title
            });

            this.map.panTo(item.location);
            this.map.setZoom(16);
        },

        isSelected(item) {
            return this.selectAddress.lat === item.location.lat
                && this.selectAddress.lng === item.location.lng;
        },



        async Save() {
            if (this.callback) {
                this.callback(this.selectAddress);
            }

            this.handleClose();

        },

        handleClose() {

            if (this.map) {
                this.map.destroy();
            }
            this.visible = false;

        },
    },

    async created() {

    },

    mounted() {

    },

    beforeDestroy() {

    },
};
</script>

<style lang="scss" scoped>
.location-select-dialog {
    :deep(.el-dialog__body) {
        padding: 0;
    }

    :deep(.el-dialog__header) {
        padding: 15px 20px;
        border-bottom: 1px solid #eee;

        .el-dialog__title {
            font-size: 18px;
            font-weight: 500;
        }
    }

    :deep(.el-dialog__footer) {
        padding: 15px 20px;
        border-top: 1px solid #eee;
    }
}

.address-select {
    height: 600px; // 修改固定高度
    display: flex;
    background: #fff;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.search-panel {
    width: 400px;
    border-right: 1px solid #eee;
    display: flex;
    flex-direction: column;
    background: #fff;

    .panel-header {
        padding: 20px;
        border-bottom: 1px solid #eee;

        h2 {
            margin: 0;
            font-size: 24px;
            color: #303133;
        }

        .subtitle {
            margin: 8px 0 0;
            color: #909399;
            font-size: 14px;
        }
    }

    .search-box {
        padding: 20px;
        border-bottom: 1px solid #eee;
    }

    .search-results {
        flex: 1;
        overflow-y: auto;
        padding: 0 10px;

        .result-item {
            padding: 15px;
            cursor: pointer;
            border-radius: 4px;
            margin: 5px 0;
            transition: all 0.3s;

            &:hover {
                background: #f5f7fa;
            }

            &.active {
                background: #ecf5ff;
                border: 1px solid #409EFF;
            }

            .item-content {
                display: flex;
                align-items: flex-start;

                i {
                    color: #409EFF;
                    font-size: 20px;
                    margin-right: 12px;
                    margin-top: 2px;
                }

                .item-details {
                    flex: 1;

                    .item-name {
                        font-weight: 500;
                        color: #303133;
                        margin-bottom: 4px;
                    }

                    .item-address {
                        font-size: 12px;
                        color: #909399;
                    }
                }
            }
        }
    }

    .selected-address {
        padding: 20px;
        border-top: 1px solid #eee;

        .info-card {
            background: #f5f7fa;
            border-radius: 8px;
            padding: 15px;

            .card-header {
                display: flex;
                align-items: center;
                margin-bottom: 12px;

                i {
                    color: #67c23a;
                    margin-right: 8px;
                    font-size: 18px;
                }

                span {
                    font-weight: 500;
                    color: #303133;
                }
            }

            .card-content {
                .address-name {
                    font-size: 14px;
                    color: #606266;
                    margin-bottom: 8px;
                }

                .address-detail {
                    margin: 10px 0;

                    p {
                        margin: 5px 0;
                        color: #606266;
                        font-size: 13px;
                    }

                    .address-components {
                        display: flex;
                        flex-wrap: wrap;
                        gap: 8px;

                        span {
                            color: #909399;
                            font-size: 12px;
                        }
                    }
                }

                .coordinates {
                    font-size: 12px;
                    color: #909399;
                    line-height: 1.6;
                }
            }

            .card-actions {
                margin-top: 15px;
                display: flex;
                gap: 10px;
            }
        }
    }
}

.map-container {
    flex: 1;
    position: relative;

    #containerMap {
        width: 100%;
        height: 100%;
    }
}

// 自定义信息窗体样式
:deep(.custom-info-window) {
    padding: 10px;

    h3 {
        margin: 0 0 5px;
        font-size: 16px;
        color: #303133;
    }

    p {
        margin: 0;
        font-size: 12px;
        color: #909399;
    }
}
</style>
