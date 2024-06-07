package android.employer_market.app

import android.app.Application
import android.employer_market.data.AppContainer
import android.employer_market.data.DefaultAppContainer
import android.employer_market.network.SessionManager

class DefaultApplication : Application() {
    lateinit var container: AppContainer
    lateinit var sessionManager: SessionManager
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        sessionManager = SessionManager(this)
    }
}