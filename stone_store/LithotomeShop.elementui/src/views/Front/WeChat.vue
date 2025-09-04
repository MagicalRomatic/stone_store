<template>
    <div>

        <div class="Topstory-container">
            <div class="Topstory-mainColumn">

                <div class="chat flex  ">
                    <div class="bar padding-xs">
                        <div>
                            <img :src="UserInfo.ImageUrls ? UserInfo.ImageUrls : require('@/assets/默认头像.png')"
                                style="width:40px;height: 40px;border-radius: 5px;">
                        </div>
                        <div class="margin-top-sm text-center text-xs"><i class="el-icon-message-solid "
                                :class="active == 0 ? 'text-green' : 'text-white'" @click="tabChange(0)"></i>
                        </div>
                        <!-- <div class="margin-top-sm text-center text-xs"><i class="el-icon-user-solid"
                                :class="active == 1 ? 'text-green' : 'text-white'" @click="tabChange(1)"></i></div> -->
                    </div>
                    <template v-if="active == 0">
                        <div class="chat-list bg-white" style="width: 200px;">
                            <div v-for="(item, index) in WechatCollectionList" @click="SelectOtherUserChatBtn(item)">
                                <div class="padding-xs flex align-center pointer"
                                    :class="SelectChatFrined != null && SelectChatFrined.Id == item.OtherUserId ? 'active' : ''">
                                    <img :src="item.OtherUserDto.ImageUrls ? item.OtherUserDto.ImageUrls : require('@/assets/默认头像.png')"
                                        style="width:50px;height: 50px;border-radius: 5px;">
                                    <div class="padding-left-xs flex-sub">
                                        <div class="flex justify-between "> <span
                                                class=" flex-sub text-bold text-black">{{
                                                    item.OtherUserDto.Name
                                                }}</span>
                                            <!-- <div class="wait_msg " v-if="item.WaitReadMsgCount > 0"><span>{{
                                                item.WaitReadMsgCount }}</span></div> -->
                                        </div>
                                        <div class="new_content">{{ item.LastMessage }}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="chat-window flex-sub">
                            <div v-if="SelectChatFrined != null" class="" style="width:100%;height:100%;">
                                <div class="padding-xs solid-bottom chat-header">{{ SelectChatFrined.Name }}</div>
                                <div id="chat-content-list" class="chat-content-list">
                                    <template v-for="(message) in MessageList">
                                        <div class="chat-content-item">
                                            <!--选中的好友是发送人-->
                                            <div v-if="UserId != message.SelfUserId" class="flex  padding-top-xs ">
                                                <div class="margin-right-xs">
                                                    <img :src="message.SelfUserDto.ImageUrls ? message.SelfUserDto.ImageUrls : require('@/assets/默认头像.png')"
                                                        style="width:40px;height: 40px;border-radius: 5px;">
                                                </div>
                                                <div>
                                                    <div class="text-left">{{ message.SelfUserDto.Name }}</div>
                                                    <div class="content ">{{ message.Content }}</div>
                                                </div>

                                            </div>
                                            <div v-if='UserId == message.SelfUserId'
                                                class="flex justify-end padding-top-xs ">
                                                <div>
                                                    <div class="text-right">{{ message.SelfUserDto.Name }}</div>
                                                    <div class="self-content  ">{{ message.Content }}</div>
                                                </div>
                                                <div class="margin-left-xs">
                                                    <img :src="message.SelfUserDto.ImageUrls ? message.SelfUserDto.ImageUrls : require('@/assets/默认头像.png')""
                                                        style=" width:40px;height: 40px;border-radius: 5px;">
                                                </div>

                                            </div>
                                        </div>
                                        <div class="margin-top-xs text-center text-gray">{{ message.SendTime }}
                                        </div>
                                    </template>
                                </div>
                                <div class="send-bottom solid-top ">

                                    <el-input type="textarea" min="1" rows="5" max="1000" clearable autofocus
                                        resize="none" class="input-content" placeholder="请输入..."
                                        v-model="WaitSeedContent">
                                    </el-input>
                                    <div
                                        class="flex justify-end align-center padding-right-xs padding-bottom-xs margin-top-lg">
                                        <el-button class="" size="mini" @click="ClearContent()">清空</el-button>
                                        <el-button class="" type="primary" size="mini" :disabled="!WaitSeedContent"
                                            @click="SendMessageToOtherUser()">发送</el-button>
                                    </div>
                                </div>

                            </div>


                        </div>
                    </template>

                </div>


            </div>
            <div class="side">
                <div v-if="SelectChatFrined != null">

                </div>
            </div>

        </div>


    </div>
</template>

<script>


