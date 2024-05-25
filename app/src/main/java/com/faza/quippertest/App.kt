package com.faza.quippertest

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner

class App : Application(), Application.ActivityLifecycleCallbacks {

    private var activityCycle = ActivityCycle.CREATED

    private enum class ActivityCycle {
        CREATED, STARTED, RESUMED, PAUSED, STOPPED, SAVE_STATE, DESTROYED
    }

    companion object {
        private lateinit var application: App
        fun getContext(): Context {
            return application.applicationContext
        }

        fun isAppOnForeground(): Boolean {
            return ProcessLifecycleOwner.get().lifecycle.currentState
                .isAtLeast(Lifecycle.State.STARTED)
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        activityCycle = ActivityCycle.CREATED
    }

    override fun onActivityStarted(p0: Activity) {
        activityCycle = ActivityCycle.STARTED
    }

    override fun onActivityResumed(p0: Activity) {
        activityCycle = ActivityCycle.RESUMED
    }

    override fun onActivityPaused(p0: Activity) {
        activityCycle = ActivityCycle.PAUSED
    }

    override fun onActivityStopped(p0: Activity) {
        activityCycle = ActivityCycle.STOPPED
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        activityCycle = ActivityCycle.SAVE_STATE
    }

    override fun onActivityDestroyed(p0: Activity) {
        activityCycle = ActivityCycle.DESTROYED
    }
}