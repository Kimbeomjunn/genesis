import { useEffect, useState } from 'react'
import Signup from './Signup'
import Login from './Login'
// 방금 만든 두 컴포넌트를 가져옴
import './App.css'

function App() {
  const [status, setStatus] = useState('연결 확인 중...')
  // Day 1에서 만든 백엔드 상태 확인용 (그대로 유지)

  useEffect(() => {
    fetch('http://localhost:8080/api/health')
        .then((res) => res.json())
        .then((data) => setStatus(data.status))
        .catch(() => setStatus('연결 실패'))
  }, [])

  return (
      <div>
        <h1>백엔드 상태: {status}</h1>
        <hr />
        <Signup />
        {/* Signup 컴포넌트를 이 위치에 그려 넣음 */}
        <hr />
        <Login />
        {/* Login 컴포넌트를 이 위치에 그려 넣음 */}
      </div>
  )
}

export default App