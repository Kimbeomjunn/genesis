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
                const errorData = await res.json()
                throw new Error(errorData.message)
            }

            const data = await res.json()
            localStorage.setItem('token', data.token)
            setMessage('로그인 성공! 토큰이 저장되었습니다.')
        } catch (err) {
            setMessage(err.message || '로그인 실패')
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
