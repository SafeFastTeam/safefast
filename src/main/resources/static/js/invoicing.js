document.addEventListener('DOMContentLoaded', function() {
  // 모달 띄우기
  var modal = document.getElementById("myModal");
  var span = document.getElementsByClassName("close")[0];

  function openModal() {
    modal.style.display = "block";
  }

  span.onclick = function() {
    modal.style.display = "none";
  }

  window.onclick = function(event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  }
  
  // 입고완료 버튼 클릭 시 모달 열기
  var receiveButtons = document.querySelectorAll("#purchase-order-list button");
  receiveButtons.forEach(function(button) {
    button.addEventListener('click', openModal);
  });
});

document.addEventListener('DOMContentLoaded', function() {
  var printButton = document.getElementById('printButton');

  printButton.addEventListener('click', function() {
    window.print(); // 페이지 프린트
  });
});

document.getElementById('email').addEventListener('click', function() {
  // 이메일 클라이언트가 없는 경우에는 다른 방법으로 이메일을 전송할 수 있도록 안내
  var emailAddress = 'ekzmemforhs3@naver.com';
  // 사용자에게 이메일 주소를 복사하도록 안내
  var dummyInput = document.createElement('input');
  document.body.appendChild(dummyInput);
  dummyInput.setAttribute('value', emailAddress);
  dummyInput.select();
  document.execCommand('copy');
  document.body.removeChild(dummyInput);
  alert('이메일 주소가 복사되었습니다: ' + emailAddress);
});