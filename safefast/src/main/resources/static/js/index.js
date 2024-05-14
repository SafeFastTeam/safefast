// 드롭다운 메뉴 기능 구현
const dropdownMenus = document.querySelectorAll('.sidebar li:has(ul)');
dropdownMenus.forEach(menu => {
    const dropdownMenu = menu.querySelector('ul');
    const dropdownToggle = menu.querySelector('a');

    dropdownToggle.addEventListener('click', (event) => {
        event.preventDefault(); // 링크 이동 방지
        dropdownMenu.classList.toggle('show');
    });
});


// 모달 창 열기/닫기 기능 추가
var modal = document.getElementById("infoModal");
var infoIcon = document.querySelector(".info-icon");
var closeBtn = document.querySelector(".close");

infoIcon.onclick = function() {
  modal.style.display = "block";
}

closeBtn.onclick = function() {
  modal.style.display = "none";
}

window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}