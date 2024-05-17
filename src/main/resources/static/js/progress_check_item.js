// 검수 계획 추가 버튼에 대한 이벤트 리스너 등록 함수
const addBtnEventListener = () => {
    const addBtns = document.querySelectorAll('.modal-table-3 .add-btn');
    addBtns.forEach((btn) => {
        btn.addEventListener('click', () => {
            const newRow = document.createElement('tr');
            const countCell = document.createElement('td');
            const dateCell = document.createElement('td');
            const actionCell = document.createElement('td');
            countCell.textContent = `${inspectionCount + 1}차`;
            // 입력 가능한 날짜 입력란 생성
            const dateInput = document.createElement('input');
            dateInput.type = 'date';
            dateInput.className = 'inspection-date'; // 날짜 입력란에 클래스 추가
            dateCell.appendChild(dateInput);
            actionCell.innerHTML = '<button class="btn add-btn">추가</button><button class="btn delete-btn">삭제</button>';
            newRow.appendChild(countCell);
            newRow.appendChild(dateCell);
            newRow.appendChild(actionCell);
            inspectionList.appendChild(newRow);
            inspectionCount++;
        });
    });
};

// 발주 리스트 테이블의 각 행에서 여섯 번째 열의 체크박스 요소들을 선택합니다.
const checkboxes = document.querySelectorAll('.main-table tbody tr td:nth-child(6) input[type="checkbox"]');

// 모달 창1 열기/닫기 기능
const modal = document.querySelector('.modal');
const openModalBtn = document.querySelector('.plan-btn');
const closeModalBtn = document.querySelector('.close-btn');

openModalBtn.addEventListener('click', () => {
    // 체크된 체크박스의 개수를 세어봅니다.
    const checkedCount = Array.from(checkboxes).filter(checkbox => checkbox.checked).length;

    // 만약 체크된 체크박스가 없으면 경고 팝업을 띄웁니다.
    if (checkedCount === 0) {
        alert('선택된 발주가 없습니다. 발주를 선택해주세요.');
    } else {
        // 체크된 체크박스가 있을 경우 모달을 엽니다.
        modal.style.display = 'block';
        displaySelectedOrders(); // 선택된 발주 데이터 표시
    }
});

closeModalBtn.addEventListener('click', () => {
    modal.style.display = 'none';
});

window.addEventListener('click', (event) => {
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

// 페이징 기능
// const itemsPerPage = 10;
// let currentPage = 1;
// let totalPages;

// const renderPagination = () => {
//     const prevBtn = document.querySelector('.prev-btn');
//     const nextBtn = document.querySelector('.next-btn');
//     const pageInfo = document.querySelector('.page-info');

//     prevBtn.disabled = currentPage === 1;
//     nextBtn.disabled = currentPage === totalPages;
//     pageInfo.textContent = `${currentPage}/${totalPages}`;
// };

// const showItems = (items, page) => {
//     const tableBody = document.querySelector('tbody');
//     tableBody.innerHTML = '';

//     const startIndex = (page - 1) * itemsPerPage;
//     const endIndex = startIndex + itemsPerPage;
//     const itemsToShow = items.slice(startIndex, endIndex);

//     itemsToShow.forEach((item) => {
//         const row = document.createElement('tr');
//         Object.values(item).forEach((value) => {
//             const cell = document.createElement('td');
//             cell.textContent = value;
//             row.appendChild(cell);
//         });
//         tableBody.appendChild(row);
//     });
// };

const loadData = () => {
    // 실제로는 여기에서 서버에서 데이터를 가져와야 합니다.
    const dummyData = [
        // 더미 데이터를 여기에 추가합니다.
    ];

    totalPages = Math.ceil(dummyData.length / itemsPerPage);
    renderPagination();
    showItems(dummyData, currentPage);
};

document.querySelector('.prev-btn').addEventListener('click', () => {
    if (currentPage > 1) {
        currentPage--;
        loadData();
    }
});

document.querySelector('.next-btn').addEventListener('click', () => {
    if (currentPage < totalPages) {
        currentPage++;
        loadData();
    }
});

// 검수 계획 삭제 기능
// const addBtn = document.querySelector('.add-btn'); // 삭제
const deleteBtn = document.querySelector('.delete-btn');
const inspectionList = document.querySelector('.modal-table-3 tbody');

let inspectionCount = 1;

addBtnEventListener(); // 이벤트 리스너 등록 함수 호출

inspectionList.addEventListener('click', (event) => {
    if (event.target.classList.contains('delete-btn')) {
        const row = event.target.parentNode.parentNode;
        inspectionList.removeChild(row);
        inspectionCount--;
    }
});


// 모달 창2 열기/닫기 기능
const modal2 = document.querySelector('.modal2');
const openModalBtn2 = document.querySelector('.process-btn');
const closeModalBtn2 = document.querySelector('.close-btn');

openModalBtn2.addEventListener('click', () => {
    modal2.style.display = 'block';
});

closeModalBtn2.addEventListener('click', () => {
    modal2.style.display = 'none';
});

window.addEventListener('click', (event) => {
    if (event.target === modal2) {
        modal2.style.display = 'none';
    }
});

// x 버튼에 대한 이벤트 리스너 추가
const closeModalBtnX = document.querySelector('.modal2 .close-btn');
closeModalBtnX.addEventListener('click', () => {
    modal2.style.display = 'none';
});

// 초기 데이터 로드
loadData();