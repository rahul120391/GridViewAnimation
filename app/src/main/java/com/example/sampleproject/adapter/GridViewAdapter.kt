package com.example.sampleproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject.R
import com.example.sampleproject.animationutils.AnimationUtils
import com.example.sampleproject.extensions.gone
import com.example.sampleproject.extensions.visible
import com.example.sampleproject.interactors.IMainView
import kotlinx.android.synthetic.main.grid_row_item.view.*

class GridViewAdapter(dataList: ArrayList<String>, private val iMainView: IMainView) :
    RecyclerView.Adapter<GridViewAdapter.ViewHolder>() {
    var data = dataList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.grid_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtItem.text = data[position]
        holder.itemView.visible()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItem: AppCompatTextView = itemView.txtContent

        init {

            itemView.setOnClickListener {
                view->
                AnimationUtils.startFlipAnimation(view).doOnEnd {
                    view.gone()
                    iMainView.onAnimationEnd(adapterPosition)
                }
            }
        }
    }
}