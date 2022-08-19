const period = document.querySelector('#period');
const dateInputDiv = document.querySelector('.periodSelect');

function OnChange() {
  let selectOption = period.options[period.selectedIndex].value;
  console.log(selectOption);
  if (selectOption == 'Enter Date') {
    dateInputDiv.classList.remove('d-none');
  } else {
    dateInputDiv.classList.add('d-none');
  }
}
