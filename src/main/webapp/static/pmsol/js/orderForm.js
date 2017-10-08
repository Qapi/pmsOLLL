/**
 * Created by wangp on 2017/10/4.
 */
window.vm = new Vue({
    el: '#modelDiv',
    data: {
        order: {
            id: '',
            orderNum: '',
            channel: '',
            hotel: '',
            roomType: '',
            leaseMode: '',
            rentMonths: '',
            checkInDate: '',
            checkOutDate: '',
            liveDays: '',
            totalAmount: '',
            contacts: '',
            contactsPhone: '',
            booker: '',
            bookRoom: '',
            status: '',
            createDate: '',
            remarks: '',
        },
        hotels: [],
        members: [],
        roomTypes: [],
        rooms: [],
        channels: [],
        leaseModes: [
            {value: false},
            {value: false},
            {value: false}
        ],
    },

    mounted: function () {
        this.$nextTick(function () {
            this.getModel();
            this.initMode();
        })
    },
    filters: {},
    methods: {
        /** 获取初始数据 **/
        getModel: function () {
            const id = $('#id').val();
            if (id) {
                axios.get(ctx + "/order/order/getModel/" + id).then(response => {
                    const res = response.data;
                    if (res && response.status == "200") {
                        this.order = res;
                    }
                });
            }
            axios.get(ctx + "/hotel/hotel/getList").then(response => {
                const res = response.data;
                if (res && response.status == "200") {
                    this.hotels = res;
                }
            });
            axios.get(ctx + "/member/member/getList").then(response => {
                const res = response.data;
                if (res && response.status == "200") {
                    this.members = res;
                }
            });
            axios.get(ctx + "/roomtype/roomType/getList").then(response => {
                const res = response.data;
                if (res && response.status == "200") {
                    this.roomTypes = res;
                }
            });
            axios.get(ctx + "/room/room/getList").then(response => {
                const res = response.data;
                if (res && response.status == "200") {
                    this.rooms = res;
                }
            });
            axios.get(ctx + "/hotelchannel/hotelChannel/getList").then(response => {
                const res = response.data;
                if (res && response.status == "200") {
                    this.channels = res;
                }
            });
        }
        ,
        /** 初始化租赁类型 **/
        initMode: function () {
            const mode = $('#leaseMode').data('id');
            if (mode != null) {
                this.leaseModes.forEach(function (ele, index) {
                    if (+mode == index) {
                        ele.value = true;
                    } else {
                        ele.value = false;
                    }
                })
            }
        }
        ,
        /** 根据房型过滤可选房间 **/
        selectRoomType: function (ele) {
            axios.get(ctx + "/room/room/getList", {params: {roomTypeId: ele.target.value}}).then(response => {
                const res = response.data;
                if (res && response.status == "200") {
                    this.rooms = res;
                }
            });
        }
        ,
        /** 根据租赁类型更改可输入入住时长和订单金额 **/
        selectLeaseMode: function (ele) {
            const mode = ele.target.value;
            this.leaseModes.forEach(function (ele, index) {
                if (+mode == index) {
                    ele.value = true;
                } else {
                    ele.value = false;
                }
            })
        }
    }
});

