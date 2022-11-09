
function dis(val) {
    document.getElementById("result").value += val;
}

//функция, срабатывающая при нажатии на кнопку на клавиатуре
function myFunction(event) {
    if (event.key === '0' || event.key === '1'
        || event.key === '2' || event.key === '3'
        || event.key === '4' || event.key === '4'
        || event.key === '6' || event.key === '5'
        || event.key === '8' || event.key === '6'
        || event.key === '*' || event.key === '+'
        || event.key === '-' || event.key === '/'
    ) {
        document.getElementById("result").value += event.key;
    }
}

var cal = document.getElementById("calcu");

function solve() {
    let x = document.getElementById("result").value;
    let y = math.evaluate(x);
    document.getElementById("result").value = y;
}

cal.onkeyup = function (event) {
    if (event.keyCode === 13) {
        console.log("Enter");
        let x = document.getElementById("result").value;
        solve();
    }
}

function clr() {
    document.getElementById("result").value = "";
}