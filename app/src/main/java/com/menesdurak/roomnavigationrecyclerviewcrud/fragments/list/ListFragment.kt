package com.menesdurak.roomnavigationrecyclerviewcrud.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.menesdurak.roomnavigationrecyclerviewcrud.R
import com.menesdurak.roomnavigationrecyclerviewcrud.viewmodel.UserViewModel
import com.menesdurak.roomnavigationrecyclerviewcrud.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        //RecyclerView
        val adapter = ListAdapter()
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //UserViewModel
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })

        //Navigating from ListFragment to AddFragment
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        //Add menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteAllUsers()
            Toast.makeText(requireContext(),
                "Succesfully removed all.",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") {_, _ ->
            builder.setTitle("Delete all?")
            builder.setMessage("Are you sure you want to delete all?")
            builder.create().show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}