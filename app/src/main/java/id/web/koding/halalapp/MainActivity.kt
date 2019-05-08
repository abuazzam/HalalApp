package id.web.koding.halalapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: ListProdukAdapter
    private lateinit var mListProduk: ArrayList<Produk>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mListProduk = ArrayList<Produk>()
        mAdapter = ListProdukAdapter(mListProduk)

        recyeler_view.apply {
            var mLayoutManager = LinearLayoutManager(this@MainActivity)
            layoutManager = mLayoutManager as RecyclerView.LayoutManager?
            addItemDecoration(DividerItemDecoration(context, mLayoutManager.orientation))
            adapter = mAdapter
        }

        search_view.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                loadData(query)
                return false
            }

        })
    }

    private fun loadData(kw: String?) {
        Log.d("KW_DATA", kw)
        try {
            var url = "http://api.agusadiyanto.net/halal/?menu=nama_produk&query=$kw"
            val task = ClientAsyncTask(this, object : ClientAsyncTask.OnPostExecuteListener {
                override fun onPostExecute(result: String) {

                    Log.d("HalalData", result)
                    try {
                        val jsonObj = JSONObject(result)
                        val jsonArray = jsonObj.getJSONArray("data")

                        mListProduk.clear()
                        for (i in 0 until jsonArray.length()) {
                            val obj = jsonArray.getJSONObject(i)
                            val produk = Produk()
                            produk.nama = obj.getString("nama_produk")
                            produk.no_sertifikat = obj.getString("nomor_sertifikat")
                            produk.produsen = obj.getString("nama_produsen")
                            produk.berlaku = obj.getString("berlaku_hingga")
                            mListProduk.add(produk)
                        }
                        mAdapter.notifyDataSetChanged()

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            })
            task.progressBar = this.progress_bar
            task.execute(url)
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
