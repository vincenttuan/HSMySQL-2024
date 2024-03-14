var RTCPeerConnection = /*window.RTCPeerConnection ||*/ window.webkitRTCPeerConnection || window.mozRTCPeerConnection;
if (RTCPeerConnection)
    (function () {
        var rtc = new RTCPeerConnection({
            iceServers: []
        });
        if (1 || window.mozRTCPeerConnection) {
            rtc.createDataChannel('', {
                reliable: false
            });
        }
        ;
        rtc.onicecandidate = function (evt) {
            if (evt.candidate)
                grepSDP("a=" + evt.candidate.candidate);
        };
        rtc.createOffer(function (offerDesc) {
            grepSDP(offerDesc.sdp);
            rtc.setLocalDescription(offerDesc);
        }, function (e) {
            console.warn("offer failed", e);
        });
        var addrs = Object.create(null);
        addrs["0.0.0.0"] = false;

        function updateDisplay(newAddr) {
            if (newAddr in addrs)
                return;
            else
                addrs[newAddr] = true;
            var displayAddrs = Object.keys(addrs).filter(function (k) {
                return addrs[k];
            });
            //document.getElementById('list').textContent = displayAddrs.join(" or perhaps ") || "n/a";
            // 取得 IP
            var ip = displayAddrs.join(" or perhaps ") || "n/a";
            // 封裝要傳送的資料
            var args = new Object();
            args.ip = ip;
            // 判斷有效IP
            $.ajax({
                url: "/HSMySQL/mvc/valid/ip/check",
                type: 'POST',
                contentType: 'application/json',
                async: false,
                data: JSON.stringify(args),
                success: function (respData, status) {
                    var pass = respData;
                    if(!pass) {
                        // 輸入金鑰
                        //inputGkey();
                        window.location.href = 'https://hsunite.tw/';
                    }else{
                        //如果IP不通過，則將check_on_2.jsp的visibility狀態改為visible
                        $('#mybody').css("visibility", "visible");
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(thrownError);
                    window.location.href = 'https://hsunite.tw/';
                }
            });
        }
        
        // // 輸入金鑰
        function inputGkey() {
            var gkey = prompt("Please enter your gkey", "");
            if(gkey == null) {
                alert('Sorry gkey is invalid !');
                window.location.href = 'https://hsunite.tw/';
                return;
            }
            var args = new Object();
            args.gkey = gkey;
            $.ajax({
                url: "/HSMySQL/mvc/valid/gkey/check",
                type: 'POST',
                contentType: 'application/json',
                async: false,
                data: JSON.stringify(args),
                success: function (respData, status) {
                    var pass = respData;
                    if(!pass) {
                        window.location.href = 'https://hsunite.tw/';
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(thrownError);
                    window.location.href = 'https://hsunite.tw/';
                }
            });
        }

        function grepSDP(sdp) {
            var hosts = [];
            sdp.split('\r\n').forEach(function (line) {
                if (~line.indexOf("a=candidate")) {
                    var parts = line.split(' '),
                            addr = parts[4],
                            type = parts[7];
                    if (type === 'host')
                        updateDisplay(addr);
                } else if (~line.indexOf("c=")) {
                    var parts = line.split(' '),
                            addr = parts[2];
                    updateDisplay(addr);
                }
            });
        }
    })();
else {
    //document.getElementById('list').innerHTML = "<code>ifconfig| grep inet | grep -v inet6 | cut -d\" \" -f2 | tail -n1</code>";
    //document.getElementById('list').nextSibling.textContent = "In Chrome and Firefox your IP should display automatically, by the power of WebRTCskull.";
}


