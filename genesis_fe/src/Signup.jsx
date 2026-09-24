import { useState } from 'react'

function Signup() {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    // 입력창에 사용자가 타이핑하는 값을 저장할 상태 두 개

    const [message, setMessage] = useState('')
    // 회원가입 성공/실패 메시지를 보여줄 상태

    const handleSignup = async () => {
        // 회원가입 버튼을 눌렀을 때 실행될 함수 (async: 서버 응답을 "기다리는" 함수라는 표시)
        try {
            const res = await fetch('http://localhost:8080/api/auth/signup', {
                method: 'POST',
                // POST 방식으로 요청 (데이터를 새로 만들 때 쓰는 방식)
                headers: { 'Content-Type': 'application/json' },
                // "내가 보내는 데이터는 JSON 형식이야"라고 서버에 알려줌
                body: JSON.stringify({ username, password }),
                // 입력받은 username, password를 JSON 문자열로 변환해서 요청 본문에 담음
            })

            if (!res.ok) {
                // res.ok는 응답 상태코드가 200번대(성공)가 아니면 false
                throw new Error('회원가입 실패')
            }

            setMessage('회원가입 성공! 이제 로그인해주세요.')
        } catch (err) {
            setMessage('회원가입 실패: 이미 존재하는 아이디일 수 있습니다.')
        }
    }

    return (
        <div>
            <h2>회원가입</h2>
            <input
                placeholder="아이디"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                // 입력창에 타이핑할 때마다, 그 값을 username 상태에 저장
            />
            <input
                placeholder="비밀번호"
                type="password"
                // type="password": 입력한 글자가 화면에 ●●●● 형태로 가려져서 보임
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button onClick={handleSignup}>가입하기</button>
            <p>{message}</p>
        </div>
    )
}

export default Signup