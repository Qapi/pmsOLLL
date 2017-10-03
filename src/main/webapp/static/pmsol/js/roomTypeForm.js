/**
 * Created by wangp on 2017/10/2.
 */
window.vm = new Vue({
    el: '#modelDiv',
    data: {
        roomType: {
            id: '',
            name: '',
            capacity: '',
            hotelId: '',
            bedNum: '',
            dailyPrice: '',
            holidayPrice: '',
            hourPrice: '',
            hourNum: '',
            monthlyRent: '',
            remarks: ''
        },
        hotels: []
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
                axios.get(ctx + "/roomtype/roomType/getModel/" + id).then(response => {
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
        }
        ,
    }
})

