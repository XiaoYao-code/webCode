console.log("OK");

// document 是在浏览器中运行时一直存在的一个变量，表示的意思是代表文档树
// html Document Object Model Tree    DOM 树
// document 粗糙的可以理解成这棵树的根

// getElementById 从树上，根据 id，找到对应的结点（标签）
let preview = document.getElementById("preview");       // <video id="preview" width="160" height="120" autoplay muted></video>
let recording = document.getElementById("recording");
let startButton = document.getElementById("startButton");
let stopButton = document.getElementById("stopButton");
let downloadButton = document.getElementById("downloadButton");

/*
function aaaa(stream) {

}
*/

function wait(delayInMS) {
    // setTimeout(执行什么方法，多少毫秒之后)
    // 类似 Java 中的定时器（Timer）
    // 设定一个闹钟一样的效果
    return new Promise(resolve => setTimeout(resolve, delayInMS));
}

function startRecording(stream, lengthInMS) {
    console.log("开始录制");

    let recorder = new MediaRecorder(stream);   // 定义一个媒体录制对象
    let data = [];

    // 当（on) 数据（data）可用(available) 时，执行该方法
    recorder.ondataavailable = function (event) {
        console.log("数据可用了");
        // event.data 录制下来的视频和音频数据，存入 data 数组
        data.push(event.data);  // 线性表的尾插
    };

    // 开始录制
    recorder.start();

    // resolve 成功的时候应该执行的方法，对应 then 传入的方法
    // reject 失败的时候应该执行的方法，对应 catch 传入的方法
    let stopped = new Promise(function (resolve, reject) {
        recorder.onstop = resolve;
        recorder.onerror = function(event) { 
            reject(event.name);
        }
    });

    // 持续 lengthInMS 时间后，执行 then 中的方法
    let recorded = wait(lengthInMS).then(
        function() {
            // 20 秒之后
            // 判断 recorder 是否还在录制，如果还在录制 == "recording"，则，停止录制
            if (recorder.state == "recording") {
                console.log("停止录制");
                recorder.stop();
            }
        }
    );

    return Promise.all([
        stopped,
        recorded
    ])
    .then(() => data);
}

function startCapturing() {
    console.log("点击了采集");
    
    // 会触发，申请权限的操作
    let promise = navigator.mediaDevices.getUserMedia({
        video: true,        // 申请摄像头权限
        audio: true         // 申请麦克风权限
    });

    // 如果用户同意了，就执行 then 中的方法，如果失败（用户不同意 or 其他失败）会执行 catch 中的方法
    let promise2 = promise.then(function(stream) {
        console.log("用户同意授权");
        // 用户同意了
        // stream 变量就代表录制的视频和音频了
        preview.srcObject = stream;

        // 处理兼容性的，类似 if (!preview.captureStream) { preview.captureStream = preview.mozCaptureStream; }
        preview.captureStream = preview.captureStream || preview.mozCaptureStream;

        // 接着执行的是，当 preview 开始(on) 播放(palying) 时，执行 then 的方法
        // resolve 形参对应的实参就是 xxxx 函数
        return new Promise(function(resolve) {
            preview.onplaying = resolve;
        });
    });

    function xxxx() {
        return startRecording(preview.captureStream(), 5000); //
    }
    
    promise2.then(xxxx) // 调用 function(resolve) { ... } 这个函数
    .then(function (data) {
        console.log("使用录制下来的数据");
        console.log(data);

        let recordedBlob = new Blob(data, { type: "video/webm" });
        recording.src = URL.createObjectURL(recordedBlob);
    })
    .catch(e => {
        console.log(e);
    });
}

function stopRecording() {
    console.log("点击了结束录制");
}

// startButton.addEventListener("click", startRecording);   <-- 和下面的写法，目前可以认为是一样的效果
startButton.onclick = startCapturing;        
// 进行事件绑定，发生了 startButton 的点击(click)事件后，
// 请执行 startRecording
// 这种形态就是俗称的回调函数（callback）
// 当 startButton 上有了 click 事件时，startButton.onclick();


stopButton.onclick = stopRecording;
// 在 stopButton 发生了(on) 点击(click)事件后，执行 stopRecording 函数
