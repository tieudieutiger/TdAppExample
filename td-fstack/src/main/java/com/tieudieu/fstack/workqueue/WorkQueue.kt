package com.tieudieu.fstack.workqueue

import com.tieudieu.utils.DebugLog
import java.util.*

class WorkQueue {

    companion object {
        var NUMBER_OF_CORE: Int = Runtime.getRuntime().availableProcessors()
    }

    private var maxThread: Int = 0
    private lateinit var queue: LinkedList<Runnable>

    private var isRunning = false


    /*private inner class WorkerThread : Thread() {

        override fun run() {
            var r: Runnable
            while (isRunning) {
                synchronized(queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait()
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

                    }
                    r = queue.removeFirst()
                    // start new Worker if need
                    newWorkerIfNeed()
                    try {
                        DebugLog.d("xxx-new-work-run")
                        r.run()
                    } catch (e: RuntimeException) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }*/


}