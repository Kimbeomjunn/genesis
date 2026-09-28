import { Component } from 'react'
import * as Sentry from '@sentry/react'

class ErrorBoundary extends Component {
    state = { hasError: false }

    static getDerivedStateFromError() {
        return { hasError: true }
    }

    componentDidCatch(error, info) {
        console.error('화면 렌더링 중 에러 발생:', error, info)
        Sentry.captureException(error, { extra: { componentStack: info.componentStack } })
        // 잡은 에러를 Sentry로 전송. componentStack은 "어느 컴포넌트 안에서 났는지" 추가 정보
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
        // 에러가 없으면 감싸고 있는 자식(App)을 그대로 보여줌. 이 줄이 없으면 화면이 통째로 비어요
    }
}

export default ErrorBoundary