package com.example.sampleproject.views

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sampleproject.R
import com.example.sampleproject.adapter.GridViewAdapter
import com.example.sampleproject.constants.Constants.Companion.ADAPTER_NOTIFY_DELAY
import com.example.sampleproject.constants.Constants.Companion.ITEM_MOVE_DELAY
import com.example.sampleproject.constants.Constants.Companion.NUM_COLUMNS_LANDSCAPE
import com.example.sampleproject.constants.Constants.Companion.NUM_COLUMNS_POTRAIT
import com.example.sampleproject.interactors.IMainView
import com.example.sampleproject.singleton.DataHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IMainView {

    private lateinit var gridViewAdapter: GridViewAdapter
    private var dataList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initializeToolBar()
        initializeData()
        initializeRecyclerView()
    }

    private fun initializeToolBar() {
        setSupportActionBar(toolBar)
        toolBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolBar?.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initializeData() {
        if (DataHolder.dataHolder.isEmpty()) {
            for (i in 10..40) {
                dataList.add(i.toString())
            }
            DataHolder.dataHolder = dataList
        } else {
            dataList = DataHolder.dataHolder
        }
    }

    private fun initializeRecyclerView() {
        gridViewAdapter = GridViewAdapter(dataList, this)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvGrid?.layoutManager = GridLayoutManager(
                this,
                NUM_COLUMNS_POTRAIT, GridLayoutManager.VERTICAL, false
            )
        } else if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvGrid?.layoutManager = GridLayoutManager(
                this,
                NUM_COLUMNS_LANDSCAPE, GridLayoutManager.VERTICAL, false
            )
        }
        rvGrid?.apply {
            hasFixedSize()
            val space = resources.getDimensionPixelSize(R.dimen.dimen_10dp)
            addItemDecoration(ItemDecoration(space))
            adapter = gridViewAdapter
        }
    }

    override fun onAnimationEnd(position: Int) {
        dataList.removeAt(position)
        DataHolder.dataHolder = dataList
        moveItems(position + 1)
    }

    private fun moveItems(position: Int) {
        if (position == gridViewAdapter.itemCount - 1) {
            Handler().postDelayed({
                gridViewAdapter.notifyDataSetChanged()
            }, ADAPTER_NOTIFY_DELAY)

            return
        }
        Handler().postDelayed(
            {
                gridViewAdapter.notifyItemMoved(position, position - 1)
                moveItems(position + 1)
            }, ITEM_MOVE_DELAY
        )
    }

}
