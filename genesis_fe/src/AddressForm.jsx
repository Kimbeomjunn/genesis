import { useState, useCallback } from 'react'
import { useJsApiLoader, Autocomplete } from '@react-google-maps/api'

const libraries = ['places']
// Google Maps의 여러 기능 중, 우리가 쓸 "장소 검색(places)" 기능만 불러오겠다고 지정

function AddressForm() {
    const { isLoaded } = useJsApiLoader({
        googleMapsApiKey: import.meta.env.VITE_GOOGLE_MAPS_API_KEY,
        // .env에 저장해둔 API 키를 Vite 방식으로 불러옴
        libraries,
    })
    // isLoaded: Google Maps 스크립트가 다 로드됐는지 여부 (로드 전엔 Autocomplete 사용 불가)

    const [autocomplete, setAutocomplete] = useState(null)
    // Google이 제공하는 Autocomplete 객체 자체를 저장해둘 상태

    const [message, setMessage] = useState('')

    const onLoad = useCallback((ac) => {
        setAutocomplete(ac)
        // Autocomplete 컴포넌트가 준비되면, 그 객체를 저장
    }, [])

    const onPlaceChanged = async () => {
        // 사용자가 자동완성 목록에서 주소를 "선택"했을 때 실행됨
        if (!autocomplete) return

        const place = autocomplete.getPlace()
        // 선택된 장소의 상세 정보를 가져옴 (주소, 좌표 등이 다 들어있음)

        if (!place.geometry) {
            setMessage('주소를 목록에서 선택해주세요.')
            return
        }

        const fullAddress = place.formatted_address
        // 사람이 읽기 좋은 형태의 전체 주소 문자열
        const latitude = place.geometry.location.lat()
        // 위도 (Google이 이미 계산해서 넣어준 값 — 우리가 따로 Geocoding API를 호출할 필요 없음!)
        const longitude = place.geometry.location.lng()
        // 경도

        // 백엔드에 저장 요청
        try {
            const token = localStorage.getItem('token')
            // Day 2에서 로그인 시 저장해둔 토큰을 꺼내옴

            const res = await fetch(`${import.meta.env.VITE_API_BASE_URL}/api/addresses`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                    // JwtAuthFilter가 검사하는 바로 그 헤더!
                },
                body: JSON.stringify({ fullAddress, latitude, longitude }),
            })

            if (!res.ok) throw new Error('저장 실패')

            setMessage(`주소 등록 완료: ${fullAddress}`)
        } catch (err) {
            setMessage('주소 등록 실패 (로그인이 필요할 수 있습니다)')
        }
    }

    if (!isLoaded) return <p>지도 로딩 중...</p>
    // 구글맵 스크립트가 아직 로드되기 전이면, 검색창 대신 로딩 메시지 표시

    return (
        <div>
            <h2>주소 등록</h2>
            <Autocomplete onLoad={onLoad} onPlaceChanged={onPlaceChanged}>
                <input
                    type="text"
                    placeholder="주소를 검색하세요"
                    style={{ width: '300px', padding: '8px' }}
                />
            </Autocomplete>
            <p>{message}</p>
        </div>
    )
}

export default AddressForm