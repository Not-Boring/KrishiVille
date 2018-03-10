package ai.notboring.krishiville

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import android.support.v7.widget.LinearLayoutManager





/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class ProductFragment : Fragment() {
    private val mTAG = "ai.nb.kv"

    private var mListener: OnListFragmentInteractionListener? = null
    private var mAdapter: FirestoreRecyclerAdapter<ProductItem, ProductItemHolder>? = null
    private var mProductList: RecyclerView? = null

    private val query = FirebaseFirestore.getInstance()
            .collection("products")
    private val options = FirestoreRecyclerOptions.Builder<ProductItem>()
            .setQuery(query, ProductItem::class.java)
            .build()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_product_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.context

            val linearLayoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
            )

            mProductList = view.findViewById(R.id.list)
            mProductList?.layoutManager = linearLayoutManager

            mAdapter = object : FirestoreRecyclerAdapter<ProductItem, ProductItemHolder>(options) {
                override fun onBindViewHolder(holder: ProductItemHolder, position: Int, model: ProductItem) {
                    // Bind the ProductItem object to the ProductItemHolder
                    holder.title.text = model.name

                    holder.itemView.setOnClickListener {
                        Log.i(mTAG, "Clicked on ${holder.title.text}")
                    }
                }

                override fun onCreateViewHolder(group: ViewGroup, i: Int): ProductItemHolder {
                    // Create a new instance of the ViewHolder, in this case we are using a custom
                    // layout for each item
                    val view = LayoutInflater.from(group.context)
                            .inflate(R.layout.fragment_product, group, false)

                    return ProductItemHolder(view)
                }

                override fun onError(e: FirebaseFirestoreException) {
                    Log.e("error", e.message)
                }
            }
        }

        mAdapter?.notifyDataSetChanged()
        mProductList?.adapter = mAdapter

        return view
    }

    class ProductItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var title: TextView = itemView.findViewById(R.id.name) as TextView

    }

    override fun onStart() {
        super.onStart()

        mAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()

        mAdapter!!.stopListening()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: ProductItem)
    }

    companion object {

        // TODO: Customize parameter initialization
        fun newInstance(): ProductFragment {
            return ProductFragment()
        }
    }
}
