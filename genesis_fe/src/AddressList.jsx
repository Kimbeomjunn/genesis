import { useState, useEffect } from 'react'
import { useJsApiLoader, GoogleMap, MarkerF } from '@react-google-maps/api'

const libraries = ['places']

const mapContainerStyle = {
    width: '100%',
    height: '400px',
}
// 지도가 화면에 차지할 크기 (픽셀 단위로 명시해야 지도가 정상적으로 보임)

const defaultCenter = { lat: 37.5665, lng: 126.9780 }
// 지도의 초기 중심 좌표 (서울시청 기준)

function AddressList() {
    const { isLoaded } = useJsApiLoader({
        googleMapsApiKey: import.meta.env.VITE_GOOGLE_MAPS_API_KEY,
        libraries,
    })

    const [addresses, setAddresses] = useState([])
    // 백엔드에서 받아온 주소 목록을 저장할 상태

    const fetchAddresses = async () => {
        const token = localStorage.getItem('token')
        if (!token) return
        // 로그인 안 되어 있으면 아예 요청 안 보냄

        try {
            const res = await fetch(`${import.meta.env.VITE_API_BASE_URL}/api/addresses`, {
                headers: { Authorization: `Bearer ${token}` },
            })
            if (!res.ok) throw new Error('불러오기 실패')

            const data = await res.json()
            setAddresses(data)
            // 응답받은 배열을 상태에 저장 → 화면이 자동으로 다시 그려짐
        } catch (err) {
            console.error(err)
        }
    }

    useEffect(() => {
        fetchAddresses()
        // 컴포넌트가 처음 화면에 나타날 때, 딱 한 번 목록을 불러옴
    }, [])

    if (!isLoaded) return <p>지도 로딩 중...</p>

    return (
        <div>
            <h2>등록된 주소 목록</h2>
            <button onClick={fetchAddresses}>새로고침</button>
            {/* 새로 주소를 등록한 뒤 다시 눌러서 최신 목록을 가져올 수 있게 함 */}

            <GoogleMap mapContainerStyle={mapContainerStyle} center={defaultCenter} zoom={11}>
                {addresses.map((addr) => (
                    <MarkerF
                        key={addr.id}
                        // key: React가 리스트의 각 항목을 구분하기 위해 필요로 하는 고유값
                        position={{ lat: addr.latitude, lng: addr.longitude }}
                        title={addr.fullAddress}
                        // 마커에 마우스를 올렸을 때 보이는 툴팁 텍스트
                    />
                ))}
            </GoogleMap>

            <ul>
                {addresses.map((addr) => (
                    <li key={addr.id}>{addr.fullAddress}</li>
                ))}
            </ul>
        </div>
    )
}

export default AddressList