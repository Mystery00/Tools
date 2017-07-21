package com.mystery0.tools.FloatMenu

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.TextView

import com.mystery0.tools.R

import java.util.ArrayList

class iFloatMenu(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs)
{
	private val button: FloatingActionButton
	private val menuList: MutableList<Menu>
	private var number: Int = 0
	private val animation_in: Animation
	private val animation_out: Animation
	private val button_in: Animation
	private val button_out: Animation
	private var isOpen = false

	private inner class Menu internal constructor(internal var fullView: View)
	{
		internal var button: FloatingActionButton = fullView.findViewById<FloatingActionButton>(R.id.fab)
		internal var text: TextView = fullView.findViewById<TextView>(R.id.text)
	}

	init
	{
		menuList = ArrayList<Menu>()
		number = 4
		LayoutInflater.from(context).inflate(R.layout.mystery0_i_float_menu, this)
		button = findViewById<FloatingActionButton>(R.id.fab_menu)
		menuList.add(Menu(findViewById<View>(R.id.item1)))
		menuList.add(Menu(findViewById<View>(R.id.item2)))
		menuList.add(Menu(findViewById<View>(R.id.item3)))
		menuList.add(Menu(findViewById<View>(R.id.item4)))
		menuList.add(Menu(findViewById<View>(R.id.item5)))

		animation_in = AnimationUtils.loadAnimation(context, R.anim.mystery0_float_menu_transform_in)
		animation_out = AnimationUtils.loadAnimation(context, R.anim.mystery0_float_menu_transform_out)
		button_in = AnimationUtils.loadAnimation(context, R.anim.mystery0_float_button_transform_in)
		button_out = AnimationUtils.loadAnimation(context, R.anim.mystery0_float_button_transform_out)

		monitor()
	}

	fun setNumber(number: Int)//设置菜单数量
	{
		if (number <= 0 || number > 5)
			throw NumberFormatException("Number error! Please make number>0 and number<=5! ")
		else
		{
			this.number = number
		}
	}

	fun setIcons(ids: IntArray)
	{
		if (ids.size > number)
			throw NumberFormatException("Number error! Please check the value! ")
		for (i in 0..number - 1)
		{
			menuList[i].button.setImageResource(ids[i])
		}
	}

	fun setTexts(texts: Array<String>)
	{
		if (texts.size > number)
			throw NumberFormatException("Number error! Please check the value! ")
		for (i in 0..number - 1)
		{
			menuList[i].text.text = texts[i]
		}
	}

	fun setIcon(resId: Int)
	{
		button.setImageResource(resId)
	}

	fun setMenuClickListener(menuClick: MenuClick)
	{
		setMenuListVisibility(View.GONE)
		for (i in 0..number - 1)
		{
			val finalI = i
			menuList[i].fullView.setOnClickListener { menuClick.menuClick(finalI) }
		}
	}

	fun setMenuVisibility(visibility: Int)//设置菜单可见性
	{
		when (visibility)
		{
			View.GONE ->
			{
				isOpen = false
				button.startAnimation(button_out)
				setMenuListVisibility(View.GONE)
			}
			View.VISIBLE ->
			{
				isOpen = true
				button.startAnimation(button_in)
				setMenuListVisibility(View.VISIBLE)
			}
		}
	}

	private fun monitor()
	{
		button.setOnClickListener {
			if (isOpen)
			{
				isOpen = false
				button.startAnimation(button_out)
				setMenuListVisibility(View.GONE)
			}
			else
			{
				isOpen = true
				button.startAnimation(button_in)
				setMenuListVisibility(View.VISIBLE)
			}
		}
	}

	private fun setMenuListVisibility(visibility: Int)
	{
		for (i in 0..number - 1)
		{
			setViewVisibility(menuList[i].fullView, visibility, i)
		}
	}

	private fun setViewVisibility(view: View, visibility: Int, index: Int)
	{
		if (index < number)
		{
			view.visibility = visibility
			when (visibility)
			{
				View.GONE -> view.startAnimation(animation_out)
				View.VISIBLE -> view.startAnimation(animation_in)
			}
		}
		else
		{
			view.visibility = View.GONE
		}
	}
}