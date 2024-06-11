document.addEventListener('DOMContentLoaded', function() {
    var saveButton = document.querySelector('.save-button');
    saveButton.addEventListener('click', function(event) {
        event.preventDefault(); // 기본 동작(폼 전송) 방지

        var form = document.querySelector('form');
        var formData = new FormData(form); // 폼 데이터 가져오기

        // 조달 납기일과 수량 가져오기
        var procDueDate = formData.get('procDuedate');
        var procQuantity = formData.get('procQuantity');

        // 조달 납기일이 비어 있는지 확인
        if (!procDueDate.trim()) {
            alert('조달납기일을 입력해주세요.');
            return; // 함수 종료
        }

        // 수량이 비어 있는지 확인
        if (!procQuantity.trim()) {
            alert('조달수량을 입력해주세요.');
            return; // 함수 종료
        }

        var xhr = new XMLHttpRequest(); // AJAX 객체 생성

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
