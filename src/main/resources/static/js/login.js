$(function () {
    $("#user_insert_btn").click(function () {

        var kaptcha = $("#kaptcha").val();
        if (kaptcha.length == 0) {
            alert("您没有输入验证码！");
        } else {


            var name = $("#name").val();
            var password = $("#password").val();
            //发送ajax请求修改员工数据
            $.ajax({
                url: "login",
                data:
                    {name: name, password: password},
                type: "post",
                success: function (msg) {
                    console.log(msg);
                    //100表示成功
                    if (msg.code == 100) {
                        //跳到发表文章页面:因为路线二，所以把这个注释先
                        //window.location.href = "article/publish.html?";

                        //跳到显示全部文章页面
                        //window.location.href = "article/showAllArticle.html?";

                        //跳转到创建文件夹及显示全部文件夹功能页面
                        window.location.href = "showAllFolder";

                        //后台获取要加一杠/showAllArticle
                        //window.location.href = "showAllArticle";
                    } else {
                        $('#prompt').text(msg.map.errMsg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }

            });

        }


    });
});
