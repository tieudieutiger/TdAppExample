package com.tieudieu.fstack

import android.support.v4.app.FragmentManager

class InitializationParams {

    private var fragmentManager: FragmentManager
    private var contentFrame: Int = -1
    private var screenManager: ScreenManager
    private var isAnimationEnabled: Boolean = false
    private var homeClass: Class<*>

    constructor(builder: Builder) {

        this.fragmentManager = builder.fragmentManager
        this.contentFrame = builder.contentFrame
        this.screenManager = builder.screenManager
        this.isAnimationEnabled = builder.isAnimationEnabled
        this.homeClass = builder.homeClass
    }


    fun getFragmentManager(): FragmentManager {

        return fragmentManager
    }

    fun getContentFrame(): Int {

        return contentFrame
    }

    fun getScreenManager(): ScreenManager {

        return screenManager
    }

    fun isAnimationEnabled(): Boolean {

        return isAnimationEnabled
    }

    fun getHomeClass(): Class<*> {

        return homeClass
    }


    /**
     * Builder class
     */
    class Builder {

        lateinit var fragmentManager: FragmentManager
        var contentFrame: Int = -1
        lateinit var screenManager: ScreenManager
        var isAnimationEnabled: Boolean = false
        lateinit var homeClass: Class<*>

        companion object {

            fun create(): Builder = Builder()
        }

        fun fragmentManager(fragmentManager: FragmentManager): Builder {

            this.fragmentManager = fragmentManager
            return this
        }

        fun contentFrame(contentFrame: Int): Builder {

            this.contentFrame = contentFrame
            return this
        }

        fun screenManager(screenManager: ScreenManager): Builder {

            this.screenManager = screenManager
            return this
        }

        fun enableAnimation(enable: Boolean): Builder {

            this.isAnimationEnabled = enable
            return this
        }

        fun homeClass(cls: Class<*>): Builder {

            this.homeClass = cls
            return this
        }

        fun build(): InitializationParams {

            if (fragmentManager == null || contentFrame == null || screenManager == null) {
                throw IllegalStateException("All parameters are mandatory")
            }

            return InitializationParams(this)
        }


    }

}