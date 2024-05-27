// scripts.js
// This file will contain JavaScript code for interactivity and functionality

// Example: Add event listener to the save button
const saveButton = document.querySelector('.save-button');
saveButton.addEventListener('click', () => {
    // Implement save functionality here
    console.log('Save button clicked');
});

// Example: Add event listener to the modify buttons
const modifyButtons = document.querySelectorAll('.modify-button');
modifyButtons.forEach(button => {
    button.addEventListener('click', () => {
        // Implement modify functionality here
        console.log('Modify button clicked');
    });
});

// Example: Add event listener to the contract buttons
const contractButtons = document.querySelectorAll('.contract-button');
contractButtons.forEach(button => {
    button.addEventListener('click', () => {
        // Implement contract functionality here
        console.log('Contract button clicked');
    });
});

// Example: Add event listener to the search button
const searchButton = document.querySelector('.search-button');
searchButton.addEventListener('click', () => {
    // Implement search functionality here
    const searchInput = document.querySelector('.search-input');
    const searchTerm = searchInput.value;
    const searchOptionElement = document.getElementById('search-option');
    const searchOption = searchOptionElement.options[searchOptionElement.selectedIndex].value;



    // 검색어와 검색 옵션을 JSON 형식으로 만듭니다.
    const searchData = { term: searchTerm, option: searchOption };

    // 서버로 검색 요청을 보냅니다.
    fetch('/search', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(searchData)
    })
        .then(response => response.json())
        .then(data => {
            // 서버에서 받은 검색 결과를 처리합니다.
            handleSearchResults(data);
            // 여기에서 검색 결과를 화면에 표시하거나 다른 동작을 수행할 수 있습니다.
        })
        .catch(error => {
            console.error('검색 요청 실패:', error);
        });
});

// 검색 결과를 처리하는 함수
function handleSearchResults(results) {
    // 검색 결과를 이용하여 테이블 업데이트 또는 화면에 표시하는 등의 작업 수행
    console.log('Search results:', results);
    // 예: 검색 결과를 테이블에 추가하거나 업데이트하는 등의 작업 수행
}