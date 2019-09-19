package com.saucefan.stuff.readinglist.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment

import com.saucefan.stuff.readinglist.R
import com.saucefan.stuff.readinglist.databinding.FragmentEditBinding
import com.saucefan.stuff.readinglist.model.Book
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.getNewID
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.titleChanged
import com.saucefan.stuff.readinglist.viewmodel.BookRepo.titleChangedBool
import kotlinx.android.synthetic.main.fragment_edit.*
import timber.log.Timber
import timber.log.Timber.i

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_NAME = "param1"
const val EDIT_BOOK = "param2"
private lateinit var binding: FragmentEditBinding

class EditFragment : DialogFragment() {


    private var book: Book? = null
    private var listener: OnFragmentInteractionListener? = null
    fun returnData(book:Book):Book {

        if (!et_title.text.isNullOrBlank()){
            //if this is the case, we'll need to delete the old file, so...
            titleChangedBool=true
            book.title=et_title.text.toString()

        } else Timber.e("title blank")
        if (!et_rtr.text.isNullOrBlank()){
            book.reasonToRead=et_rtr.text.toString()
        }else Timber.e("rtr blank")
        if(chkbox.isChecked){
            book.hasBeenRead=true
        } else book.hasBeenRead=false
        return book

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            book = it.getSerializable(EDIT_BOOK) as Book
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit,container,false)
        binding.model=book
        val view = binding.root
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        book?.let {
            et_rtr.hint =it.reasonToRead
            et_title.hint=it.title
            tv_id.text=it.id.toString()
            if(it.hasBeenRead==true) {
                fragcl.background = resources.getDrawable(R.color.bgHightlight)
                chkbox.setChecked(true)
                chkbox.invalidate()
            }else {
                fragcl.background=resources.getDrawable(R.color.bgRegular)
                chkbox.setChecked(false)
                chkbox.invalidate()
            }
            chkbox.setOnClickListener {
                if(book?.hasBeenRead as Boolean) {
                 //   chkbox.background=resources.getDrawable(R.color.bgRegular)
                    fragcl.background=resources.getDrawable(R.color.bgRegular)
                    //chkbox.isChecked=false
                   // chkbox.invalidate()
                    book?.hasBeenRead=false //?: i("failure at chkbox")
                }else {
                //    chkbox.background = resources.getDrawable(R.color.bgHightlight)
                    fragcl.background = resources.getDrawable(R.color.bgHightlight)
                   // chkbox.isChecked=true
                    //chkbox.invalidate()
                    book?.hasBeenRead=true// ?: i("failure at chkbox")
                }
            }
        } ?: Toast.makeText(view.context,"book ain't good like",Toast.LENGTH_SHORT).show(); Timber.e("$book book is empty or bad")
   btn_submit.setOnClickListener(){
       listener?.onFragSave(returnData(book as Book)) ?:Timber.e("THE LISTENER AIN'T A WORKING")
   }
        btn_delete.setOnClickListener{
            listener?.onDelete(book as Book)
            dismiss()
        }

    }

    fun onButtonPressed(book: Book) {
        listener?.onFragSave(book)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        fun onFragSave(book: Book)
        fun onDelete(book:Book)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: Book) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, param1)
                    putSerializable(EDIT_BOOK, param2)
                }
            }
    }
}
