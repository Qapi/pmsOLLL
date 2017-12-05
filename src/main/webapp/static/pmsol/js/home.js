/**
 * Created by wangp on 2017/10/4.
 */
window.vm = new Vue({
    el: '#modelDiv',
    data: {
        rooms: [{
            id: '',
            roomNum: '',
            topicName: '',
            floorNum: '',
            layout: '',
            status: '',
            hotel: '',
            roomType: '',
            status: '',
            remarks: '',
        }],
        hotels: [],
        roomTypes: []
    },
    mounted: function () {
        this.$nextTick(function () {
            this.getModel();
        })
    },
    filters: {},
    methods: {
        /** 获取初始数据 **/
        getModel: function () {
            axios.get(ctx + "/room/room/getList").then(response => {
                const res = response.data;
                if (res && response.status == "200") {
                    this.rooms = res;
                }
            });
            axios.get(ctx + "/hotel/hotel/getList").then(response => {
                const res = response.data;
                if (res && response.status == "200") {
                    this.hotels = res;
                }
            });
            axios.get(ctx + "/roomtype/roomType/getList").then(response => {
                const res = response.data;
                if (res && response.status == "200") {
                    this.roomTypes = res;
                }
            });
        }
        ,
    }
})

$(document).ready(function () {

});