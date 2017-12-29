<!DOCTYPE html>
<html>
<head>
    <title>K��ͼ</title>
    <link href="js/kline.css" rel="stylesheet"/>
    <script src="lib/require.js"></script>
    <style type="text/css">
        .kline {
            width: 1200px;
            margin-left: auto;
            margin-right: auto;
            height: 462px;
            position: relative;
        }
    </style>
</head>
<body>

<div class="kline">

    <h1>K��ͼ</h1>

    <div id="kline_container"></div>
</div>


<script type="text/javascript">

    require.config({
        paths: {
            "jquery": "lib/jquery",
            "jquery.mousewheel": "../lib/jquery.mousewheel",
            "sockjs": "lib/sockjs",
            "stomp": "lib/stomp",
            "kline": "js/kline"
        },
        shim: {
            "jquery.mousewheel": {
                deps: ["jquery"]
            },
            "kline": {
                deps: ["jquery.mousewheel", "sockjs", "stomp"]
            }
        }
    });

    require(['kline'], function () {
        var kline = new Kline({
            element: "#kline_container",
            width: 1200,
            height: 650,
            theme: 'dark', // light/dark
            language: 'zh-cn', // zh-cn/en-us/zh-tw
            ranges: ["1w", "1d", "1h", "30m", "15m", "5m", "1m", "line"],
            symbol: "coin5/coin4",
            symbolName: "COIN5_COIN4",
            type: "poll", // poll/socket
            url: "http://127.0.0.1:8080/mock.json",
            limit: 1000,
            intervalTime: 5000,
            debug: true,
            showTrade: true
        });

        kline.draw();
    });

</script>

</body>
</html>
