var nicknameCheck="";
var emailCheck="";

const checkDuplicateButton = document.getElementById('check-nickname');

if (checkDuplicateButton) {
    checkDuplicateButton.addEventListener('click', function () {

        const nickname = document.getElementById('nickname').value;
        nicknameCheck=nickname;

        if (nickname.length < 1) {
            alert('닉네임은 1자 이상 입력해야합니다.');
            return false; // 함수 종료
        }
        
        fetch(`/members/checkDuplicate`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                nickname: nickname
            })
        })
            .then(response => {
                if (response.ok) {
                    return response.json(); // JSON으로 파싱된 데이터 반환
                } else {
                    return response.text();
                }
            })
            .then(data => {
                    console.log(data);

                    if (typeof data === 'boolean') {
                        if (data === true) {
                            //console.log(data + "콘솔 디버그 True");
                            alert("사용가능한 닉네임입니다");
                            //document.getElementById('nickname').disabled = true;

                        } else {
                            //console.log(data + "콘솔 디버그 false");
                            alert("사용불가한 닉네임입니다.");
                        }

                    } else {
                        alert(data); // 에러 메시지 출력

                    }
                }
            )
    });
}


const checkDuplicateButton2 = document.getElementById('check-email');
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
if (checkDuplicateButton2) {
    checkDuplicateButton2.addEventListener('click', function () {
        const email = document.getElementById('email').value;
        emailCheck=email;

        if (!emailRegex.test(email)) {
            alert('유효하지 않은 이메일 주소입니다.');
            return; // 함수 종료
        }
        fetch(`/members/checkDuplicate`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                email: email
            })
        })
            .then(response => {
                if (response.ok) {
                    return response.json(); // JSON으로 파싱된 데이터 반환
                } else {
                    return response.text();
                }
            })
            .then(data => {
                    console.log(data);

                    if (typeof data === 'boolean') {
                        if (data === true) {
                            console.log(data + "콘솔 디버그 True");
                            alert("사용가능한 이메일입니다");
                            //document.getElementById('email').disabled = true;
                        } else {
                            console.log(data + "콘솔 디버그 false");
                            alert("사용불가한 이메일입니다.");
                        }
                    } else {
                        alert(data); // 에러 메시지 출력

                    }
                }
            )
    })
}


const form = document.getElementById('form');
//memberForm의 form의 id값을 formd으로 지정하여, form에 대입

function validation(){
    const nicknameValue = document.getElementById('nickname').value;
    const emailValue = document.getElementById('email').value;
    const passwordValue =document.getElementById('password').value;
    const passwordConfirmValue =document.getElementById('confirm_password').value;
    const agreementChecked = document.getElementById('agreement').checked;

    console.log(agreementChecked);


        //입력받은 값과 중복확인 시 사용한 값이 같은지 확인하여 회원가입 성공, 실패
     if (nicknameCheck !== nicknameValue || emailCheck !== emailValue) {
        alert('닉네임 또는 이메일 중복 검사를 통과하지 않았습니다.');
    } else if (passwordValue.length < 8 || passwordValue.length > 12) {
        alert('비밀번호는 8자 이상 12자 이하로 입력해야 합니다.');
    }else if(passwordValue !==passwordConfirmValue){
        alert("비밀번호 확인이 올바르지 않습니다.")
    } else if(!agreementChecked){
        alert('개인정보 수집 및 이용에 동의해야 합니다.');
    } else{
        alert('회원가입이 성공적으로 완료되었습니다.');
        form.submit();
    }
}