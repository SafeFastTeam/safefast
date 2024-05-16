// 저장 버튼 클릭 시 폼 데이터 전송
var saveButton = document.querySelector('.save-button');
saveButton.addEventListener('click', function(event) {
    event.preventDefault(); // 기본 동작(폼 전송) 방지

    var formData = new FormData(document.querySelector('form')); // 폼 데이터 가져오기
    var xhr = new XMLHttpRequest(); // AJAX 객체 생성
    xhr.open('POST', '서버_URL'); // 요청 준비
    xhr.onload = function() {
        if (xhr.status === 200) {
            console.log('데이터 전송 완료');
        } else {
            console.error('데이터 전송 실패');
        }
    };
    xhr.send(formData); // 데이터 전송
});