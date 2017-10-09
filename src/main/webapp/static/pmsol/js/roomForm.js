/**
 * Created by wangp on 2017/10/4.
 */
window.vm = new Vue({
    el: '#modelDiv',
    data: {
        room: {
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
        },
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
            const id = $('#id').val();
            if (id) {
                axios.get(ctx + "/room/room/getModel/" + id).then(response => {
                    const res = response.data;
                    if (res && response.status == "200") {
                        this.room = res;
                    }
                });
            };
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

