function calculateInventoryAmount() {
  const tableRows = document.querySelectorAll('#inventory-calculation-list tr');

  tableRows.forEach(row => {
    const cells = row.querySelectorAll('td');
    const initialInventory = parseInt(cells[4].textContent, 10);
    const incomingQuantity = parseInt(cells[5].textContent, 10);
    const outgoingQuantity = parseInt(cells[6].textContent, 10);
    const unitPrice = parseInt(cells[8].textContent, 10);

    const endingInventory = initialInventory + incomingQuantity - outgoingQuantity;
    const inventoryAmount = endingInventory * unitPrice;

    cells[7].textContent = endingInventory;
    cells[9].textContent = inventoryAmount.toLocaleString();
  });
}