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
            {name: '日租', value: '0'},
            {name: '时租', value: '1'},
            {name: '月租', value: '2'}
        ],
    },
    mounted: function () {
        this.$nextTick(function () {
            this.getModel();
        })
    },
    computed: {
        /** 自动计算订单总额 **/
        calTotalAmount: function () {
            switch (this.order.leaseMode) {
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
                        // // 因为是新订单，使用默认房型设定单价
                        // this.updatePrice(res[0]);
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
        /** 根据房型过滤可选房间并更新订单显示价格 **/
        selectRoomType: function (ele) {
            const roomTypeId = ele.target.value;
            axios.get(ctx + "/room/room/getList", {params: {roomTypeId: roomTypeId}}).then(response => {
                const res = response.data;
                if (res && response.status == "200") {
                    this.rooms = res;
                }
            });
            this.roomTypes.forEach((roomType,index) => {
                if(roomType.id == roomTypeId){
                    this.updatePrice(roomType);
                }
            })
        }
        ,
        /** 根据房型更新订单显示价格 **/
        updatePrice: function (roomType) {
            this.order.dailyPrice = roomType.dailyPrice;
            this.order.hourPrice = roomType.hourPrice;
            this.order.monthlyRent = roomType.monthlyRent;
        }
    }
});

