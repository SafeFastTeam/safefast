// 날짜 검색 기능
const startDateInputs = document.querySelectorAll('#start-date');
const endDateInputs = document.querySelectorAll('#end-date');

startDateInputs.forEach(input => {
  input.addEventListener('click', () => showCalendar('start-date', input));
});

endDateInputs.forEach(input => {
  input.addEventListener('click', () => showCalendar('end-date', input));
});

// 달력 UI 구현
function showCalendar(inputId, input) {
  const calendar = document.createElement('div');
  calendar.classList.add('calendar');

  const header = document.createElement('div');
  header.classList.add('header');

  const prevMonth = document.createElement('button');
  prevMonth.textContent = '<';
  prevMonth.addEventListener('click', () => changeMonth(-1));

  const monthYear = document.createElement('span');
  monthYear.textContent = getCurrentMonthYear();

  const nextMonth = document.createElement('button');
  nextMonth.textContent = '>';
  nextMonth.addEventListener('click', () => changeMonth(1));

  header.appendChild(prevMonth);
  header.appendChild(monthYear);
  header.appendChild(nextMonth);

  const daysContainer = document.createElement('div');
  daysContainer.classList.add('days');

  calendar.appendChild(header);
  calendar.appendChild(daysContainer);

  document.body.appendChild(calendar);

  // 현재 월 렌더링
  renderDays();

  // 달력 UI 클릭 이벤트 처리
  calendar.addEventListener('click', (e) => {
    if (e.target.classList.contains('day')) {
      const selectedDate = `${getCurrentMonthYear().split(' ')[1]}-${(getCurrentMonthYear().split(' ')[0] + 1).toString().padStart(2, '0')}-${e.target.textContent.padStart(2, '0')}`;
      input.value = selectedDate;
      document.body.removeChild(calendar);
    }
  });

  // 월 변경 함수
  function changeMonth(offset) {
    const currentDate = new Date(getCurrentMonthYear().split(' ')[1], getCurrentMonthYear().split(' ')[0]);
    currentDate.setMonth(currentDate.getMonth() + offset);
    monthYear.textContent = getCurrentMonthYear(currentDate);
    renderDays(currentDate);
  }

  // 현재 월 렌더링 함수
  function renderDays(currentDate = new Date()) {
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();
    const firstDay = new Date(year, month, 1).getDay();
    const lastDate = new Date(year, month + 1, 0).getDate();

    daysContainer.innerHTML = '';

    for (let i = 0; i < firstDay; i++) {
      const day = document.createElement('div');
      day.classList.add('day', 'disabled');
      daysContainer.appendChild(day);
    }

    for (let i = 1; i <= lastDate; i++) {
      const day = document.createElement('div');
      day.classList.add('day');
      day.textContent = i;
      daysContainer.appendChild(day);
    }
  }

  // 현재 월년 가져오기 함수
  function getCurrentMonthYear(currentDate = new Date()) {
    const month = currentDate.getMonth();
    const year = currentDate.getFullYear();
    return `${month} ${year}`;
  }
}

// 키워드 검색 기능
const keywordSearchTypes = document.querySelectorAll('#keyword-search-type');
const keywordSearchInputs = document.querySelectorAll('#keyword-search');
const keywordSearchBtns = document.querySelectorAll('#keyword-search-btn');

keywordSearchBtns.forEach(btn => {
  btn.addEventListener('click', () => {
    const searchType = keywordSearchTypes[btn.closest('section').classList.contains('procurement-plan') ? 0 : 1].value;
    const searchKeyword = keywordSearchInputs[btn.closest('section').classList.contains('procurement-plan') ? 0 : 1].value;
    // 선택한 검색 유형과 키워드로 데이터 필터링 및 테이블 업데이트
  });
});

// // 발주 버튼 클릭 이벤트
// const issueOrderBtn = document.getElementById('issue-order-btn');

// issueOrderBtn.addEventListener('click', () => {
//   const selectedRows = Array.from(document.querySelectorAll('#procurement-plan-list tr'))
//     .filter(row => row.querySelector('input[type="checkbox"]').checked)
//     .map(row => {
//       // 선택한 행의 데이터 추출 및 반환
//       const cells = row.querySelectorAll('td');
//       return {
//         procurementPlanNo: cells[0].textContent,
//         company: cells[1].textContent,
//         item: cells[2].textContent,
//         dueDate: cells[3].textContent
//       };
//     });

