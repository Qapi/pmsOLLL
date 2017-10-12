/**
 * Created by wangp on 2017/10/4.
 */
window.vm = new Vue({
    el: '#modelDiv',
    data: {
        room: {
            id: '',
            roomNum: '',
            topicName: '',
            floorNum: '',
            layout: '',
            status: '',
            hotel: '',
            roomType: '',
            status: '',
            remarks: '',
        },
        hotels: [],
        roomTypes: []
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
                axios.get(ctx + "/room/room/getModel/" + id).then(response => {
                    const res = response.data;
                    if (res && response.status == "200") {
                        this.room = res;
                    }
                });
            };
            axios.get(ctx + "/hotel/hotel/getList").then(response => {
                const res = response.data;
                if (res && response.status == "200") {
                    this.hotels = res;
                }
            });
            axios.get(ctx + "/roomtype/roomType/getList").then(response => {
                const res = response.data;
                if (res && response.status == "200") {
                    this.roomTypes = res;
                }
            });
        }
        ,
    }
})

var validateForm;
function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
    if (validateForm.form()) {
        $("#inputForm").submit();
        return true;
    }

    return false;
}
$(document).ready(function () {
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

});