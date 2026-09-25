import { useState } from 'react'

function Login() {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [message, setMessage] = useState('')

    const handleLogin = async () => {
        try {
            const res = await fetch(`${import.meta.env.VITE_API_BASE_URL}/api/auth/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password }),
            })

            if (!res.ok) {
                throw new Error('로그인 실패')
            }

            const data = await res.json()
            // 응답으로 온 JSON을 자바스크립트 객체로 변환 ({ token: "eyJ..." } 형태)

            localStorage.setItem('token', data.token)
            // 브라우저의 localStorage에 "token"이라는 이름으로 받은 JWT를 저장
            // 이후 로그인이 필요한 API를 호출할 때 여기서 꺼내서 사용하게 됨

            setMessage('로그인 성공! 토큰이 저장되었습니다.')
        } catch (err) {
            setMessage('로그인 실패: 아이디 또는 비밀번호를 확인해주세요.')
        }
    }

    return (
        <div>
            <h2>로그인</h2>
            <input
                placeholder="아이디"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <input
                placeholder="비밀번호"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button onClick={handleLogin}>로그인</button>
            <p>{message}</p>
        </div>
    )
}

export default Login
