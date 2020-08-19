"use strict";

let preview = document.getElementById("preview");
let recording = document.getElementById("recording");

let startButton = document.getElementById("startButton");
let recordButton = document.getElementById("recordButton");
let stopButton = document.getElementById("stopButton");
let downloadButton = document.getElementById("downloadButton");

let logElement = document.getElementById("log");
function log(message) {
    logElement.innerHTML += (message + "\n");
}

log("网页加载完毕");
startButton.addEventListener("click", function () {
    log("用户点击了开始按钮，弹出权限申请");
    let p1 = navigator.mediaDevices.getUserMedia({
        video: true,
        audio: true
    });

    p1.then(function (stream) {
        log("用户同意授权");
        preview.srcObject = stream;
    })
    .catch(log);
});

recordButton.addEventListener("click", function() {
    log("预览开始播放，开始录制");
    let stream = preview.srcObject;

    let recorder = new MediaRecorder(stream);
    let data = [];

    recorder.ondataavailable = function (event) {
        log("数据可用，记录录制数据");
        data.push(event.data);
    }
    
    recorder.onstop = function () {
        log("录制停止，创建录制视频界面");
        let recordedBlob = new Blob(data, {
            type: "video/webm"
        });
        recording.src = URL.createObjectURL(recordedBlob);
        downloadButton.href = recording.src;
        downloadButton.download = "RecordedVideo.webm";
    };

    recorder.onerror = function (event) {
        log(event.name);
    };

    // 每 5 秒数据有效一次
    recorder.start(5000);

    /*
    setTimeout(function () {
        log("5 秒已到，停止录制，会触发数据可用事件");
        if (recorder.state == "recording") {
            recorder.stop();
        }
    }, 5000);
    */
});

stopButton.addEventListener("click", function() {
    log("点击停止按钮，停止录制");
    let stream = preview.srcObject;

    let tracks = stream.getTracks();
    for (let i = 0; i < tracks.length; i++) {
        let track = tracks[i];
        track.stop();
    }
});