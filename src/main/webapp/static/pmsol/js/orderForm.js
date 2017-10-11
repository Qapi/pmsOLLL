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
            leaseMode: '0',
            checkInDate: '',
            checkOutDate: '',
            liveDays: 0,
            liveHours: 0,
            rentMonths: 0,
            hourPrice: 0,
            dailyPrice: 0,
            monthlyRent: 0,
            totalAmount: 0,
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
            {flag: false, value: '0', name: '日租'},
            {flag: false, value: '1', name: '时租'},
            {flag: false, value: '2', name: '月租'}
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
            const mode = $('#leaseMode option:selected').val();
            switch (mode) {
                case '0':
                    this.order.totalAmount = this.order.liveDays * this.order.dailyPrice;
                    break;
                case '1':
                    this.order.totalAmount = this.order.liveHours * this.order.hourPrice;
                    break;
                case '2':
                    this.order.totalAmount = this.order.rentMonths * this.order.monthlyRent;
                    break;
            }
            return this.order.totalAmount;
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
        /** 初始化租赁类型 **/
        initMode: function () {
            const mode = $('#leaseMode').data('id');
            if (mode != null) {
                this.leaseModes.forEach((ele, index) => {
                    if (+mode == index) {
                        ele.flag = true;
                    } else {
                        ele.flag = false;
                    }
                })
            }
        }
        ,
        /** 根据租赁类型更改可输入入住时长和订单金额 **/
        selectLeaseMode: function (ele) {
            alert(this.order.leaseMode);
            // const mode = ele.target.value;
            // this.leaseModes.forEach(function (ele, index) {
            //     if (+mode == index) {
            //         ele.flag = true;
            //     } else {
            //         ele.flag = false;
            //     }
            // })
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

    }
});