//   // 선택한 행의 데이터를 구매 발주서 리스트에 추가
//   const purchaseOrderList = document.getElementById('purchase-order-list');
//   selectedRows.forEach(row => {
//     const newRow = document.createElement('tr');
//     newRow.innerHTML = `
//       <td><input type="checkbox"></td>
//       <td>${row.procurementPlanNo.replace('CP', 'PO')}</td>
//       <td>${row.company}</td>
//       <td>${row.item}</td>
//       <td>${row.dueDate}</td>
//     `;
//     purchaseOrderList.appendChild(newRow);
//   });
// });

// 삭제 버튼 클릭 이벤트
const deleteOrderBtn = document.getElementById('delete-order-btn');

deleteOrderBtn.addEventListener('click', () => {
  const selectedRows = Array.from(document.querySelectorAll('#purchase-order-list tr'))
    .filter(row => row.querySelector('input[type="checkbox"]').checked);

  selectedRows.forEach(row => {
    // 선택한 행 삭제
    row.remove();
  });
});


// 발주 버튼 클릭 이벤트
const issueOrderBtn = document.getElementById('issue-order-btn');
const orderModal = document.getElementById('orderModal');
const closeModal = document.getElementsByClassName('close')[0];

issueOrderBtn.addEventListener('click', () => {
  const selectedRows = Array.from(document.querySelectorAll('#procurement-plan-list tr'))
    .filter(row => row.querySelector('input[type="checkbox"]').checked)
    .map(row => {
      // 선택한 행의 데이터 추출 및 반환
      const cells = row.querySelectorAll('td');
      return {
        procurementPlanNo: cells[0].textContent,
        company: cells[1].textContent,
        item: cells[2].textContent,
        dueDate: cells[3].textContent
      };
    });

  // 선택된 항목이 없거나 2개 이상일 때는 발주 버튼 동작하지 않도록 처리
  if (selectedRows.length === 0 || selectedRows.length > 1) {
    return;
  }
  // 모달 창에 데이터 추가


  orderModal.style.display = 'block';
});

// 모달 창 닫기
closeModal.addEventListener('click', () => {
  orderModal.style.display = 'none';
});

window.addEventListener('click', (event) => {
  if (event.target === orderModal) {
    orderModal.style.display = 'none';
  }
});

document.addEventListener('DOMContentLoaded', function() {
  var printButton = document.getElementById('printOrder');

  printButton.addEventListener('click', function() {
    window.print(); // 페이지 프린트
  });
});

document.getElementById('sendOrder').addEventListener('click', function() {
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

// 발주 버튼 클릭 시 모달에 이미지 추가
document.getElementById("issue-order-btn").addEventListener("click", function() {
  // 선택된 항목을 가져오기
  var selectedItems = document.querySelectorAll("#procurement-plan-list input[type=checkbox]:checked");

  // 이미지를 추가할 공간 찾기
  var imageContainer = document.getElementById("image-container");

  // 이미지 컨테이너 초기화
  imageContainer.innerHTML = '';

  // 선택된 항목에 대해 반복하여 이미지 추가
  selectedItems.forEach(function(item) {
    // 선택된 항목에 대한 정보 가져오기
    var itemName = item.parentElement.nextElementSibling.nextElementSibling.nextElementSibling.innerText.trim();

    // 해당 항목에 따른 이미지 경로 설정
    var imgSrc;
    if (itemName === "bolt") {
      imgSrc = "/image/bal01.png";
    } else if (itemName === "cable") {
      imgSrc = "/image/bal02.png";
    }  else if (itemName === "nut") {
      imgSrc = "/image/bal03.png";
    }// 이하 계속해서 필요한 항목과 이미지에 대한 조건 추가

    // 이미지 요소 생성
    var img = document.createElement("img");
    img.src = imgSrc;

    // 이미지를 추가할 공간에 이미지 요소 추가
    imageContainer.appendChild(img);
  });
});

document.getElementById('saveOrder').addEventListener('click', () => {
  // 모달 내용 가져오기
  const modalContent = document.querySelector('.modal-content');

  // 새로운 PDF 문서 생성
  const pdf = new jsPDF();

  // HTML을 Canvas로 렌더링
  html2canvas(modalContent).then(canvas => {
    // Canvas를 PDF로 추가하고 이미지 크기 조정
    const imgWidth = pdf.internal.pageSize.getWidth(); // PDF 페이지의 너비
    const imgHeight = pdf.internal.pageSize.getHeight(); // PDF 페이지의 높이
    pdf.addImage(canvas.toDataURL('image/png'), 'PNG', 0, 0, imgWidth, imgHeight);

    // PDF 저장
    pdf.save('order.pdf');
  });
});