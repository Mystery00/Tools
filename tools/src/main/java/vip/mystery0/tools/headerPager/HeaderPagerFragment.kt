package vip.mystery0.tools.headerPager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import vip.mystery0.tools.R
import vip.mystery0.tools.logs.Logs

/**
 * Created by myste.
 */
class HeaderPagerFragment : Fragment()
{
	private val TAG = "HeaderPagerFragment"
	private lateinit var header: Header
	private var position = -1

	companion object
	{
		fun newInstance(header: Header,
						position: Int): HeaderPagerFragment
		{
			val bundle = Bundle()
			bundle.putSerializable("data", header)
			bundle.putInt("position", position)
			val fragment = HeaderPagerFragment()
			fragment.arguments = bundle
			return fragment
		}
	}

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		header = arguments.getSerializable("data") as Header
		position = arguments.getInt("position")
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View?
	{
		Logs.i(TAG, "onCreateView: ")
		val view = inflater.inflate(R.layout.item_fragment_header_pager, container, false)
		val imageView: ImageView = view.findViewById(R.id.imageView)
		val textViewTitle: TextView = view.findViewById(R.id.textView_title)
		val textViewSubTitle: TextView = view.findViewById(R.id.textView_subtitle)
		Glide.with(activity).load(header.imgPath).into(imageView)
		textViewTitle.text = header.title
		textViewSubTitle.text = header.subtitle
		return view
	}
}