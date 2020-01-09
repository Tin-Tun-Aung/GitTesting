package com.padcmyanmar.padc9.indoorplants.component

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SmartScrollListener(private val mSmartScrollListener: OnSmartScrollListener) : RecyclerView.OnScrollListener() {

    private var isListEndReached = false
    private var isNetworkCallStart = false

    interface OnSmartScrollListener {
        fun onListEndReach()
    }

    override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(rv, dx, dy)

        val visibleItemCount = rv.layoutManager?.childCount
        val totalItemCount = rv.layoutManager?.itemCount
        val pastVisibleItems = (rv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (visibleItemCount != null) {
            if (visibleItemCount + pastVisibleItems < totalItemCount!! && !isNetworkCallStart) {
                isListEndReached = false
            }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, scrollState: Int) {
        super.onScrollStateChanged(recyclerView, scrollState)
        if (scrollState == RecyclerView.SCROLL_STATE_IDLE
            && (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() === recyclerView.adapter?.itemCount!! - 1
            && !isListEndReached
        ) {
            isListEndReached = true
            isNetworkCallStart = true
            mSmartScrollListener.onListEndReach()
        }
    }

    public fun networkCallFinished(){
        isNetworkCallStart = false
    }
}
