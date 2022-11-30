package com.menesdurak.roomnavigationrecyclerviewcrud.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.menesdurak.roomnavigationrecyclerviewcrud.R
import com.menesdurak.roomnavigationrecyclerviewcrud.databinding.FragmentUpdateBinding
import com.menesdurak.roomnavigationrecyclerviewcrud.fragments.model.User
import com.menesdurak.roomnavigationrecyclerviewcrud.viewmodel.UserViewModel

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.updateFirstNameEt.setText(args.currentUser.firstName)
        binding.updateLastNameEt.setText(args.currentUser.lastName)
        binding.updateAgeEt.setText(args.currentUser.age.toString())

        binding.btnUpdate.setOnClickListener {
            updateItem()
        }

        //Add menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val firstName = binding.updateFirstNameEt.text.toString()
        val lastName = binding.updateLastNameEt.text.toString()
        val age = Integer.parseInt(binding.updateAgeEt.text.toString())

        if((inputCheck(firstName, lastName, binding.updateAgeEt.text))) {
            //Create User object
            val updatedUser = User(args.currentUser.id, firstName, lastName, age)
            //Update current User
            mUserViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Updated succesfully!", Toast.LENGTH_SHORT).show()
            //Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(),
                "Please fill out all places!",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(),
                "Succesfully removed ${args.currentUser.firstName}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") {_, _ ->
            builder.setTitle("Delete ${args.currentUser.firstName}?")
            builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}?")
            builder.create().show()
        }
    }
}