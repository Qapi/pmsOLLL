/**
 * Created by wangp on 2017/10/4.
 */
window.vm = new Vue({
    el: '#modelDiv',
    data: {
        member: {
            id: '',
            name: '',
            nickName: '',
            hotel: '',
            idNum: '',
            birthday: '',
            gender: '',
            homeAddress: '',
            phone: '',
            email: '',
            emergencyContact: '',
            emergencyContactPhone: '',
            memberNum: '',
            memberLevel: '',
            operator: '',
            integral: '',
            validityTerm: '',
            status: '',
            remarks: '',
        },
        hotels: [],
        memberLevels: []
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
                axios.get(ctx + "/member/member/getModel/" + id).then(response => {
                    const res = response.data;
                    if (res && response.status == "200") {
                        this.member = res;
                    }
                });
            }
            axios.get(ctx + "/hotel/hotel/getList").then(response => {
                const res = response.data;
                if (res && response.status == "200") {
                    this.hotels = res;
                }
            });
            axios.get(ctx + "/memberlevel/memberLevel/getList").then(response => {
                const res = response.data;
                if (res && response.status == "200") {
                    this.memberLevels = res;
                }
            });
        }
        ,
        /** 自动提取身份证号里的生日信息 **/
        calBirth: function () {
            let birth = $(this).val();
            if(idNum.length == 18){
                birth = birth.slice(6,14);
                birth = birth.slice(0,3)+"-"+birth.slice(4,5)+"-"+birth.slice(6,7);
            }
            this.birthday =  birth;
        }
    }
})

