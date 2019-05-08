package id.web.koding.halalapp

import android.graphics.Movie
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_layout.view.*

class ListProdukAdapter(private val listProduk: ArrayList<Produk>) : RecyclerView.Adapter<ListProdukAdapter.ProdukHolder>() {

    class ProdukHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private var mProduk = view.tv_nama
        private var mSertifikat = view.tv_no_sertifikat
        private var mProdusen = view.tv_produsen
        private var mBerlaku = view.tv_berlaku


        fun bind(produk: Produk) {
            mProduk.text = produk.nama
            mSertifikat.text = produk.no_sertifikat
            mProdusen.text = produk.produsen
            mBerlaku.text = produk.berlaku
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListProdukAdapter.ProdukHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProdukHolder(inflater.inflate(R.layout.item_layout, parent, false))
    }

    override fun getItemCount() = listProduk.size

    override fun onBindViewHolder(holder: ListProdukAdapter.ProdukHolder, position: Int) {
        val produk: Produk = listProduk[position]
        holder.bind(produk)
    }
}