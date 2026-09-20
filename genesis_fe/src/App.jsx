import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [status, setStatus] = useState('연결 확인 중...')

  useEffect(() => {
    fetch('http://localhost:8080/api/health')
      .then((res) => res.json())
      .then((data) => setStatus(data.status))
      .catch(() => setStatus('연결 실패'))
  }, [])

  return (
    <div>
      <h1>백엔드 상태: {status}</h1>
    </div>
  )
}

export default App