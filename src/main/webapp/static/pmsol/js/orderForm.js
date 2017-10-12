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
            liveDays: 1,
            liveHours: 4,
            rentMonths: 1,
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
            switch (+this.order.leaseMode) {
                case 0:
                    this.order.totalAmount = this.order.liveDays * this.order.dailyPrice;
                    break;
                case 1:
                    this.order.totalAmount = this.order.liveHours * this.order.hourPrice;
                    break;
                case 2:
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
                        // 若为时租类型，则禁止修改离店日期，默认为入住日期
                        // 若为月租类型，则禁止修改离店日期，默认为入住日期下月或增加租赁月数倍数月份的的同日
                        if (this.order.leaseMode != 0) {
                            $('#checkOutDate').attr('disabled', true);
                        }
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
            // 若已超过可售时间，则不显示时租房信息，目前设定为每日18：00前
            if (new Date().getHours() > 18) {
                this.leaseModes.splice(1, 1);
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
            this.roomTypes.forEach((roomType, index) => {
                if (roomType.id == roomTypeId) {
                    this.updatePrice(roomType);
                }
            })
        }
        ,
        /** 根据房型更新订单显示价格 **/
        updatePrice: function (roomType) {
            if (roomType) {
                this.order.dailyPrice = roomType.dailyPrice;
                this.order.hourPrice = roomType.hourPrice;
                this.order.monthlyRent = roomType.monthlyRent;
            }
        }
        ,
        /** 租赁类型切换引发事件 **/
        selectLeaseMode: function (ele) {
            // 若为时租类型，则禁止修改离店日期，默认为入住日期
            // 若为月租类型，则禁止修改离店日期，默认为入住日期下月或增加租赁月数倍数月份的的同日
            if (this.order.leaseMode != 0) {
                $('#checkOutDate').attr('disabled', true);
            } else {
                $('#checkOutDate').attr('disabled', false);
            }
            // 清除入住日期和离店日期及入住时长
            $('#checkInDate').val('');
            $('#checkOutDate').val('');
            this.order.liveHours = 0;
            this.order.liveDays = 0;
            this.order.rentMonths = 0;
        }
        ,
        /** 根据输入入住时长反向计算离店日期 **/
        calCheckOutDate: function (ele) {
            const diff = +ele.target.value;
            let checkInDate = $('#checkInDate').val();
            let newDate;
            if (diff != '' && checkInDate != '') {
                if (this.order.leaseMode == '0') {
                    if(diff > 365){
                        layer.msg('输入有误,最多只能连续预订365天!');
                        ele.target.value = '';
                    }else{
                        newDate = new Date(checkInDate.replace(/-/g, "/"));
                        const newTime = newDate.getTime() + (diff * 1000 * 60 * 60 * 24); // 增加相应天数的毫秒差
                        newDate = new Date(newTime);
                        const newYear = newDate.getFullYear();
                        const newMonth = +newDate.getMonth() + 1 < 10 ? '0' + (+newDate.getMonth() + 1) : +newDate.getMonth() + 1;
                        const newDay = newDate.getDate() < 10 ? '0' + newDate.getDate() : +newDate.getDate();
                        newDate = newYear + '-' + newMonth + '-' + newDay;
                    }
                } else if (this.order.leaseMode == '2') {
                    if(diff > 12){
                        layer.msg('输入有误,最多只能连续预订12个月!');
                        ele.target.value = '';
                    }else {
                        let newYear = checkInDate.slice(0, 4);
                        let newMonth = +checkInDate.slice(5, 7) + diff;
                        let newDay = checkInDate.slice(8, 11);
                        if (newMonth > 12) {
                            newYear++;
                            newMonth = newMonth - 12;
                        }
                        newMonth = newMonth < 10 ? '0' + newMonth : newMonth;
                        newDay = newDay < 10 ? '0' + newDay : newDay;
                        newDate = newYear + '-' + newMonth + '-' + newDay;
                    }
                }
                $('#checkOutDate').val(newDate);
            }
        }
    }
});

var validateForm;

function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
    if (validateForm.form()) {
        $("#inputForm").submit();
        return true;
    }

    return false;
}

$(document).ready(function () {
    // 提交校验
    validateForm = $("#inputForm").validate({
        submitHandler: function (form) {
            loading('正在提交，请稍等...');
            form.submit();
        },
        errorContainer: "#messageBox",
        errorPlacement: function (error, element) {
            $("#messageBox").text("输入有误，请先更正。");
            if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        }
    });

    /** 日期设定及变换 **/
    laydate.render({
        elem: '#checkInDate',
        event: 'focus',
        min: 0,
        max: 365, // 默认只能预订一年内房间
        theme: 'molv',
        done: (value1, date1, endDate1) => {
            // 设定离店日期，1：日租方式则设定为入住日期后一天  2：时租方式则设定为入住日期  3：月租方式则设定为入住日期下月同日
            let dateModel, year = date1.year, month = date1.month, day = date1.date;
            const mode = $('#leaseMode option:selected').val();
            switch (+mode) {
                case 0:
                    if (day == laydate.getEndDate(year, month)) {
                        if (month == 12) {
                            year++;
                            month = '01';
                            day = '01';
                        } else {
                            month++;
                            day = '01';
                        }
                    } else {
                        day++;
                    }
                    dateModel = year + '-' + month + '-' + day;
                    window.vm.order.liveDays = 1; // 入住天数重置为1
                    break;
                case 1:
                    dateModel = year + '-' + month + '-' + day;
                    break;
                case 2:
                    if (month == 12) {
                        year++;
                        month = '01';
                    } else {
                        month++;
                    }
                    dateModel = year + '-' + month + '-' + day;
                    window.vm.order.rentMonths = 1; // 入住月数重置为1
                    break;
            }
            $('#checkOutDate').replaceWith($('#checkOutDate').clone()); // 元素换新以重置日期插件效果
            laydate.render({
                elem: '#checkOutDate',
                event: 'focus',
                value: dateModel,
                min: dateModel,
                max: 366, // 默认只能预订一年内房间
                theme: 'molv',
                done: (value2, date2, endDate2) => {
                    if (+mode == 0) {
                        window.vm.order.liveDays = calDiffForDate(new Date(value1.replace(/-/g, "/")), new Date(value2.replace(/-/g, "/")));
                    }
                }
            });
        }
    });

    /** 计算两个日期的差数 **/
    function calDiffForDate(date1, date2) {
        const times = date2.getTime() - date1.getTime();
        const diff = parseInt(times / (1000 * 60 * 60 * 24));
        return diff;
    }
});