import { AddSocketMessageMonitor, RemoveSocketMessageMonitor, SendMessage } from '@/utils/webscoket';
import { mapGetters } from 'vuex';
export default {
    name: 'WeChat',
    computed: {
        ...mapGetters([
            "UserId", "UserInfo"
        ])
    },
    components: {

    },
    data() {
        let msgId = new Date().getTime();
        return {
            active: 0,
            WechatCollectionList: [],//好友列表

            MessageList: [],
            isConnectState: false,//连接状态
            SelectChatFrined: null,
            WaitSeedContent: "",//待发送的内容

            msgId: msgId,

        };
    },
    watch: {
        "active": function (n, o) {


        },
    },

    mounted() {
        this.InitSelectWechatUser();
        AddSocketMessageMonitor(this.msgId, this.OnMessage)
    },
    beforeDestroy() {
        RemoveSocketMessageMonitor(this.msgId)
    },
    methods: {
        //滚动到聊天底部
        scrollToElementBottom(element) {
            this.$nextTick(() => {
                element.scrollTop = element.scrollHeight;
            })

        },
        //得到消息返回
        async OnMessage(res) {
            console.log("OnMessage", res);
            if (res.Type == "Wechat_SendMessage_Back") {
                this.MessageList.push(res.Data);
                console.log(this.MessageList);
                this.scrollToElementBottom(document.getElementById("chat-content-list"));
            }
        },

        //初始化选择用户
        async InitSelectWechatUser() {
            await this.GetWechatCollectionListApi();
            if (this.$route.query.OtherUserId) {
                let item = this.WechatCollectionList.find(x => x.OtherUserId == this.$route.query.OtherUserId);
                this.SelectOtherUserChatBtn(item);
            }
        },
        //得到聊天列表
        async GetWechatCollectionListApi() {
            let { Data: { Items } } = await this.$Post("/WechatCollection/List", {
                SelfUserId: this.UserId
            }
            );
            this.WechatCollectionList = Items;

        },

        //选择和那个好友进行聊天
        async SelectOtherUserChatBtn(item) {
            this.SelectChatFrined = item.OtherUserDto;
            //得到2个之间的聊天记录
            let { Data: { Items } } = await this.$Post(`/WechatMessage/List`, {
                SelfUserId: item.SelfUserId,
                OtherUserId: item.OtherUserId,

            });
            this.MessageList = Items;

            this.scrollToElementBottom(document.getElementById("chat-content-list"));
        },
        //和某个好友进行消息发话
        async SendMessageToOtherUser() {
            let body = {
                SelfUserId: this.UserId,
                OtherUserId: this.SelectChatFrined.Id,
                Content: this.WaitSeedContent,
            };
            SendMessage("Wechat_SendMessage", body);
            this.$message.success("消息发送成功");
            this.WaitSeedContent = "";
        },
        async ClearContent() {
            this.WaitSeedContent = "";
        }


    }


}
</script>

<style scoped>
.Topstory-container {

    max-width: var(--content-width);
    margin: 20px auto;
}

.Card {
    background: #fff;
    border-radius: 2px;
    -webkit-box-shadow: 0 1px 3px hsla(0, 0%, 7%, .1);
    box-shadow: 0 1px 3px hsla(0, 0%, 7%, .1);
    -webkit-box-sizing: border-box;
    box-sizing: border-box;

    overflow: hidden;
}

.Topstory-tabs {
    border-bottom: 1px solid #f8f8fa;
}

.TopstoryTabs {
    height: 58px;
    display: flex;
}

.TopstoryTabs-link {
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    font-size: 16px;
    margin: 0 22px;
}

.TopstoryTabs-link.is-active {
    font-weight: 500;
    color: #1772f6;
}

.ContentItem-time {
    color: #8491a5;
    font-size: 14px;
    margin-top: 10px;
}

.content-cut {

    overflow: hidden;
    text-overflow: ellipsis;
    word-break: break-all;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 4;

    line-height: 1.2em;
}

.RightSideBar {
    width: 296px;
    -webkit-align-self: normal;
    -ms-flex-item-align: normal;
    align-self: normal;
}

.CreatorEntrance {
    height: 52px;
    width: 100%;
    display: flex;
    align-items: center;
    padding: 10px;
}

.chat {
    width: 100%;
    height: 600px;
    -webkit-box-shadow: 0 1px 3px hsla(0, 0%, 7%, .1);
    box-shadow: 0 1px 3px hsla(0, 0%, 7%, .1);
    -webkit-box-sizing: border-box;
}

.bar {
    background-color: #1D232A;
    width: 60px;
    height: 100%;
}

.chat-list {
    background-color: #EFEFEF;
    overflow: scroll;




}

.chat-list .active {
    background-color: #D9D9D9;
}

.chat-list .new_content {
    font-size: 13px;
    margin-top: 5px;
    overflow: hidden;
    text-overflow: ellipsis;
    word-break: break-all;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 1;

    line-height: 1.2em;

}

.chat-list .wait_msg {
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: red;
    border-radius: 30%;
    width: 10px;
    height: 10px;
    padding: 7px;
    font-size: 14px;
    color: white;
}

.chat-window {
    background-color: #F4F4F4;
    height: 600px;
    position: relative;




}



.chat-window .chat-header {
    height: 50px;
}

.chat-window .chat-content-list {
    height: 360px;
    overflow: scroll;
}

.chat-window .chat-content-list .chat-content-item {}

.chat-window .chat-content-list .content {
    background-color: white;
    padding: 10px;
    border-radius: 5px;
    max-width: 250px;
    line-height: 1.3;
    position: relative;
}

.chat-window .chat-content-list .content:before {
    content: " ";
    position: absolute;
    top: 6px;
    width: 0;
    height: 0;
    border: 4px solid transparent;
    left: auto;
    left: -4px;
    border-left: none;
    border-right-color: white;
}

.chat-window .chat-content-list .self-content {
    background-color: #35D863;
    padding: 10px;
    border-radius: 5px;
    max-width: 250px;
    width: fit-content;
    line-height: 1.3;
    text-align: right;
    position: relative;
}

.chat-window .chat-content-list .self-content:before {
    content: " ";
    position: absolute;
    top: 6px;
    width: 0;
    height: 0;
    border: 4px solid transparent;
    left: auto;
    right: -4px;
    border-right: none;
    border-left-color: #35d863;
}

.chat-window .chat-content-list .send-bottom {
    position: absolute;
    bottom: 0;
    width: 100%;
    height: 210px;
    background-color: rgb(239, 239, 239);


}

.chat-window .chat-content-list .send-bottom .input-content {
    height: 170px;
}
</style>
