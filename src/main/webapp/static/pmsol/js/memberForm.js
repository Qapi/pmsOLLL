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
        // TODO 应对身份证信息做校验
        calBirth: function () {
            let birth = $('#idNum').val();
            if (birth.length == 18) {
                birth = birth.slice(6, 14);
                if (this.checkBirth(birth)) {
                    birth = birth.slice(0, 4) + "-" + birth.slice(4, 6) + "-" + birth.slice(6, 8);
                    $('#birthday').val(birth);
                }
            }
        }
        ,
        /** 校验生日信息 **/
        // TODO 校验方式不够精准
        checkBirth: function (birth) {
            const currentDate = new Date();
            if (+birth.slice(0, 4) < currentDate.getFullYear() + 1 && +birth.slice(4, 6) < currentDate.getMonth() + 2 && +birth.slice(6, 8) < currentDate.getDate() + 1) {
                return true;
            }
            return false;
        }
        ,
    }
})

