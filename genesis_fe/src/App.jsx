import { useEffect, useState } from 'react'
import Signup from './Signup'
import Login from './Login'
import AddressForm from './AddressForm'
import AddressList from './AddressList'
// Day 3에서 만든 두 컴포넌트 추가
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
            <hr />
            <Signup />
            <hr />
            <Login />
            <hr />
            <AddressForm />
            {/* 주소 검색/등록 폼 */}
            <hr />
            <AddressList />
            {/* 등록된 주소 목록 + 지도 */}
        </div>
    )
}

export default App