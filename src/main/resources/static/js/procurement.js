document.addEventListener('DOMContentLoaded', function() {
    var saveButton = document.querySelector('.save-button');
    saveButton.addEventListener('click', function(event) {
        event.preventDefault(); // 기본 동작(폼 전송) 방지

        var form = document.querySelector('form');
        var formData = new FormData(form); // 폼 데이터 가져오기
        var xhr = new XMLHttpRequest(); // AJAX 객체 생성

        console.log(formData.get('procDueDate')); // 이 값을 확인합니다.

        xhr.open('POST', '/procurement/submit_procurement_plan'); // 요청 준비
        xhr.onload = function() {
            if (xhr.status === 200) {
                console.log('데이터 전송 완료');
                alert('조달계획이 성공적으로 등록되었습니다.');
            } else {
                console.error('데이터 전송 실패');
                alert('조달계획 등록에 실패했습니다. 다시 시도해주세요.');
            }
        };

        // FormData 객체를 URL 인코딩된 문자열로 변환
        var urlEncodedData = new URLSearchParams(formData).toString();
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); // 전송할 데이터 형식 설정
        xhr.send(urlEncodedData); // 데이터 전송
    });
});