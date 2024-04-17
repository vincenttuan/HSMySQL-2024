window.addEventListener('load', function() {

    var isWindowTop = false;
    var lastTouchY = 0;

    var touchStartHandler = function(e) {
        if (e.touches.length !== 1) return;
        lastTouchY = e.touches[0].clientY;
        isWindowTop = (window.pageYOffset === 0);
    };

    var touchMoveHandler = function(e) {
        var touchY = e.touches[0].clientY;
        var touchYmove = touchY - lastTouchY;
        lastTouchY = touchY;

        if (isWindowTop) {
            isWindowTop = false;
            // 阻擋移動事件
            if (touchYmove > 0) {
                e.preventDefault();
                return;
            }
        }

    };

    document.addEventListener('touchstart', touchStartHandler, false);
    document.addEventListener('touchmove', touchMoveHandler, false);

});

var canvasDiv = document.getElementById('canvasDiv');
var canvas = document.createElement('canvas');
var screenwidth = (window.innerWidth > 0) ? window.innerWidth : screen.width;

var canvasWidth =  screenwidth;
var canvasHeight = 480;
document.addEventListener('touchmove', onDocumentTouchMove, false);
var point = {};
point.notFirst = false;
canvas.setAttribute('width', canvasWidth);
canvas.setAttribute('height', canvasHeight);
canvas.setAttribute('id', 'canvas');
canvasDiv.appendChild(canvas);
if (typeof G_vmlCanvasManager != 'undefined') {
    canvas = G_vmlCanvasManager.initElement(canvas);
}
var context = canvas.getContext("2d");
/*var ptrn = context.createPattern(img, 'no-repeat');
context.fillStyle = ptrn;
context.fillRect(0, 0, canvas.width, canvas.height);
*/
var img = new Image();
//img.src = "/HSMySQL/images/signature.png";

img.onload = function() {
    var ptrn = context.createPattern(img, 'repeat');
    context.fillStyle = ptrn;
    context.fillRect(0, 0, canvas.width, canvas.height);
}
canvas.addEventListener("touchstart", function(e) {
	//console.log(e);
    var mouseX = e.touches[0].pageX - this.offsetLeft;
    var mouseY = e.touches[0].pageY - this.offsetTop;
    paint = true;
    addClick(e.touches[0].pageX - this.offsetLeft, e.touches[0].pageY - this.offsetTop);
	//console.log(e.touches[0].pageX - this.offsetLeft, e.touches[0].pageY - this.offsetTop);
    redraw();
});



canvas.addEventListener("touchend", function(e) {
	//console.log("touch end");
    paint = false;
});

canvas.addEventListener("touchmove", function(e) {
    if (paint) {
		//console.log("touchmove");
        addClick(e.touches[0].pageX - this.offsetLeft, e.touches[0].pageY - this.offsetTop, true);
		//console.log(e.touches[0].pageX - this.offsetLeft, e.touches[0].pageY - this.offsetTop);
        redraw();
    }

});

canvas.addEventListener("mousedown", function(e) {
    var mouseX = e.pageX - this.offsetLeft;
    var mouseY = e.pageY - this.offsetTop;
    paint = true;
    addClick(e.pageX - this.offsetLeft, e.pageY - this.offsetTop);
    redraw();
});
canvas.addEventListener("mousemove", function(e) {
    if (paint) {
        addClick(e.pageX - this.offsetLeft, e.pageY - this.offsetTop, true);
        redraw();
    }
});
canvas.addEventListener("mouseup", function(e) {
    paint = false;
});
canvas.addEventListener("mouseleave", function(e) {
    paint = false;
});
document.getElementById("btn_clear").addEventListener("click", function() {
    canvas.width = canvas.width;
});
document.getElementById("btn_submit").addEventListener("click", function() {
    $("#qmimg").attr("src", canvas.toDataURL("image/png"));
    var base64 = canvas.toDataURL("image/png");
    $("#text").text(base64);
    window.parent.opener.callBackIntegrationCompleted(base64);
    window.close();
});


function onDocumentTouchStart(event) {
    if (event.touches.length == 1) {
        event.preventDefault();
        // Faking double click for touch devices
        var now = new Date().getTime();
        if (now - timeOfLastTouch < 250) {
            reset();
            return;
        }
        timeOfLastTouch = now;
        mouseX = event.touches[0].pageX;
        mouseY = event.touches[0].pageY;
        isMouseDown = true;

    }

}



function onDocumentTouchMove(event) {

    if (event.touches.length == 1) {
        event.preventDefault();
        mouseX = event.touches[0].pageX;
        mouseY = event.touches[0].pageY;
    }
}



function onDocumentTouchEnd(event) {
    if (event.touches.length == 0) {
        event.preventDefault();
        isMouseDown = false;
    }
}


var clickX = new Array();
var clickY = new Array();
var clickDrag = new Array();
var paint;

function addClick(x, y, dragging)
{
    clickX.push(x);
    clickY.push(y);
    clickDrag.push(dragging);
}



function redraw() {

    //canvas.width = canvas.width; // Clears the canvas
    context.strokeStyle = "#df4b26";
    context.lineJoin = "round";
    context.lineWidth = 6;
    while (clickX.length > 0) {
        point.bx = point.x;
        point.by = point.y;
        point.x = clickX.pop();
        point.y = clickY.pop();
        point.drag = clickDrag.pop();
        context.beginPath();
        if (point.drag && point.notFirst) {
            context.moveTo(point.bx, point.by);
        } else {
            point.notFirst = true;
            context.moveTo(point.x - 1, point.y);
        }
        context.lineTo(point.x, point.y);
        context.closePath();
        context.stroke();
    }

    

}
