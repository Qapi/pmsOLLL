/**
 * Created by wangp on 2017/10/4.
 */
window.vm = new Vue({
    el: '#modelDiv',
    data: {
        order: {
            id: '',
            orderNum: '',
            chlOrderNum: '',
            channel: '',
            hotel: '',
            roomType: '',
            leaseMode: '',
            checkInDate: '',
            checkOutDate: '',
            liveDays: '',
            liveHours: '',
            rentMonths: '',
            hourPrice: '',
            dailyPrice: '',
            monthlyRent: '',
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
            {value: false, multiplier1: this.order.liveDays, multiplier2: this.order.dailyPrice},
            {value: false, multiplier1: this.order.liveHours, multiplier2: this.order.hourPrice},
            {value: false, multiplier1: this.order.rentMonths, multiplier2: this.order.monthlyRent}
        ],
    },
    mounted: function () {
        this.$nextTick(function () {
            this.getModel();
            this.initMode();
        })
    },
    computed: {
        /** 自动计算订单总额 **/
        calTotalAmount: function () {
            this.order.totalAmount =
                this.leaseModes[+this.order.leaseMode].multiplier1
                *
                this.leaseModes[+this.order.leaseMode].multiplier2
        }
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
            } else {
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
                        // 因为是新订单，使用默认房型设定单价
                        this.order.dailyPrice = res[0].dailyPrice;
                        this.order.hourPrice = res[0].hourPrice;
                        this.order.monthlyRent = res[0].monthlyRent;
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
        /** 初始化租赁类型 **/
        initMode: function () {
            // TODO 考虑转为监测order.leaseMode的数值状态
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

