// 대분류 버튼 클릭 이벤트
document.getElementById('issue-order-btn-large').addEventListener('click', function() {
  // 모든 버튼의 색상을 기본값으로 설정
  resetButtonColor();
  // 대분류 버튼의 색상을 빨간색으로 설정
  this.style.backgroundColor = 'red';
});

// 중분류 버튼 클릭 이벤트
document.getElementById('issue-order-btn-medium').addEventListener('click', function() {
  // 모든 버튼의 색상을 기본값으로 설정
  resetButtonColor();
  // 중분류 버튼의 색상을 빨간색으로 설정
  this.style.backgroundColor = 'red';
});

// 소분류 버튼 클릭 이벤트
document.getElementById('issue-order-btn-small').addEventListener('click', function() {
  // 모든 버튼의 색상을 기본값으로 설정
  resetButtonColor();
  // 소분류 버튼의 색상을 빨간색으로 설정
  this.style.backgroundColor = 'red';
});

// 모든 버튼의 색상을 기본값으로 설정하는 함수
function resetButtonColor() {
  var buttons = document.querySelectorAll('.save-btn button');
  buttons.forEach(function(button) {
    button.style.backgroundColor = '';
  });
}

