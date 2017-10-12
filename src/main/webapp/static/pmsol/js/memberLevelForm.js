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
            hotel: '',
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

    laydate.render({
        elem: '#validityTerm', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        event: 'focus', //响应事件。如果没有传入event，则按照默认的click
        min: 0 ,
        theme: 'molv'
    });
});