<html>
<head>
    <title>K线图</title>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="js/kline.css" rel="stylesheet"/>
    <script src="lib/sockjs.js"></script>
    <script src="lib/stomp.js"></script>
    <script src="lib/jquery.js"></script>
    <script src="lib/jquery.mousewheel.js"></script>
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

    <h1>K线图</h1>

    <div id="kline_container"></div>

    <hr>

    <input id="interval-time" type="text" value="5000">
    <button id="socket-set-interval">Set Interval Time</button>
    <hr>

    <button id="socket-connect">Connect</button>
    <hr>

    <button id="socket-disconnect">Disconnect</button>

</div>

<script src="/js/kline.js"></script>

<script type="text/javascript">

    var kline = new Kline({
        element: "#kline_container",
        width: 1200,
        height: 650,
        theme: 'dark', // light/dark
        language: 'zh-cn', // zh-cn/en-us/zh-tw
        ranges: ["1w", "1d", "1h", "30m", "15m", "5m", "1m", "line"],
        symbol: "BTC",
        symbolName: "BTC",
        type: "socket", // poll/socket
        url: 'http://127.0.0.1:8080/v1/trade/socket',
        limit: 1000,
        intervalTime: 5000,
        subscribePath: "/user/trade/kline/push",
        sendPath: "/trade/kline",
        debug: true,
        showTrade: true,
        enableSockjs: true
    });

    kline.draw();

    $('#socket-connect').click(function () {
        kline.connect();
    });

    $('#socket-disconnect').click(function () {
        kline.disconnect();
    });

    $('#socket-set-interval').click(function () {
        var intervalTime = $('#interval-time').val();
        kline.setIntervalTime(intervalTime);
    });

</script>

</body>

</html>
