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
            hotelId: '',
            floorNum: '',
            layout: '',
            roomTypeId: '',
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
        getModel: function () {
            const id = $('#id').val();
            if (id) {
                axios.get(ctx + "/room/room/getModel/" + id).then(response => {
                    const res = response.data;
                    if (res && response.status == "200") {
                        this.roomType = res;
                    }
                });
            }
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

