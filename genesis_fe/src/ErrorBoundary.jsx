import { Component } from 'react'

class ErrorBoundary extends Component {
    // 에러 바운더리는 아직 함수형 컴포넌트로 만들 수 없어서, 클래스 컴포넌트로 작성

    state = { hasError: false }
    // 하위 컴포넌트에서 에러가 났는지 기억하는 상태

    static getDerivedStateFromError() {
        return { hasError: true }
        // 하위에서 렌더링 에러가 나면 React가 자동으로 호출 → 상태를 "에러 발생"으로 바꿈
    }

    componentDidCatch(error, info) {
        console.error('화면 렌더링 중 에러 발생:', error, info)
        // 에러 내용을 콘솔에 기록 (Day 6에서 Sentry로 보내는 자리)
    }

    render() {
        if (this.state.hasError) {
            return (
                <div>
                    <h2>문제가 발생했습니다</h2>
                    <p>잠시 후 다시 시도해주세요.</p>
                    <button onClick={() => window.location.reload()}>새로고침</button>
                </div>
            )
            // 에러가 났으면 흰 화면 대신 안내 문구와 새로고침 버튼을 보여줌
        }

        return this.props.children
        // 에러가 없으면 감싸고 있는 자식 컴포넌트를 그대로 보여줌
    }
}

export default ErrorBoundary