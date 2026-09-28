import { useState } from 'react'

function Signup() {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    // 입력창에 사용자가 타이핑하는 값을 저장할 상태 두 개

    const [message, setMessage] = useState('')
    // 회원가입 성공/실패 메시지를 보여줄 상태

    const handleSignup = async () => {
        try {
            const res = await fetch(`${import.meta.env.VITE_API_BASE_URL}/api/auth/signup`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password }),
            })

            if (!res.ok) {
                const errorData = await res.json()
                // 백엔드가 이제 { status, message } 형태로 정확한 에러를 주니까 그걸 읽어옴
                throw new Error(errorData.message)
            }

            setMessage('회원가입 성공! 이제 로그인해주세요.')
        } catch (err) {
            setMessage(err.message || '회원가입 실패')
            // 이제 "이미 존재하는 아이디일 수 있습니다" 같은 추측이 아니라,
            // 백엔드가 알려준 정확한 이유를 그대로 보여줌
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