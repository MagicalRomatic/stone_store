<template>
    <div class="analysis-container">

        <div class="summary-list">
            <div v-for="(item, index) in summaryItems" :key="index" class="summary-item" :class="`gradient-${index + 1}`">
                <i :class="item.icon"></i>
                <div class="content">
                    <div class="tit">{{ item.title }}</div>
                    <div class="qty">{{ OrderSummaryData[item.key] | ToFixed2 }}</div>
                </div>
            </div>
        </div>

        <el-row class="chart-container" :gutter="10">
            <el-col :span="12">
                <el-card class="chart-card">
                    <div class="echart" id="SeasonGoodSalesEchart"></div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card class="chart-card">
                    <div class="echart" id="Last30dayMoneyEchart"></div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card class="chart-card">
                    <div class="echart" id="HourGoodSalesEchart"></div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card class="chart-card">
                    <div class="echart" id="GoodSalesEchart"></div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import * as echarts from "echarts";
import { mapGetters } from "vuex";
export default {
    name: 'Home',
    props: {

    },
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId", "ShopId"
        ])
    },
    data() {
        return {
            myChart: null,
            OrderSummaryData: {},
            OrderIncomeData: {},
            summaryItems: [
                { title: '今日订单', key: 'TodayOrderCount', icon: 'el-icon-s-order' },
                { title: '7天订单', key: 'Last7OrderCount', icon: 'el-icon-date' },
                { title: '30天订单', key: 'Last30OrderCount', icon: 'el-icon-calendar' },
                { title: '待发货', key: 'WaitSendGoodOrderCount', icon: 'el-icon-box' },
                { title: '待收货', key: 'WaitGetGoodOrderCount', icon: 'el-icon-truck' },
                { title: '待售后', key: 'WaitSerivceGoodOrderCount', icon: 'el-icon-service' },
                { title: '今日收入', key: 'TodayOrderInMoneyCount', icon: 'el-icon-money' },
                { title: '7天收入', key: 'Last7OrderInMoneyCount', icon: 'el-icon-wallet' },
                { title: '30天收入', key: 'Last30OrderInMoneyCount', icon: 'el-icon-bank-card' },
            ]
        };
    },
    created() {

    },
    mounted() {
        this.GetOrderSummaryDataApi();
        this.GetOrderIncomeDataApi();
        this.GetGoodSalesApi();
        this.GetGetSeasonGoodSalesEchartApi();
        this.GetHourGoodSalesEchartApi();
    },
    methods: {
        async GetHourGoodSalesEchartApi() {
            let { Data } = await this.$Post("/OrderInfo/GetHourGoodSalesEchart", { ShopId: this.ShopId })

            let option = {
                title: {
                    text: '24小时销量分布',
                    textStyle: {
                        color: '#2c3e50',
                        fontSize: 18,
                        fontWeight: 'normal'
                    }
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        crossStyle: {
                            color: '#999'
                        }
                    }
                },
                legend: {
                    data: ['销量', '趋势'],
                    textStyle: {
                        color: '#666'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [{
                    type: 'category',
                    data: Data.map(x => x.Date),
                    axisPointer: {
                        type: 'shadow'
                    },
                    axisLabel: {
                        color: '#666',
                        interval: 2
                    }
                }],
                yAxis: [{
                    type: 'value',
                    name: '销量',
                    axisLabel: {
                        color: '#666',
                        formatter: '{value}'
                    }
                }],
                series: [
                    {
                        name: '销量',
                        type: 'bar',
                        barWidth: '50%',
                        itemStyle: {
                            color: {
                                type: 'linear',
                                x: 0,
                                y: 0,
                                x2: 0,
                                y2: 1,
                                colorStops: [{
                                    offset: 0,
                                    color: '#607d8b'
                                }, {
                                    offset: 1,
                                    color: '#90a4ae'
                                }]
                            },
                            borderRadius: [4, 4, 0, 0]
                        },
                        data: Data.map(x => x.Value)
                    },
                    {
                        name: '趋势',
                        type: 'line',
                        smooth: true,
                        symbolSize: 8,
                        itemStyle: {
                            color: '#455a64'
                        },
                        lineStyle: {
                            width: 3,
                            color: '#455a64'
                        },
                        data: Data.map(x => x.Value)
                    }
                ]
            };

            let myChart = echarts.init(document.getElementById("HourGoodSalesEchart"));
            myChart.setOption(option);

            window.addEventListener("resize", () => {
                myChart.resize();
            });
        },
        async GetOrderSummaryDataApi() {
            let { Data } = await this.$Post("/OrderInfo/GetOrderSummaryData", { ShopId: this.ShopId })
            this.OrderSummaryData = Data;
        },
        async GetOrderIncomeDataApi() {
            let { Data } = await this.$Post("/OrderInfo/GetOrderIncome", { ShopId: this.ShopId })
            this.OrderIncomeData = Data;

            // 对数据进行倒序排列
            const sortedDates = this.OrderIncomeData.Last30dayMoneyList.sort((a, b) => {
                return new Date(b.Date) - new Date(a.Date);
            });

            let option = {
                title: {
                    text: '最近30天总收益趋势',
                    textStyle: {
                        color: '#2c3e50',
                        fontSize: 18,
                        fontWeight: 'normal'
                    }
                },
                tooltip: {
                    trigger: 'axis',
                    backgroundColor: 'rgba(255, 255, 255, 0.9)',
                    borderColor: '#e9e9e9',
                    textStyle: {
                        color: '#666'
                    },
                    axisPointer: {
                        type: 'line',
                        lineStyle: {
                            color: '#8bc34a'
                        }
                    }
                },
                legend: {
                    data: ['总收益'],
                    textStyle: {
                        color: '#666'
                    }
                },
                toolbox: {
                    feature: {
                        saveAsImage: {
                            title: '保存'
                        }
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [{
                    type: 'category',
                    boundaryGap: false,
                    data: sortedDates.map(x => x.Date),
                    axisLine: {
                        lineStyle: {
                            color: '#999'
                        }
                    },
                    axisLabel: {
                        color: '#666'
                    }
                }],
                yAxis: [{
                    type: 'value',
                    axisLine: {
                        lineStyle: {
                            color: '#999'
                        }
                    },
                    axisLabel: {
                        color: '#666',
                        formatter: '{value} 元'
                    },
                    splitLine: {
                        lineStyle: {
                            type: 'dashed',
                            color: '#eee'
                        }
                    }
                }],
                series: [{
                    name: '收入',
                    type: 'line',
                    smooth: true,
                    symbol: 'circle',
                    symbolSize: 8,
                    itemStyle: {
                        color: '#607d8b'
                    },
                    lineStyle: {
                        width: 3,
                        color: '#607d8b'
                    },
                    areaStyle: {
                        color: {
                            type: 'linear',
                            x: 0,
                            y: 0,
                            x2: 0,
                            y2: 1,
                            colorStops: [{
                                offset: 0,
                                color: 'rgba(96, 125, 139, 0.3)'
                            }, {
                                offset: 1,
                                color: 'rgba(96, 125, 139, 0.1)'
                            }]
                        }
                    },
                    data: sortedDates.map(x => x.Value)
                }]
            };
            let myChart = echarts.init(document.getElementById("Last30dayMoneyEchart"));// 图标初始化
            myChart.setOption(option);// 渲染页面
            //随着屏幕大小调节图表
            window.addEventListener("resize", () => {
                myChart.resize();
            });

        },

        async GetGetSeasonGoodSalesEchartApi() {
            let { Data } = await this.$Post("/OrderInfo/GetSeasonGoodSalesEchart", { ShopId: this.ShopId });

            let option = {
                title: {
                    text: '季度销售分布',
                    textStyle: {
                        color: '#2c3e50',
                        fontSize: 18,
                        fontWeight: 'normal'
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{b} : {c}件'
                },
                legend: {
                    data: ['销量'],
                    textStyle: {
                        color: '#666'
                    }
                },
                series: [{
                    name: '销量',
                    type: 'funnel',
                    left: '10%',
                    top: 60,
                    bottom: 60,
                    width: '80%',
                    min: 0,
                    max: Math.max(...Data.map(x => x.Value)) || 10,
                    minSize: '0%',
                    maxSize: '100%',
                    sort: 'descending',
                    gap: 2,
                    label: {
                        show: true,
                        position: 'inside',
                        formatter: '{b} : {c}件',
                        fontSize: 14
                    },
                    labelLine: {
                        length: 10,
                        lineStyle: {
                            width: 1,
                            type: 'solid'
                        }
                    },
                    itemStyle: {
                        borderColor: '#fff',
                        borderWidth: 1
                    },
                    emphasis: {
                        label: {
                            fontSize: 16
                        }
                    },
                    data: [
                        { value: Data[0].Value, name: '冬季(12-3月)', itemStyle: { color: '#607d8b' } },
                        { value: Data[1].Value, name: '秋季(9-12月)', itemStyle: { color: '#455a64' } },
                        { value: Data[2].Value, name: '夏季(6-9月)', itemStyle: { color: '#37474f' } },
                        { value: Data[3].Value, name: '春季(3-6月)', itemStyle: { color: '#90a4ae' } }
                    ]
                }]
            };

            let myChart = echarts.init(document.getElementById("SeasonGoodSalesEchart"));
            myChart.setOption(option);

            window.addEventListener("resize", () => {
                myChart.resize();
            });
        },

        //得到热销的石材
        async GetGoodSalesApi() {
            let { Data } = await this.$Post("/OrderInfo/GetGoodSales", { ShopId: this.ShopId })
            Data = Data.slice(0, 10);

            let option = {
                title: {
                    text: '热门石材销量排行',
                    textStyle: {
                        color: '#2c3e50',
                        fontSize: 18,
                        fontWeight: 'normal'
                    }
                },
                tooltip: {
                    trigger: 'axis',
                    backgroundColor: 'rgba(255, 255, 255, 0.9)',
                    borderColor: '#e9e9e9',
                    textStyle: {
                        color: '#666'
                    },
                    axisPointer: {
                        type: 'shadow',
                        shadowStyle: {
                            color: 'rgba(139, 195, 74, 0.1)'
                        }
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [{
                    type: 'category',
                    data: Data.map(x => x.Good.Name.substring(0, 10)),
                    axisLine: {
                        lineStyle: {
                            color: '#999'
                        }
                    },
                    axisLabel: {
                        color: '#666',
                        interval: 0,
                        rotate: 30
                    }
                }],
                yAxis: [{
                    type: 'value',
                    name: '销量',
                    nameTextStyle: {
                        color: '#666'
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#999'
                        }
                    },
                    axisLabel: {
                        color: '#666'
                    },
                    splitLine: {
                        lineStyle: {
                            type: 'dashed',
                            color: '#eee'
                        }
                    }
                }],
                series: [{
                    name: '销量',
                    type: 'bar',
                    barWidth: '40%',
                    itemStyle: {
                        color: {
                            type: 'linear',
                            x: 0,
                            y: 0,
                            x2: 0,
                            y2: 1,
                            colorStops: [{
                                offset: 0,
                                color: '#607d8b'
                            }, {
                                offset: 1,
                                color: '#90a4ae'
                            }]
                        },
                        borderRadius: [4, 4, 0, 0]
                    },
                    data: Data.map(x => x.Qty)
                }]
            };
            let myChart = echarts.init(document.getElementById("GoodSalesEchart"));// 图标初始化
            myChart.setOption(option);// 渲染页面
            //随着屏幕大小调节图表
            window.addEventListener("resize", () => {
                myChart.resize();
            });


        }
    }
}       
</script>


<style scoped>
.analysis-container {
    padding: 24px;
    background-color: #f8f9f5;
    min-height: 100vh;
}

.page-title {
    font-size: 28px;
    color: #2c3e50;
    margin-bottom: 32px;
    font-weight: 600;
    position: relative;
    padding-left: 16px;
}

.page-title::before {
    content: '';
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    width: 4px;
    height: 24px;
    background: #607d8b;
    border-radius: 2px;
}

.summary-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 24px;
    margin-bottom: 32px;
}

.summary-item {
    border-radius: 16px;
    padding: 28px;
    display: flex;
    align-items: center;
    color: white;
    transition: all 0.3s ease;
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.08);
    backdrop-filter: blur(5px);
}

.summary-item:hover {
    transform: translateY(-6px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
}

.summary-item i {
    font-size: 32px;
    margin-right: 20px;
    opacity: 0.9;
}

.summary-item .content {
    flex: 1;
}

.summary-item .tit {
    font-size: 15px;
    font-weight: 500;
    opacity: 0.95;
    letter-spacing: 0.5px;
}

.summary-item .qty {
    font-size: 28px;
    font-weight: 600;
    margin-top: 10px;
    letter-spacing: 0.5px;
}

/* 更新渐变配色为蓝灰色系列 */
.gradient-1 {
    background: linear-gradient(135deg, #607d8b, #455a64);
}

.gradient-2 {
    background: linear-gradient(135deg, #455a64, #37474f);
}

.gradient-3 {
    background: linear-gradient(135deg, #90a4ae, #78909c);
}

.gradient-4 {
    background: linear-gradient(135deg, #546e7a, #455a64);
}

.gradient-5 {
    background: linear-gradient(135deg, #78909c, #546e7a);
}

.gradient-6 {
    background: linear-gradient(135deg, #455a64, #37474f);
}

.gradient-7 {
    background: linear-gradient(135deg, #90a4ae, #78909c);
}

.gradient-8 {
    background: linear-gradient(135deg, #607d8b, #455a64);
}

.gradient-9 {
    background: linear-gradient(135deg, #78909c, #546e7a);
}

.chart-container {
    margin-top: 30px;
}

.chart-card {
    margin-bottom: 20px;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.chart-card .echart {
    height: 500px;
    padding: 20px;
}
</style>