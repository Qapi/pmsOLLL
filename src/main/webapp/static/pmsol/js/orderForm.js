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
        channels: [],
        roomTypes: [],
        rooms: [],
        members: []
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
                axios.get(ctx + "/member/member/getModel/" + id).then(response => {
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
    }
})

