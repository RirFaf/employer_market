package android.employer_market.app

import android.app.Application
import android.employer_market.data.repository.RepositoryContainer
import android.employer_market.data.repository.DefaultRepositoryContainer
import android.employer_market.network.SessionManager

class DefaultApplication : Application() {
    lateinit var container: RepositoryContainer
    lateinit var sessionManager: SessionManager
    override fun onCreate() {
        super.onCreate()
        container = DefaultRepositoryContainer()
        sessionManager = SessionManager(this)
    }
}