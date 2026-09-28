import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import * as Sentry from '@sentry/react'
import './index.css'
import App from './App.jsx'
import ErrorBoundary from './ErrorBoundary.jsx'

Sentry.init({
    dsn: import.meta.env.VITE_SENTRY_DSN,
    // 에러를 어느 Sentry 프로젝트로 보낼지 알려주는 주소
    environment: import.meta.env.MODE,
    // "development" 또는 "production" → 대시보드에서 로컬 에러와 배포 에러를 구분
    enabled: import.meta.env.PROD,
    // 배포 빌드에서만 전송. 로컬 개발 중 에러는 보내지 않음 (무료 한도 절약)
})

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <ErrorBoundary>
            <App />
        </ErrorBoundary>
    </StrictMode>,
)