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
        // 기존 코드 그대로
    }
}

export default ErrorBoundary