package ai.notboring.krishiville

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import ai.notboring.krishiville.ProductFragment.OnListFragmentInteractionListener
import ai.notboring.krishiville.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
 class MyProductRecyclerViewAdapter(private val mValues:List<DummyItem>, private val mListener:OnListFragmentInteractionListener?):RecyclerView.Adapter<MyProductRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolder {
        val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.fragment_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position:Int) {
        holder.mItem = mValues[position]
        holder.mIdView.text = mValues[position].id
        holder.mContentView.text = mValues[position].content

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mItem!!)
        }
    }

    override fun getItemCount():Int {
        return mValues.size
    }

    inner class ViewHolder( val mView:View):RecyclerView.ViewHolder(mView) {
        val mIdView:TextView = mView.findViewById<View>(R.id.id) as TextView
        val mContentView:TextView = mView.findViewById<View>(R.id.content) as TextView
        var mItem:DummyItem? = null

        override fun toString():String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
