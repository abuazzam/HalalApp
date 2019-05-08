package id.web.koding.halalapp

import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ClientAsyncTask constructor(private val mContext: Context, postExecuteListener: OnPostExecuteListener) :
    AsyncTask<String, String, String>() {
    val CONNECTON_TIMEOUT_MILLISECONDS = 60000
    var progressBar: ProgressBar? = null
    private val mPostExecuteListener : OnPostExecuteListener = postExecuteListener

    interface OnPostExecuteListener {
        fun onPostExecute(result: String)
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        if (mPostExecuteListener != null) {
            mPostExecuteListener.onPostExecute(result)
            if (progressBar != null) {
                progressBar!!.visibility = View.GONE
            }
        }
    }

    override fun onPreExecute() {
        super.onPreExecute()
        if (progressBar != null) {
            progressBar!!.visibility = View.VISIBLE
        }
    }

    override fun doInBackground(vararg urls: String?): String {
        var urlConnection: HttpURLConnection? = null

        try {
            val url = URL(urls[0])

            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connectTimeout = CONNECTON_TIMEOUT_MILLISECONDS
            urlConnection.readTimeout = CONNECTON_TIMEOUT_MILLISECONDS

            var inString = streamToString(urlConnection.inputStream)

            return inString
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect()
            }
        }

        return ""
    }

    fun streamToString(inputStream: InputStream): String {

        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var result = ""

        try {
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    result += line
                }
            } while (line != null)
            inputStream.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return result
    }

}