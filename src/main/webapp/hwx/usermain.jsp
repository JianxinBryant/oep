<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <style>
        .barrage .screen {
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0px;
            right: 0px;
            z-index: 0;
            opacity:1;
        }


    
     

        .barrage .screen .mask div {
            position: absolute;
            font-size: 20px;
            font-weight: bold;
            white-space: nowrap;
            line-height: 40px;
            z-index: 40;
        }

        .barrage .send {
            z-index: 1;
            width: 100%;
            height: 55px;
            background: #cccccc;
            position: absolute;
            bottom: 0px;
            text-align: center;
        }

        .barrage .send .s_text {
            width: 600px;
            height: 40px;
            line-height: 10px;
            font-size: 20px;
            font-family: "微软雅黑";
        }

        .barrage .send .s_btn {
            width: 105px;
            height: 40px;
            background: #22B14C;
            color: #fff;
        }
        #textStyle{
            position:absolute;
            font-size:24px;
            color:#fff;
        }
    </style>

</head>

<body>

        <div class="barrage">
            <div class="screen">
                <a href="#" class="s_close">X</a>
                <div class="mask">
                    <!--内容在这里显示-->
                    <h1>kramer臭不要脸</h1>
                </div>
            </div>
            <!--Send Begin-->
            <div class="send">
                <input type="text" class="s_text"/>
                <input type="button" class="s_btn" value="说两句"/>
            </div>
            <!--Send End-->
            
        </div>
<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script>

    $(function () {
        $(".showBarrage,.s_close").click(function () {
            $(".barrage,.s_close").toggle("fast");
        });
        init_barrage();
    })

    //提交评论
    $(".send .s_btn").click(function () {
        var text = $(".s_text").val();
        if (text == "") {
            return;
        }

        var _lable = $("<div style='right:20px;top:0px;opacity:1;color:" + getRandomColor() + ";'>" + text + "</div>");
        $(".mask").append(_lable.show());
        init_barrage();
    })

    //初始化弹幕技术
    function init_barrage() {
        var _top = 0;
        $(".mask div").show().each(function () {
                    var _left = $(window).width() - $(this).width();//浏览器最大宽度，作为定位left的值
                    var _height = $(window).height();//浏览器最大高度
                    _top += 75;
                    if (_top >= (_height - 130)) {
                        _top = 0;
                    }
                    $(this).css({left: _left, top: _top, color: getRandomColor()});

                   //定时弹出文字
                    var time = 10000;
                    if ($(this).index() % 2 == 0) {
                        time = 15000;
                    }

                    $(this).animate({left: "-" + _left + "px"}, time, function () {
                        $(this).remove();
                    });

                }
        );

    }

    //获取随机颜色
    function getRandomColor() {
        return '#' + (function (h) {
                    return new Array(7 - h.length).join("0") + h
                })((Math.random() * 0x1000000 << 0).toString(16))
    }

</script>
</body>
</html>