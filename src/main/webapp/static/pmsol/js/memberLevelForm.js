/**
 * Created by wangp on 2017/10/4.
 */
window.vm = new Vue({
    el: '#modelDiv',
    data: {
        memberLevel: {
            id: '',
            name: '',
            flag: '',
            salePercent: '',
            status: '',
            remarks: '',
        },
        hotels: [],
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
                axios.get(ctx + "/memberlevel/memberLevel/getModel/" + id).then(response => {
                    const res = response.data;
                    if (res && response.status == "200") {
                        this.memberLevel = res;
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

